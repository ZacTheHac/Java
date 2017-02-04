package Clocks;

public class HMSClock extends Clock{
	HMSClock(short hours, short minutes, short seconds){
		if(hours>23||minutes>59||seconds>59){
			throw new IndexOutOfBoundsException("That's not a valid time");
		}
		else{
			this.Hours = hours;
			this.Minutes = minutes;
			this.Seconds = seconds;
		}
	}
	public long getSecondsDifference(Clock clock){
		long thisSec = this.getSeconds();
		long secSec = clock.getSeconds();
		thisSec += (this.getMinutes() * 60) + (this.getHours() * 60 * 60);
		secSec += (clock.getMinutes() * 60) + (clock.getHours() * 60 * 60);
		return thisSec-secSec;
	}
	public Clock getDifference(Clock secondClock){
		return new HMSClock((short)(this.getHours()-secondClock.getHours()), (short)(this.getMinutes()-secondClock.getMinutes()), (short)(this.getSeconds()-secondClock.getSeconds()));
	}
	public void incrementTime(int seconds){
		//yes, I could do it more efficiently with some fancy arithmetic and modulus division, but where's the fun in that?
		//i do fancy math everywhere else, but I hate people.
		//ignore the fact that I brought this into the base class.
		for(int i = 1; i<=seconds; i++){
			this.Seconds++;
			if(this.Seconds>59){
				this.Minutes++;
				this.Seconds -= 60;
			}
			if(this.Minutes>59){
				this.Hours++;
				this.Minutes -= 60;
			}
			if(this.Hours>23){
				//would increment days here
				this.Hours -= 23;
			}
		}
	}

}
