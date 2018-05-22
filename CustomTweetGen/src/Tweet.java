import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

public class Tweet {
	Random rand = new Random();
	public String Tweet_ID; //the identifier for this tweet
	public String in_reply_to_status_id; //the tweet it's replying to
	public String in_reply_to_user_id; //their user ID
	public String timestamp; //yeah, I'm not bothering to parse that... I don't need it
	public String source; //the app used to post the tweet
	public String text; //the actual tweet, the only thing we care about...
	public String retweeted_status_id; //this is only filled if the tweet was 100% a retweet
	public String retweeted_status_user_id; //same
	public String retweeted_status_timestamp; //same
	public String expanded_urls;	//since twitter likes to obscure stuff behind their own URLs, here's what it ACTUALLY points to
	//new quote tweets just put the URL to the quoted tweet.
	

	Tweet(String Tweet_ID,String in_reply_to_status_id,String in_reply_to_user_id,String timestamp,String source,String text,String retweeted_status_id,String retweeted_status_user_id,String retweeted_status_timestamp,String expanded_urls){
		this.Tweet_ID = Tweet_ID;
		this.in_reply_to_status_id = in_reply_to_status_id;
		this.in_reply_to_user_id = in_reply_to_user_id;
		this.timestamp = timestamp;
		this.source = source;
		this.text = text;
		this.retweeted_status_id = retweeted_status_id;
		this.retweeted_status_user_id = retweeted_status_user_id;
		this.retweeted_status_timestamp = retweeted_status_timestamp;
		this.expanded_urls = expanded_urls;
	}
	
	Tweet(CSVRecord record){
		this.Tweet_ID = record.get(0);
		this.in_reply_to_status_id = record.get(1);
		this.in_reply_to_user_id = record.get(2);
		this.timestamp = record.get(3);

		//source
		this.source = StringUtils.substringBetween(record.get(4),">", "<");
		//the actual tweet
		this.text = StringEscapeUtils.unescapeHtml4(record.get(5));
		
		this.retweeted_status_id = record.get(6);
		this.retweeted_status_user_id = record.get(7);
		this.retweeted_status_timestamp = record.get(8);
		this.expanded_urls = record.get(9);
	}
	
	Tweet(String CSVentry){
		//parse a csv entry string
		String holder = "";
		int startIndex = 0;
		int endIndex = 0;
		
		for(int i = 0; i < 9; i++){
			holder = "";
			startIndex = CSVentry.indexOf("\"");
			endIndex = CSVentry.indexOf("\",\"", startIndex+1);
			holder = CSVentry.substring(startIndex+1, endIndex);
			CSVentry = CSVentry.substring(endIndex+2);
			
			switch(i){
			case 0:
				this.Tweet_ID = holder;
				break;
			case 1:
				this.in_reply_to_status_id = holder;
				break;
			case 2:
				this.in_reply_to_user_id = holder;
				break;
			case 3:
				this.timestamp = holder;
				break;
			case 4://source. I'm gonna strip off the HTML
				startIndex = holder.indexOf(">");
				endIndex = holder.indexOf("<", startIndex);
				this.source = holder.substring(startIndex+1, endIndex);
				break;
			case 5:
				this.text = StringEscapeUtils.unescapeHtml4(holder);
				//this.text = holder;
				break;
			case 6:
				this.retweeted_status_id = holder;
				break;
			case 7:
				this.retweeted_status_user_id = holder;
				break;
			case 8:
				this.retweeted_status_timestamp = holder;
				break;
			case 9:
				this.expanded_urls = holder;
				break;
				
			}
		}
		//end of loop, the rest is the expanded URLS
		startIndex = CSVentry.indexOf("\"");
		endIndex = CSVentry.indexOf("\"",startIndex+1);
		holder = CSVentry.substring(startIndex+1, endIndex);
		this.expanded_urls = holder;
	}
	
	public int CountWords(boolean stripURLs) {
		String workText = this.text;
		if (stripURLs) {
			workText = workText.replaceAll("https?://\\S+\\s?", "");
		}
		String[] splitText = StringUtils.split(workText);
		return splitText.length;
	}
	public int CountChars(boolean stripURLs) {
		String workText = this.text;
		if (stripURLs) {
			workText = workText.replaceAll("https?://\\S+\\s?", "");
		}
		return workText.length();
	}
	public String getNthWord(int n, boolean stripURLs) {
		String workText = this.text;
		if (stripURLs) {
			workText = workText.replaceAll("https?://\\S+\\s?", "");
		}
		String[] splitText = StringUtils.split(workText);
				//workText.split(" ");
		if(n>splitText.length)
			return " ";
		return splitText[n];
	}
	public String GetWordAfter(String word, boolean stripURLs) {
		String workText = this.text;
		if (stripURLs) {
			workText = workText.replaceAll("https?://\\S+\\s?", "");
		}
		String[] splitText = StringUtils.split(workText);
		ArrayList<String> PossibleWords = new ArrayList<String>();
			for(int i = 0;i<splitText.length;i++) {
				if(splitText[i].equalsIgnoreCase(word)) {
					i++;
					PossibleWords.add(splitText[i]);
				}
			}
			PossibleWords.trimToSize();
			if(PossibleWords.size() == 1) {
				return PossibleWords.get(0);
			}
			else if(PossibleWords.size() >1){
				int n = rand.nextInt(PossibleWords.size());
				return PossibleWords.get(n);
			}
			else {
				return "";
			}
	}
	
	public String toString() {
		return "ID: "+this.Tweet_ID + " @"+ this.timestamp + " \""+this.text+"\"";
		
	}
	
	
}
