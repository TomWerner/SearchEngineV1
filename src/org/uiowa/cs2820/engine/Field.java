package org.uiowa.cs2820.engine;

import java.io.Serializable;

import org.uiowa.cs2820.engine.utilities.Utilities;

@SuppressWarnings( {"serial", "rawtypes" })
public class Field implements Serializable, Comparable<Field>
{
    // Its 256 - integer size so that the integer can point to the first
    // node in the file of identifiers and still be a power of two
    public static final int MAXSIZE = 256 - Integer.SIZE;
    private String fieldName;
    private Comparable value;

    // constructor for Fields with String
    public Field(String fieldName, Comparable value) throws IllegalArgumentException
    { 
        this.fieldName = fieldName;
        this.value = value;
        if (toBytes().length >= Field.MAXSIZE)
            throw new IllegalArgumentException("Field Size exceeded: " + fieldName);
        return;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public Comparable getFieldValue()
    {
        return value;
    }

    public byte[] toBytes()
    {
        byte[] wholeField = Utilities.convert(this);
        return wholeField;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(Field o)
    {
        if (fieldName.equals(o.fieldName))
        {
            return ((Comparable)value).compareTo(o.value);
        }
        return fieldName.compareTo(o.fieldName);
    }
    
    public boolean equals(Object other)
    {
        if (other instanceof Field)
            return ((Field)other).toString().equals(toString());
        return false;
    }
    
    public String toString()
    {
        return "(" + fieldName + " : " + value + ")";
    }
}
