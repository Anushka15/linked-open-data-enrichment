package org.utwente.cs.ds.semi.lod.ieee.scraping.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import org.utwente.cs.ds.semi.lod.ieee.scraping.exception.ApplicationException;

import java.io.IOException;

public class JsonUtils {

	private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();
	
    private static ObjectMapper createObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector());
        return mapper;
    }
    
	@SuppressWarnings("rawtypes")
    public static String marshall(Class clazz, Object object) {
        try {
        	return OBJECT_MAPPER.writeValueAsString(object);
        } catch (IOException ex) {
            throw new ApplicationException("Could not marshal " + object, ex);
        }
    }

    @SuppressWarnings("rawtypes")
    public static Object unmarshall(Class clazz, String marshalledMessage) {
        try {
            @SuppressWarnings("unchecked")
			Object value = OBJECT_MAPPER.readValue(marshalledMessage, clazz);
            return value;
        } catch (JsonParseException | JsonMappingException ex) {
            throw new ApplicationException("***** Could not unmarshal String:" + marshalledMessage, ex);
        } catch (IOException ex) {
            throw new ApplicationException("***** Could not unmarshal String:" + marshalledMessage, ex);
        }
    }
	
}
