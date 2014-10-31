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
 *  Fields are stored using BinaryFileNode's, a class which holdes 2 things:
 *      field                       - the field of the node
 *      headOfLinkedListPosition    - the location identifier linked list head
 *      
 *  When a new node is added using
 *          add(node)
 *      it adds the node to the database, which is structured as a binary tree.
 *      It using the formula left child at (2i + 1) and right at (2i + 2)
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
public class BinaryTreeFieldDatabase implements FieldDatabase
{
    private ChunkedAccess fileHandle;

    /**
     * Create a new FieldDatabase with a given ChunkedAccess file.
     * It stores fields using a binary tree.
     * @param fileHandle The file to store fields in
     */
    public BinaryTreeFieldDatabase(ChunkedAccess fileHandle)
    {
        this.fileHandle = fileHandle;
    }

    /* (non-Javadoc)
     * @see org.uiowa.cs2820.engine.FieldDatabase#add(org.uiowa.cs2820.engine.BinaryFileNode)
     */
    @Override
    public void add(BinaryFileNode node)
    {
        recursiveAdd(0, node);
    }
    
    /**
     * Using the standard method for adding nodes to a binary tree stored in an array, add a given node
     * with a root index
     * @param rootIndex Index of the "root". This changes as it recurses
     * @param node the node to add
     */
    private void recursiveAdd(int rootIndex, BinaryFileNode node)
    {
        BinaryFileNode root = (BinaryFileNode) fileHandle.get(rootIndex);
        if (root == null)
        {
            fileHandle.set(node.convert(), rootIndex);
        }
        else if (node.getField().compareTo(root.getField()) < 0)
            recursiveAdd(rootIndex * 2 + 1, node);
        else if (node.getField().compareTo(root.getField()) > 0)
            recursiveAdd(rootIndex * 2 + 2, node);
    }
    
    /* (non-Javadoc)
     * @see org.uiowa.cs2820.engine.FieldDatabase#getIdentifierPosition(org.uiowa.cs2820.engine.Field)
     */
    @Override
    public int getIdentifierPosition(Field field)
    {
        int index = 0;
        BinaryFileNode currentNode = (BinaryFileNode) fileHandle.get(index);
        if (currentNode == null)
            return -1;
        while (currentNode != null)
        {
            if (currentNode.getField().equals(field))
                return currentNode.getHeadOfLinkedListPosition();
            else if (field.compareTo(currentNode.getField()) < 0)
                index = index * 2 + 1;
            else
                index = index * 2 + 2;
            currentNode = (BinaryFileNode) fileHandle.get(index);
        }
        
        return -1;
    }

    /* (non-Javadoc)
     * @see org.uiowa.cs2820.engine.FieldDatabase#setIdentifierPosition(org.uiowa.cs2820.engine.Field, int)
     */
    @Override
    public void setIdentifierPosition(Field field, int headOfLinkedListPosition)
    {
        int index = 0;
        BinaryFileNode currentNode = (BinaryFileNode) fileHandle.get(index);
        if (currentNode == null)
            return;
        while (currentNode != null)
        {
            if (currentNode.getField().equals(field))
            {
                currentNode.setHeadOfLinkedListPosition(headOfLinkedListPosition);
                fileHandle.set(currentNode.convert(), index);
                return;
            }
            else if (field.compareTo(currentNode.getField()) < 0)
                index = index * 2 + 1;
            else
                index = index * 2 + 2;
            currentNode = (BinaryFileNode) fileHandle.get(index);
        }
    }
}
