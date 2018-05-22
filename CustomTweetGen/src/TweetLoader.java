import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.csv.*;

public class TweetLoader {
	
	public void ArchiveLoad(String filepath, ArrayList<Tweet> tweets, boolean RemoveRetweets, boolean RemoveQuoteTweets) {
		try {
			FileInputStream is = new FileInputStream(filepath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			CSVParser parser = CSVParser.parse(br, CSVFormat.EXCEL.withHeader());
			for (CSVRecord csvRecord : parser) {
				if(RemoveRetweets) {
					if(!csvRecord.get(6).isEmpty()) {
						continue;
					}
				}
				if(RemoveQuoteTweets) {
					if(csvRecord.get(6).isEmpty() && csvRecord.get(9).contains("/status/") && !csvRecord.get(9).contains(csvRecord.get(0))) {
						//make sure it's not a pure retweet, that the URL it references contains a status, and that that status isn't self-referential
							continue;
					}
				}
				
				tweets.add(new Tweet(csvRecord));
			}
		} catch (Exception e) {
			//I do nothing ^^
		}
	}
		
		/*boolean isFirstLine = true;
		Tweet tempTweet;
		try(FileInputStream is = new FileInputStream(filepath)){
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				if(!isFirstLine) {
				//System.out.println(line);//debug code

				while(StringUtils.countMatches(line,"\",\"") != 9){//if it doesn't have the right number of sections, keep adding lines
					line += System.getProperty("line.separator") + br.readLine();
				}
				tempTweet = new Tweet(line);
				tweets.add(tempTweet); //parse the tweet when it looks right.
				System.out.println(tempTweet.text); //display what we think the text is.
				
				}
				else {
					isFirstLine = false; //throw away the first line as it's just column headers
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	*/

}
