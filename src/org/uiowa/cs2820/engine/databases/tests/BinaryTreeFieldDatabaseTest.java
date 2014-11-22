package org.uiowa.cs2820.engine.databases.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.databases.BinaryTreeFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.fileoperations.ChunkedAccess;
import org.uiowa.cs2820.engine.fileoperations.MockChunkRandomAccessFile;

public class BinaryTreeFieldDatabaseTest extends FieldDatabaseTest
{
    @Test
    public void testRemovalFromSingleElementDatabase()
    {
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("a", "a"), 0));

        database.removeElement(0);
        assertEquals(null, database.getElementAt(0));
        
        Iterator<Field> iterator = database.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testRemovingRootFromTwoElementDatabase()
    {
        // This test case is special because we need to ensure
        // that the root is always at 0
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("a", "a"), 0));
        database.add(new FieldFileNode(new Field("b", "a"), 0));

        database.removeElement(0);
        assertEquals(new FieldFileNode(new Field("b", "a"), 0), database.getElementAt(0));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("b", "a"), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testRemovingMiddleFromtThreeElementDatabase()
    {
        // This test case is special because we need to ensure
        // that the root is always at 0
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("a", "a"), 0));
        database.add(new FieldFileNode(new Field("b", "a"), 0));
        database.add(new FieldFileNode(new Field("c", "a"), 0));

        // Remove "b"
        database.removeElement(1);
        
        FieldFileNode root = database.getElementAt(0);
        assertEquals(new FieldFileNode(new Field("a", "a"), 0), root);
        assertEquals(new FieldFileNode(new Field("c", "a"), 0), database.getElementAt(root.getRightPosition()));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", "a"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("c", "a"), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testRemovingChildFromTwoElementDatabase()
    {
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("a", "a"), 0));
        database.add(new FieldFileNode(new Field("b", "a"), 0));

        database.removeElement(1);
        assertEquals(new FieldFileNode(new Field("a", "a"), 0), database.getElementAt(0));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", "a"), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testLeftChildFromtThreeElementDatabase()
    {
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("m", "a"), 0)); // Root
        database.add(new FieldFileNode(new Field("n", "a"), 0)); // right child
        database.add(new FieldFileNode(new Field("l", "a"), 0)); // left child

        // Remove "l"
        database.removeElement(2);
        
        FieldFileNode root = database.getElementAt(0);
        assertEquals(new FieldFileNode(new Field("m", "a"), 0), root);
        assertEquals(new FieldFileNode(new Field("n", "a"), 0), database.getElementAt(root.getRightPosition()));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("m", "a"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("n", "a"), iterator.next());
        assertFalse(iterator.hasNext());
    }
    
    @Test
    public void testRightChildFromtThreeElementDatabase()
    {
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("m", "a"), 0)); // Root
        database.add(new FieldFileNode(new Field("n", "a"), 0)); // right child
        database.add(new FieldFileNode(new Field("l", "a"), 0)); // left child

        // Remove "n"
        database.removeElement(1);
        
        FieldFileNode root = database.getElementAt(0);
        assertEquals(new FieldFileNode(new Field("m", "a"), 0), root);
        assertEquals(new FieldFileNode(new Field("l", "a"), 0), database.getElementAt(root.getLeftPosition()));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("l", "a"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("m", "a"), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testRemoveRootFromtThreeElementDatabase()
    {
        // This test case is special because we need to ensure
        // that the root is always at 0
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("m", "a"), 0)); // Root
        database.add(new FieldFileNode(new Field("n", "a"), 0)); // right child
        database.add(new FieldFileNode(new Field("l", "a"), 0)); // left child

        // Remove "m"
        database.removeElement(0);
        
        FieldFileNode root = database.getElementAt(0);
        assertEquals(new FieldFileNode(new Field("n", "a"), 0), root);
        assertEquals(new FieldFileNode(new Field("l", "a"), 0), database.getElementAt(root.getLeftPosition()));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("l", "a"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("n", "a"), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testMultiElementDatabaseRemoval()
    {
        // binary tree from http://www.algolist.net/img/bst-remove-case-3-4.png
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("a", 5), 0));
        database.add(new FieldFileNode(new Field("a", 2), 0));
        database.add(new FieldFileNode(new Field("a", 12), 0));
        database.add(new FieldFileNode(new Field("a", -4), 0));
        database.add(new FieldFileNode(new Field("a", 3), 0));
        database.add(new FieldFileNode(new Field("a", 9), 0));
        database.add(new FieldFileNode(new Field("a", 12), 0));
        database.add(new FieldFileNode(new Field("a", 19), 0));
        database.add(new FieldFileNode(new Field("a", 21), 0));
        database.add(new FieldFileNode(new Field("a", 25), 0));
        
        // Remove "12"
        database.removeElement(2);
        
        assertEquals(new FieldFileNode(new Field("a", 5), 0),  database.getElementAt(0));
        assertEquals(new FieldFileNode(new Field("a", 2), 0),  database.getElementAt(1));
        assertEquals(new FieldFileNode(new Field("a", 19), 0), database.getElementAt(2));
        assertEquals(new FieldFileNode(new Field("a", -4), 0),  database.getElementAt(3));
        assertEquals(new FieldFileNode(new Field("a", 3), 0),  database.getElementAt(4));
        assertEquals(new FieldFileNode(new Field("a", 9), 0),  database.getElementAt(5));
        assertEquals(null,                                     database.getElementAt(6));
        assertEquals(new FieldFileNode(new Field("a", 21), 0), database.getElementAt(7));
        assertEquals(new FieldFileNode(new Field("a", 25), 0), database.getElementAt(8));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", -4), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", 2), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", 3), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", 5), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", 9), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", 19), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", 21), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", 25), iterator.next());
        assertFalse(iterator.hasNext());
    }
    

    @Override
    public FieldDatabase getDatabase(ChunkedAccess file)
    {
        return new BinaryTreeFieldDatabase(file);
    }
}
