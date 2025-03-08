package org.utwente.cs.ds.semi.lod.ieee.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.utwente.cs.ds.semi.lod.ieee.model.IeeeData;
import org.utwente.cs.ds.semi.lod.ieee.model.support.*;
import org.utwente.cs.ds.semi.lod.ieee.scraping.exception.ApplicationException;
import org.utwente.cs.ds.semi.lod.ieee.scraping.request.ApiRequest;
import org.utwente.cs.ds.semi.lod.ieee.scraping.service.QueryManager;
import org.utwente.cs.ds.semi.lod.utils.RdfSchemaConstants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import static org.utwente.cs.ds.semi.lod.ieee.scraping.service.IEEEService.client;

public class IeeeDataToRdfConversionService {

    private final static Logger LOGGER = Logger.getLogger(IeeeDataToRdfConversionService.class.getName());

    public static IeeeResponse getIeeeDataFromApi(String topic){

        IeeeResponse ieeeResponse = new IeeeResponse();
        
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setDataFormat("json");
        
        //TO DO - Make getting the topic dynamic by UI
        apiRequest.setArticleTitle(topic);
        String baseUrl = QueryManager.buildDefaultQuery(apiRequest);
        String url = baseUrl.concat("&start_record=0&max_records=200");
        LOGGER.info("Url generated:" + url);
        int callCounter = 0;
        int noOfRecords = 0;
        int totalRecords = 1;
        while(callCounter<1000 && noOfRecords <= totalRecords){
            HttpResponse response = makeApiCall(url);
            String responseBody = (String) response.body();
            int responseStatusCode = response.statusCode();
            LOGGER.info("Response Status Code:" + responseStatusCode);
            if(responseStatusCode != 200){
                throw new ApplicationException(responseBody);
            }else{
                ObjectMapper objectMapper = new ObjectMapper();
                IeeeResponse ieeeResponseChunk = null;
                try {
                    ieeeResponseChunk = objectMapper.readValue(responseBody, IeeeResponse.class);
                    //System.out.println(ieeeResponseChunk.getTotalRecords());
                    ieeeResponse.setTotalRecords(ieeeResponseChunk.getTotalRecords());
                    ieeeResponse.setTotalSearched(ieeeResponseChunk.getTotalSearched());
                    if(ieeeResponse.getIeeeDataObjects()==null){
                        List<IeeeData> ieeeDataList = new ArrayList<>();
                        ieeeResponse.setIeeeDataObjects(ieeeDataList);
                    }
                    ieeeResponse.getIeeeDataObjects().addAll(ieeeResponseChunk.getIeeeDataObjects());
                    callCounter = callCounter + 1;
                    noOfRecords = noOfRecords + 200;
                    totalRecords = ieeeResponseChunk.getTotalRecords();

                    url = baseUrl.concat("&start_record="+noOfRecords+"&max_records=200");
                    LOGGER.info("Url generated:" + url);
                } catch (JsonProcessingException e) {
                    ieeeResponse = null;
                    throw new RuntimeException(e);
                }
            }
        }
        return ieeeResponse;

    }

    private static HttpResponse makeApiCall(String url){
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public static boolean convertIeeeAPIResponseToRDF(IeeeResponse ieeeResponse, String fileName, AtomicLong publicationCount,AtomicLong authorCount,AtomicLong isbnCount) {
        Boolean rdfDataCreated = true;
        Model model = ModelFactory.createDefaultModel();
        
        String ieeeURI = RdfSchemaConstants.IEEE_BASE_URI;
        Property rdfTypeProperty = RDF.type;

        List<IeeeData> ieeeDataList = ieeeResponse.getIeeeDataObjects();
        try{
            for(IeeeData ieeeData: ieeeDataList){

                Resource publicationResource = model.createResource(ieeeURI + "Publication"+publicationCount);
                publicationResource.addProperty(rdfTypeProperty, model.createResource(ieeeURI+"Publication"));

                for (Field field : ieeeData.getClass().getDeclaredFields()) {
                    field.setAccessible(true);

                    switch (field.getType().getName()){
                        case("java.lang.String"):
                            publicationResource.addProperty(model.createProperty(ieeeURI, field.getName()), nullChecks(String.valueOf(field.get(ieeeData))));
                            break;
                        case("java.lang.Integer"):
                            publicationResource.addProperty(model.createProperty(ieeeURI, field.getName()), nullChecks(String.valueOf(field.get(ieeeData))));
                            break;
                        case("org.utwente.cs.ds.semi.lod.ieee.model.support.Authors"):
                            Property authorProperty = model.createProperty(ieeeURI, "author");
                            List<Author> authors = ieeeData.getAuthors().getAuthors();
                            for (Author author : authors) {
                                Resource authorResource = model.createResource(ieeeURI + "Author" + authorCount);
                                authorResource.addProperty(rdfTypeProperty, model.createResource(ieeeURI + "Author"));
                                authorResource
                                        .addProperty(model.createProperty(ieeeURI, "name"), nullChecks(author.getFullName()))
                                        .addProperty(model.createProperty(ieeeURI, "affiliation"), nullChecks(author.getAffiliation()));
                                AuthorAffiliations authorAffiliations = author.getAuthorAffiliations();
                                if(authorAffiliations!=null && authorAffiliations.getAuthorAffiliation()!=null){
                                    for(String authorAffiliation : authorAffiliations.getAuthorAffiliation()){
                                        authorResource.addProperty(model.createProperty(ieeeURI, "affiliation"), nullChecks(authorAffiliation));
                                    }
                                }
                                publicationResource.addProperty(authorProperty, authorResource);
                                authorCount.set(authorCount.get()+1);
                            }
                            break;
                        case("org.utwente.cs.ds.semi.lod.ieee.model.support.Isbns"):
                            Property isbnProperty = model.createProperty(ieeeURI, "isbn");
                            if(ieeeData.getIsbns()!=null && ieeeData.getIsbns().getIsbns()!=null){
                                List<Isbn> isbns = ieeeData.getIsbns().getIsbns();
                                for (Isbn isbn : isbns) {
                                    Resource isbnResource = model.createResource(ieeeURI + "Isbn" + isbnCount);
                                    isbnResource.addProperty(rdfTypeProperty, model.createResource(ieeeURI + "Isbn"));
                                    isbnResource.addProperty(RDF.type, model.createResource(ieeeURI + "Isbn" + isbnCount))
                                            .addProperty(model.createProperty(ieeeURI, "format"), nullChecks(isbn.getFormat()))
                                            .addProperty(model.createProperty(ieeeURI, "value"), nullChecks(isbn.getValue()))
                                            .addProperty(model.createProperty(ieeeURI,"type"),nullChecks(isbn.getIsbnType()));

                                    publicationResource.addProperty(isbnProperty, isbnResource);
                                    isbnCount.set(isbnCount.get()+1);
                                }
                            }

                            break;
                        default:
                            break;






                    }
                }

                publicationCount.set(publicationCount.get()+1);
            }
        } catch (Exception e) {
            rdfDataCreated = false;
            throw new RuntimeException(e);
        }
        try (FileOutputStream fos = new FileOutputStream(fileName,true)) {
            model.write(fos, "N-TRIPLE");
            System.out.println("RDF model written to output.nt successfully.");
        } catch (IOException e) {
            rdfDataCreated = false;
            System.err.println("Error writing RDF model to file: " + e.getMessage());
        }

        return rdfDataCreated;
    }

    private static String nullChecks(String value){
        if(value == null || String.valueOf(value).equalsIgnoreCase("null")){
            return "";
        }
        return value;
    }
}
