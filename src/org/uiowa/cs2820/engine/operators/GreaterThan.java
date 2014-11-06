package org.uiowa.cs2820.engine.operators;

import org.uiowa.cs2820.engine.Field;

public class GreaterThan implements Operator<Field>
{
    @Override
    public boolean evaluate(Field field1, Field field2)
    {
        return field1.getFieldValue().compareTo(field2.getFieldValue()) > 0;
    }
    
    public String toString()
    {
        return ">";
    }

    @Override
    public boolean matchesToken(String token)
    {
        return token.equals(">");
    }

    @Override
    public String[] allowableTokens()
    {
        return new String[]{ ">" };
    }
    
    public boolean equals(Object other)
    {
        return other instanceof GreaterThan;
    }

    
}
