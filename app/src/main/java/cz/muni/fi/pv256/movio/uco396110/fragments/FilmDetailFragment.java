package cz.muni.fi.pv256.movio.uco396110.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.format.DateTimeFormat;

import java.util.Locale;

import cz.muni.fi.pv256.movio.uco396110.R;
import cz.muni.fi.pv256.movio.uco396110.data.FilmManagerImpl;
import cz.muni.fi.pv256.movio.uco396110.model.Film;

public class FilmDetailFragment extends Fragment {
    public final static String ARG_FILM = "SelectedFilm";
    private Film mCurrentFilm = null;
    private FilmManagerImpl mFilmManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentFilm = savedInstanceState.getParcelable(ARG_FILM);
        }

        mFilmManager = new FilmManagerImpl(getActivity());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.film_detail_fragment, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentFilm != null) {
                    FloatingActionButton fab = (FloatingActionButton) v;

                    if (mCurrentFilm.getId() != null) {
                        mFilmManager.deleteFilm(mCurrentFilm);
                        fab.setImageResource(R.drawable.ic_add_white_48dp);
                    } else {
                        mFilmManager.createFilm(mCurrentFilm);
                        fab.setImageResource(R.drawable.clear);
                    }
                }
            }
        });

        return view;
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

        filmReleaseDateTextView.setText(film.getReleaseDate().toString(DateTimeFormat.mediumDate().withLocale(Locale.getDefault())));

        TextView filmOverviewTextView = (TextView) getView().findViewById(R.id.filmOverview);
        filmOverviewTextView.setText(film.getOverview());

        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        Film filmInDatabase = mFilmManager.getFilmByTitle(film.getTitle());
        if (filmInDatabase != null) {
            film.setId(filmInDatabase.getId());
            fab.setImageResource(R.drawable.clear);
        } else {
            fab.setImageResource(R.drawable.ic_add_white_48dp);
        }

        mCurrentFilm = film;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putParcelable(ARG_FILM, mCurrentFilm);
    }
}
