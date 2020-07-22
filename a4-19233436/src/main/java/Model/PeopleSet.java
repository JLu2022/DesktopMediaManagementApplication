package Model;

import java.util.*;

/**
 * @author: Junlin Lu
 * @date: 19/04/2020
 * @version: 1.0.0
 * @description: Singleton
 * The PeopleSet class maintains and the Person data from the container, it also is in charge of adding
 * new Person instance. Singleton pattern is adopted, as it is unnecessary to create more than one instance.
 */
public class PeopleSet extends SoloIDDataSet<Person> implements Addable<Person> {
    /*Singleton*/
    private static PeopleSet peopleSet;

    private PeopleSet(Container container){
        //create a new TreeSet and assign it to the catalogue reference
        catalogue = new TreeSet<>();

        //add all the Person from the container
        catalogue.addAll(container.getPeople());
        initializeID(container);
    }

    public static synchronized PeopleSet getPeopleSet(Container container){
        if(peopleSet==null){
            peopleSet = new PeopleSet(container);
        }
        return peopleSet;
    }
    /*Singleton*/

    /**
     * Add the person to the PeopleSet. If it has not been in the Set yet, assign it a new gid.
     * And add it to the catalogue, if it has already existed in the Set, it would be removed automatically.
     * @param person The person being supposed to be added in the Set.
     */
    @Override
    public void addNewData(Person person) {
        if(!catalogue.contains(person)){//if the person does not exist in the catalogue
            lastID++;//increment ID by 1
            person.setPid(lastID);//set the new ID to the person
        }
        catalogue.add(person);//add person to the catalogue
    }//end method

    /**
     * The initializing method of the id.
     * @param container the container
     */
    @Override
    public void initializeID(Container container) {
        //the List containing the pid
        List<Integer> idList = new ArrayList<>();

        //iterates the List<Person> and add the pid of each elements of the List
        for(Person person:container.getPeople()){
            idList.add(person.getPid());
        }//end for

        //sort the List of pid by ascending order
        Collections.sort(idList);

        //get the biggest which is the lastID of the PersonSet
        lastID = idList.get(idList.size()-1);
    }
}
