package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.uiowa.cs2820.engine.BinaryFileNode;
import org.uiowa.cs2820.engine.Field;

public class BinaryFileNodeTest
{
    @Test
    public void testToAndFromByteArray()
    {
        BinaryFileNode node = new BinaryFileNode(new Field("value", "name"), 5);
        byte[] byteRepr = node.convert();
        BinaryFileNode revertedNode = (BinaryFileNode)(node.revert(byteRepr));
        assertEquals(node, revertedNode);
    }
}
