package org.uiowa.cs2820.engine.tests;

import org.uiowa.cs2820.engine.ChunkedAccess;
import org.uiowa.cs2820.engine.utilities.Utilities;

public class MockChunkRandomAccessFile implements ChunkedAccess
{
    private byte[] mockFile;
    private int chunkSize;
    
    public MockChunkRandomAccessFile(int initialSize, int chunkSize)
    {
        mockFile = new byte[initialSize * chunkSize];
        this.chunkSize = chunkSize;
    }
    
    @Override
    public Object get(int chunkPosition)
    {
        byte[] result = new byte[chunkSize];
        if (chunkPosition > mockFile.length / chunkSize)
            doubleCapacity();
        System.arraycopy(mockFile, chunkPosition * chunkSize, result, 0, chunkSize);
        return Utilities.revert(result);
    }

    @Override
    public void set(byte[] objectByteRepr, int chunkPosition)
    {
        System.arraycopy(objectByteRepr, 0, mockFile, chunkPosition * chunkSize, objectByteRepr.length);
    }

    @Override
    public int nextAvailableChunk()
    {
        for (int i = 0; i * chunkSize < mockFile.length; i++)
        {
            if (get(i) == null)
            {
                return i;
            }
        }
        int newAvailableChunk = mockFile.length / chunkSize;
        doubleCapacity();
        return newAvailableChunk;
    }

    public void doubleCapacity()
    {
        byte[] newArray = new byte[mockFile.length * 2];
        System.arraycopy(mockFile, 0, newArray, 0, mockFile.length);
        mockFile = newArray;
    }

    @Override
    public void free(int chunkPosition)
    {
        byte[] zeros = new byte[chunkSize];
        System.arraycopy(zeros, 0, mockFile, chunkPosition * chunkSize, zeros.length);
    }
    
    
    
}
