package cz.muni.fi.pv256.movio.uco396110.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Arrays;

import cz.muni.fi.pv256.movio.uco396110.FilmAdapter;
import cz.muni.fi.pv256.movio.uco396110.FilmsStore;
import cz.muni.fi.pv256.movio.uco396110.R;

/**
 * Created by peter on  25.10. .
 */
public class FilmsFragment extends Fragment {
    OnFilmSelectedListener mCallback;

    public interface OnFilmSelectedListener {
        void onFilmSelected(int position);
    }

    // on pre-Marshmallow devices onAttach(Context context) is not called because it was added in API 23
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnFilmSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFilmSelectedListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnFilmSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFilmSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.films_fragment, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setEmptyView(view.findViewById(android.R.id.empty));
        FilmAdapter filmAdapter = new FilmAdapter(getActivity().getApplicationContext(), Arrays.asList(FilmsStore.sFilms));
        gridView.setAdapter(filmAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Send the event to the host activity
                mCallback.onFilmSelected(position);
            }
        });

        return view;
    }
}
