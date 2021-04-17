import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.util.*;

public class Client extends JFrame{

	public Client(){
		MyPanel mp=new MyPanel();
		this.add(mp);
		this.setSize(550, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	public static void main(String[] args) throws Exception{

		ClientReceiver td = new ClientReceiver();
		td.start();
		new Client();

		//BufferedReader br = new BufferedReader(new InputStreamReader(is,"gbk"));
		//String data;
		//while((data = br.readLine()) != null){
		//	System.out.println(data);
		//}
		/*
		DataInputStream dis = new DataInputStream(is);
		FileOutputStream fos = new FileOutputStream(new File(System.getProperty("user.dir") + "/1.png"));
		byte[] data = new byte[1024];
		int len;
		while((len = dis.read(data)) != -1){
			fos.write(data,0,len);
		}
		*/

	}

}

class ClientReceiver extends Thread{

	@Override
	public void run(){
		try{
			while(true){
				Socket socket = new Socket("127.0.0.1",8096);
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os,true);
				pw.println("cmd && echo ");
				InputStream is = socket.getInputStream();
				InputStreamQueue.NEW_INSTANCE.push(is);
				Thread.sleep(new Random().nextInt(200));
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}

class MyPanel extends JPanel{

	Image image=null;

	public void paint(Graphics g){
		try {
			InputStream is = InputStreamQueue.NEW_INSTANCE.pop();
			if(is != null){
				image=ImageIO.read(is);
				g.drawImage(image, 0, 0, 550, 400, null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


enum InputStreamQueue{

	NEW_INSTANCE;

	private static LinkedList<InputStream> list = new LinkedList<>();

	public static synchronized void push(InputStream is){
		list.push(is);
	}

	public static InputStream pop(){
		if(list == null || list.size() == 0){
			return null;
		}
		return list.pop();
	}

}