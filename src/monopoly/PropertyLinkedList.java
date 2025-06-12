package monopoly;

// A simple singly‐linked list of PropertyNode
class PropertyLinkedList {
	// The head (first node) of the linked list
	private PropertyNode head;

	// Constructor initializes the head to null (empty list)
	public PropertyLinkedList() {
		head = null;
	}

	// Node for linked list of properties
	class PropertyNode {
		public Property property;
		public PropertyNode next;

		public PropertyNode(Property p) {
			property = p;
			next = null;
		}
	}

	// Insert a property at the end of the linked list
	public void insert(Property p) {
		// Create a new node with the property
		PropertyNode node = new PropertyNode(p);
		// If the list is empty, set head to the new node
		if (head == null)
			head = node;
		else {
			// Otherwise, traverse to the end of the list
			PropertyNode temp = head;
			while (temp.next != null)
				temp = temp.next;
			// Add the new node at the end
			temp.next = node;
		}
	}

	// Delete the first property node found by location
	public boolean delete(int loc) {
		// If the list is empty, nothing to delete
		if (head == null)
			return false;
		// If the property to delete is at the head
		if (head.property.getLocation() == loc) {
			head = head.next; // Move head to the next node
			return true; // Deletion successful
		}
		// Otherwise, search for the node to delete
		PropertyNode prev = head, curr = head.next;
		while (curr != null) {
			// If found, remove it from the list
			if (curr.property.getLocation() == loc) {
				prev.next = curr.next; // Skip current node
				return true; // Deletion successful
			}
			prev = curr; // Move prev to curr
			curr = curr.next; // Move curr to next node
		}
		// Not found
		return false;
	}

	// Search the list for a property by name (case-insensitive)
	public Property searchByName(String name) {
		// Start at the head of the list
		PropertyNode temp = head;
		// Traverse all nodes
		while (temp != null) {
			// If property name matches, return it
			if (temp.property.getName().equalsIgnoreCase(name)) {
				return temp.property;
			}
			temp = temp.next; // Move to next node
		}
		// Not found
		return null;
	}

	// Search the list for a property by board location
	public Property searchByLocation(int loc) {
		// Start at the head of the list
		PropertyNode temp = head;
		// Traverse all nodes
		while (temp != null) {
			// If property location matches, return it
			if (temp.property.getLocation() == loc) {
				return temp.property;
			}
			temp = temp.next; // Move to next node
		}
		// Not found
		return null;
	}

	// Print all properties in the list
	public void printAll() {
		// Start at the head
		PropertyNode temp = head;
		// If the list is empty, say so
		if (temp == null) {
			System.out.println("No properties owned yet.");
		}
		// Traverse and print each property
		while (temp != null) {
			temp.property.print();
			temp = temp.next;
		}
	}

	// Sort the linked list by property location using bubble sort
	public void sortByLocation() {
		// If list is empty or has only one node, nothing to sort
		if (head == null || head.next == null)
			return;
		boolean swapped = true; // To check if any swaps happened
		// Repeat until no swaps are needed
		while (swapped) {
			swapped = false; // Assume sorted
			PropertyNode curr = head;
			// Traverse the list, comparing each pair of adjacent nodes
			while (curr.next != null) {
				// If out of order, swap their property objects
				if (curr.property.getLocation() > curr.next.property.getLocation()) {
					Property tmp = curr.property;
					curr.property = curr.next.property;
					curr.next.property = tmp;
					swapped = true; // Mark that a swap happened
				}
				curr = curr.next; // Move to next pair
			}
		}
	}
}
