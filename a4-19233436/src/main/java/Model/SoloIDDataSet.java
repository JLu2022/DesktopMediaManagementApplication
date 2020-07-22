package Model;

/**
 * @author: Junlin Lu
 * @date: 19/04/2020
 * @version: 1.0.0
 * @description: SoloIDDataSet class is the super class of the concrete DataSet class which has only one ID.
 * It has one protected field:
 * -Integer lastID;
 * and its initializing method and getter
 */
public abstract class SoloIDDataSet<T> extends DataSet<T>{
    protected Integer lastID;

    public abstract void initializeID(Container container);

    public Integer getLastId() {
        return lastID;
    }
}//end class
