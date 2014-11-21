package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;
import org.uiowa.cs2820.engine.*;
import org.uiowa.cs2820.engine.databases.AVLFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.IdentifierDatabase;
public class IntergratedAVLWithRAFile {
	
	//Integration with AVL tree and RA File

	@Test
	public void testSingleAddRetrieveCycle() {
		
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
        ChunkedAccess file1 = new RAFile( new File("testingFile.dat"), 16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new AVLFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, FieldFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);

        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        Field field = new Field("name", "value");
        String identifier = "FileName1";

        database.store(field, identifier);

        ArrayList<String> results = database.fetch(field);
        assertEquals(1, results.size());
        assertEquals(identifier, results.get(0));
    }
	
    @Test
    public void testMultipleFieldsOneIdentifier()
    {
    	
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
		
		
        ChunkedAccess file1 = new RAFile(new File ("testingFile.dat"), 16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new AVLFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File ("testing.dat"), 16, ValueFileNode.MAX_SIZE);
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
    public void testingFetchNullPointerCatch(){
    	
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
    	
        ChunkedAccess file1 = new RAFile(new File ("testingFile.dat"), 16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new AVLFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File ("testing.dat"), 16, ValueFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        Field field1 = new Field("Hope This works","a");
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        
         
        assertEquals(database.fetch(field1),null);
        
        
    }
    
    @Test
    public void testSingleFieldMultipleIdentifiers()
    {
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
        ChunkedAccess file1 = new RAFile(new File ("testingFile.dat"), 16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new AVLFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File ("testing.dat"), 16, ValueFileNode.MAX_SIZE);
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
    	
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
        ChunkedAccess file1 = new RAFile(new File ("testingFile.dat"), 16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new AVLFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File ("testing.dat"), 16, ValueFileNode.MAX_SIZE);
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
    	
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
        ChunkedAccess file1 = new RAFile(new File ("testingFile.dat"), 16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new AVLFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File ("testing.dat"), 16, ValueFileNode.MAX_SIZE);
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
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
        ChunkedAccess file1 = new RAFile(new File ("testingFile.dat"), 16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new AVLFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File ("testing.dat"), 16, ValueFileNode.MAX_SIZE);
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
    	
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
        ChunkedAccess file1 = new RAFile(new File ("testingFile.dat"), 16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new AVLFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File ("testing.dat"), 16, ValueFileNode.MAX_SIZE);
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
    
    @Test
    public void testRemoveFieldNullPointerCatch(){
    	
    	
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File ("testingFile.dat"), 16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new AVLFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File ("testing.dat"), 16, ValueFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        String identifier1 = "filename1";
        Field field1 = new Field("Hope This works","a");
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        
        database.delete(field1, identifier1);
        
    }
	}
