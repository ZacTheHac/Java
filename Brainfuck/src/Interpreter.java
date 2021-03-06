import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

@SuppressWarnings("unused")//because I moved everything inline, a lot of the functions are unneeded now. But I still want them for reference.
public class Interpreter {
	static String Code = "";
	static Integer codeLength = 0;
	static ArrayList<Integer> Data = new ArrayList<Integer>();
	static Integer CodePointer = 0;
	static Integer DataPointer = 0;
	static Character CodeChar;
	static Scanner input = new Scanner(System.in);
	static Boolean Debug = false;

	public static void main(String[] args) throws InterruptedException, IOException {
		//get code
		System.out.println("Please enter code as a single line:");
		Code = input.nextLine();
		if (Code.equals("")){
			//Load a file
			JFileChooser fileChooser = new JFileChooser(); //start setting up a file dialog
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));//go to the folder I'll want to be in
			int result = fileChooser.showOpenDialog(fileChooser);
			//open the file chooser to the desktop and record if they picked a file or hit cancel
			if (result == JFileChooser.APPROVE_OPTION) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
				String line = null;
			    while ((line = bufferedReader.readLine()) != null)
			    {
			    	Code += line;
			    }
			    bufferedReader.close();
			    System.out.println(Code);
			}
			else{
				return;
			}
		}
		//have code. continue
		codeLength = Code.length();

		long StartTime = System.currentTimeMillis();
		while(CodePointer < codeLength){//main loop
			EnsureCapacity();
			//now to interpret
			CodeChar = Code.charAt(CodePointer);
			if(Debug){
				OutputArray();
				System.out.println(CodeChar + " at index "+CodePointer);
			}
			switch(CodeChar){
			case '>':
				DataPointer++;
				break;
			case '<':
				DataPointer--;
				break;
			case '+':
				Data.set(DataPointer, Data.get(DataPointer)+1);
				break;
			case '-':
				//I'm going to make sure no values can be < 0
				Integer cellNumber = Data.get(DataPointer);
				if(cellNumber > 0){
					Data.set(DataPointer, cellNumber-1);
				}
				else{
					Data.set(DataPointer, 0);
				}
				break;
			case '.':
				System.out.print(Character.toChars((Data.get(DataPointer))));
				break;
			case ',':
				System.out.print("$");
				Data.set(DataPointer, Integer.parseInt(input.next()));//(int) input.next().charAt(0));
				break;
			case '[':
				OpenLoop();
				break;
			case ']':
				CloseLoop();
				break;
			default://brainfuck don't give a fuck
				break;	
			}
			//done interpreting. go to next character.
			CodePointer++;
			if(Debug){
				Thread.sleep(100);
			}
		}
		long ExecTime = System.currentTimeMillis() - StartTime;
		input.close();
		System.out.println();
		System.out.println("Done in "+ExecTime+"ms. Exiting.");
	}
	
	private static void addition(){
		Data.set(DataPointer, Data.get(DataPointer)+1);
	}
	
	private static void subtraction(){
		//I'm going to make sure no values can be < 0
		Integer cellNumber = Data.get(DataPointer);
		if(cellNumber > 0){
			Data.set(DataPointer, cellNumber-1);
		}
		else{
			Data.set(DataPointer, 0);
		}
	}
	
	private static void output(){
		System.out.print(Character.toChars((Data.get(DataPointer))));
	}
	
	private static void input(){
		System.out.print("$");
		Data.set(DataPointer, (int) input.next().charAt(0));
	}
	
	private static void OpenLoop(){
		if(Data.get(DataPointer) != 0){
			return;
		}
		else{
			//find ending brace and jump past it
			Integer SearchCharIndex = CodePointer+1;
			Integer BracesToSkip = 0;
			Character SearchChar;
			while(true){
				if(SearchCharIndex < codeLength){
					SearchChar = Code.charAt(SearchCharIndex);
					switch(SearchChar){
					case '[':
						BracesToSkip++;
						break;
					case ']':
						if(BracesToSkip == 0){
							CodePointer = SearchCharIndex;
							return;
						}
						else{
							BracesToSkip--;
						}
						break;
					default:
						break;
					}
					SearchCharIndex++;
				
				}
				else{
					System.err.println("unmatched opening brace at char "+CodePointer);
					return;//unmatched brace
				}
			}
		}
	}
	
	private static void CloseLoop(){
		if(Data.get(DataPointer) != 0){
			//search backwards to find opening brace and jump to it.
			Integer SearchCharIndex = CodePointer-1;
			Integer BracesToSkip = 0;
			Character SearchChar;
			while(true){
				if(SearchCharIndex >= 0){
					SearchChar = Code.charAt(SearchCharIndex);
					switch(SearchChar){
					case ']':
						BracesToSkip++;
						break;
					case '[':
						if(BracesToSkip == 0){
							CodePointer = SearchCharIndex;
							return;
						}
						else{
							BracesToSkip--;
						}
						break;
					default:
						break;
					}
					SearchCharIndex--;
				
				}
				else{
					System.err.println("unmatched closing brace at char "+CodePointer);
					return;//unmatched brace
				}
			}
		}
	}
	
	private static void EnsureCapacity(){
		//Data.ensureCapacity(DataPointer+1);//this should speed up the program if I ever need to add a bunch of items onto the list at once. May want to test the speed of with vs without.
		//I end up running this every character now, so this is irrelevant
		while(DataPointer >= Data.size()){
			Data.add(0);
		}//add 0s onto the list until we have enough to work on the one in question
	}
	
	private static void OutputArray(){
		int dataSize = Data.size();
		for(int i = 0; i < dataSize; i++){
			if(i != DataPointer){
				System.out.print(Data.get(i));
			}
			else {
				System.out.print("(("+Data.get(i)+"))");
			}
			if(i != dataSize - 1)
				System.out.print(",");
		}
		System.out.println();
		
	}
	

}
