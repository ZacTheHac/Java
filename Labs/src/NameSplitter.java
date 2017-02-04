import java.util.Scanner;


public class NameSplitter {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String fullName = "";
		String[] name;
		System.out.println("Please enter your full name.");
		while(true){
			fullName = input.nextLine();
			name = fullName.split(" ");
			if(name.length < 3){
				System.out.println("I want a first, middle, and last name, please.");
			}
			else
				break;
		}
		input.close();
		name = properlyCase(name);
		int names = name.length;//the number of names they entered
		int middleNames = names-1;
		String firstName = name[0];
		String lastName = name[middleNames];
		String middleName = "";
		for (int i = 1;i<middleNames;i++){
			middleName += name[i];
			if(i<middleNames - 1){//add a space if there's more names
				middleName +=" ";
			}
		}
		//System.out.println("First: "+firstName);
		//System.out.println("Middle: "+middleName);
		//System.out.println("Last: "+lastName);
		
		System.out.println("Your name is "+lastName+", "+firstName+" "+middleName.charAt(0)+".");

	}
	public static String[] properlyCase(String[] name){
		int length = name.length;
		for(int i = 0;i<length;++i){
			name[i]=name[i].substring(0,1).toUpperCase()+name[i].substring(1).toLowerCase();
		}
		return name;
	}

}
