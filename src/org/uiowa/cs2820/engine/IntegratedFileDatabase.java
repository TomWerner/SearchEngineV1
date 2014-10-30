package org.uiowa.cs2820.engine;

import java.util.ArrayList;

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
        if (position == -1)
            return null;
        return identDB.getAllIdentifiers(position);
    }

    @Override
    public void delete(Field field, String identifier)
    {
        int position = fieldDB.getIdentifierPosition(field);
        if (position == -1)
            return;
        int location = identDB.removeIdentifier(position, identifier);
        if (location != ValueFileNode.NULL_ADDRESS)
            fieldDB.setIdentifierPosition(field, location);
    }

    @Override
    public void store(Field field, String identifier)
    {
        // Check to see if its in the database already
        int identPos = fieldDB.getIdentifierPosition(field);

        // If it isn't in the database
        if (identPos == -1)
        {
            identPos = identDB.addIdentifier(identifier);
            fieldDB.add(new BinaryFileNode(field, identPos));
        }
        else
        {
            identPos = identDB.addIdentifier(identPos, identifier);
            fieldDB.setIdentifierPosition(field, identPos);
        }
    }

}
