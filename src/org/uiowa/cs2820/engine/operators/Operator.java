package org.uiowa.cs2820.engine.operators;


public interface Operator<T>
{
    public boolean evaluate(T item1, T item2);
    public boolean matchesToken(String token);
    public String[] allowableTokens();
}