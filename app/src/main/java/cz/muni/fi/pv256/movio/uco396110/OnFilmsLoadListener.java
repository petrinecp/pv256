package cz.muni.fi.pv256.movio.uco396110;

import cz.muni.fi.pv256.movio.uco396110.data.FilmManager;

public interface OnFilmsLoadListener {
    void onFilmsLoaded(FilmManager filmManager, Long id);
}
