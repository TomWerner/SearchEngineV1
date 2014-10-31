package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uiowa.cs2820.engine.BinaryFileNode;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.utilities.ByteConverter;

public class BinaryFileNodeTest
{
    @Test
    public void testToAndFromByteArray()
    {
        BinaryFileNode node = new BinaryFileNode(new Field("value", "name"), 5, 0, 0);
        byte[] byteRepr = node.convert();
        BinaryFileNode revertedNode = (BinaryFileNode)ByteConverter.revert(byteRepr);
        assertEquals(node, revertedNode);
        assertEquals(node.getHeadOfLinkedListPosition(), revertedNode.getHeadOfLinkedListPosition());
        assertEquals(node.getLeftPosition(), revertedNode.getLeftPosition());
        assertEquals(node.getRightPosition(), revertedNode.getRightPosition());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooLongByteArrayError()
    {
        byte[] byteArray = new byte[BinaryFileNode.MAX_SIZE + 1];
        BinaryFileNode.revert(byteArray);
    }
    
    @Test
    public void testEquals()
    {
        BinaryFileNode node1 = new BinaryFileNode(new Field("value", "name"), 5, 0, 0);
        BinaryFileNode node2 = new BinaryFileNode(new Field("value", "name"), 5, 0, 0);
        BinaryFileNode node3 = new BinaryFileNode(new Field("value", "name2"), 5, 0, 0);

        assertTrue(node1.equals(node1));
        assertTrue(node1.equals(node2));
        assertFalse(node1.equals(node3));
        assertFalse(node1.equals(new Object()));
    }
}
