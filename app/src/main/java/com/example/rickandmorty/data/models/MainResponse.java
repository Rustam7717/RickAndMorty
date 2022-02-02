
package com.example.rickandmorty.data.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainResponse {

    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("results")
    @Expose
    private List<CustomCharacter> results = null;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<CustomCharacter> getResults() {
        return results;
    }

    public void setResults(List<CustomCharacter> results) {
        this.results = results;
    }

}
