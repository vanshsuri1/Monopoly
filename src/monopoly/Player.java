// File: Player.java
package monopoly;

// Your original Player class, unmodified except package
class Player {
	private String name;
	private PropertyLinkedList ownedProperties;
	private boolean bankrupt;

	public Player(String name) {
		this.name = name;
		this.ownedProperties = new PropertyLinkedList();
		this.bankrupt = false;
	}

	public String getName() {
		return name;
	}

	public PropertyLinkedList getOwnedProperties() {
		return ownedProperties;
	}

	public boolean isBankrupt() {
		return bankrupt;
	}

	public void setBankrupt(boolean b) {
		bankrupt = b;
	}

	public void buyProperty(Property p) {
		ownedProperties.insert(p);
		System.out.println("Property bought: " + p.getName());
	}

	public void sellProperty(int loc) {
		boolean ok = ownedProperties.delete(loc);
		if (ok)
			System.out.println("Sold property at location " + loc);
		else
			System.out.println("You do not own a property at location " + loc);
	}

	public void showProperties() {
		System.out.println(name + "'s Properties:");
		ownedProperties.printAll();
	}

	public void sortProperties() {
		ownedProperties.sortByLocation();
		System.out.println("Properties sorted by location.");
	}

	public void searchProperty(String propName) {
		Property p = ownedProperties.searchByName(propName);
		if (p != null) {
			System.out.println("Found:");
			p.print();
		} else {
			System.out.println("You do not own that property.");
		}
	}

	public void mortgageProperty(int loc) {
		Property p = ownedProperties.searchByLocation(loc);
		if (p != null && !p.isMortgaged()) {
			p.setMortgaged(true);
			System.out.println("Mortgaged " + p.getName());
		} else if (p != null) {
			System.out.println(p.getName() + " is already mortgaged.");
		} else {
			System.out.println("You do not own a property at location " + loc);
		}
	}

	public void unmortgageProperty(int loc) {
		Property p = ownedProperties.searchByLocation(loc);
		if (p != null && p.isMortgaged()) {
			p.setMortgaged(false);
			System.out.println("Unmortgaged " + p.getName());
		} else if (p != null) {
			System.out.println(p.getName() + " is not mortgaged.");
		} else {
			System.out.println("You do not own a property at location " + loc);
		}
	}
}
