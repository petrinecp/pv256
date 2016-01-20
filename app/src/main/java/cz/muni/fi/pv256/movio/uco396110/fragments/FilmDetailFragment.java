package cz.muni.fi.pv256.movio.uco396110.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cz.muni.fi.pv256.movio.uco396110.R;
import cz.muni.fi.pv256.movio.uco396110.model.Film;
import cz.muni.fi.pv256.movio.uco396110.service.FilmsService;
import cz.muni.fi.pv256.movio.uco396110.service.TheMovieDbFilmsServiceImpl;

public class FilmDetailFragment extends Fragment {
    public final static String ARG_FILM = "SelectedFilm";
    private Parcelable mCurrentFilm = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentFilm = savedInstanceState.getParcelable(ARG_FILM);
        }

//        FilmsService filmsService = new TheMovieDbFilmsServiceImpl();
//        if (mCurrentFilm == null && filmsService.getFilmsCount() > 0) {
//            mCurrentFilm = FilmsStore.sFilms[0].getFilm();
//        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.film_detail_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateFilmDetailView(args.getParcelable(ARG_FILM));
        } else if (mCurrentFilm != null) {
            // Set article based on saved instance state defined during onCreateView
            updateFilmDetailView(mCurrentFilm);
        }
    }

    public void updateFilmDetailView(Parcelable selectedFilm) {
        Film film = (Film) selectedFilm;
        Context context = getActivity().getApplicationContext();

        ImageView backdropImageView = (ImageView) getView().findViewById(R.id.backdrop);
        Picasso.with(context).load(film.getBackdropPath()).fit().centerCrop().into(backdropImageView);

        ImageView posterImageView = (ImageView) getView().findViewById(R.id.posterDetail);
        Picasso.with(context).load(film.getCoverPath()).into(posterImageView);

        TextView filmTitleTextView = (TextView) getView().findViewById(R.id.filmTitle);
        filmTitleTextView.setText(film.getTitle());

        TextView filmReleaseDateTextView = (TextView) getView().findViewById(R.id.releaseDate);
        filmReleaseDateTextView.setText(String.valueOf(film.getLocalizedReleaseDate()));

        mCurrentFilm = selectedFilm;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putParcelable(ARG_FILM, mCurrentFilm);
    }
}
