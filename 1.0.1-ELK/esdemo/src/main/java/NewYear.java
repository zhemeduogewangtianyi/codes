
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewYear {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8088);

        try {
            while(true){
                Socket accept = serverSocket.accept();
                new Thread(new Read(accept)).start();
            }
        }catch (Exception e){
        }
    }

}

class Read implements Runnable{

    private Socket socket;

    public Read(Socket socket) {
        this.socket = socket;
    }

    public synchronized void run() {
        InputStream inputStream = null;
        BufferedInputStream buff = null;
        try {
            inputStream = socket.getInputStream();
            buff = new BufferedInputStream(inputStream);
            byte[] data = new byte[1024];
            int len;
            StringBuffer builder = new StringBuffer();
            while ((len = buff.read(data)) != -1){
                builder.append(new String(data,0,len,"UTF-8")).append("\r\n");
                if(len <= 0 || len < 1024){
                    break;
                }
            }
            System.out.println(builder.toString());
            new Thread(new Writer(socket)).start();
        } catch (IOException e) {
//            e.printStackTrace();
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}

class Writer implements Runnable{

    private Socket socket;

    public Writer(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(socket.getOutputStream(),true);
            StringBuffer buff = new StringBuffer();
            //buff.append("HTTP/1.1 500 Internal Server Error \r\n");
            buff.append("HTTP/1.1 200 OK \r\n");
            buff.append("Content-type:text/html \r\n\r\n");
            buff.append("\r\n");
            File file = new File("D:\\Exec\\1.0.1-ELK\\esdemo\\src\\main\\resources\\index.html");
            InputStream is = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"GBK"));
            String len ;
            while((len = bufferedReader.readLine()) != null){
                buff.append(len);
            }
            pw.println(buff.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(pw != null){
                pw.close();
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
