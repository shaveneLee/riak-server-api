package api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by shavene on 3/22/2017.
 */
public class Common {

    /**
     * check the string is a valid json string
     * @param json
     * @return
     * @throws IOException
     */
    public static boolean isValidJSON(final String json) throws IOException {
        boolean valid = true;
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(json);
        } catch(JsonProcessingException e){
            valid = false;
        }
        return valid;
    }
}
