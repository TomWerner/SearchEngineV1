package org.uiowa.cs2820.engine;

import java.util.ArrayList;

/**
 * This class combines the FieldDatabase and IdentiferDatabase to implement the functionality
 * specified in the Database interface.
 * @author Tom
 *
 */
public class IntegratedFileDatabase implements Database
{
    private FieldDatabase fieldDB;
    private IdentifierDatabase identDB;

    public IntegratedFileDatabase(FieldDatabase fieldDB, IdentifierDatabase identDB)
    {
        this.fieldDB = fieldDB;
        this.identDB = identDB;
    }
    
    @Override
    public ArrayList<String> fetch(Field field)
    {
        int position = fieldDB.getIdentifierPosition(field);
        
        // If position is -1 it didn't find the field
        if (position == -1)
            return null;
        
        return identDB.getAllIdentifiers(position);
    }

    @Override
    public void delete(Field field, String identifier)
    {
        int position = fieldDB.getIdentifierPosition(field);
        
        // If position is -1 we didn't find the field
        if (position == -1)
            return;
        
        int location = identDB.removeIdentifier(position, identifier);
        
        // if location isn't NULL_ADDRESS it means we have a new
        // position for the identifer linked list head node and need to adjust it
        // accordingly
        if (location != ValueFileNode.NULL_ADDRESS)
            fieldDB.setIdentifierPosition(field, location);
    }

    @Override
    public void store(Field field, String identifier)
    {
        // Check to see if its in the database already
        int linkedListHeadPosition = fieldDB.getIdentifierPosition(field);

        // If it isn't in the database
        if (linkedListHeadPosition == -1)
        {
            linkedListHeadPosition = identDB.addIdentifier(identifier);
            fieldDB.add(new BinaryFileNode(field, linkedListHeadPosition, 0, 0));
        }
        else
        {
            linkedListHeadPosition = identDB.addIdentifier(linkedListHeadPosition, identifier);
            fieldDB.setIdentifierPosition(field, linkedListHeadPosition);
        }
    }

}
