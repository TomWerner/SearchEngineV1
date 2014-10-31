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
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getIdentifierPosition(Field field)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setIdentifierPosition(Field field, int headOfLinkedListPosition)
    {
        // TODO Auto-generated method stub
        
    }
    
    
    private BinaryFileNode getLeft(int index)
    {
        return null;
    }
}
