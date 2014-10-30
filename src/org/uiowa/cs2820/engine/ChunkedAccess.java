package org.uiowa.cs2820.engine;

public interface ChunkedAccess
{
    public Object get(int chunkPosition);
    public void set(byte[] objectByteRepr, int chunkPosition);
    public void free(int chunkPosition);
    public int nextAvailableChunk();
    public void doubleCapacity();
}
