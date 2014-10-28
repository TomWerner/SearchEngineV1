package org.uiowa.cs2820.engine;

import java.util.ArrayList;
import java.util.Arrays;

public class LinearMemoryDatabase implements Database
{
    private ArrayList<Node> memoryArray = null;

    LinearMemoryDatabase()
    {
        this.memoryArray = new ArrayList<Node>(); // empty list
    }

    public Node fetch(byte[] key)
    {
        for (Node node : memoryArray)
            if (Arrays.equals(node.nodeKey, key))
                return node;
        return null;
    }

    public void store(byte[] key, String id)
    {
        for (Node node : memoryArray)
        {
            if (Arrays.equals(node.nodeKey, key))
            {
                node.add(id);
                return;
            }
        }
        Node newNode = new Node(key, id);
        memoryArray.add(newNode);
    }

    public void delete(byte[] key, String id)
    {
        for (Node node : memoryArray)
        {
            if (Arrays.equals(node.nodeKey, key))
            {
                node.nodeIdentifiers.remove(id);
                return;
            }
        }
    }
}
