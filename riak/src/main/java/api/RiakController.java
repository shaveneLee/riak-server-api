package api;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

/**
 * Created by shavene on 3/21/2017.
 */

@RestController
public class RiakController {

    /**
     * get value of the specify key
     * @param type
     * @param buckets
     * @param keys
     * @return
     */
    @RequestMapping(value="/fetchValue", method= RequestMethod.GET)
    public ResponseEntity fetchValue(@RequestParam(value="type", defaultValue="default") String type,
                                     @RequestParam(value="buckets") String buckets,
                                     @RequestParam(value="keys") String keys) {
        ResponseEntity result;
        RialModel riak = new RialModel();

        //fetch value by parameters
        result = riak.fetchValue(type, buckets, keys);
        return result;
    }

    /**
     * store json/object by specify parameters.
     * @param request
     * @return
     */
    @RequestMapping(value="/storeValue", method= RequestMethod.POST)
    public ResponseEntity storeValue(@RequestBody RequestEntity request) {
        ResponseEntity result;
        RialModel riak = new RialModel();

        // store json/obj value
        result = riak.storeValue(request);
        return result;
    }

    /**
     * fetch all keys via buckets
     * @param type
     * @param buckets
     * @return
     */
    @RequestMapping(value="/fetchKeys", method= RequestMethod.GET)
    public ResponseEntity fetchKeys(@RequestParam(value="type", defaultValue="default") String type,
                                     @RequestParam(value="buckets") String buckets) {
        ResponseEntity result = new ResponseEntity();
        RialModel riak = new RialModel();

        //fetch all keys by type and buckets
        try {
            result = riak.fetchKeys(type, buckets);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * fetch all keys via buckets
     * @param type
     * @param buckets
     * @return
     */
    @RequestMapping(value="/fetchValues", method= RequestMethod.GET)
    public ResponseEntity fetchValues(@RequestParam(value="type", defaultValue="default") String type,
                                    @RequestParam(value="buckets") String buckets) {
        ResponseEntity result;
        RialModel riak = new RialModel();

        //fetch all key-value by type and buckets
        result = riak.fetchValues(type, buckets);

        return result;
    }
}
