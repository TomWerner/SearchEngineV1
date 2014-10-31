package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.uiowa.cs2820.engine.BinaryFileNode;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.BinaryTreeFieldDatabase;
import org.uiowa.cs2820.engine.FieldDatabase;
import org.uiowa.cs2820.engine.IdentifierDatabase;
import org.uiowa.cs2820.engine.IntegratedFileDatabase;
import org.uiowa.cs2820.engine.ValueFileNode;

public class IntegratedDatabaseTest
{
    @Test
    public void testSingleAddRetrieveCycle()
    {
        MockChunkRandomAccessFile file1 = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new BinaryTreeFieldDatabase(file1);
        MockChunkRandomAccessFile file2 = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);

        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        Field field = new Field("name", "value");
        String identifier = "filename1";

        database.store(field, identifier);

        ArrayList<String> results = database.fetch(field);
        assertEquals(1, results.size());
        assertEquals(identifier, results.get(0));
    }

    @Test
    public void testMultipleFieldsOneIdentifier()
    {
        MockChunkRandomAccessFile file1 = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new BinaryTreeFieldDatabase(file1);
        MockChunkRandomAccessFile file2 = new MockChunkRandomAccessFile(16, ValueFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);

        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        String identifier = "filename1";
        Field field1 = new Field("name", "d");
        Field field2 = new Field("name", "a");
        Field field3 = new Field("name", "e");
        Field field4 = new Field("name", "b");
        Field field5 = new Field("name", "f");
        Field field6 = new Field("name", "c");
        Field field7 = new Field("name", "g");

        database.store(field1, identifier);
        database.store(field2, identifier);
        database.store(field3, identifier);
        database.store(field4, identifier);
        database.store(field5, identifier);
        database.store(field6, identifier);
        database.store(field7, identifier);

        ArrayList<String> results = database.fetch(field1);
        assertEquals(1, results.size());
        assertEquals(identifier, results.get(0));

        results = database.fetch(field2);
        assertEquals(1, results.size());
        assertEquals(identifier, results.get(0));

        results = database.fetch(field3);
        assertEquals(1, results.size());
        assertEquals(identifier, results.get(0));

        results = database.fetch(field4);
        assertEquals(1, results.size());
        assertEquals(identifier, results.get(0));

        results = database.fetch(field5);
        assertEquals(1, results.size());
        assertEquals(identifier, results.get(0));

        results = database.fetch(field6);
        assertEquals(1, results.size());
        assertEquals(identifier, results.get(0));

        results = database.fetch(field7);
        assertEquals(1, results.size());
        assertEquals(identifier, results.get(0));
    }

    @Test
    public void testSingleFieldMultipleIdentifiers()
    {
        MockChunkRandomAccessFile file1 = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new BinaryTreeFieldDatabase(file1);
        MockChunkRandomAccessFile file2 = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);

        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        Field field = new Field("name", "value");
        String identifier1 = "filename1";
        String identifier2 = "filename2";
        String identifier3 = "filename3";
        String identifier4 = "filename4";
        String identifier5 = "filename5";
        String identifier6 = "filename6";
        String identifier7 = "filename7";
        String identifier8 = "filename8";

        database.store(field, identifier1);
        database.store(field, identifier2);
        database.store(field, identifier3);
        database.store(field, identifier4);
        database.store(field, identifier5);
        database.store(field, identifier6);
        database.store(field, identifier7);
        database.store(field, identifier8);

        ArrayList<String> results = database.fetch(field);
        assertEquals(8, results.size());
        assertEquals(identifier8, results.get(0));
        assertEquals(identifier7, results.get(1));
        assertEquals(identifier6, results.get(2));
        assertEquals(identifier5, results.get(3));
        assertEquals(identifier4, results.get(4));
        assertEquals(identifier3, results.get(5));
        assertEquals(identifier2, results.get(6));
        assertEquals(identifier1, results.get(7));
    }

    @Test
    public void testRemovingIdentifierBasicCase()
    {
        MockChunkRandomAccessFile file1 = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new BinaryTreeFieldDatabase(file1);
        MockChunkRandomAccessFile file2 = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);

        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        Field field = new Field("name", "value");
        String identifier1 = "filename1";
        database.store(field, identifier1);

        ArrayList<String> results = database.fetch(field);
        assertEquals(1, results.size());
        assertEquals(identifier1, results.get(0));

        database.delete(field, identifier1);

        results = database.fetch(field);
        assertEquals(null, results);
    }

    @Test
    public void testRemovingIdentifierNotThere()
    {
        MockChunkRandomAccessFile file1 = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new BinaryTreeFieldDatabase(file1);
        MockChunkRandomAccessFile file2 = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);

        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        Field field = new Field("name", "value");
        String identifier1 = "filename1";
        String identifier2 = "filename2";
        database.store(field, identifier1);

        ArrayList<String> results = database.fetch(field);
        assertEquals(1, results.size());
        assertEquals(identifier1, results.get(0));

        database.delete(field, identifier2);

        results = database.fetch(field);
        assertEquals(1, results.size());
        assertEquals(identifier1, results.get(0));
    }

    @Test
    public void testRemovingIdentifierTwice()
    {
        MockChunkRandomAccessFile file1 = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new BinaryTreeFieldDatabase(file1);
        MockChunkRandomAccessFile file2 = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);

        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        Field field = new Field("name", "value");
        String identifier1 = "filename1";
        database.store(field, identifier1);

        ArrayList<String> results = database.fetch(field);
        assertEquals(1, results.size());
        assertEquals(identifier1, results.get(0));

        database.delete(field, identifier1);
        results = database.fetch(field);
        assertEquals(null, results);

        database.delete(field, identifier1);
        results = database.fetch(field);
        assertEquals(null, results);
    }

    @Test
    public void testRemovingFirstIdentifier()
    {
        MockChunkRandomAccessFile file1 = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new BinaryTreeFieldDatabase(file1);
        MockChunkRandomAccessFile file2 = new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);

        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        Field field = new Field("name", "value");
        String identifier1 = "filename1";
        String identifier2 = "filename2";
        database.store(field, identifier1);
        database.store(field, identifier2);

        ArrayList<String> results = database.fetch(field);
        assertEquals(2, results.size());
        assertEquals(identifier2, results.get(0));
        assertEquals(identifier1, results.get(1));

        database.delete(field, identifier2);
        results = database.fetch(field);
        assertEquals(1, results.size());
        assertEquals(identifier1, results.get(0));
    }
}
