package org.uiowa.cs2820.engine;

public class RandomAccessFile extends ChunkedAccess
{
	protected int chunk;
	
	public RandomAccessFile(int initialNumChunks, int chunkSize) {
		super(initialNumChunks, chunkSize);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void getChunk(byte[] result, int chunkPosition) {
		
		 
	}

	@Override
	protected void setChunk(byte[] objectByteRepr, int chunkPosition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void free(int chunkPosition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int nextAvailableChunk() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void internalDoubleCapacity() {
		// TODO Auto-generated method stub
		
	}

}
