package org.uiowa.cs2820.engine.utilities;

import java.util.Arrays;

import org.uiowa.cs2820.engine.BinaryFileNode;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.ValueFileNode;

public class ByteConverter
{
    public static final byte[] BINARY_FILE_NODE = { 0, 1 };
    public static final byte[] VALUE_FILE_NODE = { 1, 1 };
    public static final byte[] FIELD = { 1, 0 };
    public static final int EXISTS_POSITION = 0;
    public static final int EXISTS_SIZE = 2;
    
    
    public static Object revert(byte[] byteRepr)
    {
        byte[] objType = new byte[EXISTS_SIZE];
        System.arraycopy(byteRepr, EXISTS_POSITION, objType, 0, EXISTS_SIZE);
        
        if (Arrays.equals(objType, BINARY_FILE_NODE))
            return BinaryFileNode.revert(byteRepr);
        else if (Arrays.equals(objType, VALUE_FILE_NODE))
            return ValueFileNode.revert(byteRepr);
        else if (Arrays.equals(objType, FIELD))
            return Field.revert(byteRepr);
        else
            return null;
    }
}
