package Model;

/**
 * @author: Junlin Lu
 * @date: 19/04/2020
 * @version: 1.0.0
 * @description: DoubleIDDataSet is the super class for the concrete DataSet which has two kinds of ID.
 * It has two protected fields:
 * -Integer firstID
 * -Integer secondID
 * and their initializing method and getters.
 */
public abstract class DoubleIDDataSet<T> extends DataSet<T>{
    protected Integer firstID;
    protected Integer secondID;

    public abstract void initializeLastFID(Container container);

    public abstract void initializeLastTID(Container container);
}//end class
