package org.utwente.cs.ds.semi.lod.arxiv.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.utwente.cs.ds.semi.lod.arxiv.model.Author;
import org.utwente.cs.ds.semi.lod.arxiv.model.Entry;
import org.utwente.cs.ds.semi.lod.arxiv.model.Feed;
import org.utwente.cs.ds.semi.lod.arxiv.utils.ArxivConstants;
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

public class ArxivDataToRdfConversionService {

    private final static Logger LOGGER = Logger.getLogger(ArxivDataToRdfConversionService.class.getName());

    public static Feed getArxivDataFromApi(String topic) {

        Feed feed = new Feed();

        String baseUrl = ArxivConstants.ARXIV_BASE_URL + "=ti:\\\"" + topic + "\\\"";

        String url = baseUrl.concat("&start=0&max_results=1000");

        //TO DO - Make getting the topic dynamic by UI

        LOGGER.info("Url generated:" + url);
        int callCounter = 0;
        int noOfRecords = 0;
        int totalRecords = 1;





        while(callCounter< 1000 && noOfRecords <= totalRecords){
            int responseCode = 0;
            HttpURLConnection con = null;
            try{
                URL obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
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
                throw new ApplicationException();
            }else{

                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    JAXBContext jaxbContext = JAXBContext.newInstance(Feed.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();


                    // Deserialize XML string into Feed object
                    Feed feedChunk = (Feed) jaxbUnmarshaller.unmarshal(new StringReader(response.toString()));
                    feed.setUpdated(feedChunk.getUpdated());
                    feed.setId(feedChunk.getId());
                    feed.setLink(feedChunk.getLink());
                    feed.setTitle(feedChunk.getTitle());
                    feed.setStartIndex(feedChunk.getStartIndex());
                    feed.setTotalResults(feedChunk.getTotalResults());
                    feed.setItemsPerPage(feedChunk.getItemsPerPage());
                    if(feed.getEntries()==null){
                        List<Entry> entries = new ArrayList<>();
                        feed.setEntries(entries);
                    }
                    feed.getEntries().addAll(feedChunk.getEntries());
                    callCounter = callCounter + 1;
                    noOfRecords = noOfRecords + feedChunk.getItemsPerPage();
                    totalRecords = feedChunk.getTotalResults();

                    url = baseUrl.concat("&start_record="+noOfRecords+"&max_records="+feedChunk.getItemsPerPage());
                    LOGGER.info("Url generated:" + url);
                } catch (JsonProcessingException e) {
                    feed = null;
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    feed = null;
                    throw new RuntimeException(e);
                } catch (JAXBException e) {
                    feed = null;
                    throw new RuntimeException(e);
                }
            }
        }
        return feed;

    }

    public static boolean convertArxivAPIResponseToRDF(Feed feed, String fileName, AtomicLong publicationCount,AtomicLong authorCount) {
        Boolean rdfDataCreated = true;
        Model model = ModelFactory.createDefaultModel();

        String arxivBaseUri = RdfSchemaConstants.ARXIV_BASE_URI;
        Property rdfTypeProperty = RDF.type;

        List<Entry> entryList = feed.getEntries();

        try{
            for(Entry entry: entryList){

                Resource publicationResource = model.createResource(arxivBaseUri + "Publication"+publicationCount);
                publicationResource.addProperty(rdfTypeProperty, model.createResource(arxivBaseUri+"Publication"));

                for (Field field : entry.getClass().getDeclaredFields()) {
                    field.setAccessible(true);

                    switch (field.getType().getName()){
                        case("java.lang.String"):
                            publicationResource.addProperty(model.createProperty(arxivBaseUri, field.getName()), nullChecks(String.valueOf(field.get(entry))));
                            break;
                        case("java.lang.Integer"):
                            publicationResource.addProperty(model.createProperty(arxivBaseUri, field.getName()), nullChecks(String.valueOf(field.get(entry))));
                            break;
                        case("java.util.List"):
                            if(field.getName().equalsIgnoreCase("authors")){
                                Property authorProperty = model.createProperty(arxivBaseUri, "author");
                                List<org.utwente.cs.ds.semi.lod.arxiv.model.Author> authors = entry.getAuthors();
                                for (Author author : authors) {
                                    Resource authorResource = model.createResource(arxivBaseUri + "Author" + authorCount);
                                    authorResource.addProperty(rdfTypeProperty, model.createResource(arxivBaseUri + "Author"));
                                    authorResource
                                            .addProperty(model.createProperty(arxivBaseUri, "name"), nullChecks(author.getName()));
                                    publicationResource.addProperty(authorProperty, authorResource);
                                    authorCount.set(authorCount.get()+1);
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
