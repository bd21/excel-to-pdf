public class Order {
	String coordinates1;
	String coordinates2;
	String[] personalMessage;//optional
	String registrationNumber;
	String starName;
	String tinyURL;
	String brightness;
	String date;
	String email;
	boolean binary;
	
	private int order;
	
	//10 String input
	public Order(String name, String date, String number, String binary,
				 String coor1, String coor2, String msg, String email,
				 String brightness, String url, int order) {
		
		this.starName = name;
		this.date = date;
		this.registrationNumber = number;
		if(binary.equalsIgnoreCase("yes") || binary.equalsIgnoreCase("true")) {
			this.binary = true;
		} else {
			this.binary = false;
		}

		this.coordinates1 = coor1;
		this.coordinates2 = coor2;
		this.personalMessage = msg.split("~");
		this.email = email;
		this.brightness = brightness;
		this.tinyURL = url;
		this.order = order;
	}
	public String getName() {
		return this.starName;
	}

	
	public void print() {
		System.out.println("----------");
		System.out.println("These are the values for Order #" + this.order);
		System.out.println("Star Name           : " + this.starName);
		System.out.println("Registration Date   : " + this.date);
		System.out.println("Registration Number : " + this.registrationNumber);
		System.out.println("Star Coordinate 1   : " + this.coordinates1);
		if(this.coordinates2 != null && 
		   this.coordinates2 != "" && 
		   this.binary) {
			System.out.println("Star Coordinate 2   : " + this.coordinates2);//change this to print correct
		} else {
			System.out.println("Star Coordinate 2   : (None)");
		}

		System.out.print("Personal Message    : ");
		for(String line : this.personalMessage) {
			System.out.println(line);
		}
		System.out.println("Delivery Email      : " + this.email);
		System.out.println("Brightness          : " + this.brightness);
		System.out.println("TinyURL             : " + this.tinyURL);
		System.out.println("Binary?             : " + this.binary);
		System.out.println();
	}
}
