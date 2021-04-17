import com.es.demo.RestClientFactory;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class EsLowApiTest {

    private RestClient restClient;

    @Before
    public void init(){
        restClient = RestClientFactory.getRestClient();
    }

    @Test
    public void getApi(){
        Request request = new Request("GET","/user");
        Response response = null;
        try {
            response = restClient.performRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.toString());
    }

    @Test
    public void createIndexApi(){
//        restClient.performRequest()
    }

}
