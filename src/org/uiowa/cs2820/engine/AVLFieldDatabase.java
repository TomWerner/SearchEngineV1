package org.uiowa.cs2820.engine;

public class AVLFieldDatabase extends FieldDatabase
{
    public AVLFieldDatabase(ChunkedAccess file)
    {
        super(file);
    }

    @Override
    public void add(BinaryFileNode node)
    {
        // insert will return the index the new node is placed at.
        int index = insert(0, node);
        switch (balanceNumber(index))
        {
        case 1:
            rotateLeft(index);
            break;
        case -1:
            rotateRight(index);
            break;
        default:
            break;
        }
    }
    
    private int depth(BinaryFileNode node)
    {
        if (node == null)
            return 0;
        return node.getDepth();
        //0 1 + Math.max(depth(node.getLeft()), depth(node.getRight()));
    }

    @Override
    public int getIdentifierPosition(Field field)
    {
        return -1;
    }

    @Override
    public void setIdentifierPosition(Field field, int headOfLinkedListPosition)
    {
        // TODO Auto-generated method stub

    }
    
    public void 
}
