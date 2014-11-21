package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;
import org.uiowa.cs2820.engine.FieldFileNode;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.databases.BinaryTreeFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldDatabase;

public class BinaryTreeIteratorTest
{
    @Test
    public void testSingleElement()
    {
        FieldDatabase database = getDatabase();
        
        database.add(new FieldFileNode(new Field("z", "a"), 0));
        Iterator<Field> iterator = database.iterator();
        
        assertTrue(iterator.hasNext());
        assertEquals(new Field("z", "a"), iterator.next());
        
        assertFalse(iterator.hasNext());
    }
    
    @Test
    public void testElementToTheRight()
    {
        FieldDatabase database = getDatabase();

        database.add(new FieldFileNode(new Field("m", "a"), 0));
        database.add(new FieldFileNode(new Field("n", "a"), 0));
        Iterator<Field> iterator = database.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(new Field("m", "a"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("n", "a"), iterator.next());
        
        assertFalse(iterator.hasNext());
    }
    
    @Test
    public void testElementToTheLeft()
    {
        FieldDatabase database = getDatabase();

        database.add(new FieldFileNode(new Field("m", "a"), 0));
        database.add(new FieldFileNode(new Field("l", "a"), 0));
        Iterator<Field> iterator = database.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(new Field("l", "a"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("m", "a"), iterator.next());
        
        assertFalse(iterator.hasNext());
    }
    
    @Test
    public void testFullTree()
    {
        FieldDatabase database = getDatabase();

        database.add(new FieldFileNode(new Field("a", "a"), 0));
        database.add(new FieldFileNode(new Field("b", "a"), 0));
        database.add(new FieldFileNode(new Field("c", "a"), 0));
        database.add(new FieldFileNode(new Field("d", "a"), 0));
        database.add(new FieldFileNode(new Field("e", "a"), 0));
        database.add(new FieldFileNode(new Field("f", "a"), 0));
        Iterator<Field> iterator = database.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", "a"), iterator.next());
        
        assertTrue(iterator.hasNext());
        assertEquals(new Field("b", "a"), iterator.next());
        
        assertTrue(iterator.hasNext());
        assertEquals(new Field("c", "a"), iterator.next());
        
        assertTrue(iterator.hasNext());
        assertEquals(new Field("d", "a"), iterator.next());
        
        assertTrue(iterator.hasNext());
        assertEquals(new Field("e", "a"), iterator.next());
        
        assertTrue(iterator.hasNext());
        assertEquals(new Field("f", "a"), iterator.next());
        
        assertFalse(iterator.hasNext());
    }
    
    protected FieldDatabase getDatabase()
    {
        return new BinaryTreeFieldDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
    }
}
