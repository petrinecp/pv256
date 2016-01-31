package cz.muni.fi.pv256.movio.uco396110.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Film implements Parcelable {
    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
    private DateTime mReleaseDate;

    private transient Long id;

    @SerializedName("id")
    private Long mRemoteId;

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

    public Film(String title, String dateString, String overview, String coverPath) {
        mTitle = title;
        mReleaseDateString = dateString;
        mOverview = overview;
        mCoverPath = coverPath;
    }

    public boolean updateState(Film updatedFilm) {
        boolean hasBeenUpdated = false;

        if (hasBeenUpdated |= !mTitle.equals(updatedFilm.getTitle())) {
            mTitle = updatedFilm.getTitle();
        }
        if (hasBeenUpdated |= !getReleaseDate().equals(updatedFilm.getReleaseDate())) {
            mReleaseDate = updatedFilm.getReleaseDate();
        }
        if (hasBeenUpdated |= !mOverview.equals(updatedFilm.getOverview())) {
            mOverview = updatedFilm.getOverview();
        }
        if (hasBeenUpdated |= !getCoverPath().equals(updatedFilm.getCoverPath())) {
            mCoverPath = updatedFilm.getCoverPath();
        }
        if (hasBeenUpdated |= !getBackdropPath().equals(updatedFilm.getBackdropPath())) {
            mBackdropPath = updatedFilm.getBackdropPath();
        }

        return hasBeenUpdated;
    }

    //<editor-fold desc="Getters and Setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRemoteId() {
        return mRemoteId;
    }

    public void setRemoteId(Long remoteId) {
        mRemoteId = remoteId;
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
        dest.writeLong(id);
        dest.writeLong(mRemoteId);
        dest.writeString(mTitle);
        dest.writeLong(mReleaseDate.getMillis());
        dest.writeString(mOverview);
        dest.writeString(mCoverPath);
        dest.writeString(mBackdropPath);
    }

    protected Film(Parcel in) {
        id = in.readLong();
        mRemoteId = in.readLong();
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

    //<editor-fold desc="Equals, HashCode & ToString">
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        return !(id != null ? !id.equals(film.id) : film.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return mTitle;
    }
    //</editor-fold>
}
