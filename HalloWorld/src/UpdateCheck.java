
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

public class UpdateCheck {
	static ArrayList<String> servers = new ArrayList<String>();
	static String CurrentVersionHold = new String();
	
	public static void main(String[] args){
		CheckForUpdates();
	}
	
	public Boolean HasJoinedBefore(String Server){
		return servers.contains(Server);
	}
	

	public static void Join(){	
			Boolean firstLine = true;
			Boolean UpdateFound = false;
			for(String Line:UpdateCheck.CheckForUpdates()){
				if(firstLine){
					System.out.println(Line);
					firstLine=false;
					UpdateFound=true;
				}
				else
					System.out.println(Line);
			}
			if(UpdateFound){
				System.out.println("Originally designed as an update checker");
			}
	}

	public static String GetMcVersion(){//probably have to rip this out...
		String McVersion=null;
		try{
			McVersion=JOptionPane.showInputDialog("enter a value for MC Version"); //just a direct link as I dont have forge
		}
		catch(Exception e){
			System.out.println("Update Checker was unable to get the current MC version");
		}
		return McVersion;
	}
	
	public static boolean isInt(String str) {
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException nfe) {}
	    return false;
	}
	
	public static Boolean IsVersionNewer(String FoundVersion){
		if(CurrentVersionHold == null || CurrentVersionHold == ""){
			CurrentVersionHold = JOptionPane.showInputDialog("enter a value for current version");
		}
		return IsVersionNewer(CurrentVersionHold, FoundVersion);
	}

	
	public static Boolean IsVersionNewer(String CurrentVersion, String FoundVersion){//main constructor, but also used for testing
		//String CurrentVersion = metadata.version;
		if(CurrentVersion.equalsIgnoreCase(FoundVersion)){ //did you kno that == doesn't work this way on strings? it grabs where the string is, not the string
			System.out.println(CurrentVersion + " is the same as " + FoundVersion);
			return false;//better to say this here rather than itterate everything
		}
		List<String> CurVersion = new ArrayList<String>(Arrays.asList(CurrentVersion.split("\\.")));
		List<String> FndVersion = new ArrayList<String>(Arrays.asList(FoundVersion.split("\\.")));
		//ArrayList<String> FndVersion = (ArrayList<String>) Arrays.asList(FoundVersion.split("\\."));
		int listSize = 0;
		if(CurVersion.size()>FndVersion.size()||CurVersion.size()==FndVersion.size()){
			listSize = CurVersion.size();
		}
		else if(CurVersion.size()<FndVersion.size()){
			listSize = FndVersion.size();
		}
		System.out.println("# of sections found as "+ listSize);
		try{
			for(int i = 0; i<listSize ; i++){
				if(CurVersion.get(i).equalsIgnoreCase(FndVersion.get(i))){
					System.out.println(CurVersion.get(i) + " is equal to " + FndVersion.get(i)+"(position: "+ i + ")");
					continue;
				}
				if(isInt(CurVersion.get(i))&&isInt(FndVersion.get(i))){
					if(Integer.parseInt(CurVersion.get(i))>Integer.parseInt(FndVersion.get(i))){
						System.out.println(CurVersion.get(i) + " is greater than " + FndVersion.get(i)+"(position: "+ i + ")");
						return false;
					}
					else if(Integer.parseInt(CurVersion.get(i))<Integer.parseInt(FndVersion.get(i))){
						System.out.println(CurVersion.get(i) + " is less than " + FndVersion.get(i)+"(position: "+ i + ")");
						return true;
					}
				}
				else{//there's a letter in here
					if(CurVersion.get(i).length()==FndVersion.get(i).length()){
						System.out.println("I found a letter in section " + i + ". and both sides are the same length of " + FndVersion.get(i).length());
						try{
						for(int j = 0; j<CurVersion.get(i).length(); j++){
							if(CurVersion.get(i).charAt(j)==FndVersion.get(i).charAt(j)){
								System.out.println(CurVersion.get(i).charAt(j) + " is equal to " + FndVersion.get(i).charAt(j)+"(char: "+ j + " in section " + i + ")");
								continue;
							}
							else if(String.valueOf(CurVersion.get(i).charAt(j)).compareToIgnoreCase(String.valueOf(FndVersion.get(i).charAt(j)))>0){
								//WHAT A COMPARISON, this is if the CurVersion is newer
								System.out.println(CurVersion.get(i).charAt(j) + " is greater than " + FndVersion.get(i).charAt(j)+"(char: "+ j + " in section " + i + ")");
								return false;
							}
							else if(String.valueOf(CurVersion.get(i).charAt(j)).compareToIgnoreCase(String.valueOf(FndVersion.get(i).charAt(j)))<0){
								System.out.println(CurVersion.get(i).charAt(j) + " is less than " + FndVersion.get(i).charAt(j)+"(char: "+ j + " in section " + i + ")");
								return true;
							}
							else{
								return false; //just in case
							}
						}
						}
						catch(Exception e){
							return false;//something messed up, just ignore that one then
						}
					}
					else if(CurVersion.get(i).length()>FndVersion.get(i).length()){
						System.out.println("I found a letter in section " + i + ". and CurrentVersion is longer at " + CurVersion.get(i).length() + " chars, while FoundVersion is " + FndVersion.get(i).length() + " chars");
						try{
							for(int j = 0; j<CurVersion.get(i).length(); j++){
								if(CurVersion.get(i).charAt(j)==FndVersion.get(i).charAt(j)){
									System.out.println(CurVersion.get(i).charAt(j) + " is equal to " + FndVersion.get(i).charAt(j)+"(char: "+ j + " in section " + i + ")");
									continue;
								}
								else if(String.valueOf(CurVersion.get(i).charAt(j)).compareToIgnoreCase(String.valueOf(FndVersion.get(i).charAt(j)))>0){
									//WHAT A COMPARISON, this is if the CurVersion is newer
									System.out.println(CurVersion.get(i).charAt(j) + " is greater than " + FndVersion.get(i).charAt(j)+"(char: "+ j + " in section " + i + ")");
									return false;
								}
								else if(String.valueOf(CurVersion.get(i).charAt(j)).compareToIgnoreCase(String.valueOf(FndVersion.get(i).charAt(j)))<0){
									System.out.println(CurVersion.get(i).charAt(j) + " is less than " + FndVersion.get(i).charAt(j)+"(char: "+ j + " in section " + i + ")");
									return true;
								}
								else{
									return false; //just in case
								}
							}
						}
						catch(Exception e){
							//FndVersion ran out or something, meaning the current is probably a minor version above
							return false;
						}
					}
					else if(CurVersion.get(i).length()<FndVersion.get(i).length()){
						System.out.println("I found a letter in section " + i + ". and CurrentVersion is shorter at " + CurVersion.get(i).length() + " chars, while FoundVersion is " + FndVersion.get(i).length() + " chars");
						try{
							for(int j = 0; j<FndVersion.get(i).length(); j++){
								if(CurVersion.get(i).charAt(j)==FndVersion.get(i).charAt(j)){
									System.out.println(CurVersion.get(i).charAt(j) + " is equal to " + FndVersion.get(i).charAt(j)+"(char: "+ j + " in section " + i + ")");
									continue;
								}
								else if(String.valueOf(CurVersion.get(i).charAt(j)).compareToIgnoreCase(String.valueOf(FndVersion.get(i).charAt(j)))>0){
									//WHAT A COMPARISON, this is if the CurVersion is newer
									System.out.println(CurVersion.get(i).charAt(j) + " is greater than " + FndVersion.get(i).charAt(j)+"(char: "+ j + " in section " + i + ")");
									return false;
								}
								else if(String.valueOf(CurVersion.get(i).charAt(j)).compareToIgnoreCase(String.valueOf(FndVersion.get(i).charAt(j)))<0){
									System.out.println(CurVersion.get(i).charAt(j) + " is less than " + FndVersion.get(i).charAt(j)+"(char: "+ j + " in section " + i + ")");
									return true;
								}
								else{
									return false; //just in case
								}
							}
						}
						catch(Exception e){
							//curVersion ran out or something, meaning it's most likely a small update
							System.out.println("CurrentVersion ran out of sections, meaning the Found version is the same, but with an additional section, meaning it's probably a minor revision.");
							return true;
						}
					}
				}
			}
		}
		catch(Exception e){
			if(CurVersion.size()>FndVersion.size()||CurVersion.size()==FndVersion.size()){
				System.out.println("Current Version is longer, or the same length, and I ran into an error. I don't think the Found Version is newer");
				return false;
			}
			else if(CurVersion.size()<FndVersion.size()){
				System.out.println("Found Version is longer, and I ran into an error. I think the Found Version is newer");
				return true;
			}
			//most likely, this means everything was the same except for an additional minor revision, so the longer one was probably newer
		}
		System.out.println("You shouldn't get this far, so something went horribly wrong, I'm sending back a false");
		return false; //if it somehow got this far, just throw a false back
	}

	public static List<String> CheckForUpdates() {
		List<String> updateFound = new ArrayList<String>();
		String McVersion=GetMcVersion();
		if(McVersion==null){
			return null;//something broke
		}
		
		//ArrayList<String> updatefound = new ArrayList<String>();
		Boolean ChangeLogRead = false;
		String updatedVersion = "";
		URL url = null;
		try {
			System.out.println("Begining to read update file.");
			long startTime = System.currentTimeMillis();
			url = new URL("https://dl.dropboxusercontent.com/u/35081624/git/SampleUpdate.txt");//direct link = less headache
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String Line;
			String[] Data = new String[2];
			String VersionHolder = "";
			System.out.println("Update URL set to "+url.getPath());
			System.out.println("It took "+(System.currentTimeMillis()-startTime)+"ms to load the file");

			while ((Line = in.readLine()) != null) {
				if(Line.startsWith("//")){
					System.out.println("Comment Line: "+Line);
					continue; //allow us to leave notes inside the update file
				}
				if(Line.startsWith("\t")){	
					System.out.println("(Changelog) "+Line.substring(Line.indexOf("\t")+1));
					if(ChangeLogRead){
						System.out.println("Reading Changelog for " + updatedVersion);
						Line=Line.substring(Line.indexOf("\t")+1);
						if(!updatedVersion.equals(VersionHolder)){
							updateFound.add("["+updatedVersion+"]");
							VersionHolder=updatedVersion;
						}
						updateFound.add("     "+Line);
						continue;
					}
					else{
						continue;
					}
				}
				else{
					ChangeLogRead=false;
					System.out.println("Changelog reading ended. (this does not mean the end of the file)");
				}
				
				Data=Line.split(":");
				System.out.println("New data block found: " + Data[0]+":"+Data[1]);
				if(Data[0].equalsIgnoreCase(McVersion)||McVersion.startsWith(Data[0])){
					System.out.println("MC version of "+Data[0]+ " is compatable");
					if(IsVersionNewer(Data[1])){
						if(updatedVersion.equals("")){//there was no previous new version found, but one was just found
							updateFound.add("New version of "+"null()"+" found! Changelog:");
						}
						ChangeLogRead=true;
						updatedVersion = Data[1];
					}
				}
			}
			System.out.println("End of file reached. (either that, or the process got handed a null string)");
			in.close();
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException error1) {
			return null;
		} catch (Exception error) {
			return null;
		}
		
		System.out.println(updateFound.toString());
		return updateFound;
	}
}

//Update Checker By ZacTheHac