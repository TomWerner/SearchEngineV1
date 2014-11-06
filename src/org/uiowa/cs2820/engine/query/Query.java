package org.uiowa.cs2820.engine.query;

import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.operators.Equals;
import org.uiowa.cs2820.engine.operators.Operator;

public class Query implements Queryable
{

    private Field field;
    private Operator<Field> operator;

    public Query(Field field)
    {
        // Call the con
        this(field, new Equals());
    }

    public Query(Field field, Operator<Field> operator)
    {
        this.field = field;
        this.operator = operator;
    }

    @Override
    public boolean isSatisfiedBy(Field other)
    {
        return operator.evaluate(other, field) && field.getFieldName().equals(other.getFieldName());
    }

    public String toString()
    {
        return operator.getClass().getSimpleName() + " " + field.toString();
    }
    
    public Operator<Field> getOperator()
    {
        return operator;
    }
}
