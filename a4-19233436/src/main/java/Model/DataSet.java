package Model;

import Model.Model;

import java.util.TreeSet;

/**
 * @author: Junlin Lu
 * @date: 19/04/2020
 * @version: 1.0.0
 * @description: DataSet<T> is the super class of all the Data Sets of the Model.
 * It has a field 'catalogue' which is the container of all the data in each DataSet instance.
 * It has a method getCatalogue to get the reference of the TreeSet<T>
 */
public abstract class DataSet<T> {

    protected TreeSet<T> catalogue;

    public TreeSet<T> getCatalogue(){
        return catalogue;
    }
}//end class
