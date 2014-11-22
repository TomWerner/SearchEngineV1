package org.uiowa.cs2820.engine.databases;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.uiowa.cs2820.engine.Field;

public class BinaryTreeIterator implements Iterator<Field>
{
    private FieldDatabase database;
    private int lastReturned = -1;
    private int nextPointer;
    private int initialRootPosition;

    public BinaryTreeIterator(FieldDatabase database, int rootPosition)
    {
        this.database = database;
        this.initialRootPosition = rootPosition;

        resetIterator(rootPosition);
    }

    @Override
    public boolean hasNext()
    {
        return nextPointer != FieldFileNode.NULL_ADDRESS && database.getElementAt(nextPointer) != null;
    }

    @Override
    public Field next()
    {
        if (!hasNext())
            throw new NoSuchElementException();
        FieldFileNode next = (FieldFileNode) database.getElementAt(nextPointer);
        FieldFileNode originalNext = next;
        // if you can walk right, walk right, then fully left.
        // otherwise, walk up until you come from left.
        if (next.getRightPosition() != FieldFileNode.NULL_ADDRESS)
        {
            nextPointer = next.getRightPosition();
            next = (FieldFileNode) database.getElementAt(nextPointer);
            while (next.getLeftPosition() != FieldFileNode.NULL_ADDRESS)
            {
                nextPointer = next.getLeftPosition();
                next = (FieldFileNode) database.getElementAt(next.getLeftPosition());
            }
        }
        else
        {
            while (true)
            {
                if (next.getParentPosition() == FieldFileNode.NULL_ADDRESS)
                {
                    nextPointer = FieldFileNode.NULL_ADDRESS;
                    lastReturned = originalNext.getAddress();
                    return originalNext.getField();
                }
                
                if (((FieldFileNode) database.getElementAt(next.getParentPosition())).getLeftPosition() == nextPointer)
                {
                    nextPointer = next.getParentPosition();
                    next = (FieldFileNode) database.getElementAt(next.getParentPosition());
                    lastReturned = originalNext.getAddress();
                    return originalNext.getField();
                }
                nextPointer = next.getParentPosition();
                next = (FieldFileNode) database.getElementAt(next.getParentPosition());
            }
        }
        lastReturned = originalNext.getAddress();
        return originalNext.getField();
    }

    @Override
    public void remove()
    {
        if (lastReturned < 0)
            throw new IllegalStateException();
        
        database.removeElement(lastReturned);
        lastReturned = -1;
        
        resetIterator(initialRootPosition);
    }
    
    protected void resetIterator(int rootPosition)
    {
        nextPointer = rootPosition;
        FieldFileNode next = (FieldFileNode) database.getElementAt(nextPointer);

        // Go all the way left
        if (next == null)
            return;
        while (next.getLeftPosition() != FieldFileNode.NULL_ADDRESS)
        {
            nextPointer = next.getLeftPosition();
            next = (FieldFileNode) database.getElementAt(next.getLeftPosition());
        }
    }

}
