import java.io.*;
public class CurlTest{

	static String[] cmds = {"curl","-X","POST","--header","Content-Type:application/json","--header","Accept: */*","-d","{\"name\":\"123\"}","http://www.baidu.com"};

	public static void main(String[] args){

		ProcessBuilder pb = new ProcessBuilder(cmds);
		Process p;
		try{
			p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String res = null;
			StringBuffer buff = new StringBuffer();
			while((res = br.readLine()) != null){
				buff.append(new String(res.getBytes(),"UTF-8"));
				buff.append(System.getProperty("line.separator"));
			}
			System.out.println(buff.toString());
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}