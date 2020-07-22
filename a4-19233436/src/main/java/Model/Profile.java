package Model;

/**
 * @author: Junlin Lu
 * @date: 07/04/2020
 * @version: 1.0.0
 * @description: The Profile class is the Users in the program.
 */
public class Profile implements Comparable<Profile>{
    private String name;
    private Genre preferredGenre;

    //default constructor
    public Profile(){}

    /*Getters and setters*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getPreferredGenre() {
        return preferredGenre;
    }

    public void setPreferredGenre(Genre preferredGenre) {
        this.preferredGenre = preferredGenre;
    }
    /*End getters and setters*/
    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", preferredGenre=" + preferredGenre +
                '}';
    }

    @Override
    public int compareTo(Profile o) {
        return this.getName().compareTo(o.getName());
    }
}//end class
