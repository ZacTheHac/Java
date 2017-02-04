package Clocks;

public abstract class Clock {
	
	public abstract long getSecondsDifference(Clock secondClock);
	public abstract Clock getDifference(Clock secondClock);
	public short Hours;
	public short Minutes;
	public short Seconds;
	public short getHours(){
		return this.Hours;
	}
	public short getMinutes(){
		return this.Minutes;
	}
	public short getSeconds(){
		return this.Seconds;
	}
	public void addSeconds(int secToAdd){
		long sec = this.getSeconds() + secToAdd;
		this.addMinutes((int) (sec/60));
		this.Seconds = (short) (sec%60);
	}
	public void addMinutes(int minToAdd){
		long min = this.getMinutes() + minToAdd;
		this.addHours((int) (min/60));
		this.Minutes = (short) (min%60);
	}
	public void addHours(int hrsToAdd){
		long hrs = this.getHours() + hrsToAdd;
		this.Hours = (short) (hrs % 24);
	}

}
