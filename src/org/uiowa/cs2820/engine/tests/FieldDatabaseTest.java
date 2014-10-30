package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.uiowa.cs2820.engine.BinaryFileNode;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.FieldDatabase;

public class FieldDatabaseTest
{
    @Test
    public void testInitialAddGetCycle()
    {
        MockChunkRandomAccessFile file = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);

        FieldDatabase fieldDB = new FieldDatabase(file);
        BinaryFileNode node = new BinaryFileNode(new Field("name", "value"), 0);
        fieldDB.add(node);

        assertEquals(0, fieldDB.getIdentifierPosition(new Field("name", "value")));
    }
    
    @Test
    public void testAddRootAndLeft()
    {
        MockChunkRandomAccessFile file = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);

        FieldDatabase fieldDB = new FieldDatabase(file);
        BinaryFileNode node1 = new BinaryFileNode(new Field("name", "a"), 0);
        BinaryFileNode node2 = new BinaryFileNode(new Field("name", "b"), 1);
        fieldDB.add(node1);
        fieldDB.add(node2);

        assertEquals(0, fieldDB.getIdentifierPosition(new Field("name", "a")));
        assertEquals(1, fieldDB.getIdentifierPosition(new Field("name", "b")));
    }
    
    @Test
    public void testAddRootAndRight()
    {
        MockChunkRandomAccessFile file = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);

        FieldDatabase fieldDB = new FieldDatabase(file);
        BinaryFileNode node1 = new BinaryFileNode(new Field("name", "b"), 0);
        BinaryFileNode node2 = new BinaryFileNode(new Field("name", "a"), 1);
        fieldDB.add(node1);
        fieldDB.add(node2);

        assertEquals(0, fieldDB.getIdentifierPosition(new Field("name", "b")));
        assertEquals(1, fieldDB.getIdentifierPosition(new Field("name", "a")));
    }
    
    @Test
    public void testAddingADuplicate()
    {
        MockChunkRandomAccessFile file = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);

        FieldDatabase fieldDB = new FieldDatabase(file);
        BinaryFileNode node1 = new BinaryFileNode(new Field("name", "a"), 0);
        BinaryFileNode node2 = new BinaryFileNode(new Field("name", "a"), 1);
        fieldDB.add(node1);
        fieldDB.add(node2);

        assertEquals(0, fieldDB.getIdentifierPosition(new Field("name", "a")));
        // We won't overwrite data
        assertEquals(0, fieldDB.getIdentifierPosition(new Field("name", "a")));
    }
    
    @Test
    public void testAddingMultipleValuesWorstCase()
    {
        MockChunkRandomAccessFile file = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);

        FieldDatabase fieldDB = new FieldDatabase(file);
        BinaryFileNode node1 = new BinaryFileNode(new Field("name", "a"), 0);
        BinaryFileNode node2 = new BinaryFileNode(new Field("name", "b"), 1);
        BinaryFileNode node3 = new BinaryFileNode(new Field("name", "c"), 2);
        BinaryFileNode node4 = new BinaryFileNode(new Field("name", "d"), 3);
        BinaryFileNode node5 = new BinaryFileNode(new Field("name", "e"), 4);
        BinaryFileNode node6 = new BinaryFileNode(new Field("name", "f"), 5);
        BinaryFileNode node7 = new BinaryFileNode(new Field("name", "g"), 6);
        BinaryFileNode node8 = new BinaryFileNode(new Field("name", "h"), 7);
        BinaryFileNode node9 = new BinaryFileNode(new Field("name", "i"), 8);
        BinaryFileNode node10 = new BinaryFileNode(new Field("name", "j"), 9);
        fieldDB.add(node1);
        fieldDB.add(node2);
        fieldDB.add(node3);
        fieldDB.add(node4);
        fieldDB.add(node5);
        fieldDB.add(node6);
        fieldDB.add(node7);
        fieldDB.add(node8);
        fieldDB.add(node9);
        fieldDB.add(node10);
        
        
        assertEquals(0, fieldDB.getIdentifierPosition(new Field("name", "a")));
        assertEquals(1, fieldDB.getIdentifierPosition(new Field("name", "b")));
        assertEquals(2, fieldDB.getIdentifierPosition(new Field("name", "c")));
        assertEquals(3, fieldDB.getIdentifierPosition(new Field("name", "d")));
        assertEquals(4, fieldDB.getIdentifierPosition(new Field("name", "e")));
        assertEquals(5, fieldDB.getIdentifierPosition(new Field("name", "f")));
        assertEquals(6, fieldDB.getIdentifierPosition(new Field("name", "g")));
        assertEquals(7, fieldDB.getIdentifierPosition(new Field("name", "h")));
        assertEquals(8, fieldDB.getIdentifierPosition(new Field("name", "i")));
        assertEquals(9, fieldDB.getIdentifierPosition(new Field("name", "j")));
    }
    
    @Test
    public void testAddingMultipleValuesNormalCase()
    {
        MockChunkRandomAccessFile file = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);

        FieldDatabase fieldDB = new FieldDatabase(file);
        BinaryFileNode node1 = new BinaryFileNode(new Field("name", "e"), 0);
        BinaryFileNode node2 = new BinaryFileNode(new Field("name", "b"), 1);
        BinaryFileNode node3 = new BinaryFileNode(new Field("name", "j"), 2);
        BinaryFileNode node4 = new BinaryFileNode(new Field("name", "d"), 3);
        BinaryFileNode node5 = new BinaryFileNode(new Field("name", "c"), 4);
        BinaryFileNode node6 = new BinaryFileNode(new Field("name", "i"), 5);
        BinaryFileNode node7 = new BinaryFileNode(new Field("name", "a"), 6);
        BinaryFileNode node8 = new BinaryFileNode(new Field("name", "h"), 7);
        BinaryFileNode node9 = new BinaryFileNode(new Field("name", "g"), 8);
        BinaryFileNode node10 = new BinaryFileNode(new Field("name", "f"), 9);
        fieldDB.add(node1);
        fieldDB.add(node2);
        fieldDB.add(node3);
        fieldDB.add(node4);
        fieldDB.add(node5);
        fieldDB.add(node6);
        fieldDB.add(node7);
        fieldDB.add(node8);
        fieldDB.add(node9);
        fieldDB.add(node10);

        assertEquals(0, fieldDB.getIdentifierPosition(new Field("name", "e")));
        assertEquals(1, fieldDB.getIdentifierPosition(new Field("name", "b")));
        assertEquals(2, fieldDB.getIdentifierPosition(new Field("name", "j")));
        assertEquals(3, fieldDB.getIdentifierPosition(new Field("name", "d")));
        assertEquals(4, fieldDB.getIdentifierPosition(new Field("name", "c")));
        assertEquals(5, fieldDB.getIdentifierPosition(new Field("name", "i")));
        assertEquals(6, fieldDB.getIdentifierPosition(new Field("name", "a")));
        assertEquals(7, fieldDB.getIdentifierPosition(new Field("name", "h")));
        assertEquals(8, fieldDB.getIdentifierPosition(new Field("name", "g")));
        assertEquals(9, fieldDB.getIdentifierPosition(new Field("name", "f")));
    }
}
