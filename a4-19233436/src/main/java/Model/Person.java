package Model;

import com.fasterxml.jackson.annotation.*;

import java.util.Objects;

/**
 * @author: Junlin Lu
 * @date: 06/04/2020
 * @version: 1.0.0
 * @description: Person class, the pid is used as ID, as in the requirement "People must also be modelled
 * as objects in the code".
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pid")
public class Person implements Comparable<Person>{
    private int pid;
    private String name;

    //default constructor
    public Person(){}

    /*Getters and setters*/
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /*End of getters and setters*/
    @Override
    public String toString() {
        return name;
    }


    /**
     * The implementation of "two people have the same name is the same person"
     * @param o Object
     * @return whether the name is equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name);
    }//end method

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }//end method

    @Override
    public int compareTo(Person o) {
        return this.getName().compareTo(o.getName());
    }
}//end class
