import java.util.*;
import java.util.concurrent.*;

public class ContainerNotSafeDemo{

	public static void main(String[] args){

		//Map<String,String> map = new HashMap<>();

		//Map<String,String> map = Collections.synchronizedMap(new HashMap<>());

		Map<String,String> map = new ConcurrentHashMap<>();

		for(int i = 0 ; i < 30 ; i++){
			new Thread(() -> {
				map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,8));
				System.out.println(map);
			},String.valueOf(i)).start();
		}

	}

	// set unsafe operator
	private void setNotSate(){
		//Set<String> set = new HashSet<>();

		//Set<String> set = Collections.synchronizedSet(new HashSet<>());

		//继承AbstractSet<E>，构造方法创建了CopyOnWriteArrayList<E>();
		Set<String> set = new CopyOnWriteArraySet<>();

		for(int i = 0 ; i < 30 ; i++){
			new Thread(() -> {
				set.add(UUID.randomUUID().toString().substring(0,8));
				System.out.println(set);
			},String.valueOf(i)).start();
		}
	}

	// list unsafe operator
	private void listNotSafe(){
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.forEach(System.out::println);

		//List<String> list1 = new ArrayList<>();

		//List<String> list1 = new Vector<>();

		//List<String> list1 = Collections.synchronizedList(new ArrayList<>());

		List<String> list1 = new CopyOnWriteArrayList<>();

		for(int i = 0 ; i < 30 ; i++){
			new Thread(() -> {
				list1.add(UUID.randomUUID().toString().substring(0,8));
				System.out.println(list1);
			},String.valueOf(i)).start();
		}
	}

}