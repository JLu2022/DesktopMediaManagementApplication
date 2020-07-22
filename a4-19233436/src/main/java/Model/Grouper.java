package Model;

import java.util.TreeMap;

/**
 * @author: Junlin Lu
 * @date: 07/04/2020
 * @version: 1.0.0
 * @description: Strategy Pattern. The Grouper interface has two methods:
 * 1.TreeMap sort(Set<? extends Comparable> set);
 *  This is a method that can sort the passed in Set instance and return a TreeMap whose Key depends on the specific kind
 *  of Grouper the reference refers.
 *
 * 2. TreeMap<Comparable ,TreeMap<String,String>> getSortedMedia(Set<? extends Comparable> set);
 *  This is the method that reassemble the Set param into a new data structure TreeMap<Comparable ,TreeMap<String,String>>,
 *  which can be used directly by the View part of this program.
 *
 *  The reason why this interface needs both methods is that, the first method makes more sense to store the data of Media,
 *  while the second method can be directly used to initialize the related Classes in View package.
 */
public interface Grouper {
    //make more sense for store the data
    TreeMap sort(DataSet...dataSets);

    //used directly to initialize the related part in View package
    TreeMap<Comparable ,TreeMap<String,String>> getSortedMedia(DataSet...dataSets);
}
