package dieGames;

import javax.swing.JOptionPane;

//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
//* Class name      : PigDiceGame2                                      *
//*                                                                     *
//* Written by      : Zachary Muerle (C) 2014, All rights reserved      *
//*                                                                     *
//* Purpose         : a version of the dice game Pig					*
//*                   The object of the game is to be the first			*
//*						to score 100 points. However: now it's more	    *
//*                     of a gamble: if they roll a 1, the points from  *
//*                     that round are removed from their score	        *
//*                                                                     *
//* Inputs          : yes or no, if they want to continue rolling	    *
//*                   						                            *
//*                                                                     *
//* Outputs          : strings detailing the events of the game			*
//* 								                                    *
//*                                                                     *
//* Methods         : main(String[] args)							    *
//*                                                                     *
//*---------------------------------------------------------------------*
//* Change Log:                                                         *
//*                         Revision                                    *
//*       Date    Changed  Rel Ver Mod Purpose                          *
//* 09/11/14      ZMuerle  000.000.000 Initial release of program       *
//* 09/16/14      ZMuerle  000.001.000 fixed the round score not reseting*
//* 09/16/14      ZMuerle  000.001.001 let the player know their risk	*
//* 09/16/14      ZMuerle  000.002.000 added smarter ai for computer	*
//* 09/18/14      ZMuerle  000.003.000 fixed up the AI to be better		*
//* 09/25/14      ZMuerle  000.004.000 more tweaks to the AI, and give	*
//*										Recommendations to the player	*
//*                                                                     *
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *


public class PigDiceGame2 {//ch6 GZ4b

	static long WaitTime = 500;
	static boolean SlowPrint = true;
	
	public static void main(String[] args) {
		short playerScore = 0;//hold the player's score
		short compScore = 0;//the computer's score
		short roundScore = 0;//how much the player earned this round
		Die dieOne = new Die();
		Die dieTwo = new Die();//2 dice to play with
		boolean finished = false;//is the game finished?
		boolean plyTurn = true;//who's turn is it? true = ply, false = comp
		boolean newAI = true;
		int PrintChoice = JOptionPane.showConfirmDialog(null, "Would you like to print the output slowly?", "Slow down gameplay?", JOptionPane.YES_NO_OPTION);
		if(PrintChoice == JOptionPane.YES_OPTION){
			SlowPrint = true;
		}
		else{
			SlowPrint=false;
		}
		int AIChoice = JOptionPane.showConfirmDialog(null, "Would you like to try the new AI?", "Use AI?", JOptionPane.YES_NO_OPTION);
		if(AIChoice == JOptionPane.YES_OPTION){
			newAI = true;
		}
		else{
			newAI=false;
		}
		Object[] options = {"Roll","Pass","AI decision"};
		Object lastChoice = options[2];//by default it just lets the AI take over. kinda difficult having the AI fight the AI
		
		while(!finished){//game loop
			dieOne.reRoll();
			dieTwo.reRoll();//randomize the 2 die
			if(dieOne.getValue() != 1 && dieTwo.getValue() != 1){//if neither of them are 1
				roundScore += dieOne.getValue() + dieTwo.getValue();//adds the 2 scores to the round score
				if(plyTurn){
					println("Player earns " + (dieOne.getValue() + dieTwo.getValue()) + " points!");
					println("Player's points this round: "+roundScore);
					//I could keep the check in here to see if they've already won, but maybe someone wants
					//to risk it all, just to rub it in the computer's face. or lose it all.
					String reccomend = "";//let the computer make a reccomendation.
					boolean recPass = false;
					if(ShouldPass(playerScore,roundScore, compScore)){//let the player have the AI on their side as well.
						reccomend = "We advise you to pass.";
						recPass = true;
					}
					else{
						reccomend = "We reccomend you keep rolling.";
					}
					
					Object choice = JOptionPane.showInputDialog(null, "Would you like to keep rolling? \n\n You will be risking "+roundScore+" points. \nYou have "+playerScore+" points. \nYou will have "+(playerScore + roundScore)+" points if you pass now. \n\nYour Opponent has "+compScore+" points.\n\n\n"+reccomend, "Keep rolling?", JOptionPane.QUESTION_MESSAGE, null, options, lastChoice);
					//create an option pane w/ yes and no, store the result as "choice"
					lastChoice = choice;//so if they have a favorite, they don't have to switch every time.
					if(choice == options[2]){
						if(recPass){
							choice = options[1];
						}
						else{
							choice = options[0];
						}
					}
					if(choice == options[0]){//if they chose to keep rolling
						println("Player chose to roll again, risking "+roundScore+" points!");
					}
					else if(choice == options[1]){//if they chose to pass
						println("Player chose to pass their turn.");
						playerScore += roundScore;//give the player this round's score
						println("Player earns "+roundScore+" points, bringing them up to "+playerScore+" total");
						System.out.println();
						roundScore = 0;//reset this round's score
						plyTurn = false;//pass to the computer
					}
					else{//result wasn't yes or no, so I'll take that as a no. - they prob. just closed the message
						println("You closed the option box. Taking that as a \"no\".");
						playerScore += roundScore;//add the round's score to the player's score
						println("Player earns "+roundScore+" points, bringing them up to "+playerScore+" total");
						System.out.println();
						roundScore = 0;//reset the round's score
						plyTurn = false;//pass to the computer
					}
				}
				else{
					println("Computer earns " + (dieOne.getValue() + dieTwo.getValue()) + " points!");
					println("Computer's points this round: "+roundScore);
					boolean shouldPass = true;
					if(newAI){//if we're using the better AI
						shouldPass=ShouldPass(compScore, roundScore, playerScore); //just use this method to see if the comp should pass
					}
					else{
						//however: the computer is not much smarter. it'll just keep rolling, even if it can win
						double rand = Math.random();//generate a random number between 0 and 1
						//it's tempting to make a more advanced AI that chooses "no" more often when it gets a higher and higher score
						if(rand >= 0.5){
							//keep rolling
							shouldPass = false;
						}
						else{
							//pass turn
							shouldPass = true;
						}
					}
					if(shouldPass){
						//pass turn
						println("Computer chose to pass their turn.");
						compScore += roundScore; //add the roundscore to their running total
						println("Computer earns "+roundScore+" points, bringing them up to "+compScore+" total");
						System.out.println();
						roundScore = 0;//reset the round score
						plyTurn = true;//make it the player's turn
					}
					else{
						//keep rolling
						println("Computer chose to keep rolling, risking "+roundScore+" points!");
					}
				}
			}
			else if(dieOne.getValue() == 1 && dieTwo.getValue() == 1){//both die are 1
				//reset that player's score to 0 & pass their turn
				roundScore = 0;//reset the round's score
				if(plyTurn){
					playerScore = 0;//reset their score
					plyTurn = false;//switch turns to computer
					System.out.println();
					println("Turn passed to the Computer, as the player rolled both 1s, resetting their score");
				}
				else{
					compScore = 0;//reset the computer's score
					plyTurn = true;//pass turn to player
					System.out.println();
					println("Turn passed to the Player, as the computer rolled both 1s, resetting their score");
				}
				
			}
			else{//one of the die showed a 1, pass the turn to the other player
				if(plyTurn){//if it is the player's turn
					plyTurn = false;//make it not
					System.out.println();
					println("Turn passed to the Computer, as the player rolled a 1, losing out on "+roundScore+" points.");
					roundScore = 0;//reset the round's score
				}
				else{//if it's the computer's turn
					plyTurn = true;//pass it to the player
					System.out.println();
					println("Turn passed to the Player, as the computer rolled a 1, losing out on "+roundScore+" points.");
					roundScore = 0;//reset the round's score
				}
				
			}
			if(playerScore >= 100 || compScore >= 100){
				//someone has a score of 100+, end the game
				finished = true;
			}
			
		}//end of while loop
		
		if(playerScore >= 100){//if it's the player that won
			println("The player wins with " + playerScore + " points! " + (playerScore-compScore) + " points more than the computer!");
		}
		else if(compScore >= 100){//if the computer won
			println("The computer wins with " + compScore + " points! " + (compScore-playerScore) + " points more than the player!");
		}
		else{//somehow neither won, but it broke out of the loop...
			println("Please restart the program. something broke.");
		}

	}
	
	public static boolean ShouldPass(int CurrentScore, int RoundScore, int PlayerScore){
		//should add in some more ideas: 
		//more heavy biasing toward risking small amounts of points, 
		//checking if other player is likely to win next round (say within ~15 points?) and maybe being more risky if they have a chance?
		//allow a higher change of the AI just charging ahead instead of rubber-banding?
		
		if((RoundScore+CurrentScore) >=100){
			return true;//if the comp will win if it passes, tell it to pass.
		}
		else if(RoundScore >= 35){//don't risk more than 35 points, no matter what
			return true;
		}
		else{
			//40/RoundScore = chance of passing?
			//still semi-random, just change bounds
			double rand = Math.random();
			double riskFactor = 1-(double)RoundScore/35d;//basic calculated risk
			double desperation = 0;//the computer risks more if the player is beating it.
			if(CurrentScore > PlayerScore+5){
				//if the computer is winning by 5+ pts, no matter this score
				desperation = 0;
			}
			else if((CurrentScore+RoundScore) > PlayerScore){
				//if the computer will gain on the player if it earns these points
				//semi-desperate, but not terribly
				desperation = (PlayerScore - (CurrentScore + RoundScore))/20d;
				//should be negative, making the computer want to pass to earn the points
			}
			else{
				//the computer won't catch up, even with the current points
				desperation = (PlayerScore - (CurrentScore + RoundScore))/20d;
				//how much the player is beating the AI/30
				//if the player is winning by more than 30, pretty much guarantee the computer will keep rolling
			}
			if(desperation > 1){
				desperation = 1; //otherwise the AI goes a bit insane on the risk-taking if the player is really winning
			}
			
			double riskDespAvg = (riskFactor+desperation)/2;//takes the average of risk and desperation
			//System.out.println("risk ="+riskFactor);
			//System.out.println("desperation ="+desperation);
			//System.out.println("riskDespAvg ="+riskDespAvg);
			//System.out.println("rand ="+rand);
			if(rand >= riskDespAvg){//the lower the avg, the more often we should pass (high risk and/or low desperation) 
				return true;  //pass turn
			}
			else{//rand is below the risk factor
				return false; //don't skip
			}
		}
	}
	
	public static void println(String output){
		System.out.println(output);
		if(SlowPrint)
			try {
				Thread.sleep(WaitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
