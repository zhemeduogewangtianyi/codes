import java.net.URL;
import java.net.URLConnection;
import java.io.*;

public class SSH{

	public static void main(String[] args) throws Exception{

		URL url = new URL("http://120.79.67.143");
		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String data;
		while((data = br.readLine()) != null){
			System.out.println(data);
		}

	}

}