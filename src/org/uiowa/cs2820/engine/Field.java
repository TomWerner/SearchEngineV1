package org.uiowa.cs2820.engine;

import java.io.Serializable;

import org.uiowa.cs2820.engine.utilities.Utilities;

@SuppressWarnings("serial")
public class Field implements Serializable
{

    public static final int MAXSIZE = 256;
    private String FieldName;
    private Object FieldValue;

    // constructor for Fields with String
    public Field(String FieldName, Object Value) throws IllegalArgumentException
    {
        this.FieldName = FieldName;
        this.FieldValue = Value;
        if (toBytes().length >= Field.MAXSIZE)
            throw new IllegalArgumentException("Field Size exceeded: " + FieldName);
        return;
    }

    public String getFieldName()
    {
        return FieldName;
    }

    public Object getFieldValue()
    {
        return FieldValue;
    }

    public byte[] toBytes()
    {
        byte[] wholeField = Utilities.convert(this);
        return wholeField;
    }
}
