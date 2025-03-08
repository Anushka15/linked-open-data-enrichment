package org.utwente.cs.ds.semi.lod.visualizations.utils.sparql;

import com.opencsv.CSVWriter;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SparqlQueryRunner {

    public static boolean findPublicationsByTopic(){
        boolean writtenToCsv = true;
        List<String> filePaths = List.of("src/main/resources/rdf/2025/scientificPublicationsArxiv.nt",
                "src/main/resources/rdf/2025/scientificPublicationsIEEE.nt",
                "src/main/resources/rdf/2025/scientificPublicationsDblp.nt");

        // SPARQL query
        String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX ieee: <http://www.ieeeLOD.org/2024/01/scientific/data#>\n" +
                "\n" +
                "SELECT  ?universityName (COUNT(?publication) AS ?publicationCount)\n" +
                "WHERE {\n" +
                "\t?publication rdf:type ieee:Publication .\n" +
                "\t?publication ieee:author ?author .\n" +
                "\t?author rdf:type ieee:Author .\n" +
                "\t?author ieee:affiliation ?universityName .\n" +
                "\n" +
                "}\n" +
                "GROUP BY ?universityName\n" +
                "HAVING (COUNT(?publication) > 10)\n" +
                "ORDER BY DESC(?publicationCount)";

        // Create an RDF model
        Model model = ModelFactory.createDefaultModel();

        // Iterate over each file path and load data into the model
        for (String filePath : filePaths) {
            FileManager.get().readModel(model, filePath, "N-TRIPLES");
        }

        List<String[]> dataList = new ArrayList<>();
        dataList.add(new String[]{"UniversityName","PublicationCount"});
        try (QueryExecution queryExec = QueryExecutionFactory.create(query, model)) {
            ResultSet resultSet = queryExec.execSelect();

            // Output query results
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String universityName = solution.get("universityName").toString();
                String publicationCount = solution.getLiteral("publicationCount").getString();
                dataList.add(new String[]{universityName,publicationCount});

            }

            String csvFilePath = "src/main/resources/rdf/UniversityAffiliations.csv";

            // Initialize CSVWriter
            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
                // Write data to the CSV file
                writer.writeAll(dataList);
                System.out.println("CSV file written successfully at: " + csvFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(dataList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return writtenToCsv;
    }
}
