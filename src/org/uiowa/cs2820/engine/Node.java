package org.uiowa.cs2820.engine;

import java.util.ArrayList;

public class Node
{
    // Node is a basic unit in the database
    byte[] nodeKey; // Key of this node for lookup
    public ArrayList<String> nodeIdentifiers;

    public Node(byte[] f, String id)
    {
        this.nodeKey = f;
        this.nodeIdentifiers = new ArrayList<String>();
        this.nodeIdentifiers.add(id);
    }

    public void add(String id)
    {
        nodeIdentifiers.remove(id); // does nothing if id not already there
        nodeIdentifiers.add(id);
    }

    public void delete(String id)
    {

        nodeIdentifiers.remove(id);
    }


}
