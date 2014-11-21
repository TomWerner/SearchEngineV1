package org.uiowa.cs2820.engine.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.uiowa.cs2820.engine.databases.tests.AVLFieldDatabaseTest;
import org.uiowa.cs2820.engine.databases.tests.BinaryTreeFieldDatabaseTest;
import org.uiowa.cs2820.engine.databases.tests.HashmapFieldDatabaseTest;
import org.uiowa.cs2820.engine.databases.tests.IdentifierDatabaseTest;

@RunWith(Suite.class)
@SuiteClasses({ AVLFieldDatabaseTest.class, FieldFileNodeTest.class, BinaryTreeFieldDatabaseTest.class, BinaryTreeIteratorTest.class, FieldTest.class,
        HashmapFieldDatabaseTest.class, IdentifierDatabaseTest.class, IntegratedFileDatabaseTests.class, IntegrationTests.class, RAFileTest.class,
        ValueFileNodeTest.class, AVLIteratorTest.class, IntegratedFileDatabaseTestsWithRAFile.class })
public class AllTests
{

}
