import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.util.*;

public class Client extends JFrame{

	public Client(){
		MyPanel mp=new MyPanel();
		new Thread(mp).start();
		this.add(mp);
		this.setSize(550, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	public static void main(String[] args) throws Exception{

		ClientReceiver td = new ClientReceiver();
		td.start();
		new Client();

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
				Thread.sleep(new Random().nextInt(5000));
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}

class MyPanel extends JPanel implements Runnable{

	Image image=null;

	@Override
	public void run(){
		while(true){
			repaint();
		}
	}

	public void paint(Graphics g){
		try {
			InputStream is = InputStreamQueue.NEW_INSTANCE.pop();
			if(is != null){
				//image=ImageIO.read(is);
				//g.drawImage(image, 0, 0, 550, 400, null);
				Image buffer = createImage(getWidth(), getHeight());
				Graphics g1 = buffer.getGraphics();
				g1.drawImage(ImageIO.read(is), 0, 0, this);
				g.drawImage(buffer, 0, 0,this);

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