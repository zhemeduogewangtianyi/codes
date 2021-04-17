import java.util.concurrent.atomic.*;

public class ABADemo{

	static AtomicReference<Integer> ar = new AtomicReference<>(100);

	static AtomicStampedReference<Integer> asr = new AtomicStampedReference<>(100,1);

	public static void main(String[] args){

		// question ABA 产生

		new Thread(new Runnable(){

			@Override
			public void run(){
				ar.compareAndSet(100,101);
				ar.compareAndSet(101,100);
			}

		},"t1").start();

		new Thread(new Runnable(){

			@Override
			public void run(){
				try{
					Thread.sleep(2000);
				}catch(Exception e){
					e.printStackTrace();
				}
				System.out.println(ar.compareAndSet(100,2020) + "    " + ar.get());
			}

		},"t2").start();


		System.out.println("============================================");

		// ABA 解决

		new Thread(new Runnable(){

			@Override
			public void run(){
				int stamp = asr.getStamp();
				System.out.println("t3 current " + Thread.currentThread().getName() + " 111 version : " + stamp);

				try{
					Thread.sleep(1000);
				}catch(Exception e){
					e.printStackTrace();
				}
				asr.compareAndSet(100,101,asr.getStamp(),asr.getStamp() + 1);
				System.out.println("t3 current " + Thread.currentThread().getName() + " 222 version : " + asr.getStamp());
				asr.compareAndSet(101,100,asr.getStamp(), asr.getStamp() + 1);
				System.out.println("t3 current " + Thread.currentThread().getName() + "333 version : " + asr.getStamp());
			}

		},"t3").start();



		new Thread(new Runnable(){

			@Override
			public void run(){
				int stamp = asr.getStamp();
				System.out.println("my current " + Thread.currentThread().getName() + " version : " + stamp);

				try{
					Thread.sleep(3000);
				}catch(Exception e){
					e.printStackTrace();
				}
				boolean res = asr.compareAndSet(100,2020,stamp,stamp+1);

				System.out.println("t4 current " + Thread.currentThread().getName() + " version : " + stamp + "   isSuccess : " + res + " currentVersion : " + asr.getStamp());
				System.out.println(asr.getReference());
			}

		},"t4").start();

	}

}