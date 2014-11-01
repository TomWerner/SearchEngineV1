package org.uiowa.cs2820.engine;

import com.sun.xml.internal.ws.api.pipe.NextAction;


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
//        switch (balanceNumber(root))
//        {
//        case 1:
//            root = rotateLeft(root);
//            break;
//        case -1:
//            root = rotateRight(root);
//            break;
//        default:
//            break;
//        }
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
    
    private int depth(BinaryFileNode node)
    {
        if (node == null)
            return 0;
        BinaryFileNode left = (BinaryFileNode) getFileHandle().get(node.getLeftPosition());
        BinaryFileNode right = (BinaryFileNode) getFileHandle().get(node.getRightPosition());
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
        // TODO Auto-generated method stub

    }
}
