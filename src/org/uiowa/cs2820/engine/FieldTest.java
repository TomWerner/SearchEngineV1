package org.uiowa.cs2820.engine;

import static org.junit.Assert.*;

import org.junit.Test;

public class FieldTest
{

    @Test
    public void test0()
    {
        Database database = new LinearMemoryDatabase();
        FieldSearch fieldSearch = new FieldSearch(database);
        Field F1 = new Field("1", new Integer(45));
        assertEquals(fieldSearch.findEquals(F1).length, 0);
    }

    @Test
    public void test1()
    {
        Database database = new LinearMemoryDatabase();
        FieldSearch search = new FieldSearch(database);
        Indexer indexer = new Indexer(database, "abc");
        Field F1 = new Field("1", new Integer(45));
        Field F2 = new Field("part", "bolt");
        Field F3 = new Field("part", "bolt");
        indexer.addField(F1);
        indexer.addField(F2);
        indexer = new Indexer(database, "def");
        indexer.addField(F3);
        String[] S = search.findEquals(F3);
        assertEquals(S.length, 2);
        assertEquals(S[0], "abc");
        assertEquals(S[1], "def");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test2()
    {
        Database database = new LinearMemoryDatabase();
        Indexer indexer = new Indexer(database, "data");
        Field F = new Field("Iowa", "some very long string that should not" + "be allowed as part of a lookup value" + "because there is a size limit in the"
                + "code for creating a Field");
        indexer.addField(F);
    }
}
