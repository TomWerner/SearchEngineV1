package org.uiowa.cs2820.engine.databases.tests;

import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.HashmapFieldDatabase;
import org.uiowa.cs2820.engine.fileoperations.ChunkedAccess;

public class HashmapFieldDatabaseTest extends FieldDatabaseTest
{

    @Override
    public FieldDatabase getDatabase(ChunkedAccess file)
    {
        return new HashmapFieldDatabase(file);
    }

}
