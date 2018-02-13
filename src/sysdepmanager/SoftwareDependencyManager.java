package sysdepmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoftwareDependencyManager {

	// map to maintain software and list of softwares on which it is dependent
	private Map<String, List<String>> dependencyGraph = new HashMap<>();

	// map to maintain softwares name and Software that are installed
	private Map<String, Software> installedComponents = new HashMap<>();

	/**
	 * put the softwares on which dependency is there in the map corresponding to
	 * the softwares
	 * 
	 * @param depInfo
	 */
	public void recordDependency(Map<String, List<String>> depInfo) {
		dependencyGraph.putAll(depInfo);
	}

	/**
	 *
	 * @param softwareName
	 * @return List of names of software that were actually installed
	 *
	 */
	public List<String> installSoftware(String softwareName) {
		List<String> newlyInstalled = new ArrayList<>();
		if (!installedComponents.containsKey(softwareName)) {
			Software main = new Software(softwareName, true);
			installedComponents.put(softwareName, main);
			installDependencies(main);
			System.out.println("\tInstalling " + softwareName);
			newlyInstalled.add(softwareName);
		} else {
			System.out.println("\t" + softwareName + " is already installed.");
		}
		return newlyInstalled;
	}

	/**
	 *
	 * @param main
	 *            The software for which dependencies will be installed
	 * @return List of dependencies that were actually installed
	 */
	private void installDependencies(Software main) {
		List<String> deps = dependencyGraph.get(main.getName());

		if (deps != null) {
			for (String depName : deps) {
				// if dep not present, install it
				if (!installedComponents.containsKey(depName)) {
					Software dep = new Software(depName, false);
					installedComponents.put(depName, dep);
					System.out.println("\tInstalling " + depName);
				}
				Software dep = installedComponents.get(depName);
				dep.addDependant(main.getName());
				installDependencies(dep);
			}
		}
	}

	/**
	 *
	 * @param softwareName
	 * @return List of names for softwares that were removed
	 */
	public List<String> removeSoftware(String softwareName) {
		List<String> removed = new ArrayList<>();

		if (installedComponents.containsKey(softwareName)) {
			Software toRemove = installedComponents.get(softwareName);
			if (toRemove.hasNoDependants()) {
				installedComponents.remove(softwareName);
				removed.add(softwareName);

				System.out.println("\tRemoving " + softwareName);

				removed.addAll(removeDependencies(softwareName));
			} else {
				System.out.println("\t" + softwareName + " is still needed.");
			}
		} else {
			System.out.println("\t" + softwareName + " is not installed.");
		}

		return removed;
	}

	private List<String> removeDependencies(String softwareName) {
		List<String> removed = new ArrayList<>();
		List<String> deps = dependencyGraph.get(softwareName);

		if (deps != null) {
			for (String depName : deps) {
				if (installedComponents.containsKey(depName)) {
					Software dep = installedComponents.get(depName);
					dep.removeDependant(softwareName);

					// Remove a dependency iff it was not installed explicitly and doesn't have any
					// more dependants
					if (dep.hasNoDependants() && !dep.isExplicit()) {
						installedComponents.remove(depName);
						removed.add(depName);
						System.out.println("\tRemoving " + depName);
						removed.addAll(removeDependencies(depName));
					}
					// else {
					// System.out.println("\t" + depName + " is still needed.");
					// }
				}
				// else {
				// System.out.println("\t" + depName + " is not installed.");
				// }
			}
		}
		return removed;
	}

	/**
	 * List all the installed software
	 */
	public void listAll() {
		for (String softwareName : installedComponents.keySet()) {
			System.out.println("\t" + softwareName);
		}
	}
}
