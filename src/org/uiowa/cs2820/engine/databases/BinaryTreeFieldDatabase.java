package org.uiowa.cs2820.engine.databases;

import java.util.Iterator;

import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.fileoperations.ChunkedAccess;



/**
 * This database holds all of the fields that have been added to our database.
 * It uses a ChunkedAccess file to store the identifiers, represented below.
 * A ChunkedAccess file allows a user to:
 *      get(chunkPosition)                  - get the object at a specific chunk
 *      set(objectByteRepr, chunkPosition)  - set a specific chunk to an object's byte representation
 *      free(chunkPosition)                 - delete the data at a specific chunk
 *      nextAvailableChunk()                - get the next open chunk
 *  These are the methods that this class will use to store field.
 *  
 *  Fields are stored using BinaryFileNode's, a class which holds 5 things:
 *      field                       - the field of the node
 *      headOfLinkedListPosition    - the location identifier linked list head
 *      position                    - the location of this node in the database
 *      leftChildPosition           - the position of the left child node
 *      rightChildPosition          - the position of the right child node
 *      
 *  When a new node is added using
 *          add(node)
 *      it adds the node to the database, which is structured as a binary tree.
 *      Each node has 3 values, its own position, its left child position, and the right child position.
 *      
 *  If there is a duplicate added, the duplicate is ignored.
 *  
 * 
 * ----
 * Example
 * ----
 * 
 * add(new BinaryFileNode(new Field("value", "name"), 0)
 * 
 * +----------+----------+----------+----------+----------+
 * | value    |          |          |          |          |
 * | name     |          |          |          |          |
 * | head = 0 |          |          |          |          |
 * +----------+----------+----------+----------+----------+
 * 
 * add(new BinaryFileNode(new Field("value", "mane"), 1) // Left child
 * 
 * +----------+----------+----------+----------+----------+
 * | value    | value    |          |          |          |
 * | name     | mane     |          |          |          |
 * | head = 0 | head = 1 |          |          |          |
 * +----------+----------+----------+----------+----------+
 * 
 * add(new BinaryFileNode(new Field("value", "open"), 2) // Right child
 * 
 * +----------+----------+----------+----------+----------+
 * | value    | value    | value    |          |          |
 * | name     | mane     | open     |          |          |
 * | head = 0 | head = 1 | head = 2 |          |          |
 * +----------+----------+----------+----------+----------+
 * 
 * The database then allows getting and setting of a field's pointer to
 * the head of its linked list.
 * 
 */
public class BinaryTreeFieldDatabase extends FieldDatabase
{
    /*
     * ONE RULE. The ROOT MUST ALWAYS BE AT 0
     */
    public BinaryTreeFieldDatabase(ChunkedAccess file)
    {
        super(file);
    }

    public void add(FieldFileNode data)
    {
        insert(0, FieldFileNode.NULL_ADDRESS, data);
    }
    
    private int insert(int rootIndex, int parent, FieldFileNode node)
    {
        FieldFileNode root = (FieldFileNode) getFileHandle().get(rootIndex);
        if (root == null)
        {
            rootIndex = getFileHandle().nextAvailableChunk();
            node.setAddress(rootIndex);
            node.setParentPosition(parent);
            getFileHandle().set(node.convert(), rootIndex);
            return rootIndex;
        }
        else if (node.getField().compareTo(root.getField()) < 0)
        {
            if (root.getLeftPosition() == FieldFileNode.NULL_ADDRESS)
            {
                rootIndex = insert(root.getLeftPosition(), root.getAddress(), node);
                root.setLeftPosition(rootIndex);
                getFileHandle().set(root.convert(), root.getAddress());
            }
            else
                insert(root.getLeftPosition(), root.getAddress(), node);
        }
        else if (node.getField().compareTo(root.getField()) > 0)
        {
            if (root.getRightPosition() == FieldFileNode.NULL_ADDRESS)
            {
                rootIndex = insert(root.getRightPosition(), root.getAddress(), node);
                root.setRightPosition(rootIndex);
                getFileHandle().set(root.convert(), root.getAddress());
            }
            else
                insert(root.getRightPosition(), root.getAddress(), node);
        }
        
        return rootIndex;
    }

    @Override
    public int getIdentifierPosition(Field field)
    {
        int index = 0;
        FieldFileNode currentNode = (FieldFileNode) getFileHandle().get(index);
        if (currentNode == null)
            return -1;
        while (currentNode != null)
        {
            if (currentNode.getField().equals(field))
                return currentNode.getHeadOfLinkedListPosition();
            else if (field.compareTo(currentNode.getField()) < 0)
                index = currentNode.getLeftPosition();
            else
                index = currentNode.getRightPosition();
            currentNode = (FieldFileNode) getFileHandle().get(index);
        }
        
        return -1;
    }

    @Override
    public void setIdentifierPosition(Field field, int headOfLinkedListPosition)
    {
        int index = 0;
        FieldFileNode currentNode = (FieldFileNode) getFileHandle().get(index);
        if (currentNode == null)
            return;
        while (currentNode != null)
        {
            if (currentNode.getField().equals(field))
            {
                currentNode.setHeadOfLinkedListPosition(headOfLinkedListPosition);
                getFileHandle().set(currentNode.convert(), index);
                return;
            }
            else if (field.compareTo(currentNode.getField()) < 0)
                index = currentNode.getLeftPosition();
            else
                index = currentNode.getRightPosition();
            currentNode = (FieldFileNode) getFileHandle().get(index);
        }
    }

    @Override
    public Iterator<Field> iterator()
    {
        return new BinaryTreeIterator(this, 0);
    }
    
    public String toString()
    {
        return getFileHandle().toString();
    }
    
    
}
