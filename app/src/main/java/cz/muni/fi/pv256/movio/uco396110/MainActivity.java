package cz.muni.fi.pv256.movio.uco396110;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import cz.muni.fi.pv256.movio.uco396110.fragments.FilmsFavoritesFragment;
import cz.muni.fi.pv256.movio.uco396110.fragments.FilmsFragment;
import cz.muni.fi.pv256.movio.uco396110.sync.UpdaterSyncAdapter;

public class MainActivity extends FilmActivity {
    private static final String STATE_DISPLAY_MODE = "displayMode";
    private int mCheckedMenuItemId;
    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.films_list);

        UpdaterSyncAdapter.initializeSyncAdapter(this);
        FilmsFragment filmsFragment = new FilmsFragment();
        filmsFragment.setArguments(getIntent().getExtras());

        if (savedInstanceState != null) {
            mCheckedMenuItemId = savedInstanceState.getInt(STATE_DISPLAY_MODE);
        }

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, filmsFragment).commit();
        } else {
            getFragmentManager().beginTransaction()
                    .add(R.id.filmsFragmentHolder, filmsFragment).commit();
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
        mOptionsMenu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (mCheckedMenuItemId != 0) {
            MenuItem menuItem = menu.findItem(mCheckedMenuItemId);
            setTitle(menuItem.getTitle());
            menuItem.setChecked(true);
        } else {
            setTitle(R.string.action_discover);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        mCheckedMenuItemId = item.getItemId();
        item.setChecked(true);
        Resources resources = getResources();
        Fragment fragment = null;
        switch (mCheckedMenuItemId) {
            case R.id.action_sync:
                setRefreshActionButtonState(true);
                final Handler handler = new Handler();
                final Context context = this;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UpdaterSyncAdapter.syncImmediately(context);
                    }
                }, 2000);
                return true;
            case R.id.action_favorites:
                setTitle(resources.getString(R.string.action_favorites));
                fragment = new FilmsFavoritesFragment();
                break;
            case R.id.action_discover:
                setTitle(resources.getString(R.string.action_discover));
                fragment = new FilmsFragment();
                break;
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (findViewById(R.id.fragment_container) != null) {
            transaction.replace(R.id.fragment_container, fragment);
        } else {
            transaction.replace(R.id.filmsFragmentHolder, fragment);
        }

        // Commit the transaction
        transaction.commit();

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_DISPLAY_MODE, mCheckedMenuItemId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSyncsStarted() {

    }

    @Override
    public void onSyncsFinished() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setRefreshActionButtonState(false);
            }
        });
    }

    public void setRefreshActionButtonState(final boolean refreshing) {
        if (mOptionsMenu != null) {
            final MenuItem refreshItem = mOptionsMenu
                    .findItem(R.id.action_sync);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
                } else {
                    refreshItem.setActionView(null);
                }
            }
        }
    }
}
