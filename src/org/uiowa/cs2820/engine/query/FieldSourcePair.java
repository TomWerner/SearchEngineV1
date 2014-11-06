package org.uiowa.cs2820.engine.query;

import org.uiowa.cs2820.engine.Field;

public class FieldSourcePair implements Comparable<FieldSourcePair>
{
    private Field field;
    private String source;

    public FieldSourcePair(Field field, String source)
    {
        super();
        this.field = field;
        this.source = source;
    }

    public Field getField()
    {
        return field;
    }

    public String getSource()
    {
        return source;
    }
    
    public String toString()
    {
        return field + " found in " + source;
    }
    
    public boolean equals(Object other)
    {
        if (other instanceof FieldSourcePair)
            return field.equals(((FieldSourcePair) other).field) && source.equals(((FieldSourcePair) other).source);
        return false;
    }

    @Override
    public int compareTo(FieldSourcePair other)
    {
        return (field + source).compareTo(other.field + other.source);
    }
}
