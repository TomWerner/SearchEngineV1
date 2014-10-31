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
			file.seek(chunkPosition);
			byte[] bytes = new byte[chunkSize];
			file.read(bytes);
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	protected void setChunk(byte[] objectByteRepr, int chunkPosition) {
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(FILE, "rw");
			file.seek(chunkPosition);
			file.write(objectByteRepr);
		    file.close();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void free(int chunkPosition)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void internalDoubleCapacity() 
	{
		// TODO Auto-generated method stub
		
	}

}
