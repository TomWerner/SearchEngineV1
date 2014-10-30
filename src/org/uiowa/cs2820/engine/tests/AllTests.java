package org.uiowa.cs2820.engine.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BinaryFileNodeTest.class, FieldDatabaseTest.class, FieldTest.class, IdentifierDatabaseTest.class, IntegratedDatabaseTest.class,
        IntegrationTests.class, ValueFileNodeTest.class })
public class AllTests
{

}
