package org.uiowa.cs2820.engine.query.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.operators.And;
import org.uiowa.cs2820.engine.operators.Or;
import org.uiowa.cs2820.engine.query.DoubleQuery;
import org.uiowa.cs2820.engine.query.Query;

public class DoubleQueryTest
{
    protected class MockQuery extends Query
    {
        private boolean boolToReturn;

        public MockQuery(boolean boolToReturn)
        {
            super(null);
            this.boolToReturn = boolToReturn;
        }
        
        public boolean isSatisfiedBy(Field other)
        {
            return boolToReturn;
        }
    }
    
    @Test
    public void test_default_operator()
    {
        Query queryTrue = new MockQuery(true);
        Query queryFalse = new MockQuery(false);

        // All are default and so are OR
        DoubleQuery query1 = new DoubleQuery(queryTrue, queryFalse);
        DoubleQuery query2 = new DoubleQuery(queryFalse, queryTrue);
        DoubleQuery query3 = new DoubleQuery(queryFalse, queryFalse);
        DoubleQuery query4 = new DoubleQuery(queryTrue, queryTrue);

        assertTrue(query1.isSatisfiedBy(null));
        assertTrue(query2.isSatisfiedBy(null));
        assertFalse(query3.isSatisfiedBy(null));
        assertTrue(query4.isSatisfiedBy(null));
    }
    
    @Test
    public void test_or_operator()
    {
        Query queryTrue = new MockQuery(true);
        Query queryFalse = new MockQuery(false);

        // All are default and so are OR
        DoubleQuery query1 = new DoubleQuery(queryTrue, queryFalse, new Or());
        DoubleQuery query2 = new DoubleQuery(queryFalse, queryTrue, new Or());
        DoubleQuery query3 = new DoubleQuery(queryFalse, queryFalse, new Or());
        DoubleQuery query4 = new DoubleQuery(queryTrue, queryTrue, new Or());

        assertTrue(query1.isSatisfiedBy(null));
        assertTrue(query2.isSatisfiedBy(null));
        assertFalse(query3.isSatisfiedBy(null));
        assertTrue(query4.isSatisfiedBy(null));
    }

    @Test
    public void test_and_operator()
    {
        Query queryTrue = new MockQuery(true);
        Query queryFalse = new MockQuery(false);

        // All are default and so are OR
        DoubleQuery query1 = new DoubleQuery(queryTrue, queryFalse, new And());
        DoubleQuery query2 = new DoubleQuery(queryFalse, queryTrue, new And());
        DoubleQuery query3 = new DoubleQuery(queryFalse, queryFalse, new And());
        DoubleQuery query4 = new DoubleQuery(queryTrue, queryTrue, new And());

        assertFalse(query1.isSatisfiedBy(null));
        assertFalse(query2.isSatisfiedBy(null));
        assertFalse(query3.isSatisfiedBy(null));
        assertTrue(query4.isSatisfiedBy(null));
    }
}
