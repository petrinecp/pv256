package cz.muni.fi.pv256.movio.uco396110.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Films {
    @SerializedName("results")
    private ArrayList<Film> results;

    public ArrayList<Film> getResults() {
        return results;
    }
}
