package Model;

import Model.Model;

/**
 * @author: Junlin Lu
 * @date: 19/04/2020
 * @version: 1.0.0
 * @description: This interface in this case, is used mainly for the DataSet sub-classes, as some of the classes
 * like ModelSet, GenreSet, PeopleSet and YearSet are asked to add some new instance some time, while the ProfileSet
 * is not 'addable' according to the current requirements. So this interface is designed for those DataSet which
 * is supposed to be able to add new element.
 */
public interface Addable<T> {

    void addNewData(T t);
}
