package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;
import org.uiowa.cs2820.engine.ValueFileNode;
import org.uiowa.cs2820.engine.utilities.ByteConverter;

public class ValueFileNodeTest
{
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThrowsForTooLong()
    {
        new ValueFileNode("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }
    
    @Test
    public void testConvertToBytesAndBack()
    {
        ValueFileNode node = new ValueFileNode("Identifier", 0);
        node.setAddress(2);
        
        byte[] byteRepr = node.convert();
        ValueFileNode newNode = (ValueFileNode) ByteConverter.revert(byteRepr);
        assertEquals(node, newNode);
        assertEquals(node.getAddress(), newNode.getAddress());
        assertEquals(node.getNextNode(), newNode.getNextNode());
    }
    
    @Test
    public void testEquals()
    {
        ValueFileNode node1 = new ValueFileNode("Identifier", 0);
        node1.setAddress(2);
        
        ValueFileNode node2 = new ValueFileNode("Identifier", 0);
        node2.setAddress(2);
        
        ValueFileNode node3 = new ValueFileNode("Identifier2", 1);
        node3.setAddress(3);
        
        assertEquals(node1, node2);
        assertNotSame(node1, node3);
        
    }
}
