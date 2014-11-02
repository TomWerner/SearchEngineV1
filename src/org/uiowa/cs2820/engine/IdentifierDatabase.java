package org.uiowa.cs2820.engine;

import java.util.ArrayList;

/**
 * This database holds all of the identifiers that have been added to our database.
 * It uses a ChunkedAccess file to store the identifiers, represented below.
 * A ChunkedAccess file allows a user to:
 *      get(chunkPosition)                  - get the object at a specific chunk
 *      set(objectByteRepr, chunkPosition)  - set a specific chunk to an object's byte representation
 *      free(chunkPosition)                 - delete the data at a specific chunk
 *      nextAvailableChunk()                - get the next open chunk
 *  These are the methods that this class will use to store identifiers.
 *  
 *  Identifiers are stored using ValueFileNode's, a class which holdes 3 things:
 *      address         - the location the node has within the database
 *      nextNode        - the location of the next node in a linked list
 *      identifier      - the identifier of the node
 *      
 *  When a new node is added using
 *          addIdentifier(identifier)
 *      the database creates a new node with:
 *          address         = the next available chunk of the ChunkedAccess file
 *          nextNode        = NULL_ADDRESS (it doesn't point to anything)
 *          identifier      = the identifier specified.
 *      The function then returns address to be used as a pointer to the linked list.
 *  
 *  When a new node is added using
 *          addIdentifier(linkedListHeadPosition, identifier)
 *      The database loads the node at the given head position.
 *      It then creates a new node with:
 *          address         = next available chunk of ChunkedAccess
 *          nextNode        = address of the previous head
 *          identifier      = the identifier specified
 * 
 * ----
 * Example
 * ----
 * 
 * location = add("file")
 * location is now 0
 * +----------+----------+----------+----------+----------+
 * | addr = 0 |          |          |          |          |
 * | next = -1|          |          |          |          |
 * | id = file|          |          |          |          |
 * +----------+----------+----------+----------+----------+
 * 
 * location = add(location, "file2")
 * location is now 1
 * +----------+----------+----------+----------+----------+
 * | addr = 0 | addr = 1 |          |          |          |
 * | next = -1| next = 0 |          |          |          |
 * | id = file| id =file2|          |          |          |
 * +----------+----------+----------+----------+----------+
 * 
 * 
 * 
 * The function getAllIdentifiers(location) starts at the head of the list and
 * iterates through it until it hits the end, NULL_ADDRESS.
 * 
 * The function removeIdentifier(linkedListHeadPosition, identifier) does standard
 * linked list removal of an identifier.
 * 
 */
public class IdentifierDatabase
{
    private ChunkedAccess fileHandle;

    /**
     * Create a new IdentifierDatabase, a structure to store and manipulate identifiers,
     * stored as linked lists.
     * @param fileHandle the ChunkedAccess file to store identifier data
     */
    public IdentifierDatabase(ChunkedAccess fileHandle)
    {
        this.fileHandle = fileHandle;
    }

    /**
     * Add a new identifier to a given linked list
     * @param linkedListHeadPosition head of the linked list
     * @param identifier identifier to add
     * @return the new linkedListHeadPosition
     */
    public int addIdentifier(int linkedListHeadPosition, String identifier)
    {
        ValueFileNode node = new ValueFileNode(identifier);
        ValueFileNode existingNode = (ValueFileNode) fileHandle.get(linkedListHeadPosition);
        // If it already exists, make the new node point to the existing one
        // Then find the next available spot to stick our new node and change
        // the address to that, then add normally.
        if (existingNode != null)
        { 
            if (node.getNextNode() != ValueFileNode.NULL_ADDRESS)
                throw new IllegalAccessError();
            node.setNextNode(existingNode.getAddress());
            linkedListHeadPosition = fileHandle.nextAvailableChunk();
        } 

        node.setAddress(linkedListHeadPosition);
        fileHandle.set(node.convert(), linkedListHeadPosition);

        return linkedListHeadPosition;
    }
    
    /**
     * Add new identifier without an existing linked list
     * @param identifier the identifier to add
     * @return the head of the new linked list
     */
    public int addIdentifier(String identifier)
    {
        int addrOfIdentifier = fileHandle.nextAvailableChunk();
        ValueFileNode node = new ValueFileNode(identifier);
        node.setAddress(addrOfIdentifier);
        fileHandle.set(node.convert(), addrOfIdentifier);

        return addrOfIdentifier;
    }

    /**
     * Remove a given identifier from a given linked list of identifiers
     * @param linkedListHeadPosition position of the linked list head
     * @param identifier identifier to remove
     * @return -1 if the head did not change, otherwise the new head of the linked list
     */
    public int removeIdentifier(int linkedListHeadPosition, String identifier)
    {
        ValueFileNode start = (ValueFileNode) fileHandle.get(linkedListHeadPosition);
        if (start == null)
            return ValueFileNode.NULL_ADDRESS;
        if (start.getIdentifier().equals(identifier))
        {
            int next = start.getNextNode();
            fileHandle.free(linkedListHeadPosition);
            return next;
        }
        else
        {
            int next = start.getNextNode();
            if (next == ValueFileNode.NULL_ADDRESS)
                return ValueFileNode.NULL_ADDRESS;
            ValueFileNode node = (ValueFileNode) fileHandle.get(next);
            ValueFileNode previous = start;
            while (!node.getIdentifier().equals(identifier))
            {
                previous = node;
                
                // Break out of quick loop
                if (node.getNextNode() == next)
                    break;
                next = node.getNextNode();
                node = (ValueFileNode) fileHandle.get(next);
            }
            // Node is now the node to remove, previous is the one before it
            previous.setNextNode(node.getNextNode());
            fileHandle.set(previous.convert(), previous.getAddress());
            fileHandle.free(node.getAddress());

            return ValueFileNode.NULL_ADDRESS;
        }
    }

    /**
     * Get all of the identifier strings in a linked list of identifiers
     * @param linkedListPositionHead The head of the linked list
     * @return an ArrayList of the identifier strings 
     */
    public ArrayList<String> getAllIdentifiers(int linkedListPositionHead)
    {
        ArrayList<String> result = new ArrayList<String>();

        ValueFileNode start = (ValueFileNode) fileHandle.get(linkedListPositionHead);
        if (start == null)
            return null;
        result.add(start.getIdentifier());

        int next = start.getNextNode();
        ValueFileNode node;
        while (next != ValueFileNode.NULL_ADDRESS)
        {
            node = (ValueFileNode) fileHandle.get(next);
            result.add(node.getIdentifier());
            if (node.getNextNode() == next)
                break;
            next = node.getNextNode();
        }

        return result;
    }

}
