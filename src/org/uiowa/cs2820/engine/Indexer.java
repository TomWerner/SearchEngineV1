package org.uiowa.cs2820.engine;

public class Indexer
{
    private Database database;
    private String identifier;

    Indexer(Database d, String id)
    {
        // constructor does nothing now, but someday
        // may need to set up database for doing things
        this.database = d;
        this.identifier = id;
    }

    public void addField(Field f)
    {
        // Field has (name,value) which is used as key for
        // the database operations
        byte[] key = f.toBytes();
        database.store(key, identifier);
    }

}
