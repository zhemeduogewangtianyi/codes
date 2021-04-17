import java.lang.reflect.*;
import java.util.concurrent.atomic.*;
import java.io.*;

public class SingletonDemo implements Serializable{

	private static volatile SingletonDemo instance = null;

	private static volatile AtomicBoolean flag = new AtomicBoolean(false);

	private SingletonDemo(){

		if(!flag.get()){
			synchronized(SingletonDemo.class){
				if(!flag.get()){
					flag.set(true);
					System.out.println(111);
				}else{
					//Singleton is bad !
					throw new RuntimeException("Singleton is bad ! ");
				}
			}

		}else{
			//Singleton is bad !
			throw new RuntimeException("Singleton is bad ! ");
		}

	}

	public static SingletonDemo instance(){
		if(instance == null){
			synchronized(SingletonDemo.class){
				if(instance == null){
					return instance = new SingletonDemo();
				}
			}
		}
		return instance;
	}


	Object readResolve() throws ObjectStreamException{
		return instance;
	}

	public static void main(String[] args) throws Exception {

		for(int i = 0 ; i < 10 ; i++){
			new Thread(new Runnable(){

				@Override
				public void run(){

					try{
						System.out.println(SingletonDemo.instance());
						//reflect();
						serializableCreate();
					}catch(Exception e){
						e.printStackTrace();
					}
				}

			}).start();
		}
	}

	public static void reflect() throws Exception{
		Class<?> classType = SingletonDemo.class;
		Constructor<?> c = classType.getDeclaredConstructor();
		c.setAccessible(true);
		SingletonDemo e1 = (SingletonDemo) c.newInstance();
		System.out.println(e1);
	}

	public static void serializableCreate() throws IOException,ClassNotFoundException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(SingletonDemo.instance());

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		SingletonDemo demo = (SingletonDemo)ois.readObject();
		System.out.println("seralizable -> " + demo);
	}

}