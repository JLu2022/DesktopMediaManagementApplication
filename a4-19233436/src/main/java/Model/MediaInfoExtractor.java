package Model;

import java.util.List;

/**
 * @author: Junlin Lu
 * @date: 18/04/2020
 * @version: 1.0.0
 * @description: Strategy pattern.
 * As the View part always needs to extract some String data based on Profile or Title of the Media, the function
 * of extracting the related info of the Media Library is implemented following a strategy pattern, to make the
 * code more clean, and maintainable.
 */
public interface MediaInfoExtractor {

    /**
     *
     * @param token the token passed in the method to get according to which the Data should be provided.
     * @param dataSets pass in a DataSet[] to get the information
     * @return List<String> the reassembled info data structure
     */
    List<String> getMediaInfo(String token, DataSet...dataSets);
}
