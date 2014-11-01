package org.uiowa.cs2820.engine;



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

    public void add(BinaryFileNode data)
    {
        insert(0, data);
    }
    
    private int insert(int rootIndex, BinaryFileNode node)
    {
        BinaryFileNode root = (BinaryFileNode) getFileHandle().get(rootIndex);
        if (root == null)
        {
            rootIndex = getFileHandle().nextAvailableChunk();
            node.setAddress(rootIndex);
            getFileHandle().set(node.convert(), rootIndex);
            return rootIndex;
        }
        else if (node.getField().compareTo(root.getField()) < 0)
        {
            if (root.getLeftPosition() == BinaryFileNode.NULL_ADDRESS)
            {
                rootIndex = insert(root.getLeftPosition(), node);
                root.setLeftPosition(rootIndex);
                getFileHandle().set(root.convert(), root.getAddress());
            }
            else
                insert(root.getLeftPosition(), node);
        }
        else if (node.getField().compareTo(root.getField()) > 0)
        {
            if (root.getRightPosition() == BinaryFileNode.NULL_ADDRESS)
            {
                rootIndex = insert(root.getRightPosition(), node);
                root.setRightPosition(rootIndex);
                getFileHandle().set(root.convert(), root.getAddress());
            }
            else
                insert(root.getRightPosition(), node);
        }
        
        return rootIndex;
    }
    
    public int depth(int index)
    {
        BinaryFileNode node = (BinaryFileNode) getFileHandle().get(index);
        if (node == null)
            return 0;
        int left = node.getLeftPosition();
        int right = node.getRightPosition();
        return 1 + Math.max(depth(left), depth(right));
    }

    @Override
    public int getIdentifierPosition(Field field)
    {
        int index = 0;
        BinaryFileNode currentNode = (BinaryFileNode) getFileHandle().get(index);
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
            currentNode = (BinaryFileNode) getFileHandle().get(index);
        }
        
        return -1;
    }

    @Override
    public void setIdentifierPosition(Field field, int headOfLinkedListPosition)
    {
        int index = 0;
        BinaryFileNode currentNode = (BinaryFileNode) getFileHandle().get(index);
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
            currentNode = (BinaryFileNode) getFileHandle().get(index);
        }
    }
    
    public void printTree(int index, int level)
    {
        BinaryFileNode node = (BinaryFileNode) getFileHandle().get(index);
        if (node != null)
        {
            printTree(node.getLeftPosition(), level + 1);
            System.out.println("Level " + level + ". " + node.getField());
            printTree(node.getRightPosition(), level + 1);
        }
    }
}
