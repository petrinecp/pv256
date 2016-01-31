package cz.muni.fi.pv256.movio.uco396110.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.GridView;

import cz.muni.fi.pv256.movio.uco396110.ActionsListener;
import cz.muni.fi.pv256.movio.uco396110.FilmsCursorAdapter;
import cz.muni.fi.pv256.movio.uco396110.R;
import cz.muni.fi.pv256.movio.uco396110.data.FilmContract.FilmEntry;
import cz.muni.fi.pv256.movio.uco396110.data.FilmManagerImpl;

public class FilmsFavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private CursorAdapter mAdapter;
    private ActionsListener mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (ActionsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ActionsListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.films_favorites_fragment, container, false);

        String[] from = new String[] { FilmEntry.COLUMN_COVER_PATH };
        int[] to = new int[] { R.id.grid_image };

        getLoaderManager().initLoader(0, null, this);
        mAdapter = new FilmsCursorAdapter(container.getContext(), null, 0);
        GridView gridView = (GridView) view.findViewById(R.id.gridViewFavorites);
        gridView.setEmptyView(view.findViewById(android.R.id.empty));
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) mAdapter.getItem(position);
                mCallback.onFilmSelected(new FilmManagerImpl(parent.getContext()), cursor.getLong(cursor.getColumnIndex(FilmEntry._ID)));
            }
        });

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {FilmEntry._ID, FilmEntry.COLUMN_COVER_PATH};
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                FilmEntry.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        if (data.getCount() > 0) {
            if (data.moveToFirst()) {
                Long filmId = data.getLong(data.getColumnIndex(FilmEntry._ID));
                mCallback.onFilmsLoaded(new FilmManagerImpl(loader.getContext()), filmId);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
