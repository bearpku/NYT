package com.codepath.richard_huang.nyt.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by richard_huang on 4/3/17.
 */

class Byline {

    @SerializedName("person")
    @Expose
    private List<Person> person = null;
    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("organization")
    @Expose
    private String organization;

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

}
