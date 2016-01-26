package cz.muni.fi.pv256.movio.uco396110.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.IOException;

import cz.muni.fi.pv256.movio.uco396110.FilmAdapter;
import cz.muni.fi.pv256.movio.uco396110.FilmsStorage;
import cz.muni.fi.pv256.movio.uco396110.R;
import cz.muni.fi.pv256.movio.uco396110.service.FilmsService;
import cz.muni.fi.pv256.movio.uco396110.service.TheMovieDbFilmsServiceImpl;

public class FilmsFragment extends Fragment {
    private FilmAdapter mAdapter;
    private Activity mActivity;
    private FilmsLoaderTask mLoader;
    private OnFilmSelectedListener mCallback;

    public interface OnFilmSelectedListener {
        void onFilmSelected(int position);
    }

    // on pre-Marshmallow devices onAttach(Context context) is not called because it was added in API 23
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;

        try {
            mCallback = (OnFilmSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFilmSelectedListener");
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        try {
//            mCallback = (OnFilmSelectedListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " must implement OnFilmSelectedListener");
//        }
//    }

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
                mCallback.onFilmSelected(position);
            }
        });

        if (mAdapter.getCount() == 0) {
            // start loading
            mLoader = new FilmsLoaderTask(new TheMovieDbFilmsServiceImpl());
            mLoader.execute();
        }

        return view;
    }

    @Override
    public void onDetach() {
        // cancel the loader if it is running
        if(mLoader != null) {
            mLoader.cancel(true);
        }

        mActivity = null;
        super.onDetach();
    }

    private class FilmsLoaderTask extends AsyncTask<String, Void, String> {

        private FilmsService mFilmsService;

        public FilmsLoaderTask(FilmsService filmsService) {
            mFilmsService = filmsService;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                mFilmsService.Update();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(mActivity != null) {
                // notify the adapter
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
