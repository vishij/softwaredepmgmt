package sysdepmanager;

import java.util.HashSet;
import java.util.Set;

public class Software {
	private String name;
	private boolean isExplicit;
	private Set<String> dependants;

	public Software(String name, boolean isExplicit) {
		this.name = name;
		this.isExplicit = isExplicit;
		dependants = new HashSet<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isExplicit() {
		return isExplicit;
	}

	public void setExplicit(boolean isExplicit) {
		this.isExplicit = isExplicit;
	}

	public Set<String> getDependants() {
		return dependants;
	}

	public void setDependants(Set<String> dependants) {
		this.dependants = dependants;
	}

	public void addDependant(String s) {
		dependants.add(s);
	}

	public void removeDependant(String s) {
		dependants.remove(s);
	}

	public boolean hasNoDependants() {
		return dependants.size() == 0 ? true : false;
	}

}
