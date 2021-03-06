package com.nickwelna.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public String getShortDescription() {

        return shortDescription;

    }

    public String getDescription() {

        return description;

    }

    public String getVideoURL() {

        return videoURL;

    }

    public int getId() {

        return id;

    }

    public String getThumbnailURL() {

        return thumbnailURL;

    }

    @Override
    public int describeContents() {

        return 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);

    }

    public Step() {

    }

    protected Step(Parcel in) {

        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();

    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {

        @Override
        public Step createFromParcel(Parcel source) {

            return new Step(source);

        }

        @Override
        public Step[] newArray(int size) {

            return new Step[size];

        }

    };

}
