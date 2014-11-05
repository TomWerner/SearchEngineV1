package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.uiowa.cs2820.engine.BinaryFileNode;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.RAFile;

public class RAFileTest
{
    @Test
    public void testSimpleReadWriteCycle()
    {
        RAFile file = new RAFile(new File("test filename.dat"), 16, BinaryFileNode.MAX_SIZE);
        BinaryFileNode testObject = new BinaryFileNode(new Field("name", "value"), 0);

        file.set(testObject.convert(), 1);
        BinaryFileNode result = (BinaryFileNode) file.get(1);

        assertEquals(testObject, result);
    }
    
    @Test 
    public void testChunkDeletion()
    {
    	RAFile file = new RAFile(new File("test filename.dat"), 16, BinaryFileNode.MAX_SIZE);
    	BinaryFileNode testObject = new BinaryFileNode(new Field("name", "value"),1);

    	file.set(testObject.convert(), 1);
    	file.free(1);
    	BinaryFileNode result = (BinaryFileNode) file.get(1);
    	assertEquals(null, result);
    }

    @Test
    public void readLargeChunkTest()
    {
        RAFile file = new RAFile(new File("second test.dat"), 16, BinaryFileNode.MAX_SIZE);
        //BinaryFileNode testObject = new BinaryFileNode(new Field("name", "value"), 0);
        // ^^ shouldn't need this line since you aren't actually adding the testObject

        assertEquals(null,file.get(20));

    }

    @Test
    public void writeLargeChunkTest()
    {
        RAFile file = new RAFile(new File("third test.dat"), 16, BinaryFileNode.MAX_SIZE);
        BinaryFileNode testObject = new BinaryFileNode(new Field("name", "value"), 0);

        file.set(testObject.convert(), 25);
        BinaryFileNode result = (BinaryFileNode) file.get(25);

        assertEquals(result, file.get(25));
    }
    
    @Test
    public void testOverwriteOccupiedChunk()
    {
    	RAFile file = new RAFile(new File("test filename.dat"), 16, BinaryFileNode.MAX_SIZE);
    	BinaryFileNode testObject = new BinaryFileNode(new Field("name","value"),1);
    	file.set(testObject.convert(), 1);
    	
    	BinaryFileNode overwriteObject = new BinaryFileNode(new Field("number","value"),1);
    	file.set(overwriteObject.convert(), 1);

    	BinaryFileNode result = (BinaryFileNode) file.get(1);
    	assertEquals(overwriteObject, result);
    }
    
    /*
     * Other test ideas:
     *      test that the nextAvailableChunk method returns what would be expected
     *      etc
     */
}
