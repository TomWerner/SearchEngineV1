package org.uiowa.cs2820.engine;

import java.nio.ByteBuffer;

import org.uiowa.cs2820.engine.utilities.ByteConvertable;
import org.uiowa.cs2820.engine.utilities.ByteConverter;

public class BinaryFileNode implements ByteConvertable
{
    public static final int MAX_SIZE = 256;
    private static final int ADDRESS_SIZE = Integer.SIZE / Byte.SIZE;;
    public static final int MAX_FIELD_SIZE = MAX_SIZE - ADDRESS_SIZE - ByteConverter.EXISTS_SIZE;
    private static final int ADDRESS_POSITION = ByteConverter.EXISTS_POSITION + ByteConverter.EXISTS_SIZE;
    private static final int FIELD_POSITION = ADDRESS_POSITION + ADDRESS_SIZE;
    
    private Field field;
    private int addrOfIdentifierStart;

    public BinaryFileNode(Field field, int addrOfIdentifierStart)
    {
        this.field = field;
        this.addrOfIdentifierStart = addrOfIdentifierStart;
    }

    public int getAddrOfIdentifierStart()
    {
        return addrOfIdentifierStart;
    }

    public void setAddrOfIdentifierStart(int addrOfIdentifierStart)
    {
        this.addrOfIdentifierStart = addrOfIdentifierStart;
    }

    public Field getField()
    { 
        return field;
    }

    public void setField(Field field)
    {
        this.field = field;
    }
    
    public boolean equals(Object other)
    { 
        if (other instanceof BinaryFileNode)
            return ((BinaryFileNode)other).field.equals(field);
        return false;
    }
    
    public String toString()
    {
        return field.toString() + " -> " + addrOfIdentifierStart;
    }

    @Override
    public byte[] convert()
    {
        byte[] result = new byte[MAX_SIZE];
        
        for (int i = 0; i < ByteConverter.EXISTS_SIZE; i++)
            result[i + ByteConverter.EXISTS_POSITION] = ByteConverter.BINARY_FILE_NODE[i];
        
        byte[] addrSection = ByteBuffer.allocate(ADDRESS_SIZE).putInt(addrOfIdentifierStart).array();
        byte[] fieldSection = field.convert();
        System.arraycopy(addrSection, 0, result, ADDRESS_POSITION, addrSection.length);
        System.arraycopy(fieldSection, 0, result, FIELD_POSITION, fieldSection.length);
        return result;
    }

    public static Object revert(byte[] byteArray)
    {
        if (byteArray.length != MAX_SIZE)
            throw new IllegalArgumentException("Byte array is not the correct size");
        
        byte[] addressSection = new byte[ADDRESS_SIZE];
        System.arraycopy(byteArray, ADDRESS_POSITION, addressSection, 0, ADDRESS_SIZE);
        ByteBuffer wrapped = ByteBuffer.wrap(addressSection);
        int address = wrapped.getInt();
        
        byte[] fieldSection = new byte[MAX_FIELD_SIZE];
        System.arraycopy(byteArray, FIELD_POSITION, fieldSection, 0, MAX_FIELD_SIZE);
        Field field = (Field) ByteConverter.revert(fieldSection);
        
        return new BinaryFileNode(field, address);
    }
    
    
}
