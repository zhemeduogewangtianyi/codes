public class Test{

	public static void main(String[] args){

		int[] arr1 = {3,5,7,8,9};
		int[] arr2 = {1,2,6,7,10};

		StringBuffer sb = new StringBuffer();
		for(int x = 0 ; x < 5 ; x++){
			sb.append(arr1[x]).append(",").append(arr2[x]).append(",");;
		}


		String[] strs = sb.toString().substring(0,sb.toString().length() - 1).split(",");


		Integer[] is = new Integer[10];
		for(int k = 0 ; k < strs.length ; k++){
			is[k] = Integer.valueOf(strs[k]);
		}

		for(int i = 0 ; i < is.length - 1 ;  i++){
			for(int j = 0 ; j < is.length - i - 1 ; j++){
				int a = is[j];
				int b = is[j + 1];
				if(a > b){
					is[j] = is[j] ^ is[j+1];
					is[j + 1] = is[j] ^ is[j+1];
					is[j] = is[j] ^ is[j+1];
				}else{
					continue;
				}
			}
		}

		if(((is.length) & 1) == 0){
			System.out.println((double)(((is[is.length / 2] + is[is.length / 2 - 1]) * 100) / 2) / 100);
		}



	}

}