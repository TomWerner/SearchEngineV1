package org.uiowa.cs2820.engine;

public interface Database
{
    public abstract Node fetch(byte[] key); // fetch a Node by key

    public abstract void delete(byte[] key, String id); // delete an id

    public abstract void store(byte[] key, String id); // store an id
}
