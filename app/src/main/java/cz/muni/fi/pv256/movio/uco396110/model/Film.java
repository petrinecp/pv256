package cz.muni.fi.pv256.movio.uco396110.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Film implements Parcelable {
    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
    private DateTime mReleaseDate;

    private transient Long id;

    @SerializedName("original_title")
    private String mTitle;

    @SerializedName("release_date")
    private String mReleaseDateString;

    @SerializedName("poster_path")
    private String mCoverPath;

    @SerializedName("backdrop_path")
    private String mBackdropPath;

    @SerializedName("overview")
    private String mOverview;

    public Film() {}

    public Film(String title, String dateString, String coverPath) {
        mTitle = title;
        mReleaseDateString = dateString;
        mCoverPath = coverPath;
    }

    //<editor-fold desc="Getters and Setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public DateTime getReleaseDate() {
        if (mReleaseDate == null) {
            mReleaseDate = DateTime.parse(mReleaseDateString, DateTimeFormat.forPattern(DATE_FORMAT_STRING));
        }

        return mReleaseDate;
    }

    public void setReleaseDate(DateTime releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getCoverPath() {
        return mCoverPath;
    }

    public void setCoverPath(String coverPath) {
        mCoverPath = coverPath;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }
    //</editor-fold>

    //<editor-fold desc="Parcelable">
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeLong(mReleaseDate.getMillis());
        dest.writeString(mOverview);
        dest.writeString(mCoverPath);
        dest.writeString(mBackdropPath);
    }

    protected Film(Parcel in) {
        mTitle = in.readString();
        mReleaseDate = new DateTime(in.readLong());
        mOverview = in.readString();
        mCoverPath = in.readString();
        mBackdropPath = in.readString();
    }

    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
    //</editor-fold>

    @Override
    public String toString() {
        return mTitle;
    }
}
