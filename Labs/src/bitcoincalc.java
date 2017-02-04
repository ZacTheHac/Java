import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class bitcoincalc {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		double difficulty;
		float hardwareCost;
		int hashRate;
		float costPerDay;
		long timePerBlock;
		long approxHashesNeeded;
		short currentPayout;
		float valueOfCoin;
		int daysToTry;
		float hardwareCostPerDay;
		while(true){
			System.out.println("Input the current difficulty.");
			String diff = input.nextLine();
			try{
				difficulty = Double.parseDouble(diff);
				break;
			}
			catch(Exception ex){
				System.out.println("Try again. it should be a float.");
			}
		}
		while(true){
			System.out.println("Input the cost of the hardware.");
			String hwc = input.nextLine();
			try{
				hardwareCost = Float.parseFloat(hwc);
				break;
			}
			catch(Exception ex){
				System.out.println("Try again. it should be a float.");
			}
		}
		while(true){
			System.out.println("Input the hashrate capable of the cards, in H/s.");
			String hr = input.nextLine();
			try{
				hashRate = Integer.parseInt(hr);
				break;
			}
			catch(Exception ex){
				System.out.println("Try again. it should be an int.");
			}
		}
		while(true){
			System.out.println("Input the cost to run the rig for a day.");
			String cost = input.nextLine();
			try{
				costPerDay = Float.parseFloat(cost);
				break;
			}
			catch(Exception ex){
				System.out.println("Try again. it should be a float.");
			}
		}
		while(true){
			System.out.println("Input the current payout in BC.");
			String payout = input.nextLine();
			try{
				currentPayout = Short.parseShort(payout);
				break;
			}
			catch(Exception ex){
				System.out.println("Try again. it should be an int less than 255 (approx. 5).");
			}
		}
		while(true){
			System.out.println("Input the current value of a bitcoin");
			String val = input.nextLine();
			try{
				valueOfCoin = Float.parseFloat(val);
				break;
			}
			catch(Exception ex){
				System.out.println("Try again. it should be a float.");
			}
		}
		while(true){
			System.out.println("How many days are you going to use this rig?");
			String days = input.nextLine();
			try{
				daysToTry = Integer.parseInt(days);
				break;
			}
			catch(Exception ex){
				System.out.println("Try again. it should be an integer.");
			}
		}
		input.close();
		//done with inputs
		approxHashesNeeded = (long) ((difficulty * Math.pow(2,32))/600);
		//Math.
		System.out.println("You need to do approx. "+approxHashesNeeded+" hashes to break a single block.");
		timePerBlock = (long) ((difficulty * Math.pow(2, 32))/hashRate);
		System.out.println("At your speed of "+hashRate+"H/s, it will take approximately "+getTimeFromSeconds(timePerBlock));
		System.out.println("This would earn you $"+currentPayout*valueOfCoin);
		hardwareCostPerDay = hardwareCost/daysToTry;
		float hashCost = ((hardwareCostPerDay/24/60/60)+(costPerDay/24/60/60))*timePerBlock;
		System.out.println("During this time, it will cost you $"+hashCost);
		float debt = (currentPayout*valueOfCoin)-hashCost;
		long secondsToTry = daysToTry*24*60*60;
		float totalGains = 0;
		for(long time = 0;time<=secondsToTry;time+=timePerBlock){
			totalGains += debt;
		}
		if(debt<0){
			System.out.println("You are spending more money on the rig than it will earn you.");
			System.out.println("You are $"+Math.abs(debt)+" in debt for every block you break, losing you $"+Math.abs(totalGains));
		}
		else{
			System.out.print("You will earn $"+debt+" every block you break, ");
			System.out.println("bringing you to a grand total of $"+totalGains);
		}
	}
	
	public static String getTimeFromSeconds(long seconds){
		long days = TimeUnit.SECONDS.toDays(seconds);
		long hours = TimeUnit.SECONDS.toHours(seconds - (days*24*60*60));
		long minutes = TimeUnit.SECONDS.toMinutes(seconds - (days*24*60*60) - (hours*60*60));
		seconds = (seconds - (days*24*60*60) - (hours*60*60) - (minutes*60));
		
		String output = "";
		if(days > 0){
			output += days+" Day";
			if(days > 1){
				output += "s";
			}
			if(hours > 0||minutes > 0||seconds > 0){
				output += ", ";
			}
			else{
				output += ".";
			}
		}
		if(hours > 0){
			output += hours+" Hour";
			if(hours > 1){
				output += "s";
			}
			if(minutes > 0||seconds > 0){
				output += ", ";
			}
			else{
				output += ".";
			}
		}
		if(minutes > 0){
			output += minutes+" Minute";
			if(minutes > 1){
				output += "s";
			}
			if(seconds > 0){
				output += ", ";
			}
			else{
				output += ".";
			}
		}
		if (seconds > 0&&(minutes > 0||hours > 0||days > 0)){
			output += "and "+seconds+" Second";
			if(seconds > 1){
				output += "s.";
			}
			else{
				output += ".";
			}
		}
		else if(seconds > 0){
			output += seconds+" Second";
			if(seconds > 1){
				output += "s.";
			}
			else{
				output += ".";
			}
		}
		return output;
	}

}
