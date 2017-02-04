import java.util.Scanner;


public class DiamondGen {

	public static void main(String[] args) {
		int width = getInput();
		
		//solidDiamond(width);
		hollowDiamond(width);
		

	}
	public static void hollowDiamond(int width){
		/*boolean even = false;
		if(width%2==0){
			even = true;
		}
		*/
		int height = ((width-1)*2)+1;
		
		for(int i = 1;i<=height;i++){
			String line = "";
			if(i<width){
				for(int a = 0;a < i;a++){
					line+="*";
				}
			}
			else if(i==width){
				
			}
			else{
				
			}
			System.out.println(line);
		}
	}
	public static void solidDiamond(int width){
		for(int i =1;i<=width;i++){//growing
			String line = "";
			int spaces = (width-i)/2;
			for(int s = 0;s<spaces;s++){
				line+=" ";
			}
			for(int a = 0;a<i;a++){
				line+="*";
			}
			for(int s = 0;s<spaces;s++){
				line+=" ";
			}
			System.out.println(line);
	}
	for(int i = width - 1;i>0;i--){//shrinking
		String line = "";
		int spaces = (width-i)/2;
		for(int s = 0;s<spaces;s++){
			line+=" ";
		}
		for(int a = 0;a<i;a++){
			line+="*";
		}
		for(int s = 0;s<spaces;s++){
			line+=" ";
		}
		System.out.println(line);
	}
	}
	public static int getInput(){
		int number = 0;
		Scanner inputDevice = new Scanner(System.in);
		System.out.println("Please input an int between 1 and 10");
		boolean inputWrong = true;
		while(inputWrong){
			try{
				System.out.print("Width: ");
				number = Integer.parseInt(inputDevice.nextLine());
				if(number<=10 && number > 0){
					inputWrong = false;
				}
			}
			catch(Exception e){
				inputWrong = true;
				System.out.println("Yeah, that wasn't an integer between 1 and 10");
			}
		}//done with input
		inputDevice.close();
		return number;
	}

}
