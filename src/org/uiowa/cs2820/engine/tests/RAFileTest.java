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
    
    /*
     * Other test ideas:
     *      delete a chunk, make sure nothing is there
     *      write to an occupied chunk, see if you get back the newest object
     *      write to a chunk larger than the number of chunks in the file
     *      read from a chunk larger than the number of chunks in the file
     *      test that the nextAvailableChunk method returns what would be expected
     *      etc
     */
}
