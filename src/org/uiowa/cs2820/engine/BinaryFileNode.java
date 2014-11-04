package org.uiowa.cs2820.engine;

import java.nio.ByteBuffer;

import org.uiowa.cs2820.engine.utilities.ByteConverter;

public class BinaryFileNode implements Comparable<BinaryFileNode>
{
    /*
     * Constants used for serialization
     */
    public static final int MAX_SIZE = 256;
    private static final int INTEGER_SIZE = Integer.SIZE / Byte.SIZE;
    
    public static final int MAX_FIELD_SIZE = MAX_SIZE - ByteConverter.EXISTS_SIZE - INTEGER_SIZE * 4;
    
    private static final int IDENT_POINTER_POS = ByteConverter.EXISTS_POSITION + ByteConverter.EXISTS_SIZE;
    private static final int LEFT_CHILD_POSITION = IDENT_POINTER_POS + INTEGER_SIZE;
    private static final int RIGHT_CHILD_POSITION = LEFT_CHILD_POSITION + INTEGER_SIZE;
    private static final int ADDRESS_POSITION = RIGHT_CHILD_POSITION + INTEGER_SIZE;
    private static final int FIELD_POSITION = ADDRESS_POSITION + INTEGER_SIZE;
    
    public static final int NULL_ADDRESS = -1;
    
    /*
     * Fields used by the node
     */
    private Field field;
    private int headOfLinkedListPosition;
    private int leftChildPosition = NULL_ADDRESS;
    private int rightChildPosition = NULL_ADDRESS;
    private int address = NULL_ADDRESS;

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
    
    public void setAddress(int newAddress)
    {
        address = newAddress;
    }
    
    public int getAddress()
    {
        return address;
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
    public byte[] convert()
    {
        byte[] result = new byte[MAX_SIZE];
        
        for (int i = 0; i < ByteConverter.EXISTS_SIZE; i++)
            result[i + ByteConverter.EXISTS_POSITION] = ByteConverter.BINARY_FILE_NODE[i];

        byte[] identPointSection = ByteBuffer.allocate(INTEGER_SIZE).putInt(headOfLinkedListPosition).array();
        byte[] leftSection = ByteBuffer.allocate(INTEGER_SIZE).putInt(leftChildPosition).array();
        byte[] rightSection = ByteBuffer.allocate(INTEGER_SIZE).putInt(rightChildPosition).array();
        byte[] addrSection = ByteBuffer.allocate(INTEGER_SIZE).putInt(address).array();
        byte[] fieldSection = field.convert();
        System.arraycopy(identPointSection, 0, result, IDENT_POINTER_POS, identPointSection.length);
        System.arraycopy(leftSection, 0, result, LEFT_CHILD_POSITION, leftSection.length);
        System.arraycopy(rightSection, 0, result, RIGHT_CHILD_POSITION, rightSection.length);
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
        
        byte[] addressSection = new byte[INTEGER_SIZE];
        System.arraycopy(byteArray, IDENT_POINTER_POS, addressSection, 0, INTEGER_SIZE);
        ByteBuffer wrapped = ByteBuffer.wrap(addressSection);
        int address = wrapped.getInt();
        
        byte[] leftSection = new byte[INTEGER_SIZE];
        System.arraycopy(byteArray, LEFT_CHILD_POSITION, leftSection, 0, INTEGER_SIZE);
        wrapped = ByteBuffer.wrap(leftSection);
        int left = wrapped.getInt();
        
        byte[] rightSection = new byte[INTEGER_SIZE];
        System.arraycopy(byteArray, RIGHT_CHILD_POSITION, rightSection, 0, INTEGER_SIZE);
        wrapped = ByteBuffer.wrap(rightSection);
        int right = wrapped.getInt();
        
        byte[] addrSection = new byte[INTEGER_SIZE];
        System.arraycopy(byteArray, ADDRESS_POSITION, addrSection, 0, INTEGER_SIZE);
        wrapped = ByteBuffer.wrap(addrSection);
        int addr = wrapped.getInt();
        
        byte[] fieldSection = new byte[MAX_FIELD_SIZE];
        System.arraycopy(byteArray, FIELD_POSITION, fieldSection, 0, MAX_FIELD_SIZE);
        Field field = (Field) ByteConverter.revert(fieldSection);
        
        BinaryFileNode result = new BinaryFileNode(field, address);
        result.setAddress(addr);
        result.setLeftPosition(left);
        result.setRightPosition(right);
        
        return result;
    }

    @Override
    public int compareTo(BinaryFileNode o)
    {
        return field.compareTo(o.field);
    }
    
    public int getLeftPosition()
    {
        return leftChildPosition;
    }
    
    public int getRightPosition()
    {
        return rightChildPosition;
    }

    public void setLeftPosition(int left)
    {
        this.leftChildPosition = left;
    }
    
    public void setRightPosition(int right)
    {
        this.rightChildPosition = right;
    }
    
}
