package org.utwente.cs.ds.semi.lod.dblp.model;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AuthorsDeserializer implements JsonDeserializer<Authors> {
    @Override
    public Authors deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Authors authors = new Authors();
        List<Author> authorList = new ArrayList<>();
        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.get("author").isJsonObject()) {
            // Single author object
            Author author = context.deserialize(jsonObject.get("author"), Author.class);
            authorList.add(author);
        } else if (jsonObject.get("author").isJsonArray()) {
            // Array of authors
            JsonArray jsonArray = jsonObject.get("author").getAsJsonArray();
            for (JsonElement element : jsonArray) {
                Author author = context.deserialize(element, Author.class);
                authorList.add(author);
            }
        }

        authors.setAuthor(authorList);
        return authors;
    }
}
