package org.uiowa.cs2820.engine;

import java.io.Serializable;

import org.uiowa.cs2820.engine.utilities.Utilities;

@SuppressWarnings("serial")
public class ValueFileNode implements Serializable
{
    public static final int MAX_SIZE = 256;
    public static final int NULL_ADDRESS = -1;

    private int nextNode;
    private String identifier;
    private int address;

    public ValueFileNode(String identifier)
    {
        nextNode = NULL_ADDRESS;
        setIdentifier(identifier);
    }

    public ValueFileNode(String identifier, int nextNode)
    {
        this.nextNode = nextNode;
        setIdentifier(identifier);
    }

    public byte[] toBytes()
    {
        byte[] result = Utilities.convert(this);
        return result;
    }

    public int getNextNode()
    {
        return nextNode;
    }

    public void setNextNode(int nextNode)
    {
        this.nextNode = nextNode;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
        if (toBytes().length >= MAX_SIZE)
            throw new IllegalArgumentException("Identifier size exceeded: " + identifier);
    }

    public int getAddress()
    {
        return address;
    }
    
    public void setAddress(int address)
    {
        this.address = address;
    }

    public String toString()
    {
        return identifier + "@ chunk " + address + ". Points to " + nextNode;
    }
}
