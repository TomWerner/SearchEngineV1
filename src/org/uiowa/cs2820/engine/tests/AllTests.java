package org.uiowa.cs2820.engine.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AVLFieldDatabaseTest.class, BinaryFileNodeTest.class, BinaryTreeFieldDatabaseTest.class, FieldTest.class, IdentifierDatabaseTest.class,
        IntegratedDatabaseWithBinaryTreeTest.class, IntegratedDatabaseWithAVLTest.class, IntegrationTests.class, RAFileTest.class, ValueFileNodeTest.class })
public class AllTests
{

}
