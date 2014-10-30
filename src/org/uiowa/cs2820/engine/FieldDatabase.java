package org.uiowa.cs2820.engine;

import org.uiowa.cs2820.engine.utilities.Utilities;


public class FieldDatabase
{
    private ChunkedAccess fileHandle;

    public FieldDatabase(ChunkedAccess fileHandle)
    {
        this.fileHandle = fileHandle;
    }

    public void add(BinaryFileNode node)
    {
        recursiveAdd(0, node);
    }
    
    private void recursiveAdd(int rootIndex, BinaryFileNode node)
    {
        BinaryFileNode root = (BinaryFileNode) fileHandle.get(rootIndex);
        if (root == null)
        {
            fileHandle.set(Utilities.convert(node), rootIndex);
        }
        else if (node.getField().compareTo(root.getField()) < 0)
            recursiveAdd(rootIndex * 2 + 1, node);
        else if (node.getField().compareTo(root.getField()) > 0)
            recursiveAdd(rootIndex * 2 + 2, node);
    }
    
    public int getIdentifierPosition(Field field)
    {
        int index = 0;
        BinaryFileNode currentNode = (BinaryFileNode) fileHandle.get(index);
        if (currentNode == null)
            return -1;
        while (currentNode != null)
        {
            if (currentNode.getField().equals(field))
                return currentNode.getAddrOfIdentifierStart();
            else if (field.compareTo(currentNode.getField()) < 0)
                index = index * 2 + 1;
            else
                index = index * 2 + 2;
            currentNode = (BinaryFileNode) fileHandle.get(index);
        }
        
        return -1;
    }

    public void setIdentifierPosition(Field field, int identPos)
    {
        int index = 0;
        BinaryFileNode currentNode = (BinaryFileNode) fileHandle.get(index);
        if (currentNode == null)
            return;
        while (currentNode != null)
        {
            if (currentNode.getField().equals(field))
            {
                currentNode.setAddrOfIdentifierStart(identPos);
                fileHandle.set(Utilities.convert(currentNode), index);
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