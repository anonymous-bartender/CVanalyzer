package main;

public class stub {

	public static void main(String[] args) {
		Engine e = new Engine("C:\\Users\\Data.pdf");
		if(!e.isSupported()) {
			System.out.println("Not suported file type.");
		}
		else {
			e.run();
			System.out.println(e.getAll());
			
			
		}

	}

}
