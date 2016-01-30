package cz.muni.fi.pv256.movio.uco396110.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class FilmContract {

    public static final String CONTENT_AUTHORITY = "cz.muni.fi.pv256.movio.uco396110.data";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FILM = "film";

    public static final String DATE_FORMAT = "yyyyMMddHHmm";

    /**
     * Converts Date class to a string representation, used for easy comparison and database
     * lookup.
     *
     * @param date The input date
     * @return a DB-friendly representation of the date, using the format defined in DATE_FORMAT.
     */
    public static String getDbDateString(DateTime date) {
        return date.toString(DATE_FORMAT);
    }

    /**
     * Converts a dateText to a long Unix time representation
     *
     * @param dateText the input date string
     * @return the Date object
     */
    public static DateTime getDateFromDb(String dateText) {
        return DateTime.parse(dateText, DateTimeFormat.forPattern(DATE_FORMAT).withOffsetParsed());
    }

    public static final class FilmEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FILM).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_FILM;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_FILM;

        public static final String TABLE_NAME = "films";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE_TEXT = "release_date";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_COVER_PATH = "cover_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";

        public static Uri buildFilmUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
