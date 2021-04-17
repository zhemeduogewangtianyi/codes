import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.*;

public class Server{

	public static void main(String[] args) throws Exception{

		ServerSocket ss = new ServerSocket(8096);
		while(true){
			Socket socket = ss.accept();
			Receiver td = new Receiver(socket);
			td.start();
			ReplyImage im = new ReplyImage(socket);
			im.start();
		}
	}

}

class Receiver extends Thread{

	private Socket socket;

	public Receiver(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run(){
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try{
			is = socket.getInputStream();
			isr = new InputStreamReader(is,"UTF-8");
			br = new BufferedReader(isr);
			String data;
			while((data = br.readLine()) != null){
				System.out.println(data);
			}
		}catch(IOException ioe){
			if(socket != null){
				try{
					socket.close();
				}catch(Exception e){
					e.printStackTrace();
				}

			}
		}finally{
			if(br != null){
				try{
					br.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			if(isr != null){
				try{
					isr.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			if(is != null){
				try{
					is.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}

	}
}



class ReplyImage extends Thread{

	private Socket socket;


	public ReplyImage(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run(){
		OutputStream os = null;
		DataOutputStream dos = null;
		try{

			os = socket.getOutputStream();
			dos = new DataOutputStream(os);

			InputStream is = ImageResource.getResource();
			DataInputStream ois = new DataInputStream(is);
			byte[] data = new byte[1024];
			int len;
			while((len = ois.read(data)) != -1){
				dos.write(data,0,len);
			}

		}catch(IOException e){
			try{
				if(socket != null){
					socket.close();
				}
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}finally{
			if(dos != null){
				try{
					dos.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			if(os != null){
				try{
					os.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}

		}

	}

}


class ImageResource {

	public static InputStream getResource(){
		try{
			Dimension dii = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle rt = new Rectangle(dii);
			Robot robot = new Robot();
			BufferedImage image = robot.createScreenCapture(rt);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			ImageIO.write(image,"png",baos);
			InputStream is = new ByteArrayInputStream(baos.toByteArray());
			Thread.sleep(new Random().nextInt(500));
			return is;
		}catch(AWTException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		return null;
	}

}