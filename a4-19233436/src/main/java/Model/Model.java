package Model;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Junlin Lu
 * @date: 07/04/2020
 * @version: 1.0.0
 * @description: This is the Model class. It is in charge of reading in the JSON file into the Container, initialize the
 * data i.e. mediaCatalogue, lastFID, lastTID, lastGID, genreSet and yearSet of the existed Media,
 * instances of Factory, and the reference of the strategy class.
 * This class provides methods in the interaction with the Controller and View.
 */
public class Model {
    private static Model model;
    private Container container;
    private final FilmFactory filmFactory = FilmFactory.getFilmFactory();
    private final TVSeriesFactory tvSeriesFactory = TVSeriesFactory.getTvSeriesFactory();
    private MediaSet mediaSet;
    private YearSet yearSet;
    private PeopleSet peopleSet;
    private GenreSet genreSet;
    private ProfileSet profileSet;
    private Grouper grouper;
    private MediaInfoExtractor mediaInfoExtractor;

    /**
     * the constructor, when a new Model is created, the file with String fileName is read in by Jackson's
     * ObjectMapper, and stored in the Container container. Then the data in container is assembled by the
     * assemblyMediaSet() method as a new data structure TreeSet<Media>. Next, call the method renewData(),
     * to initialize(For the constructor only) the FID,TID,GID,genreSet,yearSet.
     * @throws IOException
     */
    private Model() {

    }//end constructor

    public static synchronized Model getModel(){
        if(model==null){
            model=new Model();
        }
        return model;
    }

    /**
     * Initialize the Model
     * @param fileName the file read in the Model
     * @throws IOException file not exist
     */
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

    /**
     * Takes a set of params for creating new Media instance.
     * There are three blocks in this method. Checking block; Assembling block; Add in block
     * Checking block:
     *  The String passed in are firstly trimmed than the yearString is checked whether it consists of pure numbers
     *  if not, throw an InputFormatException which will be catch in the Controller and handled by the View part.
     * Assembling block:
     *  Then assemble the String into the format which is suitable for creating new Media instance.
     * Add in block:
     *  Creating new Media instance and add to the mediaSet.
     * @param title the title of the media
     * @param description the description of the media
     * @param yearString the year of the media
     * @param genreString the genre of the media
     * @param personString the person of the media(director/creator)
     * @param castString the cast of the media
     * @param TVSeries whether the media is a TVSeries
     * @throws InputFormatException thrown out if the param does not fit the rules.
     */
    public void addMedia(String title, String description, String yearString, String genreString,String personString ,String castString , boolean TVSeries) throws InputFormatException {
        /**checking block*/
        //trim the Strings
        title=title.trim();
        description=description.trim();
        yearString=yearString.trim();
        genreString=genreString.trim();
        personString=personString.trim();
        castString=castString.trim();
        if(title.isEmpty()||description.isEmpty()||yearString.isEmpty()||genreString.isEmpty()||personString.isEmpty()||castString.isEmpty()){
            throw new InputFormatException(InputFormatException.EMPTY_INPUT);
        }
        //determine whether the year is pure numbers
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(yearString);
        if(!isNum.matches() ){
            throw new InputFormatException(InputFormatException.INCORRECT_YEAR);
        }
        /**End of checking block*/

        /**Assembling block*/
        //Create two List, one Person, an int year instances to carry the data of the casts, genres, person, and year.
        List<Person> casts =new ArrayList<Person>();
        List<Genre> genres = new ArrayList<Genre>();
        Person person = new Person();
        person.setName(personString);
        int year = Integer.parseInt(yearString);
        yearSet.addNewData(year);
        peopleSet.addNewData(person);
        //The data transmitted in of genre has separator ","
        //This for-loop is used to iterate the genreString, get each of its split String
        for(String s: genreString.split(",")){
            //creat a new Genre instance
            Genre genre = new Genre();
            genre.setGenre(s);
            genreSet.addNewData(genre);
            //add the genre to the genres- the field of the created Media instance
            genres.add(genre);
        }//end for

        //now do the same thing for cast List
        for(String s: castString.split(",")){//split the String by the separator ","
            Person castMember = new Person();//create new Person
            castMember.setName(s);
            peopleSet.addNewData(castMember);
            //add the person in to the cast List
            casts.add(castMember);
        }
        /**End of assembling block*/

        /**Add in block*/
        if(!TVSeries){//if the Media the user wants to add is not TVSeries, it is a film.
            //Call the filmFactory's createMedia method, note: the lastFID is going to be plus 1 in the method body, which
            //means the new added Media has a incremented FID
            Film film =filmFactory.createMedia(0,title, description, year, genres,person,casts);
            mediaSet.addNewData(film);
        }
        else
        {//if the Media the user wants to add is TVSeries, call the tvSeriesFactory's createMedia method, the lastTID is the
            //same as the lastFID.
            TVSeries tvSeries = tvSeriesFactory.createMedia(0,title,description,year,genres,person,casts);
            mediaSet.addNewData(tvSeries);
        }
        /**End of add in block*/
    }
    /**
     * Get the profile in the container
     * @return List<String> userName
     */
    public List<String> getProfile(){
        List<String> userName = new ArrayList<>();
        for(Profile p:profileSet.getCatalogue()) {
            userName.add(p.getName());
        }//end for
        return userName;
    }//end method

    /**
     * Assign the GroupByYear singleton to the reference grouper, and sort the Media data according to the yearSet
     * @return TreeMap<Comparable,TreeMap<String,String>>
     */
    public TreeMap<Comparable,TreeMap<String,String>> getListedMediaByYear(){
        grouper = GrouperByYear.getGrouperByYear();
        return grouper.getSortedMedia(mediaSet,yearSet);
    }//end method

    /**
     * Assign the GroupByGenre singleton to the reference grouper, and sort the Media data according to the genreSet
     * @return TreeMap<Comparable,TreeMap<String,String>>
     */
    public TreeMap<Comparable, TreeMap<String,String>> getListedMediaByGenre() {
        grouper = GrouperByGenre.getGrouperByGenre();
        return grouper.getSortedMedia(mediaSet,genreSet);
    }//end method

    /**
     * Assign the MediaInfoExtractorByProfile singleton to the reference mediaInfoExtractor, get the info and return
     * @param profile the User Profile
     * @return List<String>
     */
    public List<String> getMediaInfoByProfile(String profile){
        mediaInfoExtractor = MediaInfoExtractorByProfile.getMediaInfoExtractorByProfile();
        return mediaInfoExtractor.getMediaInfo(profile,mediaSet,profileSet);
    }//end method

    /**
     * Assign the MediaInfoExtractorByTitle singleton to the reference mediaInfoExtractor, get the info and return
     * @param title the title of the Media
     * @return List<String>
     */
    public List<String> getMediaInfoByTitle(String title){
        mediaInfoExtractor = MediaInfoExtractorByTitle.getMediaInfoExtractorByTitle();
        return mediaInfoExtractor.getMediaInfo(title,mediaSet);
    }//end method

    public Container getContainer() {
        return container;
    }
}//end class
