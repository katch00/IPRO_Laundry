package laundrySimulator;

import java.util.Scanner;

public class LaundrySimulator{
	public static void main(String[] args){

		Scanner input = new Scanner(System.in);

		System.out.println("Creating a laundry simulation...");
		Simulator s = new Simulator();
		s.start();
		System.out.println("Simulation has started");

		while(true){
			System.out.print("Action: ");
			String action = input.nextLine();
			String location;

			switch (action) {
				case "all":
					System.out.println(s.allMachines());
					break;
				case "all location":
					System.out.print("Enter location: ");
					location = input.nextLine();
					System.out.println(s.allMachinesByLoc(location));
					break;
				case "num all":
					System.out.println(s.numAvailable());
					break;
					case "num location":
						System.out.print("Enter location: ");
						location = input.nextLine();
						System.out.println(s.numAvailableByLoc(location));
						break;
				case "end":
					System.out.println("Ending the simulation.");
					System.exit(0);
					break;
				default:
					System.out.println("Wrong option! Try again");
					break;
			}
		}


  }
}
