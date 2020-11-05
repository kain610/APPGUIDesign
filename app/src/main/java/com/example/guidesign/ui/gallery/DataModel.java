package com.example.guidesign.ui.gallery;

public class DataModel {
    String name;
    String act;
    int img;
    String feature;

    public DataModel(String name, String act, String feature , int img ) {
        this.name=name;

        this.img=img;
        this.feature=feature;

    }



    public String getName() {
        return name;
    }

    public String getact() {
        return act;
    }
    public int getimg() {
        return img;
    }





    public String getFeature() {
        return feature;
    }
}
