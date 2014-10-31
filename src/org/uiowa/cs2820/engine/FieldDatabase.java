package org.uiowa.cs2820.engine;

public interface FieldDatabase
{

    /**
     * Add a node to the database
     * @param node the node to add
     */
    public abstract void add(BinaryFileNode node);

    /**
     * Get the position of the head of the linked list of identifiers for the given field
     * @param field The field to search for
     * @return the corresponding identifier linked list head position 
     */
    public abstract int getIdentifierPosition(Field field);

    /**
     * Set the position of the corresponding identifier's linked list head position for a given field
     * @param field The field to change
     * @param headOfLinkedListPosition The new position of the identifier linked list
     */
    public abstract void setIdentifierPosition(Field field, int headOfLinkedListPosition);

}