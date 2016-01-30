package cz.muni.fi.pv256.movio.uco396110;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import cz.muni.fi.pv256.movio.uco396110.data.FilmManager;
import cz.muni.fi.pv256.movio.uco396110.fragments.FilmDetailFragment;

public class FilmActivity extends AppCompatActivity implements ActionsListener {

    @Override
    public void onFilmSelected(FilmManager filmManager, long id) {
        Parcelable selectedFilm = filmManager.getFilm(id);

        FilmDetailFragment filmDetailFragment = (FilmDetailFragment) getFragmentManager().findFragmentById(R.id.movie_detail_fragment);

        // If film detail frag is available, we're in two-pane layout...
        if (filmDetailFragment != null) {

            // Call a method in the FilmDetailFragment to update its content
            filmDetailFragment.updateFilmDetailView(selectedFilm);
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            FilmDetailFragment newFilmDetailFragment = new FilmDetailFragment();
            Bundle args = new Bundle();
            args.putParcelable(FilmDetailFragment.ARG_FILM, selectedFilm);
            newFilmDetailFragment.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFilmDetailFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public void onFilmsLoaded(FilmManager filmManager, Long id) {
        if (findViewById(R.id.filmsFragmentHolder) != null) {
            FilmDetailFragment filmDetailFragment = (FilmDetailFragment) getFragmentManager().findFragmentById(R.id.movie_detail_fragment);
            filmDetailFragment.updateFilmDetailView(filmManager.getFilm(id));
        }
    }
}
