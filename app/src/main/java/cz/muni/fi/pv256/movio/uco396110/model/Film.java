package cz.muni.fi.pv256.movio.uco396110.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Film implements Parcelable {
    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
    private long mReleaseDate;

    @SerializedName("original_title")
    private String mTitle;

    @SerializedName("release_date")
    private String mReleaseDateString;

    private String mLocalizedReleaseDate;

    @SerializedName("poster_path")
    private String mCoverPath;

    @SerializedName("backdrop_path")
    private String mBackdropPath;

    public Film(String title, String dateString, String coverPath) {
        mTitle = title;
        mReleaseDateString = dateString;
        mCoverPath = coverPath;
    }

    public long getReleaseDate() {
        if (mReleaseDate == 0) {
            SimpleDateFormat f = new SimpleDateFormat(DATE_FORMAT_STRING);
            long milliseconds;
            try {
                Date d = f.parse(mReleaseDateString);
                milliseconds = d.getTime();
            } catch (ParseException e) {
                milliseconds = 0;
            }
            mReleaseDate = milliseconds;
        }

        return mReleaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getLocalizedReleaseDate() {
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());
        String localPattern  = ((SimpleDateFormat)formatter).toLocalizedPattern();
        Date date = new Date(getReleaseDate());
        SimpleDateFormat df = new SimpleDateFormat(localPattern);
        return df.format(date);
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

    @Override
    public String toString() {
        return mTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mReleaseDate);
        dest.writeString(this.mCoverPath);
        dest.writeString(this.mTitle);
    }

    protected Film(Parcel in) {
        this.mReleaseDate = in.readLong();
        this.mCoverPath = in.readString();
        this.mTitle = in.readString();
    }

    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}
