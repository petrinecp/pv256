package cz.muni.fi.pv256.movio.uco396110.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by peter on  18.10. .
 */
public class Film implements Parcelable {
    long releaseDate;
    String coverPath;
    String title;

//    public Film(String title, long releaseDate, String coverPath) {
//        this.releaseDate = releaseDate;
//        this.coverPath = coverPath;
//        this.title = title;
//    }

    public Film(String title, String dateString, String coverPath) {
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");

        long milliseconds;
        try {
            Date d = f.parse(dateString);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            milliseconds = 0;
        }

        this.title = title;
        this.releaseDate = milliseconds;
        this.coverPath = coverPath;
//        return new Film(title, milliseconds, coverPath);
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }
//    public static Film createRandomFilm(String title) {
//        GregorianCalendar gc = new GregorianCalendar();
//        int year = randBetween(1900, 2010);
//        gc.set(gc.YEAR, year);
//        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
//        gc.set(gc.DAY_OF_YEAR, dayOfYear);
//
//        return new Film(gc.getTimeInMillis(), "", title);
//    }

//    private static int randBetween(int start, int end) {
//        return start + (int)Math.round(Math.random() * (end - start));
//    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.releaseDate);
        dest.writeString(this.coverPath);
        dest.writeString(this.title);
    }

    protected Film(Parcel in) {
        this.releaseDate = in.readLong();
        this.coverPath = in.readString();
        this.title = in.readString();
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
