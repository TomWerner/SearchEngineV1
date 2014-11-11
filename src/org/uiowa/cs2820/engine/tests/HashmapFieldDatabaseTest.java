package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.uiowa.cs2820.engine.ChunkedAccess;
import org.uiowa.cs2820.engine.FieldDatabase;
import org.uiowa.cs2820.engine.HashmapFieldDatabase;

public class HashmapFieldDatabaseTest extends FieldDatabaseTest {

	@Override
	public FieldDatabase getDatabase(ChunkedAccess file) {
		return new HashmapFieldDatabase(file);
	}

}