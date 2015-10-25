package cz.muni.fi.pv256.movio.uco396110.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio.uco396110.FilmAdapter;
import cz.muni.fi.pv256.movio.uco396110.R;
import cz.muni.fi.pv256.movio.uco396110.model.Film;

/**
 * Created by peter on  25.10. .
 */
public class FilmsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.films_fragment, container, false);

        List<Film> films = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            films.add(Film.createRandomFilm("Film " + i));
        }

        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        View emptyView = view.findViewById(R.id.gridEmptyLayout);
        gridView.setEmptyView(emptyView);
        FilmAdapter filmAdapter = new FilmAdapter(getActivity(), films);
        gridView.setAdapter(filmAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FilmDetailFragment filmDetailFragment = new FilmDetailFragment();
            Parcelable selectedItem = (Parcelable) parent.getItemAtPosition(position);
            Bundle args = new Bundle();
            args.putParcelable(FilmDetailFragment.ARG_FILM, selectedItem);
            filmDetailFragment.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, filmDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            }
        });

        return view;
    }
}
