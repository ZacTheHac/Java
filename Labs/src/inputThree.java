import java.util.Scanner;
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
//* Class name      : inputThree	                                    *
//*                                                                     *
//* Written by      : Zachary Muerle (C) 2014, All rights reserved      *
//*                                                                     *
//* Purpose         : let the user input 3 integers, then put them in	*
//*                   order from smallest to largest					*
//*                                                                     *
//* Inputs          : 3 integers									    *
//*                   						                            *
//*                                                                     *
//* Outputs         : the input integers in order						*
//* 								                                    *
//*                                                                     *
//* Methods         : main(String[] args)							    *
//*                                                                     *
//*---------------------------------------------------------------------*
//* Change Log:                                                         *
//*                         Revision                                    *
//*       Date    Changed  Rel Ver Mod Purpose                          *
//* 09/18/14      ZMuerle  000.000.000 Initial release of program       *
//* 09/18/14      ZMuerle  000.001.000 organize code & make it loop     *
//*                                                                     *
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
public class inputThree {
	static int input1 = 0;
	static int input2 = 0;
	static int input3 = 0;
	static int smallest = 0;
	static int middle = 0;
	static int largest = 0;
	
	public static void main(String[] args) {
		//I compressed it into methods to clean it up
		System.out.println("Enter 1000+ as one of the numbers to terminate");
		while(true){
			getInput();
			sort();
			System.out.println("Smallest: "+smallest);
			System.out.println("  Middle: "+middle);
			System.out.println(" Largest: "+largest);
			if(input1 > 999 || input2 > 999 || input3 > 999){
				System.out.println("Alright, Bye!");
				break;
			}
		}

	}
	public static void getInput(){
		Scanner inputDevice = new Scanner(System.in);
		System.out.println("Please input 3 integers.");
		boolean inputRight = false;
		for(int i = 1; i <= 3; i++){
			inputRight = false;
			while(!inputRight){
				try{
					if(i == 1){
						System.out.print("Number 1: ");
						input1 = Integer.parseInt(inputDevice.nextLine());
						inputRight = true;
					}
					else if(i==2){
						System.out.print("Number 2: ");
						input2 = Integer.parseInt(inputDevice.nextLine());
						inputRight = true;
					}
					else if(i==3){
						System.out.print("Number 3: ");
						input3 = Integer.parseInt(inputDevice.nextLine());
						inputRight = true;
					}
				}
				catch(Exception e){
					inputRight = false;
					System.out.println("Yeah, that wasn't an integer. try again.");
				}
			}
		}//done with input
		inputDevice.close();
	}
	public static void sort(){
		//time for lots of fun nested ifs
		if(input1==input2||input1==input3||input2==input3){
			System.out.println("Some of those were equal");
			//useless to state, it still works fine
		}
		if(input1>input2){
			if(input1>input3){
				largest = input1;//1 must be the largest
				if(input2>input3){//3 is the smallest
					middle = input2;
					smallest = input3;
					//done
				}//2>3
				else{//2 is the smallest
					middle = input3;
					smallest = input2;
					//done
				}
			}//1>3
			else{//1 must be in middle, 2 is the smallest, leaving 3 as largest
				largest = input3;
				middle = input1;
				smallest = input2;
				//done
			}
		}//1>2
		else{
			if(input1>input3){
				middle = input1;
				largest = input2;
				smallest = input3;
				//done
			}//1>3
			else{//1 must be the smallest
				smallest = input1;
				if(input2>input3){
					largest = input2;
					middle = input3;
					//done
				}//2>3
				else{
					largest = input3;
					middle = input2;
					//done
				}
			}
			
		}//DONE!
	}

}
