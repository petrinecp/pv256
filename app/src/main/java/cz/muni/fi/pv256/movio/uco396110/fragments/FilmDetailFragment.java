package cz.muni.fi.pv256.movio.uco396110.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cz.muni.fi.pv256.movio.uco396110.R;
import cz.muni.fi.pv256.movio.uco396110.model.Film;

/**
 * Created by peter on  25.10. .
 */
public class FilmDetailFragment extends Fragment {
    public static final String ARG_FILM = "SelectedFilm";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.film_detail_fragment, container, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        Film film = getArguments().getParcelable(ARG_FILM);
        textView.setText(film.toString());
        return view;
    }
}
