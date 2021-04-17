public class TestTransferValue{

	public void changeValue1(int a){
		a = 10;
	}

	public void changeValue2(Student stu){
		stu.name = "小明";
		stu.age = 0;
	}

	public void changeValue3(String str){
		str = "llll1111" + "aaa";
	}

	public void changeValue4(String str){
			str = new String("aaa");
	}

	public static void main(String[] args){

		TestTransferValue test = new TestTransferValue();

		int a = 20;

		test.changeValue1(a);

		System.out.println(a);

		Student stu = new Student("张三",11);

		test.changeValue2(stu);

		System.out.println(stu.name + "  " + stu.age + "");

		String str = "GBK";

		test.changeValue3(str);

		System.out.println(str);

		String str1 = "UTF-8";

		test.changeValue4(str1);

		System.out.println(str1);

		String str2 = new String("ISO-8859-1");

		test.changeValue4(str2);

		System.out.println(str2);

	}

}

class Student{

	String name;

	int age;

	Student(String name, int age){
		this.name = name;
		this.age = age;
	}

}