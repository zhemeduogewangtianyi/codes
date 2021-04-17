import java.util.concurrent.atomic.AtomicInteger;

public class VolatileDemo01{

	public static void main(String[] args) throws InterruptedException{

		//testVolatile();

		//testVolatileAotmic();

		Data data = new Data();

		for(int i = 0 ; i < 20 ; i++){
			new Thread(new Runnable(){
				@Override
				public void run(){
					for(int x = 0 ; x < 1000 ; x++){
						data.addPlus();
						data.addAtomic();
					}
				}
			},"Thread-" + i).start();
		}

		// wait top 20 Thread all run end ! main - Thread1  gc - Thread2

		while(Thread.activeCount() > 1){
			Thread.yield();
		}

		//Thread.sleep(1000);

		System.out.println("result = " + data.a);
		System.out.println("result = " + data.ai.get());

	}


	private static void testVolatile(){

		Data data = new Data();

		new Thread(new Runnable(){

			@Override
			public void run(){
				System.out.println("我this is " + Thread.currentThread().getName());
				try{
					Thread.sleep(3000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				data.add();
				System.out.println(Thread.currentThread().getName() + " update value " + data.a);
			}

		}," 我Thread A").start();

		while(data.a == 0){

		}

		System.out.println("this is " + Thread.currentThread().getName());
	}

}

class Data{

	//public int a = 0;

	public volatile int a = 0;

	public void add(){
		this.a = 60;
	}

	public void addPlus(){
		a++;
	}

	public synchronized void addSyncPlus(){
			a++;
	}

	//unsafe aotmic
	AtomicInteger ai = new AtomicInteger();
	public void addAtomic(){
		ai.getAndIncrement();
	}

}