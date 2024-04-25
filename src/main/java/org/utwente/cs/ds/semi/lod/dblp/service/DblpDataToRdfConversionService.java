package org.utwente.cs.ds.semi.lod.dblp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import org.apache.jena.query.*;
import org.apache.jena.query.Query;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.utwente.cs.ds.semi.lod.arxiv.model.Entry;
import org.utwente.cs.ds.semi.lod.arxiv.model.Feed;
import org.utwente.cs.ds.semi.lod.arxiv.service.ArxivDataToRdfConversionService;
import org.utwente.cs.ds.semi.lod.arxiv.utils.ArxivConstants;
import org.utwente.cs.ds.semi.lod.dblp.model.*;
import org.utwente.cs.ds.semi.lod.dblp.utils.DblpConstants;
import org.utwente.cs.ds.semi.lod.ieee.model.IeeeData;
import org.utwente.cs.ds.semi.lod.ieee.model.support.AuthorAffiliations;
import org.utwente.cs.ds.semi.lod.ieee.model.support.IeeeResponse;
import org.utwente.cs.ds.semi.lod.ieee.model.support.Isbn;
import org.utwente.cs.ds.semi.lod.ieee.scraping.exception.ApplicationException;
import org.utwente.cs.ds.semi.lod.utils.RdfSchemaConstants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class DblpDataToRdfConversionService {

    private final static Logger LOGGER = Logger.getLogger(DblpDataToRdfConversionService.class.getName());

    public static Hits getDblpDataFromApi(String topic) {

        Hits hits = new Hits();

        String baseUrl = DblpConstants.DBLP_BASE_URL + "?q=" + topic ;
        String url = baseUrl.concat("&format=json&h=10&f=0");

        //TO DO - Make getting the topic dynamic by UI

        LOGGER.info("Url generated:" + url);

        LOGGER.info("Url generated:" + url);
        int callCounter = 0;
        int noOfRecords = 0;
        int totalRecords = 1;

        int responseCode = 0;
        HttpURLConnection con = null;
        while(callCounter <1 && noOfRecords <= totalRecords ){
            try{
                URL obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");

                responseCode = con.getResponseCode();

            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(responseCode != 200){
                System.out.println(responseCode);
            }else{

                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Authors.class, new AuthorsDeserializer())
                            .create();


                    Result result = gson.fromJson(response.toString(), Result.class);
                    Hits hitsChunk = result.getResult().getHits();
                    if(hits.getHit()==null){
                        List<Hit> hitsList = new ArrayList<>();
                        hits.setHit(hitsList);
                    }
                    hits.getHit().addAll(hitsChunk.getHit());
                    callCounter = callCounter + 1;
                    noOfRecords = noOfRecords + 1000;
                    totalRecords = Integer.parseInt(hitsChunk.getTotal());
                    url = baseUrl.concat("&format=json&h=1000&f="+noOfRecords);
                    System.out.println("URL: "+url);
                } catch (JsonProcessingException e) {
                    hits = null;
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    hits = null;
                    throw new RuntimeException(e);
                }
            }
        }


        return hits;

    }

    public static boolean convertDblpAPIResponseToRDF(Hits hits, String fileName, AtomicLong publicationCount, AtomicLong authorCount) {
        Boolean rdfDataCreated = true;
        Model model = ModelFactory.createDefaultModel();

        String dblpBaseUri = RdfSchemaConstants.DBLP_BASE_URI;
        Property rdfTypeProperty = RDF.type;

        List<Hit> dblpData = hits.getHit();
        long isbnCount = 1;
        try{
            for(Hit entry: dblpData){

                Resource publicationResource = model.createResource(dblpBaseUri + "Publication"+publicationCount);
                publicationResource.addProperty(rdfTypeProperty, model.createResource(dblpBaseUri+"Publication"));

                for (Field field : entry.getClass().getDeclaredFields()) {
                    field.setAccessible(true);

                    switch (field.getType().getName()){
                        case("java.lang.String"):
                            publicationResource.addProperty(model.createProperty(dblpBaseUri, field.getName()), nullChecks(String.valueOf(field.get(entry))));
                            break;
                        case("org.utwente.cs.ds.semi.lod.dblp.model.Info"):

                            Field[] declaredFieldsInfo = field.get(entry).getClass().getDeclaredFields();
                            for(Field infoField : declaredFieldsInfo){
                                infoField.setAccessible(true);

                                switch (infoField.getType().getName()){
                                    case("java.lang.String"):
                                        publicationResource.addProperty(model.createProperty(dblpBaseUri, infoField.getName()), nullChecks(String.valueOf(infoField.get(field.get(entry)))));
                                        break;
                                    case("org.utwente.cs.ds.semi.lod.dblp.model.Authors"):
                                        if(entry.getInfo().getAuthors()!=null && entry.getInfo().getAuthors().getAuthor()!=null){
                                            Property authorProperty = model.createProperty(dblpBaseUri, "author");
                                            List<Author> authors = entry.getInfo().getAuthors().getAuthor();
                                            for (Author author : authors) {
                                                Resource authorResource = model.createResource(dblpBaseUri + "Author" + authorCount);
                                                authorResource.addProperty(rdfTypeProperty, model.createResource(dblpBaseUri + "Author"));
                                                authorResource
                                                        .addProperty(model.createProperty(dblpBaseUri, "name"), nullChecks(author.getText()))
                                                        .addProperty(model.createProperty(dblpBaseUri, "pid"), nullChecks(author.getPid()));
                                                String name = nullChecks(author.getText());
                                                if(!name.isBlank() && !name.isEmpty()){
                                                    String primaryAffiliation = getPrimaryAffiliation(name);
                                                    if(primaryAffiliation!=null && !String.valueOf(primaryAffiliation).equalsIgnoreCase("null")
                                                    && !String.valueOf(primaryAffiliation).isEmpty() && !String.valueOf(primaryAffiliation).isBlank()){
                                                        authorResource.addProperty(model.createProperty(dblpBaseUri,"primaryAffiliation"),primaryAffiliation);
                                                    }
                                                }
                                                publicationResource.addProperty(authorProperty, authorResource);
                                                authorCount.set(authorCount.get()+1);
                                            }
                                        }

                                        break;
                                    default:
                                        break;
                                }
                            }
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

//    private static String getPrimaryAffiliation(String pid) throws InterruptedException {
//
//        Thread.sleep(10);
//
//        Model model = ModelFactory.createDefaultModel();
//
//        // RDF file URL
//        String rdfURL = "https://dblp.org/pid/" + pid + ".nt";
//
//        // Read RDF data from the URL
//        model.read(rdfURL, "N-TRIPLES");
//        String primaryAffiliation = null;
//        String queryString =
//                "SELECT ?affiliation\n" +
//                        "WHERE {\n" +
//                        "    <https://dblp.org/pid/" + pid + "> " + "<https://dblp.org/rdf/schema#primaryAffiliation> ?affiliation .\n" +
//                        "}";
//        Query query = QueryFactory.create(queryString);
//
//        // Execute the query
//        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
//            // Get results
//            ResultSet results = qexec.execSelect();
//
//            while (results.hasNext()) {
//                primaryAffiliation = String.valueOf(results.next().get("affiliation"));
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return primaryAffiliation;
//    }

    private static String getPrimaryAffiliation(String name) throws InterruptedException {


        Thread.sleep(1000);
        String primaryAffiliation = null;

        String url = "https://dblp.org/search/author/api?format=json&q=" + name.replace(" ","%20");
        try{
            HttpURLConnection con = null;
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            if(responseCode != 200){
                System.out.println(responseCode);
            }else{

                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(response.toString());
                    JsonNode authorData = jsonNode.get("result").get("hits").get("hit").get(0).get("info");
                    if(authorData.get("notes")!=null){
                        if(authorData.get("notes").get("note")!=null){
                            if(authorData.get("notes").get("note").get(0)!=null){
                                primaryAffiliation = String.valueOf(authorData.get("notes").get("note").get(0).get("text"));
                            }
                        }
                    }

                } catch (JsonProcessingException e) {

                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return primaryAffiliation;
    }
}
