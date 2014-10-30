package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.uiowa.cs2820.engine.Field;

public class FieldTest
{

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThrowsForTooLong()
    {
        new Field("Iowa", "some very long string that should not" + "be allowed as part of a lookup value" + "because there is a size limit in the"
                + "code for creating a Field");
    }
    
    @Test
    public void testComparisonMatchingFieldName()
    {
        Field field1 = new Field("name", "a");
        Field field2 = new Field("name", "b");

        assertTrue(field1.compareTo(field1) == 0);
        assertTrue(field1.compareTo(field2) == -1);
        assertTrue(field2.compareTo(field1) == 1);
    }
    
    @Test
    public void testComparisonDifferentFieldName()
    {
        Field field1 = new Field("fieldA", "value");
        Field field2 = new Field("fieldB", "value");

        assertTrue(field1.compareTo(field1) == 0);
        assertTrue(field1.compareTo(field2) == -1);
        assertTrue(field2.compareTo(field1) == 1);
    }
}
