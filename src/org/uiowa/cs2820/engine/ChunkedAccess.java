package org.uiowa.cs2820.engine;

import org.uiowa.cs2820.engine.utilities.Utilities;

public abstract class ChunkedAccess
{
    protected int chunkSize;
    protected int numberOfChunks;

    public ChunkedAccess(int initialNumChunks, int chunkSize)
    {
        this.chunkSize = chunkSize;
        numberOfChunks = 1;
        while (numberOfChunks < initialNumChunks)
            numberOfChunks <<= 1;
    }

    public Object get(int chunkPosition)
    {
        byte[] result = new byte[chunkSize];
        if (isChunkPositionOutOfBounds(chunkPosition))
            return null;
        getChunk(result, chunkPosition);
        return Utilities.revert(result);
    }

    protected abstract void getChunk(byte[] result, int chunkPosition);

    public void set(byte[] objectByteRepr, int chunkPosition)
    {
        if (objectByteRepr.length > chunkSize)
            throw new IllegalArgumentException("Byte array is larger than chunk size");

        while (isChunkPositionOutOfBounds(chunkPosition))
        {
            doubleCapacity();
        }

        setChunk(objectByteRepr, chunkPosition);
    }

    protected abstract void setChunk(byte[] objectByteRepr, int chunkPosition);

    public abstract void free(int chunkPosition);

    public abstract int nextAvailableChunk();

    public abstract void doubleCapacity();

    protected boolean isChunkPositionOutOfBounds(int chunkPosition)
    {
        if (chunkPosition < 0)
            throw new ArrayIndexOutOfBoundsException("Tried to access " + chunkPosition + ". Currently we only have " + numberOfChunks + " chunks");
        return chunkPosition >= numberOfChunks;
    }
}
