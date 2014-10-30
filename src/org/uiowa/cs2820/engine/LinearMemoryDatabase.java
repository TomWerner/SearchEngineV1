package org.uiowa.cs2820.engine;

import java.util.ArrayList;

import org.uiowa.cs2820.engine.utilities.Utilities;

public class LinearMemoryDatabase implements Database
{
    private ArrayList<Node> memoryArray = null;

    public LinearMemoryDatabase()
    {
        this.memoryArray = new ArrayList<Node>(); // empty list
    } 

    public ArrayList<String> fetch(Field key)
    {
        for (Node node : memoryArray)
            if (Utilities.revert(node.nodeKey).equals(key))
                return node.nodeIdentifiers;
        return null;
    } 

    public void store(Field key, String id)
    {
        for (Node node : memoryArray)
        {
            if (Utilities.revert(node.nodeKey).equals(key))
            {
                node.add(id);
                return;
            }
        }
        Node newNode = new Node(key.toBytes(), id);
        memoryArray.add(newNode);
    }

    public void delete(Field key, String id)
    {
        for (Node node : memoryArray)
        {
            if (node.nodeKey.equals(key))
            {
                node.nodeIdentifiers.remove(id);
                return;
            }
        }
    }
}
