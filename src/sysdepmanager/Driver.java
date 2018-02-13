package sysdepmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Driver {

	private static SoftwareDependencyManager dependencyManager = new SoftwareDependencyManager();

	// can be made modular by using switch case and a new method to parse inputs
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		String inputLine = null;
		do {
			inputLine = in.nextLine();
			System.out.println(inputLine);
			String[] command = inputLine.split(" ");

			String commandName = command[0];
			if (commandName.equals("DEPEND")) {
				String commandKey = command[1];
				Map<String, List<String>> depInfo = new HashMap<>();
				depInfo.put(commandKey, new ArrayList<>());

				for (int i = 2; i < command.length; i++) {
					depInfo.get(commandKey).add(command[i]);
				}

				dependencyManager.recordDependency(depInfo);

			} else if (commandName.equals("INSTALL")) {
				dependencyManager.installSoftware(command[1]);

			} else if (commandName.equals("REMOVE")) {
				dependencyManager.removeSoftware(command[1]);
			} else if (commandName.equals("LIST")) {
				dependencyManager.listAll();
			}
		} while (!inputLine.equals("END"));
	}
}
