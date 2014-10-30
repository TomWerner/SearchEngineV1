package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.uiowa.cs2820.engine.Database;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.FieldSearch;
import org.uiowa.cs2820.engine.Indexer;
import org.uiowa.cs2820.engine.LinearMemoryDatabase;

public class IntegrationTests
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
        assertEquals(2, S.length);
        assertEquals(S[0], "abc");
        assertEquals(S[1], "def");
    }
}
