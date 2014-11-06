package org.uiowa.cs2820.engine.query;

import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.operators.Operator;
import org.uiowa.cs2820.engine.operators.Or;

public class MultiQuery implements Queryable
{
    private Queryable[] queries;
    private Operator<Boolean> operator;

    public MultiQuery(Queryable... args)
    {
        this(new Or(), args);
    }

    public MultiQuery(Operator<Boolean> operator, Queryable... args)
    {
        if (args == null || args.length == 0)
            args = new Query[0];
        queries = args;
        this.operator = operator;
    }

    @Override
    public boolean isSatisfiedBy(Field other)
    {
        if (queries.length == 0)
            return false;
        
        boolean passedSoFar = queries[0].isSatisfiedBy(other);
        for (int i = 1; i < queries.length; i++)
            if (!queries[i].isSatisfiedBy(other))
                passedSoFar = operator.evaluate(passedSoFar, false);
        return passedSoFar;
    }

}
