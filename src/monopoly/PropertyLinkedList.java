// File: PropertyLinkedList.java
package monopoly;

// A simple singly‐linked list of PropertyNode
class PropertyLinkedList {
    private PropertyNode head;

    public PropertyLinkedList() {
        head = null;
    }

    // Insert at end
    public void insert(Property p) {
        PropertyNode node = new PropertyNode(p);
        if (head == null) head = node;
        else {
            PropertyNode temp = head;
            while (temp.next != null) temp = temp.next;
            temp.next = node;
        }
    }

    // Delete first by location
    public boolean delete(int loc) {
        if (head == null) return false;
        if (head.property.getLocation() == loc) {
            head = head.next;
            return true;
        }
        PropertyNode prev = head, curr = head.next;
        while (curr != null) {
            if (curr.property.getLocation() == loc) {
                prev.next = curr.next;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    // Search by name (case-insensitive)
    public Property searchByName(String name) {
        PropertyNode temp = head;
        while (temp != null) {
            if (temp.property.getName().equalsIgnoreCase(name)) {
                return temp.property;
            }
            temp = temp.next;
        }
        return null;
    }

    // Search by board location
    public Property searchByLocation(int loc) {
        PropertyNode temp = head;
        while (temp != null) {
            if (temp.property.getLocation() == loc) {
                return temp.property;
            }
            temp = temp.next;
        }
        return null;
    }

    // Print all
    public void printAll() {
        PropertyNode temp = head;
        if (temp == null) {
            System.out.println("No properties owned yet.");
        }
        while (temp != null) {
            temp.property.print();
            temp = temp.next;
        }
    }

    // Bubble-sort by location
    public void sortByLocation() {
        if (head == null || head.next == null) return;
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            PropertyNode curr = head;
            while (curr.next != null) {
                if (curr.property.getLocation() 
                  > curr.next.property.getLocation()) {
                    Property tmp = curr.property;
                    curr.property = curr.next.property;
                    curr.next.property = tmp;
                    swapped = true;
                }
                curr = curr.next;
            }
        }
    }
}
