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
    
    private void rotateLeft(int index)
    {
        BinaryFileNode q = (BinaryFileNode) getFileHandle().get(index);
        BinaryFileNode p = (BinaryFileNode) getFileHandle().get(q.getRightPosition());
        BinaryFileNode c = (BinaryFileNode) getFileHandle().get(q.getLeftPosition());
        BinaryFileNode a = (BinaryFileNode) getFileHandle().get(p.getLeftPosition());
        BinaryFileNode b = (BinaryFileNode) getFileHandle().get(p.getRightPosition());
        
        q.setAddress(p.getAddress());
        p.setAddress(index);
        p.setLeftPosition(rotateAddress(q));
        p.setRightPosition(rotateAddress(b));
        q.setLeftPosition(rotateAddress(c));
        q.setRightPosition(rotateAddress(a));

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
        p.setLeftPosition(rotateAddress(a));
        p.setRightPosition(rotateAddress(q));
        q.setLeftPosition(rotateAddress(b));
        q.setRightPosition(rotateAddress(c));
        
        getFileHandle().set(q.convert(), q.getAddress());
        getFileHandle().set(p.convert(), p.getAddress());
    }
    
    private int rotateAddress(BinaryFileNode node)
    {
        if (node == null)
            return -1;
        return node.getAddress();
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
}
