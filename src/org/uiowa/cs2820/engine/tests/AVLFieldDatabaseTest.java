package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uiowa.cs2820.engine.AVLFieldDatabase;
import org.uiowa.cs2820.engine.BinaryFileNode;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.FieldDatabase;

public class AVLFieldDatabaseTest extends FieldDatabaseTest
{
    @Override
    public FieldDatabase getDatabase(MockChunkRandomAccessFile file)
    {
        return new AVLFieldDatabase(file);
    }

    @Override
    @Test
    public void testAddingMultipleValuesWorstCase()
    {
        super.testAddingMultipleValuesWorstCase();
        
        // BENEFIT OF THE AVL TREE. With the standard tree it'd be a depth of 10
        assertTrue(((AVLFieldDatabase) fieldDB).depth(0) <= 5);
    }

    @Override
    @Test
    public void testAddingMultipleValuesWorstCaseReversed()
    {
        super.testAddingMultipleValuesWorstCaseReversed();
        
        // BENEFIT OF THE AVL TREE. With the standard tree it'd be a depth of 10
        assertTrue(((AVLFieldDatabase) fieldDB).depth(0) <= 5);
    }

    @Override
    @Test
    public void testAddingMultipleValuesNormalCase()
    {
        super.testAddingMultipleValuesNormalCase();
        
        // BENEFIT OF THE AVL TREE. With the standard tree it'd be a depth of 10
        assertTrue(((AVLFieldDatabase) fieldDB).depth(0) <= 5);
    }


    @Test
    public void testHeightOfTreeStructureRandomData()
    {
        MockChunkRandomAccessFile file = new MockChunkRandomAccessFile(6, BinaryFileNode.MAX_SIZE);

        FieldDatabase fieldDB = getDatabase(file);

        int number = 100;
        for (int i = 0; i < number; i++)
            fieldDB.add(new BinaryFileNode(new Field("name", Math.random()), 0));

        int theoretical = (int) (Math.log(number) / Math.log(2) + .5);
        int actual = ((AVLFieldDatabase) fieldDB).depth(0);
        assertTrue(Math.abs(theoretical - actual) <= 2);
    }
    
    @Test
    public void testHeightOfTreeStructureSortedData()
    {
        MockChunkRandomAccessFile file = new MockChunkRandomAccessFile(6, BinaryFileNode.MAX_SIZE);

        FieldDatabase fieldDB = getDatabase(file);

        int number = 100;
        for (int i = 0; i < number; i++)
            fieldDB.add(new BinaryFileNode(new Field("name", i), 0));

        int theoretical = (int) (Math.log(number) / Math.log(2) + .5);
        int actual = ((AVLFieldDatabase) fieldDB).depth(0);
        assertTrue(Math.abs(theoretical - actual) <= 2);
    }
}
