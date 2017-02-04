
public class ExecEmployees {

	public static void main(String[] args) {
		Employee bob = new Employee("Bob","123 nobody cares ln.","January 1st, 1009");
		CAEmployee Shaniqua = new CAEmployee("Shaniqua collins","293 mulberry ln.","January 2nd, 2014","Solano");
		
		bob.Display();
		bob.Display("name");
		Shaniqua.Display();
		Shaniqua.Display("county");
		
	}

}
