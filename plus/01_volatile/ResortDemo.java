public class ResortDemo{

	int a = 0;
	boolean flag = false;

	public void method01(){
	    a = 1;
	    flag = true;
    }

    public void method02(){
	    if(flag){
	        a = a + 5;
            System.out.println("reset value a = " + a);
        }
    }

    public static void main(String[] args){
	    ResortDemo resortDemo = new ResortDemo();
	    for(int i = 0 ; i < 100 ; i ++){
			new Thread(new Runnable(){
				@Override
				public void run(){
					resortDemo.method01();
					resortDemo.method02();
				}
			}).start();
		}

    }

}