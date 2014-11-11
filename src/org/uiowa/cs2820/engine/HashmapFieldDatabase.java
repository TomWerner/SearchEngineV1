package org.uiowa.cs2820.engine;

import java.util.ArrayList;

public class HashmapFieldDatabase extends FieldDatabase {

	public HashmapFieldDatabase(ChunkedAccess fileHandle) {
		super(fileHandle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void add(BinaryFileNode node) {
		int hash = node.hashCode();
		int position = getFileHandle().numberOfChunks;
		int oldNumChunks = position;
		position = Math.abs(hash % position);
		if (node.equals(getFileHandle().get(position))) { return; }
		else if (getFileHandle().get(position) == null)
		{
			getFileHandle().set(node.convert(), position);
		}
		else
		{
			int result = getFileHandle().nextAvailableChunk( position );
			if (getFileHandle().numberOfChunks > oldNumChunks )
			{
				ArrayList<BinaryFileNode> nodes = new ArrayList<BinaryFileNode>();
				for (int i = 0; i < oldNumChunks ;i++)
				{
					BinaryFileNode tempNode = (BinaryFileNode) getFileHandle().get(i);
					if (tempNode != null)
					{
						nodes.add(tempNode);
						getFileHandle().free(i);
					}
				}
				for (BinaryFileNode toAdd: nodes)
				{
					add(toAdd);
				}
				add(node);
			}
			else
			{
			getFileHandle().set(node.convert(), result);
			}
		}
	}

	@Override
	public int getIdentifierPosition(Field field) 
	{
		int hash = field.hashCode();
		int position = getFileHandle().numberOfChunks;
		position = Math.abs(hash % position);
        
        BinaryFileNode currentNode = (BinaryFileNode) getFileHandle().get(position);
        
        while (currentNode != null && !currentNode.getField().equals(field) && position < getFileHandle().numberOfChunks)
        {
        	position++;
        	currentNode = (BinaryFileNode) getFileHandle().get(position);
        }
        
        if (currentNode == null || position >= getFileHandle().numberOfChunks) { return -1; }
        return currentNode.getHeadOfLinkedListPosition();
	}

	@Override
	public void setIdentifierPosition(Field field, int headOfLinkedListPosition) 
	{
		int hash = field.hashCode();
		int position = getFileHandle().numberOfChunks;
		position = Math.abs(hash % position);
		
		BinaryFileNode currentNode = (BinaryFileNode) getFileHandle().get(position);
		
		while (currentNode != null && !currentNode.getField().equals(field) && position < getFileHandle().numberOfChunks)
		{
			position++;
			currentNode = (BinaryFileNode) getFileHandle().get(position);
		}
		
		if (currentNode == null || position >= getFileHandle().numberOfChunks) { return; }
		
		currentNode.setHeadOfLinkedListPosition(headOfLinkedListPosition);
		getFileHandle().set(currentNode.convert(), position);
		
	}

}
