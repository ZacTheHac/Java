public class Employee {
	private String name;
	private String addr;
	private String DOH;
	
	Employee(String name, String address, String DOH){
		this.setName(name);
		this.setAddress(address);
		this.setDateOfHire(DOH);
	}
	void setName(String name){
		this.name = name;
	}
	String getName(){
		return this.name;
	}
	
	void setAddress(String addr){
		this.addr=addr;
	}
	String getAddress(){
		return this.addr;
	}
	
	void setDateOfHire(String DOH){
		this.DOH = DOH;
	}
	String getDateOfHire(){
		return this.DOH;
	}
	
	void Display(){
		System.out.println( this.getName()+" was hired on "+this.getDateOfHire()+" and lives at "+this.getAddress());
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
		else {
			throw new IllegalArgumentException(data+" wasn't Address, Name, or DOH");
		}
	}
	
}
