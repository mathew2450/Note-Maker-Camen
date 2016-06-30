
public class WorkTime {
	int hrs, mins, hrsWorked;
	String time;
	boolean used;
	
	public WorkTime(String workT){
		this.time = workT;
		String[] sTime = workT.split(":");
		this.hrs = Integer.parseInt(sTime[0]);
		this.mins = Integer.parseInt(sTime[1]);
		this.used = false;
		//System.out.println("New WorkTime Created: " + this.time + " " + this.hrs +":" + this.mins + " " + this.used);
	}
	
	public void setHrs(int h){
		this.hrs = h;
		this.time = this.timeToString(false);
		return;
	}
	
	public boolean isEqualTo(WorkTime ct){
		if(this.hrs == ct.hrs && this.mins == ct.mins)
			return(true);
		else
			return(false);
	}
	
	public boolean isLessThan(WorkTime t){
		//System.out.println(this.hrs + " is less than " + t.hrs);
		if(t.hrs > this.hrs)
				return(true);
		if(t.hrs == this.hrs && t.mins >= (this.mins+15))
			return(true);
		else 
			return(false);
	}
	
	public boolean isMoreThan(WorkTime t){
		if(t.hrs < this.hrs)
			return(true);
		if(t.hrs == this.hrs && t.mins <= this.mins)
			return(true);
		else 
			return(false);
	}
	
	public WorkTime addquarterHour(){
		if(this.mins + 15 == 60){
			this.hrs++;
			this.mins = 00;
		}
		else
			this.mins+=15;
		this.time = this.timeToString(false);
		
	return(this);
	}
	
	public void fromMilitaryTime(){
		if(this.hrs > 12)
			this.hrs -= 12;
		return;
	}
	
	public String timeToString(boolean b){
		if(b == true)
			fromMilitaryTime();
		String time = (this.hrs + ":" + this.mins);
		return(time);		
	}
}