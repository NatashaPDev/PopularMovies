package com.natashapetrenko.popularmovies;

/**
 * Created by petrenkonv on 09.11.2017.
 */

public class Trailer implements LoadingData{

    private String mId;
    private String mName;

    public Trailer(String id, String name) {
        mId = id;
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
