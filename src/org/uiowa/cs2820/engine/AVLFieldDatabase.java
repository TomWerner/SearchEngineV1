package org.uiowa.cs2820.engine;




public class AVLFieldDatabase extends FieldDatabase
{
    /*
     * ONE RULE. The ROOT MUST ALWAYS BE AT 0
     */
    public AVLFieldDatabase(ChunkedAccess file)
    {
        super(file);
    }

    public void add(BinaryFileNode data)
    {
        int root = insert(0, data);
        switch (balanceNumber(root))
        {
        case 1:
            rotateLeft(root);
            break;
        case -1:
            rotateRight(root);
            break;
        default:
            break;
        }
    }
    
    private int balanceNumber(int index)
    {
        BinaryFileNode node = (BinaryFileNode) getFileHandle().get(index);
        int leftDepth = depth(node.getLeftPosition());
        int rightDepth = depth(node.getRightPosition());
        
        if (leftDepth - rightDepth >= 2)
            return -1;
        else if (leftDepth - rightDepth <= -2)
            return 1;
        return 0;
    }
    
    
    
    private int insert(int rootIndex, BinaryFileNode node)
    {
        // Get the node at the position given. It is the root of a subtree.
        BinaryFileNode root = (BinaryFileNode) getFileHandle().get(rootIndex);
        
        // If there is no node at this position we add our node here.
        if (root == null)
        {
            rootIndex = getFileHandle().nextAvailableChunk();
            node.setAddress(rootIndex);
            getFileHandle().set(node.convert(), rootIndex);
            
            // We return the address we put the new node at so that its
            // parent can update it's child pointer correctly
            return rootIndex;
        }
        else if (node.getField().compareTo(root.getField()) < 0)
        {
            // If our current node doesn't have a left child, add the new
            // node in that position
            if (root.getLeftPosition() == BinaryFileNode.NULL_ADDRESS)
            {
                rootIndex = insert(root.getLeftPosition(), node);
                root.setLeftPosition(rootIndex);
                getFileHandle().set(root.convert(), root.getAddress());
            }
            else //Continue recursively
                insert(root.getLeftPosition(), node);
        }
        else if (node.getField().compareTo(root.getField()) > 0)
        {
            // Our current node doesn't have a right child, add a new node
            // in that position
            if (root.getRightPosition() == BinaryFileNode.NULL_ADDRESS)
            {
                rootIndex = insert(root.getRightPosition(), node);
                root.setRightPosition(rootIndex);
                getFileHandle().set(root.convert(), root.getAddress());
            }
            else //Continue recursively
                insert(root.getRightPosition(), node);
        }
        
        // Check the balance of our sub tree
        switch (balanceNumber(rootIndex))
        {
        case 1:
            rotateLeft(rootIndex);
            break;
        case -1:
            rotateRight(rootIndex);
            break;
        default:
            break;
        }
        
        return rootIndex;
    }
    
    /**
     * Check the depth of a subtree with the root at a specific index
     * @param index The address of the subtree root
     * @return The depth of the subtree
     */
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
        
        // If the node is null there's nothing in the database
        if (currentNode == null)
            return -1;
        
        // Do a standard binary tree search
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
    
    /*
     * ------------------------------------------------------------------------
     *          Rotate Methods
     * ------------------------------------------------------------------------
     * 
     * These methods are the standard rotation methods for an AVL tree.
     * They are based off of the methods found here:
     *  https://gist.github.com/antonio081014/5939018
     * and from wikipedia.
     * 
     * They were changed to allow for pointer based node relations and needing
     * to store the nodes in an array.
     */
    private void rotateLeft(int index)
    {
        BinaryFileNode q = (BinaryFileNode) getFileHandle().get(index);
        BinaryFileNode p = (BinaryFileNode) getFileHandle().get(q.getRightPosition());
        BinaryFileNode c = (BinaryFileNode) getFileHandle().get(q.getLeftPosition());
        BinaryFileNode a = (BinaryFileNode) getFileHandle().get(p.getLeftPosition());
        BinaryFileNode b = (BinaryFileNode) getFileHandle().get(p.getRightPosition());
        
        q.setAddress(p.getAddress());
        p.setAddress(index);
        p.setLeftPosition(getAddressOfNodeWithDefault(q));
        p.setRightPosition(getAddressOfNodeWithDefault(b));
        q.setLeftPosition(getAddressOfNodeWithDefault(c));
        q.setRightPosition(getAddressOfNodeWithDefault(a));

        getFileHandle().set(q.convert(), q.getAddress());
        getFileHandle().set(p.convert(), p.getAddress());
    }
    
    private void rotateRight(int index)
    {
        BinaryFileNode q = (BinaryFileNode) getFileHandle().get(index);
        BinaryFileNode p = (BinaryFileNode) getFileHandle().get(q.getLeftPosition());
        BinaryFileNode c = (BinaryFileNode) getFileHandle().get(q.getRightPosition());
        BinaryFileNode a = (BinaryFileNode) getFileHandle().get(p.getLeftPosition());
        BinaryFileNode b = (BinaryFileNode) getFileHandle().get(p.getRightPosition());
        
        q.setAddress(p.getAddress());
        p.setAddress(index);
        p.setLeftPosition(getAddressOfNodeWithDefault(a));
        p.setRightPosition(getAddressOfNodeWithDefault(q));
        q.setLeftPosition(getAddressOfNodeWithDefault(b));
        q.setRightPosition(getAddressOfNodeWithDefault(c));
        
        getFileHandle().set(q.convert(), q.getAddress());
        getFileHandle().set(p.convert(), p.getAddress());
    }
    
    private int getAddressOfNodeWithDefault(BinaryFileNode node)
    {
        if (node == null)
            return -1;
        return node.getAddress();
    }
    
}
