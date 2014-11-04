package org.uiowa.cs2820.engine;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RAFile extends ChunkedAccess
{
	protected File FILE;
	
	public RAFile( File fileName, int initialNumChunks, int chunkSize) 
	{
		// gives me chunkSize and numberOfChunks (power of 2)
		super(initialNumChunks, chunkSize);
		this.FILE = fileName;
	}

	@Override
	protected void getChunk(byte[] result, int chunkPosition) 
	{
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(FILE, "r");
			file.seek(chunkPosition * chunkSize);
			file.read(result);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	protected void setChunk(byte[] objectByteRepr, int chunkPosition) {
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(FILE, "rw");
			file.seek(chunkPosition * chunkSize);
			file.write(objectByteRepr);
		    file.close();	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	// Only need to write first two bytes at chunkPosition to 0. Because of the way
	// ByteConverter utility works, if it reads [0,0] for first two bytes, that content will
	// be ignored, and overwritten if needed.
	public void free(int chunkPosition)
	{
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(FILE, "rw");
			file.seek(chunkPosition*chunkSize); // seek chunkPosition
			byte[] erase = convert(0);
			file.write(erase); 					// only write first two bytes?
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}

	private byte[] convert(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void internalDoubleCapacity() 
	{
		// TODO Auto-generated method stub
		
	}

}
