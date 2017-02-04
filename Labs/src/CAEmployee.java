
public class CAEmployee extends Employee {
	private String county;
	
	CAEmployee(String name, String address, String DOH, String county){
		super(name,address,DOH);
		this.setCounty(county);
	}
	
	void setCounty(String county){
		this.county = county;
	}
	String getCounty(){
		return this.county;
	}
	
	void Display(){
		super.Display();
		System.out.println(" and works in "+this.getCounty()+" county");
	}
	
	void Display(String data){
		if(data.equalsIgnoreCase("Address")){
			System.out.println(this.getAddress());
		}
		else if(data.equalsIgnoreCase("Name")){
			System.out.println(this.getName());
		}
		else if(data.equalsIgnoreCase("DOH")||data.equalsIgnoreCase("Date of Hire")){
			System.out.println(this.getDateOfHire());
		}
		else if(data.equalsIgnoreCase("county")){
			System.out.println(this.getCounty());
		}
		else {
			throw new IllegalArgumentException(data+" wasn't Address, Name, DOH, or County");
		}
	}
	
}
