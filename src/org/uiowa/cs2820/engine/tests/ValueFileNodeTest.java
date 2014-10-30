package org.uiowa.cs2820.engine.tests;

import org.junit.Test;
import org.uiowa.cs2820.engine.ValueFileNode;

public class ValueFileNodeTest
{
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThrowsForTooLong()
    {
        new ValueFileNode("some very long string that should not" + "be allowed as part of a lookup value" + "because there is a size limit in the"
                + "code for creating a Field");
    }
}
