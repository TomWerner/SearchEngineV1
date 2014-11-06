package org.uiowa.cs2820.engine.operators;

import org.uiowa.cs2820.engine.Field;

public class Not implements Operator<Field>
{
    @Override
    public boolean evaluate(Field field1, Field field2)
    {
        return !field1.getFieldValue().equals(field2.getFieldValue());
    }
    
    public String toString()
    {
        return "NOT";
    }

    @Override
    public boolean matchesToken(String token)
    {
        return token.equalsIgnoreCase("NOT") || token.equals("!=");
    }

    @Override
    public String[] allowableTokens()
    {
        return new String[]{ "NOT", "not", "!=" };
    }
    
    public boolean equals(Object other)
    {
        return other instanceof Not;
    }
    
    
    
}
