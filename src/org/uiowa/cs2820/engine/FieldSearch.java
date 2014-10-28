package org.uiowa.cs2820.engine;

public class FieldSearch
{

    private Database database;

    FieldSearch(Database database)
    {
        this.database = database;
    }

    public String[] findEquals(Field searchField)
    {
        byte[] key = searchField.toBytes();
        Node p = database.fetch(key);
        if (p == null)
            return new String[0];
        String[] results = new String[p.nodeIdentifiers.size()];
        results = p.nodeIdentifiers.toArray(results);
        return results;
    }
}