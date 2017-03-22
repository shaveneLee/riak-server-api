package api;

import api.util.Common;
import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.ListKeys;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/** 对riak db进行CRUD操作
 * Created by shavene on 3/21/2017.
 */
public class RialModel {
    /**
     * get value of the specify key
     * @param type
     * @param buckets
     * @param keys
     * @return
     */
    public ResponseEntity fetchValue(String type, String buckets, String keys) {
        ResponseEntity result = new ResponseEntity();

        //save query request parameter for return
        RequestEntity request = new RequestEntity();
        request.setType(type);
        request.setBuckets(type);
        request.setKeys(keys);
        result.setQuery(request);

        //init connect client
        Cluster cluster = new Cluster();
        RiakClient client = cluster.getClient();

        //set query type/bucket/keys
        Location location = new Location(new Namespace(type, buckets), keys);

        FetchValue fv = new FetchValue.Builder(location).build();
        try {
            //fetch data
            FetchValue.Response response;
            response = client.execute(fv);
            if (response.isNotFound()) {
                result.setErrorCode(40001);
                result.setMessage("未找到数据");
                return result;
            }
            String value = response.getValue(RiakObject.class).getValue().toString();
            try {
                if (Common.isValidJSON(value)) {
                    ObjectMapper mapper = new ObjectMapper();
                    HashMap map  = mapper.readValue(value, HashMap.class);
                    result.setResult(map);
                } else {
                    result.setResult(value);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            client.shutdown();
        } catch (ExecutionException e) {
            e.printStackTrace();
            result.setErrorCode(40003);
            result.setMessage(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            result.setErrorCode(40003);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    /**
     * store json/object by specify parameters.
     * @param request
     * @return
     */
    public ResponseEntity storeValue(RequestEntity request) {
        ResponseEntity result = new ResponseEntity();
        if (null == request) {
            result.setErrorCode(40002);
            result.setMessage("请求参数为空");
            return result;
        }

        //save query request parameter for return
        result.setQuery(request);

        //init connect client
        Cluster cluster = new Cluster();
        RiakClient client = cluster.getClient();

        //get request parameters
        String type = request.getType();
        String buckets = request.getBuckets();
        String keys = request.getKeys();
        String string = request.getString();
        HashMap obj = request.getObj();

        Location location = new Location(new Namespace(type, buckets), keys);

        if ((null == obj || obj.isEmpty()) && (null == string || string.isEmpty())) {
            result.setErrorCode(40002);
            result.setMessage("请求参数为空");
            return result;
        }
        StoreValue sv = null;
        if (null != obj && !obj.isEmpty()) { //store object
            sv = new StoreValue.Builder(obj).withLocation(location).build();
        } else if (null != string && !string.isEmpty()) { //store string
            sv = new StoreValue.Builder(string).withLocation(location).build();
        }
        try {
            StoreValue.Response svResponse  = client.execute(sv);
            String keyString = svResponse.getLocation().getKeyAsString();
            if (keyString.isEmpty()) {
                result.setErrorCode(40001);
                result.setMessage("保存数据失败");
                return result;
            }
            result.setResult(keyString);
            client.shutdown();
        } catch (ExecutionException e) {
            e.printStackTrace();
            result.setErrorCode(40004);
            result.setMessage(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            result.setErrorCode(40004);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    /**
     * get all keys by buckets
     * @param type
     * @param buckets
     * @return
     */
    public ResponseEntity fetchKeys(String type, String buckets) throws ExecutionException, InterruptedException {
        ResponseEntity result = new ResponseEntity();

        //save query request parameter for return
        RequestEntity request = new RequestEntity();
        request.setType(type);
        request.setBuckets(type);
        result.setQuery(request);

        //init connect client
        Cluster cluster = new Cluster();
        RiakClient client = cluster.getClient();

        try {
            Namespace ns = new Namespace(type, buckets);
            ListKeys lk = new ListKeys.Builder(ns).build();
            ListKeys.Response response = client.execute(lk);

            //format to output
            HashMap keys = new HashMap();
            for (Location location : response) {
                keys.put(location.getKeyAsString(), location.getKeyAsString());
            }
            result.setResult(keys);
        } catch (ExecutionException e) {
            e.printStackTrace();
            result.setErrorCode(40003);
            result.setMessage(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            result.setErrorCode(40003);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    /**
     * get all values by type and buckets
     * @param type
     * @param buckets
     * @return
     */
    public ResponseEntity fetchValues(String type, String buckets) {
        ResponseEntity result = new ResponseEntity();

        //save query request parameter for return
        RequestEntity request = new RequestEntity();
        request.setType(type);
        request.setBuckets(type);
        result.setQuery(request);

        //init connect client
        Cluster cluster = new Cluster();
        RiakClient client = cluster.getClient();

        Namespace ns = new Namespace(type, buckets);
        ListKeys lk = new ListKeys.Builder(ns).build();
        ListKeys.Response lkResponse = null;
        try {
            lkResponse = client.execute(lk);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //fetch key-value one by one
        HashMap values = new HashMap();
        for (Location t : lkResponse) {
            String key = t.getKeyAsString();
            Location location = new Location(new Namespace(type, buckets), key);

            FetchValue fv = new FetchValue.Builder(location).build();
            FetchValue.Response response;
            try {
                //fetch data
                response = client.execute(fv);
                if (response.isNotFound()) {
                    result.setErrorCode(40001);
                    result.setMessage("未找到数据");
                    return result;
                }
                String value = response.getValue(RiakObject.class).getValue().toString();
                try {
                    if (Common.isValidJSON(value)) {
                        ObjectMapper mapper = new ObjectMapper();
                        HashMap map  = mapper.readValue(value, HashMap.class);
                        values.put(key, map);
                    } else {
                        values.put(key, value);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
                result.setErrorCode(40003);
                result.setMessage(e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
                result.setErrorCode(40003);
                result.setMessage(e.getMessage());
            }
        }
        result.setResult(values);

        client.shutdown();

        return result;
    }
}
