
public class Counter {

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		short ones=0,tens=0,hundreds=0;
		while(hundreds<10){
			
			//System.out.println(hundreds+" "+tens+" "+ones);
			ones++;
			if(ones >= 10){
				ones -= 10;
				tens++;
			}
			if(tens >= 10){
				tens -= 10;
				hundreds++;
			}
			
		}
		System.out.println("Counting completed in "+(System.nanoTime()-startTime)+"ns");
		
		startTime = System.nanoTime();
		for(int i = 0;i<1000;i++){
			hundreds = (short) (i/100);
			tens = (short) ((i-(hundreds*100))/10);
			ones = (short) (i-(hundreds*100+tens*10));
			//System.out.println(hundreds+" "+tens+" "+ones);
			
		}
		System.out.println("Counting completed in "+(System.nanoTime()-startTime)+"ns");
		
		startTime = System.nanoTime();
		for(hundreds = 0;hundreds<10;hundreds++){
			for(tens = 0;tens<10;tens++){
				for(ones = 0;ones<10;ones++){
					//System.out.println(hundreds+" "+tens+" "+ones);
				}
			}
		}
		System.out.println("Counting completed in "+(System.nanoTime()-startTime)+"ns");
	
	}

}
