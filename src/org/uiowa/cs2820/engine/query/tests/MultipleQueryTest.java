package org.uiowa.cs2820.engine.query.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uiowa.cs2820.engine.operators.And;
import org.uiowa.cs2820.engine.operators.Or;
import org.uiowa.cs2820.engine.query.MultiQuery;
import org.uiowa.cs2820.engine.query.Query;

public class MultipleQueryTest extends DoubleQueryTest
{
    @Test
    public void test_default_operator()
    {
        Query queryTrue = new MockQuery(true);
        Query queryFalse = new MockQuery(false);

        // All are default and so are OR
        MultiQuery query1 = new MultiQuery(queryFalse, queryFalse, queryFalse);
        MultiQuery query2 = new MultiQuery(queryTrue, queryFalse, queryFalse);
        MultiQuery query3 = new MultiQuery(queryTrue, queryTrue, queryFalse);
        MultiQuery query4 = new MultiQuery(queryTrue, queryTrue, queryTrue);

        assertFalse(query1.isSatisfiedBy(null));
        assertTrue(query2.isSatisfiedBy(null));
        assertTrue(query3.isSatisfiedBy(null));
        assertTrue(query4.isSatisfiedBy(null));
    }
    
    @Test
    public void test_or_operator()
    {
        Query queryTrue = new MockQuery(true);
        Query queryFalse = new MockQuery(false);

        // All are default and so are OR
        MultiQuery query1 = new MultiQuery(new Or(), queryFalse, queryFalse, queryFalse);
        MultiQuery query2 = new MultiQuery(new Or(), queryTrue, queryFalse, queryFalse);
        MultiQuery query3 = new MultiQuery(new Or(), queryTrue, queryTrue, queryFalse);
        MultiQuery query4 = new MultiQuery(new Or(), queryTrue, queryTrue, queryTrue);

        assertFalse(query1.isSatisfiedBy(null));
        assertTrue(query2.isSatisfiedBy(null));
        assertTrue(query3.isSatisfiedBy(null));
        assertTrue(query4.isSatisfiedBy(null));
    }

    @Test
    public void test_and_operator()
    {
        Query queryTrue = new MockQuery(true);
        Query queryFalse = new MockQuery(false);

        // All are default and so are OR
        MultiQuery query1 = new MultiQuery(new And(), queryFalse, queryFalse, queryFalse);
        MultiQuery query2 = new MultiQuery(new And(), queryTrue, queryFalse, queryFalse);
        MultiQuery query3 = new MultiQuery(new And(), queryTrue, queryTrue, queryFalse);
        MultiQuery query4 = new MultiQuery(new And(), queryTrue, queryTrue, queryTrue);

        assertFalse(query1.isSatisfiedBy(null));
        assertFalse(query2.isSatisfiedBy(null));
        assertFalse(query3.isSatisfiedBy(null));
        assertTrue(query4.isSatisfiedBy(null));
    }
}
