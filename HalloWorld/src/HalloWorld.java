import java.util.Scanner;

//import javax.swing.JOptionPane;



public class HalloWorld {

	public static void main(String[] args) {
		System.out.println("Ello!");
		System.out.println(System.out.toString());
		
		//scanner
		Scanner inputDevice = new Scanner(System.in);
		System.out.println("input something:");
		System.out.println("You input \""+inputDevice.nextLine()+"\". Why would you do that?");
		inputDevice.close();
		
		//JOptionPane.showMessageDialog(null, "yet again, I don't know what I'm doing. this is a lie.");
		/*
		String CurVer = JOptionPane.showInputDialog("Current Version");
		String FndVer = JOptionPane.showInputDialog("Found Version");
		if(CurVer == null || FndVer == null){
			System.out.println("Well you're not very fun.");
		}
		else{
			Boolean IsNewer = UpdateCheck.IsVersionNewer(CurVer, FndVer);
			if(IsNewer){
				System.out.println("Found version, "+ FndVer + ", is newer than the current version: " + CurVer);
			}
			else{
				System.out.println("Current version, "+ CurVer + ", is newer than the found version: " + FndVer);
			}
		}
		*/
		UpdateCheck.Join();
		//JOptionPane.showMessageDialog(null, IsNewer.toString());

	}

}
