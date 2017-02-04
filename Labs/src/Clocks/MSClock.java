package Clocks;

public class MSClock extends Clock {
	int MS;
	MSClock(short hours, short minutes, short seconds, int ms){
		if(hours>23||minutes>59||seconds>59){
			throw new IndexOutOfBoundsException("That's not a valid time");
		}
		else{
			this.Hours = hours;
			this.Minutes = minutes;
			this.Seconds = seconds;
		}
		if(ms > 999){
			throw new IndexOutOfBoundsException("ms must be below 1000");
		}
		else{
			this.MS = ms;
		}
	}
	
	public void addMiliseconds(long ms){
		short hToAdd = (short) (ms/(1000*60*60));
		ms -= hToAdd*(1000*60*60);
		short mToAdd = (short) (ms/(1000*60));
		ms -= mToAdd*(1000*60);
		short sToAdd = (short) (ms/1000);
		ms -= sToAdd*1000;
		
		this.addHours(hToAdd);
		this.addMinutes(mToAdd);
		this.addSeconds(sToAdd);
		
		int milli = (int) (ms + this.getMiliSeconds());
		this.addSeconds(milli/1000);
		this.MS = milli%1000;
	}
	public int getMiliSeconds(){
		return this.MS;
	}

	@Override
	public long getSecondsDifference(Clock secondClock) {
		long thisMS = this.getMiliSeconds();
		long otherMS = 0;
		thisMS += (this.getSeconds() * 1000)+(this.getMinutes()*60*1000)+(this.getHours()*60*60*1000);
		otherMS += (secondClock.getSeconds() * 1000)+(secondClock.getMinutes()*60*1000)+(secondClock.getHours()*60*60*1000);
		return Math.round(((thisMS-otherMS)/1000f));
	}

	@Override
	public Clock getDifference(Clock secondClock){
		return new HMSClock((short)(this.getHours()-secondClock.getHours()), (short)(this.getMinutes()-secondClock.getMinutes()), (short)(this.getSeconds()-secondClock.getSeconds()));
	}
	public Clock getDifference(MSClock secondClock) {
		return new MSClock((short)(this.getHours()-secondClock.getHours()), (short)(this.getMinutes()-secondClock.getMinutes()), (short)(this.getSeconds()-secondClock.getSeconds()), (int)(this.getMiliSeconds()-secondClock.getMiliSeconds()));
	}
}
