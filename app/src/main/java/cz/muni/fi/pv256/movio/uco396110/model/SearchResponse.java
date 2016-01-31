package cz.muni.fi.pv256.movio.uco396110.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
    @SerializedName("results")
    private List<Film> results;

    public List<Film> getResults() {
        return results;
    }
}
