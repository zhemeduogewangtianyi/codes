package com.es.demo;

public class FactoryMethodText{

	public static void main(String[] args){

		Application app = new ConcreteProductA();
		Product p = app.getObject();
		p.method1("2");

	}

}

/**/
interface Product{

	public void method1(String type);

}

/*2 */
class SimpleFactory{

	public static Product createProduct(String type){
		if(type.equals("1")){
			return new ProductA();
		}else if(type.equals("2")){
			return new ProductA1();
		}
		return null;
	}

}


/*3*/
class ProductA1 implements Product{

	public void method1(String type) {
		System.out.println("ProductA1.method1");
	}
}

class ProductA implements Product{


	public void method1(String type) {
		System.out.println("ProductA.method1");
	}
}


abstract class Application{

	public abstract Product createProduct();

	public Product getObject(){
			//ProductA product = new ProductA();
			//Product product = new ProductA();
			Product product = createProduct();

			//...

			return product;
	}

}

class ConcreteProductA extends Application{

	@Override
	public Product createProduct(){
		return new ProductA();
	}
}

class ConcreteProductA2 extends Application{

	@Override
	public Product createProduct(){
		return new ProductA1();
	}
}