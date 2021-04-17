import com.es.demo.RestClientFactory;
import com.es.demo.RestHighLevelClientFactory;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EsRestClientTest {

    private RestHighLevelClient restHighLevelClient;

    private RestClient client;

    @Before
    public void init(){
        restHighLevelClient = RestHighLevelClientFactory.getRestHighLevelClient();
        client = RestClientFactory.getRestClient();
    }

    @Test
    public void createIndexApi() throws IOException {
        XContentBuilder contentBuilder = JsonXContent.contentBuilder();
        contentBuilder.startObject()

                .startObject("mappings")
                .startObject("user")
                .startObject("properties")

                .startObject("name")
                .field("type","keyword")
                .endObject()
                .startObject("age")
                .field("type","long")
                .endObject()
                .startObject("create_time")
                .field("type","date")
                .field("format","yyyy-MM-dd || yyyy-MM-dd HH:mm:ss")
                .endObject()
                .startObject("salary")
                .field("type","float")
                .endObject()

                .endObject()
                .endObject()
                .endObject()

                .startObject("settings")
                .field("number_of_shards",5)
                .field("number_of_replicas",1)
                .endObject()

                .endObject();

        IndexRequest indexRequest = new IndexRequest();

        String data = indexRequest.source(contentBuilder).source().utf8ToString();
        NStringEntity nStringEntity = new NStringEntity(data,ContentType.APPLICATION_JSON);

        Response get = client.performRequest("PUT", "/user", Collections.<String, String>emptyMap(), nStringEntity);
        client.close();
        System.out.println(get.toString());

    }

    @Test
    public void addOneData() throws IOException {
        XContentBuilder contentBuilder = JsonXContent.contentBuilder();
        contentBuilder.startObject()

                .field("name","小明")
                .field("age",19)
                .field("create_time",new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .field("salary",1.25)

                .endObject();

        String data = new IndexRequest().source(contentBuilder).source().utf8ToString();
        System.out.println(data);
        NStringEntity nStringEntity = new NStringEntity(data, ContentType.APPLICATION_JSON);
        Response get = client.performRequest("POST", "/user/user/", Collections.<String, String>emptyMap(), nStringEntity);
        System.out.println(get.toString());

    }

    @Test
    public void indexAPI() throws IOException {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name","小红");
        map.put("age",15);
        map.put("salary",1.99F);
        map.put("create_time",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        IndexRequest request = new IndexRequest("user","user","1").source(map);
        try{
            IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            System.out.println(index.toString());
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            restHighLevelClient.close();
        }
    }

    @Test
    public void getAPI() throws IOException {
        GetRequest request = new GetRequest("user","user","1");
        GetResponse documentFields = null;
        try {
            documentFields = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            restHighLevelClient.close();
        }
        System.out.println(documentFields.getSource());
    }

    @Test
    public void getAPI_low() throws IOException {
        XContentBuilder builder = JsonXContent.contentBuilder().startObject()

                .startObject("query")
                .startObject("match_all")
                .endObject()
                .endObject()

                .endObject();

        String data = new IndexRequest().source(builder).source().utf8ToString();
        System.out.println(data);
        NStringEntity nStringEntity = new NStringEntity(data, ContentType.APPLICATION_JSON);
        Response response = client.performRequest("GET", "/user/user/_search", Collections.<String, String>emptyMap(), nStringEntity);
        HttpEntity entity = response.getEntity();
        String res = EntityUtils.toString(entity);
        System.out.println(res);


    }

    @Test
    public void PageQueryApi() throws IOException {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(QueryBuilders.termQuery("name","哎呦哥哥海你好"));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);

        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        searchSourceBuilder.fetchSource(new String[]{"name","age","salary"},new String[]{"name"});

        String index = "user";
        String type = "user";

        SearchRequest request = new SearchRequest(index);
        request.types(type);
        request.source(searchSourceBuilder);

        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(request);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            restHighLevelClient.close();
        }
        System.out.println(search.toString());
    }

    @Test
    public void updateAPI() throws IOException {
        Map map = new HashMap();
        map.put("name","哎呦哥哥嗨你好");
        UpdateRequest request = new UpdateRequest("user","user","1").doc(map);
        UpdateResponse update = null;
        try {
            update = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            restHighLevelClient.close();
        }
        System.out.println(update.toString());
    }

    @Test
    public void deleteAPI() throws IOException {
        DeleteRequest request = new DeleteRequest("user","user","1");
        DeleteResponse delete = null;
        try {
            delete = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            restHighLevelClient.close();
        }
        System.out.println(delete);
    }


}
