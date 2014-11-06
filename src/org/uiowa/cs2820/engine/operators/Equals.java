package org.uiowa.cs2820.engine.operators;

import org.uiowa.cs2820.engine.Field;


public class Equals implements Operator<Field>
{
    public boolean evaluate(Field field1, Field field2)
    {
        return field1.equals(field2);
    }

    public String toString()
    {
        return "==";
    }

    @Override
    public boolean matchesToken(String token)
    {
        return token.equals("==");
    }

    @Override
    public String[] allowableTokens()
    {
        return new String[]{ "==" };
    }
    
    public boolean equals(Object other)
    {
        return other instanceof Equals;
    }
    
    
    
    
}