package cz.muni.fi.pv256.movio.uco396110;

import android.test.AndroidTestCase;

import cz.muni.fi.pv256.movio.uco396110.data.FilmContract.FilmEntry;
import cz.muni.fi.pv256.movio.uco396110.data.FilmManagerImpl;
import cz.muni.fi.pv256.movio.uco396110.model.Film;

public class TestFilmManager extends AndroidTestCase {
    private static final String TAG = TestFilmManager.class.getSimpleName();

    private FilmManagerImpl mManager;

    @Override
    protected void setUp() throws Exception {
        mManager = new FilmManagerImpl(mContext);
    }

    @Override
    public void tearDown() throws Exception {
        mContext.getContentResolver().delete(
                FilmEntry.CONTENT_URI,
                null,
                null
        );
    }

    public void testGetFilmByTitle() {
        Film expectedFilm = new Film("Se7en", "1995-09-22", "Best Movie Ever", "http://");
        mManager.createFilm(expectedFilm);

        Film returned = mManager.getFilmByTitle("Se7en");
        assertEquals(expectedFilm, returned);
    }

    private void assertEquals(Film expected, Film actual) {
        if (expected != null && actual != null) {
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getReleaseDate(), actual.getReleaseDate());
            assertEquals(expected.getOverview(), actual.getOverview());
            assertEquals(expected.getCoverPath(), actual.getCoverPath());
            assertEquals(expected.getBackdropPath(), actual.getBackdropPath());
        }
    }
}
