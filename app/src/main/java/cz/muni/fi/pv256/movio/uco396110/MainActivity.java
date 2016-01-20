package cz.muni.fi.pv256.movio.uco396110;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.lang.ref.WeakReference;

import cz.muni.fi.pv256.movio.uco396110.fragments.FilmDetailFragment;
import cz.muni.fi.pv256.movio.uco396110.fragments.FilmsFragment;
import cz.muni.fi.pv256.movio.uco396110.service.FilmsService;
import cz.muni.fi.pv256.movio.uco396110.service.TheMovieDbFilmsServiceImpl;

public class MainActivity extends AppCompatActivity implements FilmsFragment.OnFilmSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.films_list);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            FilmsFragment filmsFragment = new FilmsFragment();
            filmsFragment.setArguments(getIntent().getExtras());

            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, filmsFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();

        if(fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFilmSelected(int position) {
        FilmsService filmsService = new TheMovieDbFilmsServiceImpl();
        Parcelable selectedFilm = filmsService.getFilm(position);

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

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        if (mDownloadFilmsTask != null) {
//            mDownloadFilmsTask.cancel(true);
//        }
//    }
//
//    public void startTask() {
//        if (mDownloadFilmsTask == null) {
//            FilmsService filmsService = new TheMovieDbFilmsServiceImpl();
//            mDownloadFilmsTask = new DownloadFilmsTask(MainActivity.this, filmsService);
//            mDownloadFilmsTask.execute();
//        }
//    }
//
//    public void onTaskFinished() {
//        mDownloadFilmsTask = null;
//    }
//
//    private static class DownloadFilmsTask extends AsyncTask<String, Void, String> {
//
//        private final WeakReference<MainActivity> mActivityWeakReference;
//        private FilmsService mFilmsService;
//
//        public DownloadFilmsTask(MainActivity mainActivity, FilmsService filmsService) {
//            mActivityWeakReference = new WeakReference<>(mainActivity);
//            mFilmsService = filmsService;
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                mFilmsService.Update();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            MainActivity activity = mActivityWeakReference.get();
//            if (activity == null) {
//                return;
//            }
//            activity.onTaskFinished();
//        }
//    }
}
