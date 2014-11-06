package org.uiowa.cs2820.engine.operators;


public class And implements Operator<Boolean>
{
    @Override
    public boolean evaluate(Boolean item1, Boolean item2)
    {
        return item1 && item2;
    }
    
    public String toString()
    {
        return "AND";
    }

    public boolean matchesToken(String token)
    {
        return token.equalsIgnoreCase("AND") || token.equals("&&");       
    }
    
    public String[] allowableTokens()
    {
        return new String[]{"AND", "and", "&&"};
    }
    
    public boolean equals(Object other)
    {
        return other instanceof And;
    }
}
