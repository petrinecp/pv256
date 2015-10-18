package cz.muni.fi.pv256.movio.uco396110.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by peter on  18.10. .
 */
public class Film {
    long releaseDate;
    String coverPath;
    String title;

    public Film(long releaseDate, String coverPath, String title) {
        this.releaseDate = releaseDate;
        this.coverPath = coverPath;
        this.title = title;
    }

    public static Film createFilm(String dateString, String coverPath, String title) throws ParseException {
//        SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
//        Date d = f.parse(dateString);
//        long milliseconds = d.getTime();
        return null;
    }

    public static Film createRandomFilm(String title) {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1900, 2010);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        return new Film(gc.getTimeInMillis(), "", title);
    }

    private static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    @Override
    public String toString() {
        return title;
    }
}
