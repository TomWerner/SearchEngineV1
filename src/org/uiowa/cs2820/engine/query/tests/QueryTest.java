package org.uiowa.cs2820.engine.query.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.operators.Equals;
import org.uiowa.cs2820.engine.operators.GreaterThan;
import org.uiowa.cs2820.engine.operators.GreaterThanEqualTo;
import org.uiowa.cs2820.engine.operators.LessThan;
import org.uiowa.cs2820.engine.operators.LessThanEqualTo;
import org.uiowa.cs2820.engine.query.Query;

public class QueryTest
{
    @Test
    public void testEqualsQuery()
    {
        Query query = new Query(new Field("name", "value"), new Equals());
        assertEquals(true, query.isSatisfiedBy(new Field("name", "value")));
        assertEquals(false, query.isSatisfiedBy(new Field("name", "different value")));
        assertEquals(false, query.isSatisfiedBy(new Field("different name", "value")));
        assertEquals(new Equals(), query.getOperator());
    }
    
    @Test
    public void testDefaultQuery()
    {
        Query query = new Query(new Field("name", "value"));
        assertEquals(true, query.isSatisfiedBy(new Field("name", "value")));
        assertEquals(false, query.isSatisfiedBy(new Field("name", "different value")));
        assertEquals(false, query.isSatisfiedBy(new Field("different name", "value")));
        assertEquals(new Equals(), query.getOperator());
    }
    
    @Test
    public void testLessThanQuery()
    {
        Query query = new Query(new Field("name", "d"), new LessThan());
        assertEquals(true, query.isSatisfiedBy(new Field("name", "c")));
        assertEquals(false, query.isSatisfiedBy(new Field("name", "d")));
        assertEquals(false, query.isSatisfiedBy(new Field("different name", "value")));
        assertEquals(new LessThan(), query.getOperator());
    }
    
    @Test
    public void testGreaterThanQuery()
    {
        Query query = new Query(new Field("name", "d"), new GreaterThan());
        assertEquals(true, query.isSatisfiedBy(new Field("name", "e")));
        assertEquals(false, query.isSatisfiedBy(new Field("name", "d")));
        assertEquals(false, query.isSatisfiedBy(new Field("name", "c")));
        assertEquals(false, query.isSatisfiedBy(new Field("different name", "value")));
        assertEquals(new GreaterThan(), query.getOperator());
    }
    
    @Test
    public void testLessThanEqualToQuery()
    {
        Query query = new Query(new Field("name", "d"), new LessThanEqualTo());
        assertEquals(true, query.isSatisfiedBy(new Field("name", "c")));
        assertEquals(true, query.isSatisfiedBy(new Field("name", "d")));
        assertEquals(false, query.isSatisfiedBy(new Field("name", "e")));
        assertEquals(false, query.isSatisfiedBy(new Field("different name", "value")));
        assertEquals(new LessThanEqualTo(), query.getOperator());
    }
    
    @Test
    public void testGreaterThanEqualToQuery()
    {
        Query query = new Query(new Field("name", "d"), new GreaterThanEqualTo());
        assertEquals(true, query.isSatisfiedBy(new Field("name", "e")));
        assertEquals(true, query.isSatisfiedBy(new Field("name", "d")));
        assertEquals(false, query.isSatisfiedBy(new Field("name", "c")));
        assertEquals(false, query.isSatisfiedBy(new Field("different name", "value")));
        assertEquals(new GreaterThanEqualTo(), query.getOperator());
    }
}
