import java.util.concurrent.atomic.AtomicInteger;

public class CASdemo{

	public static void main(String[] args){

		AtomicInteger ai = new AtomicInteger(1);

		boolean one = ai.compareAndSet(1,2020);
		boolean two = ai.compareAndSet(1,12345);

		System.out.println(one + "   " + ai.get());
		System.out.println(two + "   " + ai.get());

		ai.getAndIncrement();

	}

}