package org.uiowa.cs2820.engine.databases;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.uiowa.cs2820.engine.Field;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class BinaryTreeIterator implements Iterator<Field>
{
    private FieldDatabase database;
    private int nextPointer;

    public BinaryTreeIterator(FieldDatabase database, int rootPosition)
    {
        this.database = database;

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
                    return originalNext.getField();
                }
                if (((FieldFileNode) database.getElementAt(next.getParentPosition())).getLeftPosition() == nextPointer)
                {
                    nextPointer = next.getParentPosition();
                    next = (FieldFileNode) database.getElementAt(next.getParentPosition());
                    return originalNext.getField();
                }
                nextPointer = next.getParentPosition();
                next = (FieldFileNode) database.getElementAt(next.getParentPosition());
            }
        }
        return originalNext.getField();
    }

    @Override
    public void remove()
    {
        throw new NotImplementedException();
    }

}
