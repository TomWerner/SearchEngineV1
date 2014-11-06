package org.uiowa.cs2820.engine.query;

import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.operators.Operator;
import org.uiowa.cs2820.engine.operators.Or;

public class DoubleQuery implements Queryable
{
    private Queryable query1;
    private Queryable query2;
    private Operator<Boolean> operator;

    public DoubleQuery(Queryable query1, Queryable query2)
    {
        this.query1 = query1;
        this.query2 = query2;
        this.operator = new Or();
    }

    public DoubleQuery(Queryable query1, Queryable query2, Operator<Boolean> operator)
    {
        this.query1 = query1;
        this.query2 = query2;
        this.operator = operator;
    }

    @Override
    public boolean isSatisfiedBy(Field field)
    {
        return operator.evaluate(query1.isSatisfiedBy(field), query2.isSatisfiedBy(field));
    }

    public String toString()
    {
        return "[ (" + query1 + ") " + operator.getClass().getSimpleName() + " (" + query2 + ") ]";
    }

    public boolean equals(Object other)
    {
        if (other instanceof DoubleQuery)
        {
            return ((DoubleQuery) other).query1.equals(query1) && ((DoubleQuery) other).query2.equals(query2)
                    && ((DoubleQuery) other).operator.equals(operator);
        }
        return false;
    }
}
