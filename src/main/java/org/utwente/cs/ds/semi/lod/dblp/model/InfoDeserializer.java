package org.utwente.cs.ds.semi.lod.dblp.model;

import com.google.gson.*;
import org.utwente.cs.ds.semi.lod.dblp.model.Info;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InfoDeserializer implements JsonDeserializer<Info> {

    private static final Gson delegateGson = new GsonBuilder()
            .registerTypeAdapter(Authors.class, new AuthorsDeserializer())
            .create();

    @Override
    public Info deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        JsonObject clone = obj.deepCopy();
        clone.remove("year");

        Info info = delegateGson.fromJson(clone, Info.class);

        JsonElement yearElement = obj.get("year");
        List<String> years = new ArrayList<>();

        if (yearElement != null) {
            if (yearElement.isJsonArray()) {
                for (JsonElement elem : yearElement.getAsJsonArray()) {
                    years.add(elem.getAsString());
                }
            } else if (yearElement.isJsonPrimitive()) {
                years.add(yearElement.getAsString());
            }
            info.setYear(years);
        }

        return info;
    }
}
