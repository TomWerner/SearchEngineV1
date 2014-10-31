package org.uiowa.cs2820.engine;

import java.nio.ByteBuffer;

import org.uiowa.cs2820.engine.utilities.ByteConvertable;
import org.uiowa.cs2820.engine.utilities.ByteConverter;

public class BinaryFileNode implements ByteConvertable
{
    /*
     * Constants used for serialization
     */
    public static final int MAX_SIZE = 256;
    private static final int ADDRESS_SIZE = Integer.SIZE / Byte.SIZE;;
    public static final int MAX_FIELD_SIZE = MAX_SIZE - ADDRESS_SIZE - ByteConverter.EXISTS_SIZE;
    private static final int ADDRESS_POSITION = ByteConverter.EXISTS_POSITION + ByteConverter.EXISTS_SIZE;
    private static final int FIELD_POSITION = ADDRESS_POSITION + ADDRESS_SIZE;
    
    /*
     * Fields used by the node
     */
    private Field field;
    private int headOfLinkedListPosition;

    /**
     * Construct a new BinaryFileNode that contains a field and a pointer to the start of
     * and identifier linked list.
     * @param field The field of the node
     * @param headOfLinkedListPosition the position of the head node of the identifier linked list in the IdentifierDatabase
     */
    public BinaryFileNode(Field field, int headOfLinkedListPosition)
    {
        this.field = field;
        this.headOfLinkedListPosition = headOfLinkedListPosition;
    }

    /**
     * 
     * @return the position of the start of the identifier linked list
     */
    public int getHeadOfLinkedListPosition()
    {
        return headOfLinkedListPosition;
    }

    /**
     * Set the location of the linked list head node
     * NOTE: You need to save the node for it persist.
     * @param headOfLinkedListPosition the new location of the head node
     */
    public void setHeadOfLinkedListPosition(int headOfLinkedListPosition)
    {
        this.headOfLinkedListPosition = headOfLinkedListPosition;
    }

    public Field getField()
    { 
        return field;
    }
    
    public boolean equals(Object other)
    { 
        if (other instanceof BinaryFileNode)
            return ((BinaryFileNode)other).field.equals(field);
        return false;
    }
    
    public String toString()
    {
        return field.toString() + " -> " + headOfLinkedListPosition;
    }

    /**
     * Convert this object into a byte array
     */
    @Override
    public byte[] convert()
    {
        byte[] result = new byte[MAX_SIZE];
        
        for (int i = 0; i < ByteConverter.EXISTS_SIZE; i++)
            result[i + ByteConverter.EXISTS_POSITION] = ByteConverter.BINARY_FILE_NODE[i];
        
        byte[] addrSection = ByteBuffer.allocate(ADDRESS_SIZE).putInt(headOfLinkedListPosition).array();
        byte[] fieldSection = field.convert();
        System.arraycopy(addrSection, 0, result, ADDRESS_POSITION, addrSection.length);
        System.arraycopy(fieldSection, 0, result, FIELD_POSITION, fieldSection.length);
        return result;
    }

    /**
     * Create a new object from a given byte array
     * @param byteArray the byte array the object is in
     * @return the new object, a BinaryFileNode
     */
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
