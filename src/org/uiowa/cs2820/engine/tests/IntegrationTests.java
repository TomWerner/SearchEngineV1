package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;
import org.uiowa.cs2820.engine.*;

public class IntegrationTests
{

    @Test
    public void testEmptyDatabaseReturnsNothing()
    {
        Database database = new IntegratedFileDatabase(new AVLFieldDatabase(new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE)), new IdentifierDatabase(new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE)));
        FieldSearch fieldSearch = new FieldSearch(database);
        Field F1 = new Field("1", new Integer(45));
        assertEquals(fieldSearch.findEquals(F1).length, 0);
    }

    @Test
    public void testSameFieldDifferentIdentifiers()
    {
        Database database = new IntegratedFileDatabase(new AVLFieldDatabase(new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE)), new IdentifierDatabase(new MockChunkRandomAccessFile(16, BinaryFileNode.MAX_SIZE)));
        FieldSearch search = new FieldSearch(database);
        Indexer indexer = new Indexer(database, "abc");
        
        Field F1 = new Field("1", new Integer(45));
        Field F2 = new Field("check", new Integer(30) );
        indexer.addField(F1);
        indexer.addField(F2); 
        
        indexer = new Indexer(database, "def");
        Field F3 = new Field("check", new Integer(30));
        indexer.addField(F3);
         
        String[] S = search.findEquals(F3);
        assertEquals(2, S.length);
        assertEquals(S[1], "abc");
        assertEquals(S[0], "def");
    }
    
    @Test
    public void testEmptyDatabaseRAFile(){
    	
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, BinaryFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new BinaryTreeFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, BinaryFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        Field field = new Field("name", "value");
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);

        indexer.addField(field);

        ArrayList<String> results = database.fetch(field);
        assertEquals(1, results.size());
        assertEquals(identifier, results.get(0));
    }
    
    @Test
    public void BinaryIntegrationTesting(){
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, BinaryFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new BinaryTreeFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, BinaryFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);
        FieldSearch search = new FieldSearch(database);

        
        Field field1 = new Field("name", "d");
        Field field2 = new Field("name", "a");
        Field field3 = new Field("name", "e");
        Field field4 = new Field("name", "b");
        Field field5 = new Field("name", "f");
        Field field6 = new Field("name", "c");
        Field field7 = new Field("name", "g");
        
        indexer.addField(field1);
        indexer.addField(field2);
        indexer.addField(field3);
        indexer.addField(field4);
        indexer.addField(field5);
        indexer.addField(field6);
        indexer.addField(field7);
        
        String[] results = search.findEquals(field1);
        
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
      
        results = search.findEquals(field2);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field3);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field4);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field5);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field6);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field7);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
    }
    
    @Test
    public void AVLIntegrationTesting(){
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, BinaryFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new AVLFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, BinaryFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);
        FieldSearch search = new FieldSearch(database);

        
        Field field1 = new Field("name", "d");
        Field field2 = new Field("name", "a");
        Field field3 = new Field("name", "e");
        Field field4 = new Field("name", "b");
        Field field5 = new Field("name", "f");
        Field field6 = new Field("name", "c");
        Field field7 = new Field("name", "g");
        
        indexer.addField(field1);
        indexer.addField(field2);
        indexer.addField(field3);
        indexer.addField(field4);
        indexer.addField(field5);
        indexer.addField(field6);
        indexer.addField(field7);
        
        String[] results = search.findEquals(field1);
        
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
      
        results = search.findEquals(field2);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field3);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field4);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field5);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field6);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field7);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
    }
    
    @Test
    public void testRemovingIdentifierBasicCaseAVL()
    {
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, BinaryFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new AVLFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, BinaryFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);
        FieldSearch search = new FieldSearch(database);
        
        Field field = new Field("name","value");
        
        indexer.addField(field);

        String[] results = search.findEquals(field);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);

        database.delete(field, identifier);

        results = search.findEquals(field);
        assertEquals(0,results.length);
    }
    
    @Test
    public void testRemovingIdentifierBasicCaseBinary()
    {
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, BinaryFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new BinaryTreeFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, BinaryFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);
        FieldSearch search = new FieldSearch(database);
        
        Field field = new Field("name","value");
        
        indexer.addField(field);

        String[] results = search.findEquals(field);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);

        database.delete(field, identifier);

        results = search.findEquals(field);
        assertEquals(0,results.length);
    }


    
    
    
}
