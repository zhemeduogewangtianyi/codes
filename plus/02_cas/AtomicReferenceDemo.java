import java.util.concurrent.atomic.*;

public class AtomicReferenceDemo{

	public static void main(String[] args){

		//AtomicInteger ai = new AtomicInteger(5);

		User z3 = new User("zhangsan",22);
		User l4 = new User("lisi",25);

		AtomicReference<User> ar = new AtomicReference<>();
		ar.set(z3);

		System.out.println(ar.compareAndSet(z3,l4) + "\t" + ar.get().toString());
		System.out.println(ar.compareAndSet(z3,l4) + "\t" + ar.get().toString());

	}

}

class User{

	private String name;

	private int age;

	public User(String name,int age){
		this.name = name;
		this.age = age;
	}

	public User(){

	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setAge(int age){
		this.age = age;
	}

	public int getAge(){
		return this.age;
	}

	@Override
	public String toString(){
		return "name=" + name + " age=" + age;
	}

}