package org.uiowa.cs2820.engine.tests;

import org.uiowa.cs2820.engine.BinaryTreeFieldDatabase;
import org.uiowa.cs2820.engine.ChunkedAccess;
import org.uiowa.cs2820.engine.FieldDatabase;


public class BinaryTreeFieldDatabaseTest extends FieldDatabaseTest
{

    @Override
    public FieldDatabase getDatabase(ChunkedAccess file)
    {
        return new BinaryTreeFieldDatabase(file);
    }
}
