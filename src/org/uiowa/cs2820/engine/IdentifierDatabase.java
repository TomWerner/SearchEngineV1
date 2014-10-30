package org.uiowa.cs2820.engine;

import java.util.ArrayList;

import org.uiowa.cs2820.engine.utilities.Utilities;

public class IdentifierDatabase
{
    private ChunkedAccess fileHandle;

    public IdentifierDatabase(ChunkedAccess fileHandle)
    {
        this.fileHandle = fileHandle;
    }

    public int addIdentifier(int addrOfIdentifier, String identifier)
    {
        ValueFileNode node = new ValueFileNode(identifier);
        ValueFileNode existingNode = (ValueFileNode) fileHandle.get(addrOfIdentifier);
        // If it already exists, make the new node point to the existing one
        // Then find the next available spot to stick our new node and change
        // the address to that, then add normally.
        if (existingNode != null)
        {
            if (node.getNextNode() != ValueFileNode.NULL_ADDRESS)
                throw new IllegalAccessError();
            node.setNextNode(existingNode.getAddress());
            addrOfIdentifier = fileHandle.nextAvailableChunk();
        }

        node.setAddress(addrOfIdentifier);
        fileHandle.set(Utilities.convert(node), addrOfIdentifier);

        return addrOfIdentifier;
    }
    
    public int addIdentifier(String identifier)
    {
        int addrOfIdentifier = fileHandle.nextAvailableChunk();
        ValueFileNode node = new ValueFileNode(identifier);
        node.setAddress(addrOfIdentifier);
        fileHandle.set(Utilities.convert(node), addrOfIdentifier);

        return addrOfIdentifier;
    }

    public int removeIdentifier(int addrOfIdentifierStart, String identifier)
    {
        ValueFileNode start = (ValueFileNode) fileHandle.get(addrOfIdentifierStart);
        if (start == null)
            return ValueFileNode.NULL_ADDRESS;
        if (start.getIdentifier().equals(identifier))
        {
            int next = start.getNextNode();
            fileHandle.free(addrOfIdentifierStart);
            return next;
        }
        else
        {
            int next = start.getNextNode();
            if (next == ValueFileNode.NULL_ADDRESS)
                return ValueFileNode.NULL_ADDRESS;
            ValueFileNode node = (ValueFileNode) fileHandle.get(next);
            ValueFileNode previous = start;
            while (!node.getIdentifier().equals(identifier))
            {
                previous = node;
                if (node.getNextNode() == next)
                    break;
                next = node.getNextNode();
                node = (ValueFileNode) fileHandle.get(next);
            }
            // Node is now the node to remove, previous is the one before it
            previous.setNextNode(node.getNextNode());
            fileHandle.set(Utilities.convert(previous), previous.getAddress());
            fileHandle.free(node.getAddress());

            return ValueFileNode.NULL_ADDRESS;
        }
    }

    public ArrayList<String> getAllIdentifiers(int addrOfIdentifierStart)
    {
        ArrayList<String> result = new ArrayList<String>();

        ValueFileNode start = (ValueFileNode) fileHandle.get(addrOfIdentifierStart);
        if (start == null)
            return null;
        result.add(start.getIdentifier());

        int next = start.getNextNode();
        ValueFileNode node;
        while (next != ValueFileNode.NULL_ADDRESS)
        {
            node = (ValueFileNode) fileHandle.get(next);
            result.add(node.getIdentifier());
            if (node.getNextNode() == next)
                break;
            next = node.getNextNode();
        }

        return result;
    }

}
