package org.uiowa.cs2820.engine.operators;

import org.uiowa.cs2820.engine.Field;

public class LessThanEqualTo extends LessThan
{
    @Override
    public boolean evaluate(Field field1, Field field2)
    {
        return super.evaluate(field1, field2) || field1.equals(field2);
    }
    
    public String toString()
    {
        return "<=";
    }
    
    @Override
    public boolean matchesToken(String token)
    {
        return token.equals("=<");
    }

    @Override
    public String[] allowableTokens()
    {
        return new String[]{ "=<" };
    }
    
    public boolean equals(Object other)
    {
        return other instanceof LessThanEqualTo;
    }
}
