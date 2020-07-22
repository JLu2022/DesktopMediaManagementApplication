# 1.Reason behind Design Choices
(**To satisfy requirement 1: "The system must be designed and implemented using model-view-controller (MVC): user interface code shall never directly access the data"**) The whole application is designed based on MVC pattern, making it more flexible and maintainable. There are three packages Model, View, Controller (actually, there is another package Runtime). The Model package represents the underlying data structure and business logic.
**And to satisfy requirement 7:The initial data in the catalogue must be loaded from the JSON file provided with the assignment in Blackboard. The JSON will contain initialization data about users, catalogue items, people, and genres.** 
So the Jackson is used to read the data from JSON file:
```
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.9.10</version>
    </dependency>
```
In this case, it reads the data from the data.json file. The View package contains the classes of presentation, it focuses on the display and layout of the data provided by the Model and Controller. And the Controller package contains one class controller which is responsible for dealing directly with the end user requests and requesting data from the Model.


**The Model Package**


The Model package contains classes of business logic and readers of data.
The design patterns adopted in this package are factory pattern, singleton pattern,and strategy pattern.
The classes in Model package are divided into 8 parts.
1. Container
    
    This is the class temperarily storing the data read from the JSON file.It has several fields against each part of the object stored in the JSON file.
    **To satisfy requirement 6:It is enough that created catalogue items and user profiles remain in memory: it is not required to store the data to a database or file.** The Container class instance is then passed to the DataSet classes(mentioned later) to store the data in memory.
    **And to satisfy requirement 7:The initial data in the catalogue must be loaded from the JSON file provided with the assignment in Blackboard. The JSON will contain initialization data about users, catalogue items, people, and genres.** This is the class which loaded the JSON file and store the data firstly.
    **To increase the read-in speed of the JSON file, the HashSet rather than the TreeSet is used in the Container class:**  
```sh
    HashSet<Person> people = new HashSet<>();
    HashSet<Film> films = new HashSet<>();
    HashSet<Genre> genres = new HashSet<>();
    HashSet<TVSeries> tvseries = new HashSet<>();
```
2. Normal Classes

2.1 Media: 

The abstract super class of Film and TVSeries,implementing the Comparable<Media> interface.Two Media instances can be compared by their title alphabetically, and if two Media instances have the same title they are regarded equal. (**To (prepare for) satisfy requirement 2: "In each view, the items must be sorted first by the selected dimension (genre if in the “List by genre” view etc.), then by title."**) 
     
        @Override
        public int compareTo(Media o) {
            return this.getTitle().compareTo(o.getTitle());
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Media media = (Media) o;
            return Objects.equals(title, media.title);
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(title);
        }

2.1.1 Film

The sub-class of Media.

2.1.2 TVSeries

The sub-class of Media.
     
2.2 Genre

Genre class, implementing Comparable<Genre>, uses gid as ID. And two 'Genre' instances are regarded as equal if their 'genre's (name) are the same, they can be compared alphebatically by their genre (name) as well.
        
        @Override
        public int compareTo(Genre o) {
            return this.getGenre().compareTo(o.getGenre());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Genre genre1 = (Genre) o;
            return genre.equals(genre1.genre);
        }

        @Override
        public int hashCode() {
            return Objects.hash(genre);
        }
        
2.3 Person
(**To satisfy requirement 3: "People (e.g. directors, cast members) must also be modelled as objects in the code. For simplicity, equality is by name alone: two people with the same name are the same person."**)  
Person class, uses pid as ID, two Person instances are regard equal if their names are the same and they can be compared with their names alphabetically.

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
    
2.4 Profile
The Profile class is the Users in the program. Different Profile instances can compare with each other with their names alphabetically.
   
    @Override
    public int compareTo(Profile o) {
        return this.getName().compareTo(o.getName());
    }
    
3. Grouper classes

(**To satisfy requirement 2: "In each view, the items must be sorted first by the selected dimension (genre if in the “List by genre” view etc.), then by title."**
**And to satisfy requirement 5: Each view uses different criteria for sorting and grouping media items: implement a Strategy pattern to assign them at runtime.**) These are the classes including interface which realize the 'sorted first by the selected dimension' part, the strategy pattern is adopted. Sorting by title is implemented automatically when store the data in each 'DataSet' instance, as it is a basic function realized by a TreeSet data structure.
The reason why I designed it like this is that to extract the grouping by year,and grouping by genre out of the Model class, adopting strategy pattern, Grouper interface is created. And each of the Strategy classes which has the implementation of the method in Grouper interface also adopts singleton pattern, as it is not necessary to instantiate more than one object holding the groupin algorithm.
The design makes the program confirm 'open/closed principle' when more grouping algorithms are introduced in this application. 

3.1 The Grouper interface 

It has two methods:
1)TreeMap sort(Set<? extends Comparable> set);
This is a method sorts the passed in Set instance and return a TreeMap whose Key depends on the specific kind of Grouper the reference refers.
2)TreeMap<Comparable ,TreeMap<String,String>> getSortedMedia(Set<? extends Comparable> set);
This is the method reassembling the Set param into a new data structure TreeMap<Comparable ,TreeMap<String,String>>,which can be used directly by the View part of this program.The reason why this interface needs both methods is , the first method makes more sense to return the data of Media,while the second method can be directly used to initialize the related Classes in View package.

3.2 GroupByYear class

Strategy pattern and singleton pattern
GrouperByYear class implementing the Grouper interface, provides an implementation of the methods in the interface. The two methods here can sort the passed in Set and reassemble to TreeMap, and can be used in different cases. The second method returns data which can be directly used to initialize the related Classes in View package.

3.3 GrouperByGenre class

Strategy pattern and singleton pattern
GrouperByGenre class which implements the Grouper interface, provides an implementation of the methods in the interface. The two methods here can sort the passed in Set and reassemble to TreeMap, and can be used in different cases. The second method returns data which can be directly used to initialize the related Classes in View package.

4. InfoExtractor classes

(**To satisfy requirement 1: "The system must be designed and implemented using model-view-controller (MVC): user interface code shall never directly access the data"**) These classes including interfaces deal with the data in the Model and return to the View.
As the mainScreen, ListByGenre and ListByYear user interfaces and the function where pressing the title of the Media instance can return detailed information of the Media all need some infomation from Model in some specific format to display in the View, the Model's return data should be inline with the format the View needed. However, the data stored in mediaSet class, genreSet class are TreeSet<T> and cannot be directly used by the View. So I created the information extracting classes following the strategy pattern and singleton pattern, with the interface 'MediaInfoExtractor' to return a List of String and concrete classes 'MediaInfoExtractorByProfile' and 'MediaInfoExtractorByTitle' implementing this interface. The strategy pattern is used here to achieve 'open-close principle' for further extension of  different formats (the order of returned String) of returning information. And singleton pattern is adopted in each concrete class because to save resource, one instance of the Extractor is enough. 

4.1 MediaInfoExtractor interface

As the View part always needs to extract some String data based on Profile or Title of the Media, the function of extracting the related info of the Media Library is implemented following a strategy pattern, to make the code more clean, and maintainable.
In this interface, the method 'List<String> getMediaInfo(String token, DataSet...dataSets);' takes in a String token, and a DataSet array. The String token is the input from the View side which can be the Title of the Media the user pointed, or the Profile's name loaded in the mainScreen and the DataSet array is a set of DataSet instances like mediaSet, yearSet, profileSet etc. It provides the data where to compare the token with the data from DataSet instances and get the related Media.

4.2 MediaExtractorByProfile class

This class 'MediaInfoExtractorByProfile' implementing 'MediaInfoExtractor' and the overridden method "getMediaInfo" can return the data structure of media info which can be directly used in View-mainScreen. 
In this implementation of the interface, The String token is the name of 'Profile' which is loaded in the mainScreen of the View part. And the 'DataSet' instances are the 'MediaSet' and the 'ProfileSet'.
Based on the String token, the system iterates the 'ProfileSet',compares with the token, finds the specific 'Profile' instance and gets his/her preferrence. Then gets the related 'Media' instances from the 'MediaSet' extracts the information, assembles the return String List and returns. Singleton pattern is adopted in this class, as only one instance is enough.

4.3 MediaExtractorByTitle class
    
This class MediaInfoExtractorByTitle implementing MediaInfoExtractor and the overridden method "getMediaInfo"can return the data structure of media info which can be directly used in View-mainScreen.
In this implementation of the interface, The String token is the title of the 'Media' which is loaded in the mainScreen, the ListByYear, the ListByGenre UIs of the View part. And the 'DataSet' instances is the 'MediaSet'.
Based on the String token, the system iterates the 'MediaSet',compares with the token, finds the specific 'Media' instance with the same title (acturally means the same Media because equals() and hashcode() methods have been overridden), extracts the information, assembles the return String List and returns.Singleton pattern is adopted in this class, as only one instance is enough.

5. Factory classes

(**To satisfy requirement 4: "The generation of new objects for films and TV series must be implemented using a factory pattern (Abstract Factory or Factory Method)."**) In this application, for creating Media instances, Abstract Factory is adopted. Also for each concrete factory, singleton pattern is adopted as well.

5.1 MediaFactory abstract class

Using generic in Java to make sure all the subclass of this parent factory class can create the subclass instance of Media.
     
     public abstract T createMedia(int id,String title, String description, int year, List<Genre> genre, Person person, List<Person> cast);
     
5.2 FilmFactory class

This is the FilmFactory class extending MediaFactory<Film> following factory pattern. Also, for FilmFactory, singleton pattern is taken as there is no need to create more than one FilmFactory instances.

5.3 TVSeriesFactory class

This is the TVSeriesFactory class extending MediaFactory<Film> following factory pattern.Also, for TVSeriesFactory, singleton pattern is taken as there is no need to create more than one TVSeriesFactory instance.

6. DataSet classes

(**To satisfy requirement 2: "In each view, the items must be sorted first by the selected dimension (genre if in the “List by genre” view etc.), then by title."** **And requirement 6:It is enough that created catalogue items and user profiles remain in memory: it is not required to store the data to a database or file.**) These classes are designed to solve the 'then sorted by title' part of the requirement 2. Singleton pattern is used in each concrete sub-classes for only one instance of some specific DataSet is needed for each container. In other words, each container can only have one instance for each kind of DataSet. For requirement 6, this set of classes store the data in memory rather than DB.

6.1 DataSet abstract class

DataSet<T> is the super class of all the DataSet sub-classes of the Model. It has a field 'catalogue' which is the container of all the data in each DataSet instance. It has a method getCatalogue to get the reference of the TreeSet<T>.

6.1.1 DoubleIDDataSet abstract class

DoubleIDDataSet is the super class for the concrete DataSet which has two kinds of ID. Thus it has two protected fields:
Integer firstID
Integer secondID
and their initializing method and getters.

6.1.1.1 MediaSet 

The MediaSet class maintains the Media data from the container, it also is in charge of adding new Media instance. Singleton pattern is adopted, as it is unnecessary to create more than one instance.

6.2 SoloIDDataSet abstract class

SoloIDDataSet class is the super class of the concrete DataSet class which has only one ID. It has one protected field:
-Integer lastID;
and its initializing method and getter

6.2.1 GenreSet
            
The GenreSet class maintains the Genre data from the container, it also is in charge of adding new Genre instance. Singleton pattern is adopted.

6.2.2 PeopleSet

The PeopleSet class maintains the Person data from the container, it also is in charge of adding new Person instance. Singleton pattern is adopted.

6.3 NoneIDDataSet abstract class

This is the sub-class of DataSet<T> where the object does not have any ID.

6.3.1 ProfileSet

The Profile Set, when being constructed with the specific container, the Profile instances are ordered by their name alphabetically in the TreeSet.

6.3.2 YearSet

The Year Set.
    
    
7. Model class

(**To satisfy requirement 1: "The system must be designed and implemented using model-view-controller (MVC): user interface code shall never directly access the data"**)
This is the Model class. It is in charge of reading in the JSON file into the Container, initializing the data i.e. mediaCatalogue, lastFID, lastTID, lastGID, genreSet and yearSet of the existed Media, instances of Factory, and the reference of the strategy class. This class provides methods in the interaction with the Controller and View. Also the singleton pattern is used, as only one Model instance is enough. After the instance of Model class is created, the application can call this method:

    public void init(String fileName) throws IOException {
        //create an object for the ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        //read in the data in JSON file and store it in container
        container =objectMapper.readValue(new File(fileName),Container.class);
        mediaSet = MediaSet.getMediaSet(container);
        yearSet = YearSet.getYearSet(container);
        genreSet = GenreSet.getGenreSet(container);
        peopleSet = PeopleSet.getPeopleSet(container);
        profileSet = ProfileSet.getProfileSet(container);
    }
    
to read the data from a JSON file. This satisfies the requirement 7 where 
**The initial data in the catalogue must be loaded from the JSON file provided with the assignment in Blackboard. The JSON will contain initialization data about users, catalogue items, people, and genres.**

8. Addable interface
    
This interface in this case, is used mainly for the DataSet sub-classes, as some of the classes like ModelSet, GenreSet, PeopleSet and YearSet are asked to add some new instance some time, while the ProfileSet is not 'addable' according to the current requirements. So this interface is designed for those DataSet which is supposed to be able to add new element.


**The Controller Package**


(**To satisfy requirement 1: "The system must be designed and implemented using model-view-controller (MVC): user interface code shall never directly access the data"**) There should obviously be a Controller package. 
In this package, there is only one class 'Controller'. It is responsible for dealing directly with end users requests and requests data from the model. It is simply the coordinator in the communication between the View and Model,  transmiting the data from the Model to the View for displaying and initializing the buttons in each UI. 
I tried my best to make the Controller thin, and made sure it only takes charge of initialization the buttons and transmitting data, without any background data manipulation code block. 
It has the constructor taking Model and View instances to be constructed.



    public Controller(Model model, View view){
        this.view = view;
        this.model = model;
        updateMainScreen();
    }

It also has two methods(overloading):

    //By default, the first window displayed is the MainScreen with the first element in the ProfileSet of Model
    public void updateMainScreen(){
        updateMainScreen(model.getProfile().get(0));
    }
    /**
     * When choosing another userProfile this initializing method is called
     * It firstly gets the info of the Media by the Profile.
     * If there was a MainScreen, dispose it, then create a new MainScreen and  call initializeMainScreenController()
     * @param profile the userProfile whose information should be displayed on the MainScreen
     */
    public void updateMainScreen(String profile){
        List<String> infoList = model.getMediaInfoByProfile(profile);
        if(view.getMainScreen()!=null)
            view.getMainScreen().getMainScreenFrame().dispose();
        view.createMainScreen(profile,infoList);
        initializeMainScreenController();
    }
    
which are called to update the mainScreen after adding some new Item to the MediaSet or just select another user profile.


**The View Package**


(**To satisfy requirement 1: "The system must be designed and implemented using model-view-controller (MVC): user interface code shall never directly access the data"**)
This is the View Package in charge of displaying the data. 

1. View class

It is in charge of the creation of each kind of JFrame and their getters,and communicates with Model directly through the Controller,holding the following fields currently:

    private MainScreen mainScreen;
    private SelectUser selectUser;
    private ListMediaDisplay listMediaDisplay;
    private ItemView itemView;

Also there are the getters and creating methods of those classes.

2. MainScreen class

The class extends JFrame with a constructor:

    public MainScreen(String profile,List<String> info){...}

The String profile is the profile name showed in the UI, and List<String> info is the info List gotten from the Model according to the profile. 

3. SelectUser class

The class extends JFrame with a constructor:

    public SelectUser(List<String> userNames){...}

This List<String> userNames is the names of the SelectUser UI. The names are assigned to the JLabels in this class one by one.

4. ItemView class

Realize the UI of the “Add Item” screen can be reused for “Item details” screen with disabled fields.
It has a constructor:

    public ItemView(Boolean addNew, List<String> strings){...}

The Boolean addNew means addNew UI when it is true, and show detailed information UI if it is false. The List<String> strings is the info used when the addNew is false.
Also, it has the exception handler:


    public void handleInputException(String formatInfo){
            //create a JOptionPane to show the exception
            JOptionPane.showMessageDialog(null,formatInfo,"Incorrect format",JOptionPane.INFORMATION_MESSAGE);
        }

If the 'year' the user enters is not pure numbers or any of the fields is left empty, the addItem method in Model will throw out an Exception, and it is handled in this method, to create a JOptionPane, informing the user to input the right format of data.

5. ListMediaDisplay class

ListMediaDisplay class is a class used by both 'list by Year' and 'list by Genre' function.
It has the constructor:

    public ListMediaDisplay(Integer type,TreeMap<Comparable, TreeMap<String,String>> infoMap){...}

It has several final int which represent the name of different grouping strategy. The TreeMap<Comparable, TreeMap<String,String>> infoMap is used to fill in the corresponding part of the JFrame.

**The Runtime Package**

Just the simple Application class where the main method is located.

# 2.Description of Test Scenarios

**2.1 Unit Tests for Model package**
In some cases, I used this test.json to do my unit tests:
        
        {"films" : [
          {
          "fid" : 1,
          "title" : "The Platform",
          "year": 2019,
          "genre": [1],
          "director": 1,
          "cast": [1] ,
          "description": "A vertical prison with one cell per level. Two people per cell. One only food platform and two minutes per day to feed from up to down. An endless nightmare trapped in The Hole."
          }
        ],
          "tvseries" : [
            {
              "tid" : 1,
              "title" : "Breaking Bad",
              "year": 2008,
              "genre": [2],
              "creator": 1,
              "cast": [1] ,
              "description": " A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine in order to secure his family's future."
            }
          ],
          "people" : [
            { "pid": 1, "name" : "Galder Gaztelu-Urrutia" }
          ],
          "genres" : [
            { "gid": 1, "genre": "Horror" },
            { "gid": 2, "genre": "ASci-Fi" }
          ],
          "profiles": [
            { "name": "Mewtwo", "preferredGenre": 1 }
          ]
        }
    
    
**2.1.1 Unit Tests for Container class**
    
     /**
     * Scenario:The following are all the testing methods for getters, if the file is
     * read successfully, the setters are used to initialize the corresponding
     * objects, the getters can get the record in the constructed objects. In
     * the @Test methods I compared the record of the constructed objects
     * directly with the specific content in the test.json. If they match then
     * the read from test.json, setters and getters all worked.
     * @throws Exception
     */
    @Test
    public void testGetPeople() {
        for(Person p:container.getPeople())
            Assert.assertEquals(p.getName(),"Galder Gaztelu-Urrutia");
    }
    @Test
    public void testGetFilms() {
        for(Film f:container.getFilms())
            Assert.assertEquals(f.getTitle(), "The Platform");

    }
    @Test
    public void testGetGenres() {
        for(Genre g:container.getGenres())
            Assert.assertEquals(g.getGenre(),"Horror");
    }
    @Test
    public void testGetProfiles() {
        for(Profile p:container.getProfiles())
            Assert.assertEquals(p.getName(),"Mewtwo");
    }
    @Test
    public void testGetTvseries() {
        for(TVSeries t:container.getTvseries())
            Assert.assertEquals(t.getTitle(),"Breaking Bad");
    }
    @Test
    public void testGetMedia() {
        for(Media m:container.getMedia()){
            Assert.assertTrue(container.getMedia().contains(m));
        }
        
**2.1.2 Unit Tests for Film class**


    /**Scenario: Three Film instances are created, both assign the title "a" but different ids.
     * The testEqualsOfMedia() is used to test whether the overridden equals() hashcode() in
     * the super class 'Media' of 'Film' and 'TVSeries' work or not.
     * If the equals() and hashcode() work successfully, the film1.equals(film2) should be true.
     * The testCompareTo() is used to test whether the overridden compareTo in 'Media' class works
     * or not.
     * If it works the film1.compareTo(film3)<0 should be true which means the Film instance 
     * which has a alphabetically small name is smaller than the Film instance with a alphabetically
     * bigger name.
     *
     * Then is the getters test if the instance of the Film is constructed successfully, then if the getters
     * can get the corresponding data, it means they work successfully.
     * 
     * This testClass test the methods in Media and Film. So in TVSeries testing, I won't repeat
     * the test of Media part.
     */
    @BeforeClass
    public void setUp(){
        //create new Film instance
        film1 = new Film();
        director =new Person();
        film1.setTitle("a");
        film1.setFid(2);
        director.setName("director");
        film1.setDirector(director);

        film2 = new Film();
        film2.setTitle("a");
        film2.setFid(3);
        film2.setDirector(director);

        film3 = new Film();
        film3.setTitle("c");
        film3.setFid(1);
        film3.setDirector(director);

    }

    @Test
    public void testEqualsOfMedia(){
        Assert.assertEquals(film1,film2);
    }

    @Test
    public void testCompareTo(){
        Assert.assertTrue(film1.compareTo(film3)<0);
    }
    //test the getters
    @Test
    public void testGetFid() {
        Assert.assertEquals(film1.getFid(),2);
    }
    @Test
    public void testGetDirector() {
        Assert.assertEquals(film1.getDirector(),director);
    }
    
**2.1.3 Unit Tests for TVSeries class**

    /**
     * Scenarios: Create a tvSeries and use the setters to set properties. And the test methods 
     * test the getters, if it is correct then it will return the same property value as set.
     * 
     */
    TVSeries tvSeries;
    Person creator;
    @BeforeClass
    public void setUp(){
        //create a new TVSeries instance
        tvSeries = new TVSeries();
        tvSeries.setTitle("a");
        tvSeries.setTid(1);
        creator = new Person();
        creator.setName("creator");
        tvSeries.setCreator(creator);
    }

    //test of two getters of TVSeries class
    @Test
    public void testGetTid() {
        Assert.assertEquals(tvSeries.getTid(),1);
    }
    @Test
    public void testGetCreator() {
        Assert.assertEquals(tvSeries.getCreator().getName(),"creator");
    }
    
**2.1.3 Unit Tests for Genre class**

     /**
     * Scenario: create three Genre instances genre1, genre2, and genre3
     * Use setters set the genre1, genre3 the same genre(name), and genre2 a different genre(name).
     * Then use getters to get the name and id of genre1 and compare with the real value. If the values
     * match, it means the setters successfully set the value and the getters successfully get the value.
     *
     * Further more, the method testCompareTo() tests the compareTo() in the Genre class, as the Genre instances
     * are compared by their genre(name). When comparing genre1 and genre2, genre2 (with the name "b_testGenre")
     * should be bigger than genre1(named "a_testGenre"). If the 'Assert.assertTrue(genre1.compareTo(genre2)<0);'
     * successfully worked, it means the 'compareTo' method in Genre class works fine.
     *
     * Also, the method testEquals() and testHashCode() are the methods testing the overridden
     * equals() and hashcode() methods. Two Genre instances are equal to each other when they have the same
     * genre(name). So with the same name genre1 and genre3 should be equal to each other.
     * If the testEquals() and testHashCode() work fine, then the equals() and hashcode() in Genre class
     * work fine.
     */
    Genre genre1;
    Genre genre2;
    Genre genre3;
    @BeforeClass
    public void setUp(){
        //create three Genre instances, genre1 and genre3 have the same name but different ids
        genre1 = new Genre();
        genre1.setGenre("a_testGenre");
        genre1.setGid(1);
        genre2 = new Genre();
        genre2.setGenre("b_testGenre");
        genre2.setGid(2);
        genre3 = new Genre();
        genre3.setGenre("a_testGenre");
        genre3.setGid(3);
    }
    @Test
    public void testGetGid() {
        Assert.assertEquals(genre1.getGid(),1);
    }
    @Test
    public void testGetGenre() {
        Assert.assertEquals(genre1.getGenre(),"a_testGenre");
    }

    @Test
    public void testCompareTo() {
        //genre1 should be bigger than genre2 because of their name alphabetically
        Assert.assertTrue(genre1.compareTo(genre2)<0);
    }

    @Test
    public void testEquals() {
        //as have the same name, genre1 and genre3 should be equal
        Assert.assertTrue(genre1.equals(genre3));
    }

    @Test
    public void testHashCode() {
        //the hashcode of genre1 and genre3 are equal, because of the same name
        Assert.assertTrue(genre1.hashCode() == genre3.hashCode());
    }
**2.1.4 Unit Test of Person class**

     /**
     * Scenario: create three Person instances person1, person2, and person3
     * Use setters set the person1, person2 the same name, and person3 a different name.
     * Then use getters to get the name and id of person1 and compare with the real value. If the values
     * match, it means the setters successfully set the value and the getters successfully get the value.
     *
     * Further more, the method testCompareTo() tests the compareTo() in the Person class, as the Person instances
     * are compared by their name. When comparing person1 and person3, person1 (with the name "a")
     * should be smaller than person3(named "b"). If the 'Assert.assertTrue(person1.compareTo(person3)<0);'
     * successfully worked, it means the 'compareTo' method in Person class works fine.
     *
     * Also, the method testEquals() and testHashCode() are the methods testing the overridden
     * equals() and hashcode() methods. Two Person instances are equal to each other when they have the same
     * genre(name). So with the same name person1 and person2 should be equal to each other.
     * If the testEquals() and testHashCode() work fine, then the equals() and hashcode() in Person class
     * work fine.
     */
    Person person1;
    Person person2;
    Person person3;
    @BeforeClass
    public void setUp(){
        //create 3 instances of Person
        person1 = new Person();
        person2 = new Person();
        person1.setName("a");
        person1.setPid(10);
        person2.setName("a");
        person2.setPid(99);
        person3 = new Person();
        person3.setName("b");
    }
    @Test
    public void testGetPid() {
        Assert.assertEquals(person1.getPid(),10);
    }
    @Test
    public void testGetPerson() {
        Assert.assertEquals(person1.getName(),"a");
    }
    @Test
    public void testTestEquals() {
        //person1 and person2 are two different reference variable but has the same name
        //they are supposed to be the same person
        Assert.assertEquals(person1,person2);
    }
    @Test
    public void testTestHashCode() {
        Assert.assertEquals(person1.hashCode(),person2.hashCode());
    }
    @Test
    public void testCompareTo() {
        //person1 (named 'a') should be smaller than person3 (named 'b') alphabetically because of their names.
       Assert.assertTrue(person1.compareTo(person3)<0);
    }
**2.1.6 Unit Test of Profile class**

    /**
     * Scenario: create two Profile instances profile1, profile2
     * Use setters set the profile1, profile2 different names.
     * Then use getters to get the perferredGenre and name of profile1 and compare with the real value. If the values
     * match, it means the setters successfully set the value and the getters successfully get the value.
     *
     * Further more, the method testCompareTo() tests the compareTo() in the Profile class, as the Profile instances
     * are compared by their name. When comparing profile1 and profile2, profile1 (with the name "b")
     * should be bigger than profile2(named "a"). If the 'Assert.assertTrue(profile1.compareTo(profile2)>0);'
     * successfully worked, it means the 'compareTo' method in Profile class works fine.
     *
     */
    Profile profile1;
    Profile profile2;
    Genre genre;
    @BeforeClass
    public void setUp(){
        //create two profile
        profile1=new Profile();
        profile2=new Profile();
        profile1.setName("b");
        profile2.setName("a");
        genre = new Genre();
        profile1.setPreferredGenre(genre);
    }
    @Test
    public void testGetPrefferedGenre() {
        Assert.assertEquals(profile1.getPreferredGenre(),genre);
    }
    @Test
    public void testGetProfile() {
        Assert.assertEquals(profile1.getName(),"b");
    }
    @Test
    public void testCompareTo() {
        //The profile1 named 'b', should be bigger than the profile2 named 'a'
        Assert.assertTrue(profile1.compareTo(profile2)>0);
    }
**2.1.5 Unit Test of GrouperByGenre and Grouper Interface**

     /**
     * Scenario: Read the test.json file into the container, and get the mediaSet whose data is 
     * sorted then and the genreSet according to which the mediaSet's data is sorted.
     * Then create a Genre instance 'genreTest' whose genre (name) is the same as the alphabetically biggest
     * Genre instance from the JSON file. 
     * As the GrouperByGenreTest implementing singleton pattern, the getGrouperByGenre() method
     * is tested by testGetGrouperByGenre(). If the reference of Grouper in this case is not null, 
     * it means the getGrouperByGenre() works successfully.
     * The sort() method is the method from Interface Grouper which returns TreeMap<Comparable,TreeSet<Media>>
     * In testSort() method, after sorting the mediaSet, the first Key of the TreeMap should be the Genre instance
     * whose genre (name) is "ASci-Fi". If the Key and the genreTest are the same, it means the sort() method
     * works successfully. This is also a test of the Genre class' equals() and hashcode() methods,
     * where only if two Genre have the same genre('name'),the two instances are equal to each other.
     * 
     * The getSortedMedia() method is the method from InterfaceGrouper as well. In the testGetSortedMedia() method
     * similar as the testSort() method, but the two variables are evaluated equal or not are String.
     * As the getSortedMedia() returns TreeMap<Comparable, TreeMap<String, String>> which can be used directly by
     * View part.
     */
    Grouper grouper;
    GenreSet genreSet;
    Container container;
    Genre genreTest;
    MediaSet mediaSet;
    @BeforeClass
    public void setUp() throws IOException {
        //read in the file
        Model model = Model.getModel();
        model.init("test.json");
        container =model.getContainer();
        mediaSet = MediaSet.getMediaSet(container);
        //get the grouper singleton
        grouper = GrouperByGenre.getGrouperByGenre();
        //generate the genreSet
        genreSet = GenreSet.getGenreSet(container);

        //create a new Genre instance
        genreTest =new Genre();
        genreTest.setGenre("ASci-Fi");
    }

    @Test
    public void testGetGrouperByGenre() {
        Assert.assertNotNull(grouper);//succeed if the grouper reference is not null
    }

    @Test
    public void testSort() {
        //the new genre object whose genreName is " ASci-Fi" should be the first key in the sorted catalogue
        Assert.assertEquals(grouper.sort(mediaSet,genreSet).firstKey(), genreTest);
    }
    @Test
    public void testGetSortedMedia(){
        //similar as the testSort() method, but the two variables evaluate equal or not are String.
        Assert.assertEquals(grouper.getSortedMedia(mediaSet,genreSet).firstKey(), genreTest.getGenre());
    }
**2.1.5 Unit Test of GrouperByYear class**

    /**
     * Scenario: Read the test.json file into the container, and get the mediaSet whose data is
     * sorted then and the yearSet according to which the mediaSet's data is sorted.
     * Also in this test class a new Film instance is created by the FilmFactory singleton, so this class also tests
     * the FilmFactories 'getFilmFactory' method. This new Film instance was made in the year '1000', which should be 
     * the first element after being added to the YearSet.
     * As the GrouperByYear implementing singleton pattern, the getGrouperByYear() method
     * is tested by testGetGrouperByYear(). If the reference of Grouper in this case is not null,
     * it means the getGrouperByYear() works successfully.
     * The sort() method is the method from Interface Grouper which returns TreeMap<Comparable,TreeSet<Media>>
     * In testSort() method, after sorting the mediaSet, the first Key of the TreeMap should be the Integer(year) 
     * instance 1000. If the first Key is the same as 1000, it means the sort() method
     * works successfully.
     * The getSortedMedia() method is the method from InterfaceGrouper as well. In the testGetSortedMedia() method
     * similar as the testSort() method. If the First Key which is a  Comparable instance is equal to 1000, it means the 
     * method worked successfully.
     * As the getSortedMedia() returns TreeMap<Comparable, TreeMap<String, String>> which can be used directly by
     * View part.
     */
    Container container;
    Grouper grouper;
    YearSet yearSet;
    FilmFactory filmFactory;
    Film film;
    MediaSet mediaSet;
    @BeforeClass
    public void setUp() throws IOException {
        Model model = Model.getModel();
        model.init("test.json");
        container =model.getContainer();
        //get the grouper singleton
        grouper = GrouperByYear.getGrouperByYear();
        //generate the genreSet
        yearSet = YearSet.getYearSet(container);
        filmFactory = FilmFactory.getFilmFactory();
        film = filmFactory.createMedia(12,"Test","testDesc",1000,new ArrayList<Genre>(),new Person(),new ArrayList<Person>());
        mediaSet = MediaSet.getMediaSet(container);
    }
    @Test
    public void testGetGrouperByYear() {
        //should not be null if get the singleton successfully
        Assert.assertNotNull(grouper);
    }
    @Test
    public void testSort() {
        //add the new year to yearSet
        yearSet.addNewData(film.getYear());
        //add the film into the mediaSet
        mediaSet.addNewData(film);
        //if sorted successfully the first key should be 1000 the year of the reference 'film'
        Assert.assertEquals(grouper.sort(mediaSet,yearSet).firstKey(),1000);
    }
    @Test
    public void testGetSortedMedia() {
        //add the new year to yearSet
        yearSet.addNewData(film.getYear());
        //add the film into the mediaSet
        mediaSet.addNewData(film);
        //if sorted successfully the first key should be 1000 the year of the reference 'film'
        Assert.assertEquals(grouper.getSortedMedia(mediaSet,yearSet).firstKey(),1000);
    }
**2.1.5 Unit Test of interface Addable, abstract DataSet class, abstract DoubleIDSet class and MediaSet class**

    /**
     * Scenario: This test class tests the abstract class DoubleIDDataSet, DataSet, the Interface Addable,
     * and the concrete class MediaSet.
     * It firstly reads in the test.json file to the container and create an Film instance film1 and TVSeries instance
     * tvSeries1 for the later testing of method addNewData() and pass the container MediaSet's
     * getMediaSet method to get the MediaSet singleton. 
     * testGetCatalogue() if it is not null the method getCatalogue does get the protected catalogue variable from the DataSet class
     * testGetMediaSet() if the mediaSet reference is not null, that means getting the singleton successfully.
     * testInitializeLastFID() test the abstract method in DoubleIDDataSet class and the concrete implementation in MediaSet class
     *      if the id is initialized successfully the last FID should be 1
     * testInitializeLastTID() test the abstract method in DoubleIDDataSet class and the concrete implementation in MediaSet class
     *      if the id is initialized successfully the last TID should be 1
     * testAddNewData() test the method in Addable interface and the concrete implementation in MediaSet
     *      firstly add two Media objects into the mediaSet,the two Media objects should now be in the catalogue of the mediaSet
     *      if the MediaSet instance contains the two Media objects, it means the addNewData() method run successfully
     */
    //the fields
    Film film1;
    TVSeries tvSeries1;
    MediaSet mediaSet;
    Container container;

    @BeforeClass
    public void setUp() throws IOException {
        //read file
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);

        film1 = new Film();
        film1.setTitle("a");
        film1.setFid(4);
        tvSeries1 = new TVSeries();
        tvSeries1.setTitle("b");
        tvSeries1.setTid(3);
        //get the MediaSet singleton
        mediaSet =MediaSet.getMediaSet(container);
    }
    //if it is not null the method getCatalogue does get the protected catalogue variable from the DataSet class
    @Test
    public void testGetCatalogue(){
        Assert.assertNotNull(mediaSet.getCatalogue());
    }
    //test the singleton of the MediaSet
    @Test
    public void testGetMediaSet() {
        Assert.assertNotNull(mediaSet);
    }
    //test the abstract method in DoubleIDDataSet class and the concrete implementation in MediaSet class
    @Test
    public void testInitializeLastFID() {
        //if the id is initialized successfully the last FID should be 1
        Assert.assertEquals(mediaSet.getLastFID(),(Integer) 1);
    }
    //test the abstract method in DoubleIDDataSet class and the concrete implementation in MediaSet class
    @Test
    public void testInitializeLastTID() {
        //if the id is initialized successfully the last TID should be 1
        Assert.assertEquals(mediaSet.getLastTID(),(Integer) 1);
    }
    //test the method in Addable interface and the concrete implementation in MediaSet
    @Test
    public void testAddNewData() {
        //add two Media objects into the mediaSet
        mediaSet.addNewData(film1);
        mediaSet.addNewData(tvSeries1);
        //the two Media objects should now be in the catalogue of the mediaSet
        Assert.assertTrue(mediaSet.getCatalogue().contains(film1));
        Assert.assertTrue(mediaSet.getCatalogue().contains(tvSeries1));
    }
**2.1.5 Unit Test of PersonSet and abstract SoloIDDataSet**

    /**
     * Scenario: This test class tests the abstract class SoloIDDataSet and the concrete class PeopleSet
     * It firstly reads in the test.json file to the container and get the singleton of PeopleSet by passing
     * in container as param. 
     * testGetPeopleSet() is the method tests the getPeopleSet() method, if the peopleSet is not null, it means the 
     *      singleton is gotten successfully.
     * testAddNewData() is the method tests the addNewData() method inherits from the Addable interface,
     *      test the concrete implementation of the method in Addable, if the catalogue in peopleSet has 3 
     *      elements, it means that all three Person instances are successfully added.
     * testInitializeID() tests the abstract method initializeID() and abstract method getLastId() in SoloIDDataSet class
     * and the concrete implementation in the PeopleSet class. 
     *       The id is initialized successfully if the last id is 1.
     */
    Person person1;
    Person person2;
    Person person3;
    Container container;
    PeopleSet peopleSet;
    @BeforeClass
    public void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);
        //get the PeopleCatalogue
        peopleSet = PeopleSet.getPeopleSet(container);

        //create three Person instances
        person1 = new Person();
        person2 = new Person();
        person3 = new Person();
        person1.setName("a");
        person2.setName("a");
        person3.setName("b");
    }
    //if the peopleSet is not null, it means the singleton is gotten successfully
    @Test
    public void testGetPeopleSet() {
        Assert.assertNotNull(peopleSet);
    }
    
    //test the concrete implementation of the method in Addable, if the catalogue in peopleSet has 3 
    //elements, it means that all three Person instances are successfully added
    @Test
    public void testAddNewData() {
        peopleSet.addNewData(person1);
        peopleSet.addNewData(person2);
        peopleSet.addNewData(person3);
        //person1 and person3 should be treated as the same person, so even though 3 people were added only 2
        //more new people are now in the peopleSet's catalogue
        Assert.assertEquals(peopleSet.getCatalogue().size(),3);
    }

    //this test the abstract method initializeID() and abstract method getLastId()
    // in SoloIDDataSet class.
    @Test
    public void testInitializeID() {
        //if the id is initialized successfully the last id should be 1
        Assert.assertEquals(peopleSet.getLastId(),(Integer)1);
    }
**2.1.5 Unit Test of GenreSet class**

     /**
     * Scenario: This test class tests the abstract class SoloIDDataSet and the concrete class GenreSet
     * It firstly reads in the test.json file to the container and get the singleton of GenreSet by passing
     * in container as param.
     * testGetGenreSet() is the method tests the getGenreSet() method, if the genreSet is not null, it means the
     *      singleton is gotten successfully.
     * testAddNewData() is the method tests the addNewData() method inherits from the Addable interface,
     *      test the concrete implementation of the method in Addable.Add the two Genre instances into the genreSet 
     *      genre1 is a new Genre object while genre2 is a Genre object has the same Genre Name in the catalogue of genreSet.
     *      The catalogue of genreSet should contain genre1 if it is added successfully.
     *      While genre2, even though is added as well, but the genreSet will not be refreshed
     * testInitializeID() tests the abstract method initializeID() and abstract method getLastId() in SoloIDDataSet class
     * and the concrete implementation in the GenreSet class.
     *       if the id is initialized successfully the last id should be 2.
     */
    //the fields
    Container container;
    Genre genre1;
    Genre genre2;
    GenreSet genreSet;

    @BeforeClass
    public void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);

        //get the GenreCatalogue
        genreSet= GenreSet.getGenreSet(container);

        //create new Genre objects
        genre1 = new Genre();
        genre1.setGenre("testGenre1");

        genre2 = new Genre();
        genre2.setGenre("ASci-Fi");
    }

    @Test
    public void testGetGenreSet() {
        Assert.assertNotNull(genreSet);
    }//end method

    @Test
    public void testAddNewData() {
        //add the two Genre instances into the genreSet genre1 is a new Genre object while genre2 is a Genre
        //object has the same Genre Name in the catalogue of genreSet.
        genreSet.addNewData(genre1);
        genreSet.addNewData(genre2);

        //the catalogue of genreSet should contain genre1 if it is added successfully.
        Assert.assertTrue(genreSet.getCatalogue().contains(genre1));
        //while genre2, even though is added as well, but the genreSet will not be refreshed
        Assert.assertEquals(genreSet.getCatalogue().size(),3);
    }//end method
    @Test
    public void testInitializeID() {
        //if the id is initialized successfully the last id should be 2
        Assert.assertEquals(genreSet.getLastId(),(Integer) 2);
    }//end method
    
**2.1.5 Unit Test of YearSet class**
    
     /**
     * Scenario: This test class tests the concrete class YearSet
     * It firstly reads in the test.json file to the container and get the singleton of YearSet by passing
     * in container as param.
     * testGetYearSet() is the method tests the getYearSet() method, if the yearSet is not null, it means the
     *      singleton is gotten successfully. 
     * testAddNewData() is the method tests the addNewData() method inherits from the Addable interface,
     *      test the concrete implementation of the method in Addable. Add a new Integer 1998 to the yearSet
     *      If the size of the catalogue in YearSet singleton is 3, it means the Integer is added successfully
     */

    Container container;
    YearSet yearSet;
    @BeforeClass
    public void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);
        yearSet = YearSet.getYearSet(container);
    }
    @Test
    public void testGetYearSet() {
        //test the singleton getter, the reference should not be null
        Assert.assertNotNull(yearSet);
    }
    @Test
    public void testAddNewData() {
        //add a new Integer to the yearSet
        yearSet.addNewData(1988);
        //the size of the yearSet should be 3 now
        Assert.assertEquals(yearSet.getCatalogue().size(),3);
    }
    
**2.1.5 Unit Test of ProfileSet class**

     /**
     * Scenario: This test class tests the concrete class ProfileSet
     * It firstly reads in the test.json file to the container and get the singleton of ProfileSet by passing
     * in container as param.
     * testGetProfileSet() is the method tests the getYearSet() method, if the yearSet is not null, it means the
     *      singleton is gotten successfully.
     */
    Container container;
    ProfileSet profileSet;
    @BeforeClass
    public void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);
        profileSet = ProfileSet.getProfileSet(container);
    }
    @Test
    public void testGetProfileSet() {
        Assert.assertNotNull(profileSet);
    }
**2.1.5 Unit Test of MediaInfoExtractor interface and MediaInfoExtractorByProfile class**

    /**
     * Scenario: This class tests the interface MediaInfoExtractor and the concrete class MediaInfoExtractorByProfile
     * 
     * testGetMediaInfoExtractorByProfile() test the singleton getter.
     *      If the mediaInfoExtractor is not null, the singleton is gotten successfully.
     * testGetMediaInfo() test the getMediaInfo() method.
     *      If succeed, the first element of the returned List should be the title of the Media instance, "The Platform"
     */
    MediaInfoExtractor mediaInfoExtractor;
    Container container;
    DataSet profileSet;
    DataSet mediaSet;
    @BeforeClass
    public void setUp() throws IOException {
        //read in a test.json file and check the result of the read in objects
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);
        mediaInfoExtractor = MediaInfoExtractorByProfile.getMediaInfoExtractorByProfile();
        profileSet = ProfileSet.getProfileSet(container);
        mediaSet = MediaSet.getMediaSet(container);
    }
    
    @Test
    public void testGetMediaInfoExtractorByProfile() {
        Assert.assertNotNull(mediaInfoExtractor);
    }
    
    @Test
    public void testGetMediaInfo() {
        //If succeed, the first element of the returned List should be the title of the Media instance, "The Platform" 
        Assert.assertEquals(mediaInfoExtractor.getMediaInfo("Mewtwo",mediaSet,profileSet).get(0),"The Platform");
    }
**2.1.5 Unit Test of MediaInfoExtractorByTitle class**

    /**
     * Scenario: This class tests the interface MediaInfoExtractor and the concrete class MediaInfoExtractorByTitle
     *
     * testGetMediaInfoExtractorByProfile() test the singleton getter.
     *      If the mediaInfoExtractor is not null, the singleton is gotten successfully.
     * testGetMediaInfo() test the getMediaInfo() method.
     *      If succeed, the third element of the returned List should be "2008"
     */
    MediaInfoExtractor mediaInfoExtractor;
    Container container;
    DataSet mediaSet;
    @BeforeClass
    public void setUp() throws IOException {
        //read in a test.json file and check the result of the read in objects
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);
        mediaInfoExtractor = MediaInfoExtractorByTitle.getMediaInfoExtractorByTitle();
        mediaSet = MediaSet.getMediaSet(container);
    }
    @Test
    public void testGetMediaInfoExtractorByProfile() {
        Assert.assertNotNull(mediaInfoExtractor);
    }

    @Test
    public void testGetMediaInfo() {
        //If succeed, the first element of the returned List should be the title of the Media instance, "The Platform"
        Assert.assertEquals(mediaInfoExtractor.getMediaInfo("Breaking Bad",mediaSet).get(2),"2008");
    }

**2.1.5 Unit Test of FilmFactory class and abstract class MediaFactory**

    /**
     * Scenario: This class tests the abstract class MediaFactory and the concrete class FilmFactory.
     * testGetFilmFactory() tests whether get the singleton of FilmFactory successfully. If the filmFactory
     *      is not null, the singleton is successfully gotten.
     * testCreateMedia() create a Film with the filmFactory and compare the property value gotten from the created
     *      Film instance with the value. If the values are the same, it means the Media instance is created successfully.
     */
    Film film;
    Genre genre;
    Person person;
    FilmFactory filmFactory;
    @BeforeClass
    public void setUp(){
        //get the singleton of the FilmFactory
         filmFactory =FilmFactory.getFilmFactory();

        /*create the elements to construct a new Film*/
        List<Genre> genreList = new ArrayList<>();
        List<Person> people = new ArrayList<>();

        genre = new Genre();
        genre.setGid(1);
        genre.setGenre("testGenre");

        person = new Person();
        person.setPid(1);
        person.setName("testPerson");

        genreList.add(genre);
        people.add(person);

        //create the instance of Film
        film = filmFactory.createMedia(1,"testTitle","testDescription",1000,
                genreList,person,people);
    }
    @Test
    public void testGetFilmFactory(){
        Assert.assertNotNull(filmFactory);//should not be null if successfully get
    }
    @Test
    public void testCreateMedia() {
        //test the filmFactory's create method to see if there is any disorder of the params
        Assert.assertEquals(film.getFid(),1);
        Assert.assertEquals(film.getTitle(),"testTitle");
        Assert.assertEquals(film.getDescription(),"testDescription");
        Assert.assertEquals(film.getYear(),1000);
        Assert.assertTrue(film.getGenre().contains(genre));
        Assert.assertEquals(film.getGenre().size(),1);
        Assert.assertEquals(film.getDirector(),person);
        Assert.assertEquals(film.getCast().size(),1);
        Assert.assertTrue(film.getCast().contains(person));
    }
**2.1.5 Unit Test of TVSeriesFactory class**

    /**
     * Scenario: This class tests the concrete class TVSeriesFactory.
     * testGetTVSeriesFactory() tests whether get the singleton of TVSeriesFactory successfully. If the filmFactory
     *      is not null, the singleton is successfully gotten.
     * testCreateMedia() create a TVSeries with the TVSeriesFactory and compare the property value gotten from the created
     *      TVSeries instance with the value. If the values are the same, it means the Media instance is created successfully.
     */
    TVSeries tvSeries;
    Genre genre;
    Person creator;
    TVSeriesFactory tvSeriesFactory;
    //create a new tvSeries to test the createTVSeries method.
    @BeforeClass
    public void setUp(){
        tvSeriesFactory =TVSeriesFactory.getTvSeriesFactory();
        List<Genre> genreList = new ArrayList<>();
        List<Person> people = new ArrayList<>();

        genre = new Genre();
        genre.setGid(1);
        genre.setGenre("testGenre");

        creator = new Person();
        creator.setPid(1);
        creator.setName("testPerson");

        genreList.add(genre);
        people.add(creator);

        tvSeries = tvSeriesFactory.createMedia(1,"testTitle","testDescription",1000,
                genreList,creator,people);
    }
    @Test
    public void testGetTVSeriesFactory() {
        Assert.assertNotNull(tvSeriesFactory);
    }
    @Test
    public void testCreateMedia() {
        Assert.assertEquals(tvSeries.getTid(),1);
        Assert.assertEquals(tvSeries.getTitle(),"testTitle");
        Assert.assertEquals(tvSeries.getDescription(),"testDescription");
        Assert.assertEquals(tvSeries.getYear(),1000);
        Assert.assertTrue(tvSeries.getGenre().contains(genre));
        Assert.assertEquals(tvSeries.getGenre().size(),1);
        Assert.assertEquals(tvSeries.getCreator(),creator);
        Assert.assertEquals(tvSeries.getCast().size(),1);
        Assert.assertTrue(tvSeries.getCast().contains(creator));
    }

**2.1.5 Unit Test of Model class**

    /**
     * Scenario: get the Model singleton by the method getModel(), use init() method to initialize the
     * Model singleton to read the test.json. Get the container by the Model's getContainer() method, and
     * initialize the yearSet,genreSet,peopleSet,profileSet and mediaSet with the container.
     * Create the param Strings for the method addMedia() to add the data to the yearSet,genreSet,peopleSet,profileSet
     * and mediaSet.
     * testGetModel() method tests the getModel() method. The singleton is successfully gotten
     * if model is not null.
     * testAddMedia() the so-called Media instance has been added in the setUp() method.
     *      if the Media is added successfully, the size of the mediaSet should be 3
     *      if the Media is added successfully, the size of the yearSet should be 3
     *      if the Media is added successfully, the size of the genreSet should be 5
     *      if the Media is added successfully, the size of the peopleSet should be 5
     * testGetProfile() method create a new Profile with the same name of an element in the profileSet
     *      get the profiles in the memory by the method getCatalogue(). If the new Profile instance which is
     *      the instance equal to some element of the profile catalogue, this method runs successfully because the
     *      profiles are gotten.
     * testGetListedMediaByYear() the first key should be 1992 if the media is listed by year
     * testGetListedMediaByGenre() the first key should be the same Genre instance created in this method
     *      if the media is listed by Genre.
     * testGetMediaInfoByProfile() get the media info from by profile, for the profile 'Mewtwo' the first element of the
     *      returned List should be the title of the media 'The Platform'
     * testGetMediaInfoByTitle() get the media info by title, for the title 'titleTest' the second element of the returned
     *      List should be the description 'descTest'.
     * testGetContainer() container should not be null if the method works
     */
    Model model;
    MediaSet mediaSet;
    YearSet yearSet;
    GenreSet genreSet;
    PeopleSet peopleSet;
    ProfileSet profileSet;
    Container container;
    String title = "titleTest";
    String description= "descTest";
    String yearString= "1992";
    String genreString= "genre1,genre2,genre3";
    String personString ="personTest";
    String castString ="cast1,cast2,cast3";
    boolean tvSeries = false;

    @BeforeClass
    public void setUp() throws IOException, InputFormatException {
        model= Model.getModel();
        model.init("test.json");
        container=model.getContainer();
        mediaSet=MediaSet.getMediaSet(container);
        yearSet=YearSet.getYearSet(container);
        genreSet = GenreSet.getGenreSet(container);
        peopleSet=PeopleSet.getPeopleSet(container);
        profileSet=ProfileSet.getProfileSet(container);
        model.addMedia(title,description,yearString,genreString,personString,castString, false);
    }
    @Test
    public void testGetModel() {
        Assert.assertNotNull(model);
    }
    @Test
    public void testAddMedia() {
        //if the Media is added successfully, the size of the mediaSet should be 3
        Assert.assertEquals(mediaSet.getCatalogue().size(),3);
        //if the Media is added successfully, the size of the yearSet should be 3
        Assert.assertEquals(yearSet.getCatalogue().size(),3);
        //if the Media is added successfully, the size of the genreSet should be 5
        Assert.assertEquals(genreSet.getCatalogue().size(),5);
        //if the Media is added successfully, the size of the peopleSet should be 5
        Assert.assertEquals(peopleSet.getCatalogue().size(),5);
    }
    @Test
    public void testGetProfile() {
        Profile profile = new Profile();
        profile.setName("Mewtwo");
        //the newly added Profile should be in the profileSet's catalogue
        Assert.assertTrue(profileSet.getCatalogue().contains(profile));
    }
    @Test
    public void testGetListedMediaByYear() {
        //the first key should be 1992 if the media is listed by year
        Assert.assertEquals(model.getListedMediaByYear().firstKey(),1992);
    }
    @Test
    public void testGetListedMediaByGenre() {
        Genre genre = new Genre();
        genre.setGenre("ASci-Fi");
        //the first key should be the same Genre instance created in this method
        // if the media is listed by Genre
        Assert.assertEquals(model.getListedMediaByGenre().firstKey(),genre);
    }
    @Test
    public void testGetMediaInfoByProfile() {
        //get the media info from by profile, for the profile 'Mewtwo' the first element of the
        //returned List should be the title of the media 'The Platform'
        Assert.assertEquals(model.getMediaInfoByProfile("Mewtwo").get(0),"The Platform");
    }
    @Test
    public void testGetMediaInfoByTitle() {
        //get the media info by title, for the title 'titleTest' the second element of the returned
        //List should be the description 'descTest'.
        Assert.assertEquals(model.getMediaInfoByTitle("titleTest").get(1),"descTest");
    }
    @Test
    public void testGetContainer() {
        //this should not be null if the method works
        Assert.assertNotNull(container);
    }

# 3. User Guideline
When the application is started, a default user profile (the first Profile in the background ProfileSet) is shown to the main screen namde as 'Video Catalogue'. There are four buttons on the left ("Switch Profile","Add New","List by Year","List by Genre"), all Media items based on user’s preferred genre, and the current active profile. Each Media item is listed in terms of title, year, and genre. The title for each item links to a new screen to show its details. MainScreen:
**The default Main Screen:**
![](https://raw.githubusercontent.com/Dovakiin-cocode/Markdown-Photos/master/CT548/MainScreen.PNG)

The buttons link to four screens, when you click on the “Switch Profile” button in the main screen, the profile selection screen is displayed, which contains a button for each user profile. The following shows an example screen with four user profiles. Once you click on a profile button, the profile selection screen is closed, and the main screen is updated with the chosen profile.
**The SelectUser UI:**
![](https://raw.githubusercontent.com/Dovakiin-cocode/Markdown-Photos/master/CT548/SelectUser.PNG)
**The Main Screen after switching the User Profile:**
![](https://raw.githubusercontent.com/Dovakiin-cocode/Markdown-Photos/master/CT548/MainScreenAfterSwitchingTheUser.PNG)
When you click the “Add New” button in the main screen, the “Add Item” screen is displayed that contains seven input fields to collect information about a new catalogue item. You can enter the details of the new item and press the “Save” button that saves the data and close the “Add Item” screen
**The Add Item UI:**
![](https://raw.githubusercontent.com/Dovakiin-cocode/Markdown-Photos/master/CT548/AddItem.PNG)
When you click the link on the title of an item, the “Item details” screen is displayed that contains seven fields to display information about the linked catalogue item. You can view the details of the linked item and press the “Back” button to close the screen.
**The Detail Info UI:**
![](https://raw.githubusercontent.com/Dovakiin-cocode/Markdown-Photos/master/CT548/ShowDetailInfo.PNG)
When you click the “List by Year” button in the main screen, the “List by Year details” screen is displayed that contains the list of all items in catalogue grouped and ordered according to year. The items are ordered according to their title in each year group. The title of each item is linked to its details screen like the main screen.
**The List By Year UI:**
![](https://raw.githubusercontent.com/Dovakiin-cocode/Markdown-Photos/master/CT548/ListByYear.PNG)
**The Detail Info UI:**
![](https://raw.githubusercontent.com/Dovakiin-cocode/Markdown-Photos/master/CT548/ShowDetailInfoListByYear.PNG)
When you click the “List by Genre” button in the main screen, the “List by Genre” screen is displayed that contains the list of all items in catalogue grouped and ordered by genre. The items are ordered according to their title in each genre group. The title of each item is again linked to its details screen like the main screen.
**The List By Genre UI:**
![](https://raw.githubusercontent.com/Dovakiin-cocode/Markdown-Photos/master/CT548/ListByGenre.PNG)
**The Detail Info UI:**
![](https://raw.githubusercontent.com/Dovakiin-cocode/Markdown-Photos/master/CT548/ShowDetailInfoListByGenre.PNG)

# 4. How the media item creation subsystem can be extended to create movies and series that are physical separate from digital ones?

To extend the system for the new feature to separate the digital and physical media, I think the first thing is to add a new attributes 'mediaFormat' to the abstract Media class which can then be inherited by its subclasses 'Film' and 'TVSeries', then overload the constructor (extension but not modification) of 'Film' and 'TVSeries', with the new attribute as a parameter. The 'mediaFormat' attribute can be declared as Enum. In this case, the Media with the same title are no longer the same, as they may have different foramat, so the equals() and hashcode() should be modified in Media class to only the Media with the same name and format are the same media Items, as well as the compareTo() method, to make sure that the Media compare firstly the title then the format name alphabetically. 
And I whould modify the implementation of List<String> getMediaInfo(...) method. Take MediaInfoExtractorByTitle as an example, add a different String separator(not ","), i.e. %,$ etc. to separate the information of the two Meida instances gotten by the title, while at the controller side, two detail frames would be created when the information of two Media instances is sent back by the Model recognized by the special separator. And on the View side one more input (media format) should be taken in when adding a NewItem, and one more output JLabel (media format) should be invoked when display the detail of the Media Item.
During answering this question, I found that my program cannot perfectly fit the Open-Close principle in this case. When adding a new attribute, the code is always modified but not only extended. 

# 5.Further extension

1. Being compared to the desktop application, the web application is more popular currently, so this system can be deployed on the internet. And the View part can be constructed by HTML, CSS and JavaScript to display the data rendered by the browser, also the input checking can be operated on the front-end, but not like this application, on the backend.
2. I noticed that the requirement did not ask for the function of adding a new User, so this can be another extension point. And this is easy to implement just by making the ProfileSet implement Addable, adding a new button in the View part, constructing a new UI for this function and adding a action listener to this button in the Controller part.
3. Furthermore, as the system is related to some private information, so a password feature is another extension point.
4. Importantly, the system is now reading data firstly from the JSON file and then from the memory. But if we are going to have huge amount of data and a persistent storage of data, DB is the unavoidable choice. So this can be an extension point.
5. Also, considering the system/application can be deployed on the Internet, 
high concurrency is also an critical situation, where the application must achieve some thread safe design. 
