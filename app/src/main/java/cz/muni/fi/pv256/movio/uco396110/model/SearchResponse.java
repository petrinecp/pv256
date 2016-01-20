package cz.muni.fi.pv256.movio.uco396110.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by peter on  20.1. .
 */
public class SearchResponse {
    @SerializedName("results")
    private List<Film> results;

    public List<Film> getResults() {
        return results;
    }
}
