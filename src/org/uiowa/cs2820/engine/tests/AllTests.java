package org.uiowa.cs2820.engine.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AVLFieldDatabaseTest.class, BinaryFileNodeTest.class, BinaryTreeFieldDatabaseTest.class, BinaryTreeIteratorTest.class,
        FieldTest.class, HashmapFieldDatabaseTest.class, IdentifierDatabaseTest.class, IntegratedDatabaseWithAVLTest.class,
        IntegratedDatabaseWithBinaryTreeTest.class, IntegrationTests.class, IntergratedAVLWithRAFile.class, RAFileTest.class,
        ValueFileNodeTest.class })
public class AllTests
{

}
