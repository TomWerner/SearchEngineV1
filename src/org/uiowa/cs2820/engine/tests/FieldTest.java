package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.uiowa.cs2820.engine.Database;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.FieldSearch;
import org.uiowa.cs2820.engine.Indexer;
import org.uiowa.cs2820.engine.LinearMemoryDatabase;

public class FieldTest
{

    @Test
    public void testEmptyDatabaseReturnsNothing()
    {
        Database database = new LinearMemoryDatabase();
        FieldSearch fieldSearch = new FieldSearch(database);
        Field F1 = new Field("1", new Integer(45));
        assertEquals(fieldSearch.findEquals(F1).length, 0);
    }

    @Test
    public void testSameFieldDifferentIdentifiers()
    {
        Database database = new LinearMemoryDatabase();
        FieldSearch search = new FieldSearch(database);
        Indexer indexer = new Indexer(database, "abc");
        
        Field F1 = new Field("1", new Integer(45));
        Field F2 = new Field("part", "bolt");
        indexer.addField(F1);
        indexer.addField(F2);
        
        indexer = new Indexer(database, "def");
        Field F3 = new Field("part", "bolt");
        indexer.addField(F3);
        
        String[] S = search.findEquals(F3);
        assertEquals(S.length, 2);
        assertEquals(S[0], "abc");
        assertEquals(S[1], "def");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testErrorIsThrownForTooLongFieldValue()
    {
        Database database = new LinearMemoryDatabase();
        Indexer indexer = new Indexer(database, "data");
        Field F = new Field("Iowa", "some very long string that should not" + "be allowed as part of a lookup value" + "because there is a size limit in the"
                + "code for creating a Field");
        indexer.addField(F);
    }
}
