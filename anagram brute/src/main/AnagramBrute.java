package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class AnagramBrute {
	static String Anagram = "DUECLAW";
	static ArrayList<String> records = new ArrayList<String>();
	static ArrayList<String> matches = new ArrayList<String>();
	static long timeStart = 0;

	public static void main(String[] args){
		Anagram = GetAnagram();
		records = PickAndLoadFile();
		System.out.println("File Loaded in "+(System.currentTimeMillis() - timeStart)+"ms. Beginning search.");
		int recordSize = records.size();
		int loopUpdateSize = recordSize/100;
		for(int i = 0; i < recordSize; i++){
			if(containsUnordered(records.get(i),Anagram)){
				matches.add(records.get(i));
				//System.out.println(records.get(i));
			}
			
			if((i+1)%loopUpdateSize == 1 || i == (recordSize-1)){//100 updates throughout
				System.out.println((i+1)+"/"+recordSize+" records processed. ("+(int)Math.floor(((float)(i+1)/recordSize)*100)+"%)");
			}
		}
		System.out.println("Done. In " + (System.currentTimeMillis() - timeStart) + "ms.");
		System.out.println(matches.size()+" match(es) found.");
		for(String match:matches){
			System.out.println(match);
		}
	}
	public static String GetAnagram(){
		System.out.println("Input Anagram:");
		Scanner input = new Scanner(System.in);
		String text = input.nextLine();
		input.close();
		
		text.replaceAll("\\s+","");//removes all whitespace
		text = text.toLowerCase();
		return text;
	}
	
	public static boolean containsUnordered(String input, String searchFor){
	    char[] characters = searchFor.toCharArray();
	    for (char c: characters)
	        if (!input.contains(String.valueOf(c)))
	            return false;
	    return true;
	}
	
	public static ArrayList<String> PickAndLoadFile(){
		System.out.println("Select word file.");
		String line = null;
		ArrayList<String> temprecords = new ArrayList<String>();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop/"));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				timeStart = System.currentTimeMillis();//start the timer here so we don't count the user being slow as fuck
				BufferedReader bufferedReader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
			    while ((line = bufferedReader.readLine()) != null)
			    {
			        temprecords.add(line.toLowerCase());//make sure it's lowercase as well
			    }
			    bufferedReader.close();
			    System.out.println("Loaded "+temprecords.size()+" records.");
			    return temprecords;
			} catch (FileNotFoundException e) {
				System.err.println("File Not Found.");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Generic IO exception.");
				e.printStackTrace();
			}
			
		}
		else{
			System.err.println("You have to select a file.");
		}
		return null;
		
	}

}
