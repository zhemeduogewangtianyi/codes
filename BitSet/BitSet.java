import java.util.Date;

public class BitSet{

	public static void main(String[] args){

		int cacheDay = 0;

		//64
		long current = System.currentTimeMillis() / (24 * 60 * 60 * 1000) % 30;
		//32
		int bit = (int)current;

		//bit = -2147483648;

		System.out.println(bit);

		//currentDay
		int preDay = cacheDay >> current  & 1;

		System.out.println(preDay);

//--------------------------------------------------------------------------------

		long day = System.currentTimeMillis() / (24 * 60 * 60 * 1000) % 30;


		//assgin
		int res = 1 << day  | cacheDay;

		System.out.println(res + "  " + Integer.toBinaryString(res) + " -- " + print(res));

	}

	public static String print(int res){

		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < 32 ; i++){
			sb.append(res >> i & 1);
		}
		return sb.toString();
	}

}