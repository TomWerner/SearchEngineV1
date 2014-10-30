package org.uiowa.cs2820.engine;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BinaryFileNode implements Serializable
{
    public static final int MAX_SIZE = 256;
    private Field field;
    private int addrOfIdentifierStart;

    public BinaryFileNode(Field field, int addrOfIdentifierStart)
    {
        this.field = field;
        this.addrOfIdentifierStart = addrOfIdentifierStart;
    }

    public int getAddrOfIdentifierStart()
    {
        return addrOfIdentifierStart;
    }

    public void setAddrOfIdentifierStart(int addrOfIdentifierStart)
    {
        this.addrOfIdentifierStart = addrOfIdentifierStart;
    }

    public Field getField()
    { 
        return field;
    }

    public void setField(Field field)
    {
        this.field = field;
    }
    
    public boolean equals(Object other)
    { 
        if (other instanceof BinaryFileNode)
            return ((BinaryFileNode)other).field.equals(field);
        return false;
    }

}
