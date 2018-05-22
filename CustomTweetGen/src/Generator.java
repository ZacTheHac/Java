import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import twitter4j.*;
import twitter4j.auth.*;
import twitter4j.conf.ConfigurationBuilder;

//given a database of tweets
	//grab one to start.
	//pick any sentence inside of it to begin
	//every word has a 25% chance of splitting
	//when it splits, it looks for another tweet with the same word, and begins working there
	//given how many tweets are likely to be had, loading it into RAM is simply not a possibility.
	//maybe have the database's first few lines be data about it
	//such as how many tweets are actually contained in it, so we know how many lines to choose from
	//what about multi-line tweets? Would have to previously replace them with character controls?
	//The end of a sentence may have a different chance of branching, but it's also possible to stay on the same original tweet.
	//make sure we stop before 140 characters
	//how do I guarentee that on a branching path? EEHHHHHHHHH I dunno without complicated search patterns that would fail a lot. Probably just cut it.
	//have a high chance of just ending a tweet at the end of a sentence?
	//probably make an array of "faces" so that I know to treat them differently, because they may have periods or anything in them.
	//maybe randomly insert them with a very low chance after a sentence?
	
	//new info: my main twitter archive is <3MB total. I can slam it all into RAM EASILY.
	
	//parsing notes: newlines are preserved, so reading line by line isn't entirely viable.
	//	Quote marks are double quote marks: ""
	//	commas are just commas. Thankfully, all the separators are "," so we can pick those out easily enough
	//	Anything after 'RT @' that has a RT URL isn't my own writing, so it should be discarded. (even pure RTs are formatted like this)
	//		This is actually rather difficult. fix problems like "@DonryuART @" being caught
	//			Edge cases: check if 'RT @' is the very first 4 chars, ignore it. if it's not, check before it: is there a space?
	//			Case sensitivity matters a lot here
	//	Replace all HTML fuckery back with the proper characters (&amp; = &, &lt; = <, etc...)
	//	When importing from the file, count the number of ","s to make sure you have a complete tweet (should be 9). if not, grab the next line and check. continue appending.
//note on parsing: apache actually made a really nice csv loading library. I just use that...
	
	//feature creep: 
	//	Allow the program to accept an input word to start the tweet generation process. If it's empty, make one at random
	//	keep a list of all the tweets used to form the final tweet and list them: url and content
	

public class Generator {
	public static Random rand = new Random();
	public static String[] faces = {":<",":)",";)",":(",":'(",
			">.>","<.<",">.<",">//<",">///<",">//>",">///>","<//<","<///<",">_>","<_<",">->","<-<",
			";~;",";-;",
			":3",":3c",":3€",";3",";3€",";3c",
			":P",":p",
			":x",":/",":\\",
			"^^","^^;",
			":T",":L",
			":o",
			"XD","XP",":D","D:","DX",
			"u.u","v.v","<3","</3","-_-",
			"¯\\_(ツ)_/¯","( ͡° ͜ʖ ͡°)","ಠ_ಠ","(¬‿¬)","(╯°□°）╯︵ ┻━┻"}; //just a big list of all the faces I can think that I've used.
	public static int MaxTweetLength = 280;
	public static boolean SendTweet = false;

	public static void main(String[] args){
		ArrayList<Tweet> UsedTweets = new ArrayList<Tweet>();
		ArrayList<Tweet> vanillaTweets = new ArrayList<Tweet>();
		ArrayList<Tweet> WorkTweets = new ArrayList<Tweet>();
		Tweet currTweet = null;
		TweetLoader Loader = new TweetLoader();
		int n = 0;
		int length = 0;
		String strWorkArea = "";
		String AiTweet = "";
		boolean notDone = true; //this will be true until I evaluate that the tweet is done composing.
		String NextWord = "";

		Loader.ArchiveLoad("C:\\Users\\john\\Desktop\\22 Oct 17 MWM Twitter Archive\\tweets.csv", vanillaTweets, true, false);
		Loader.ArchiveLoad("C:\\Users\\john\\Desktop\\sundige archive 10-14-17\\tweets.csv", vanillaTweets, true, false);
		Loader.ArchiveLoad("C:\\Users\\john\\Desktop\\blass archive 10-14-17\\tweets.csv", vanillaTweets, true, false);
		
		System.out.println("Database Loaded! "+vanillaTweets.size()+" tweets loaded!");
		Scanner keyInput = new Scanner(System.in);
		String input = keyInput.nextLine();
		if(!SendTweet)
			keyInput.close();

		if(input.isEmpty()) {
		do{
			n = rand.nextInt(vanillaTweets.size());
			currTweet = vanillaTweets.get(n);
			length = currTweet.CountWords(true);
		}while(length == 0);
			strWorkArea = RandomFirstWord(currTweet);
			AiTweet = strWorkArea;
			UsedTweets.add(currTweet);
		}
		else {
			AiTweet = input;
			//assign it an arbitrary tweet to work with
			GetTweetsWithWord(GetLastWord(AiTweet), vanillaTweets, WorkTweets);
			if(WorkTweets.size() == 0) {
				//no tweets contain that
				System.err.println("I can't work with that");
				keyInput.close();
				return;
			}
			else {
				n = rand.nextInt(WorkTweets.size());
				currTweet = WorkTweets.get(n);
				UsedTweets.add(currTweet);
			}
		}
		//we have the first word
		
		
		while (notDone) {
			
			if(EndsWithEndingMark(AiTweet)) {
				do{
					n = rand.nextInt(vanillaTweets.size());
					currTweet = vanillaTweets.get(n);
					length = currTweet.CountWords(true);
				}while(length == 0);
					strWorkArea = RandomFirstWord(currTweet);
					AiTweet += " " + strWorkArea;
					UsedTweets.add(currTweet);
			}//Act like we're starting fresh.
			
			//should we branch?
			n = rand.nextInt(2);
			if(n==1) {//branch
				System.out.println("(BRANCH)");
				GetTweetsWithWord(GetLastWord(AiTweet), vanillaTweets, WorkTweets);
				//System.out.println(WorkTweets.size());
				n = rand.nextInt(WorkTweets.size());
				currTweet = WorkTweets.get(n);
				UsedTweets.add(currTweet);
			}
			NextWord = currTweet.GetWordAfter(GetLastWord(AiTweet), true);
			if(NextWord.equals("")){
				if(notDone(AiTweet) == true) {//should we just end it there? no?
					continue; //just go again.
				}
				else {//just end it there
					notDone = false;
					continue;
				}
			}
			else {
				AiTweet += " " + NextWord; //append the next word
			}
			
			

			// check if we should finish
			notDone = notDone(AiTweet);
			System.out.println(AiTweet);
		}
		
		System.out.println();
		System.out.println("Used Tweets:");
		for(Tweet twit:UsedTweets) {
			System.out.println(twit.toString());
		}
		
		if(SendTweet) {
			System.out.println();
			System.out.println("Send tweet: \""+AiTweet+"\"?");
			input = keyInput.nextLine();
			keyInput.close();
			if(input.isEmpty() || input.equalsIgnoreCase("no")) {
				System.out.println("Oh. I guess it was too shitty...");
			}
			else {
				SendTweet(AiTweet);
			}
		}

		

	}
	
	public static boolean SendTweet(String tweet) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("1b6VfsLJh2kAFuP9c6FdBXqfW")
				.setOAuthConsumerSecret("NJkBuRENykyZIj1MRLQ9405pzFtWY96dIOo0fDoVoxLrEkZr99")
				.setOAuthAccessToken("64996357-LoC5ppqPRrQyDru301VLSGQCIjgl2fnlDUVcvG1P9")
				.setOAuthAccessTokenSecret("8cvSOqYRKhAJe9DcV6tg2hizmp4DywbOVwplzf5JmTGhF");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		try {
			Status status = twitter.updateStatus(tweet);
			System.out.println("Status posted: " + status.getText());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String RandomFirstWord(Tweet randTweet) {
		ArrayList<String> PossibleWords = new ArrayList<String>();
		String WorkText = randTweet.text;
		WorkText = WorkText.replaceAll("https?://\\S+\\s?", "");
		
		String[] SplitWords = StringUtils.split(WorkText);
		PossibleWords.add(SplitWords[0]);
		for(int i = 1;i<SplitWords.length;i++) {
			if(Character.isUpperCase(SplitWords[i].charAt(0))) {
				PossibleWords.add(SplitWords[i]);
			}
			if(EndsWithEndingMark(SplitWords[i])&& i < (SplitWords.length - 1)) {
				PossibleWords.add(SplitWords[i+1]);
			}
			if(StartsWithStartingMark(SplitWords[i])) {
				PossibleWords.add(SplitWords[i]);
			}
		}
		//grab any words after newlines
		int index = WorkText.indexOf("\n");
		while(index != -1) {
			int index2 = WorkText.indexOf(" ",index);
			if(index2 == -1) {
				index2 = WorkText.indexOf("\n",index);
			}
			if(index2 == -1) {
				index2 = WorkText.indexOf("\t",index);
			}
			if(index2 == -1) {
				index2 = WorkText.indexOf("\r",index);
			}
			if(index2 == -1) {
				index2 = WorkText.length()-1;
			}
			
			PossibleWords.add(randTweet.text.substring(index, index2));
			WorkText = WorkText.substring(index2);
			index = WorkText.indexOf("\n");
		}
		
		
		int n = rand.nextInt(PossibleWords.size());
		return PossibleWords.get(n);
		
	}
	
	public static boolean notDone(String AiTweet) {
		int n = 0;
		if (AiTweet.length() > MaxTweetLength) {// it's too long
			System.err.println("(TOO LONG)");
			return false;
		}else if(AiTweet.length() == MaxTweetLength) {
				System.err.println("(Just right)");
				return false;
		} else if(EndsWithEndingMark(AiTweet)) {// give it a 50/50 of making another sentence.
			n = rand.nextInt(2);
			if (n == 1) {
				return false;
			}
		} else {// just a random 1% chance of ending
		/*	n = rand.nextInt(99);
			if (n == 19) {
				return false;
			}*/
		}
		return true;
	}
	
	public static boolean EndsWithFace(String text) {
		if(text.matches(".*([\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee])")) {
			return true;
		}
		else {		
			return StringUtils.endsWithAny(text, faces);
		}
		
		
	}
	
	public static void GetTweetsWithWord(String word, ArrayList<Tweet> AllTweets,ArrayList<Tweet> workspace) {
		workspace.clear();
		for(Tweet twit:AllTweets) {
			if(StringUtils.containsIgnoreCase(twit.text, " "+word+" ")) {//make sure there are spaces around it so it's its own word
				workspace.add(twit);
			}
		}
		
	}
	
	public static String GetLastWord(String sentence){
		String[] SentenceArray = StringUtils.split(sentence);
		if(SentenceArray.length == 0) {
			return "";
		}
		else {
			return SentenceArray[SentenceArray.length-1];
		}
		
	}
	
	public static boolean EndsWithEndingMark(String AiTweet) {
		return (AiTweet.endsWith(".") || AiTweet.endsWith("!") || AiTweet.endsWith("?") || AiTweet.endsWith("\"") || AiTweet.endsWith("”") //|| AiTweet.endsWith(",") //might need to rethink the comma. I want it to branch, but not end
				|| AiTweet.endsWith(")") || AiTweet.endsWith("~") || 
				EndsWithFace(AiTweet));
	}
	
	public static boolean StartsWithStartingMark(String word) {
		return (word.startsWith("\"")||word.startsWith("“")||word.startsWith("("));
	}

}
