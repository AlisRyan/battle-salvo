package cs3500.pa04.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


/**
 * The json for a round of coordinates
 *
 * @param volley list of the appropriate coord adapters
 */
public record CoordinatesJson(
    @JsonProperty("coordinates") List<CoordAdapter> volley
) {

}
