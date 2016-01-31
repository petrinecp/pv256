package cz.muni.fi.pv256.movio.uco396110.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco396110.ActionsListener;
import cz.muni.fi.pv256.movio.uco396110.DownloadResultReceiver;
import cz.muni.fi.pv256.movio.uco396110.FilmAdapter;
import cz.muni.fi.pv256.movio.uco396110.FilmCategory;
import cz.muni.fi.pv256.movio.uco396110.FilmsStorage;
import cz.muni.fi.pv256.movio.uco396110.OnFilmSelectedListener;
import cz.muni.fi.pv256.movio.uco396110.R;
import cz.muni.fi.pv256.movio.uco396110.data.FilmManager;
import cz.muni.fi.pv256.movio.uco396110.model.Film;
import cz.muni.fi.pv256.movio.uco396110.service.FilmsIntentService;
import cz.muni.fi.pv256.movio.uco396110.service.TheMovieDbFilmsServiceImpl;

public class FilmsFragment extends Fragment implements DownloadResultReceiver.Receiver {
    private FilmAdapter mAdapter;
    private ActionsListener mCallback;
    private DownloadResultReceiver mReceiver;

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case FilmsIntentService.STATUS_RUNNING:
                break;
            case FilmsIntentService.STATUS_FINISHED:
                FilmsStorage filmsStorage = FilmsStorage.getInstance();

                ArrayList<Film> results = resultData.getParcelableArrayList(FilmsIntentService.FILMS_IN_THEATRE_ARG);
                filmsStorage.addFilms(results, FilmCategory.IN_THEATRES);

                results = resultData.getParcelableArrayList(FilmsIntentService.MOST_POPULAR_FILMS_ARG);
                FilmsStorage.getInstance().addFilms(results, FilmCategory.MOST_POPULAR);

                /* Update GridView with result */
                mAdapter.notifyDataSetChanged();

                if (getActivity().findViewById(R.id.fragment_container_large) != null) {
                    if (mAdapter.getCount() > 0) {
                        mCallback.onFilmsLoaded(new TheMovieDbFilmsServiceImpl(), mAdapter.getItemId(0));
                    }
                }
                break;
            case FilmsIntentService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                break;
        }
    }

    // on pre-Marshmallow devices onAttach(Context context) is not called because it was added in API 23
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (ActionsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFilmSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the root view of the fragment
        View view = inflater.inflate(R.layout.films_fragment, container, false);

        // initialize the adapter
        mAdapter = new FilmAdapter(getActivity(), FilmsStorage.getInstance().getFilms());

        // initialize the GridView
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setEmptyView(view.findViewById(android.R.id.empty));
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Send the event to the host activity
                mCallback.onFilmSelected(new TheMovieDbFilmsServiceImpl(), position);
            }
        });

        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);

        if (mAdapter.getCount() == 0) {
            /* Starting Download Service */
            Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), FilmsIntentService.class);
            intent.putExtra("receiver", mReceiver);
            getActivity().startService(intent);
        } else {
            mCallback.onFilmsLoaded(new TheMovieDbFilmsServiceImpl(), mAdapter.getItemId(0));
        }

        return view;
    }
}
