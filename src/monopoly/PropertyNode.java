// File: PropertyNode.java
package monopoly;

// Node for linked list of properties
class PropertyNode {
    public Property    property;
    public PropertyNode next;

    public PropertyNode(Property p) {
        property = p;
        next     = null;
    }
}
