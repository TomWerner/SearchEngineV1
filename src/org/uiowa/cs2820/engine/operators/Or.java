package org.uiowa.cs2820.engine.operators;

public class Or implements Operator<Boolean>
{

    @Override
    public boolean evaluate(Boolean item1, Boolean item2)
    {
        return item1 || item2;
    }
    
    public String toString()
    {
        return "OR";
    }

    @Override
    public boolean matchesToken(String token)
    {
        return token.equalsIgnoreCase("OR") || token.equals("||");
    }

    @Override
    public String[] allowableTokens()
    {
        return new String[]{ "OR", "or", "||" };
    }
    
    public boolean equals(Object other)
    {
        return other instanceof Or;
    }

    
}
