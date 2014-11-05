package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.uiowa.cs2820.engine.Node;

public class NodeTest {

	@Test
	public void testNodedelete() {
		Node node1 = new Node(null, "string");
		
		node1.remove("string");
	}

    @Test
    public void testNodeadd(){
        Node node2 = new Node(null, "string2");

        node2.remove("string2");
        node2.add("string2");

     }


}
