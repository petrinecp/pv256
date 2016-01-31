package cz.muni.fi.pv256.movio.uco396110;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import cz.muni.fi.pv256.movio.uco396110.fragments.FilmsFavoritesFragment;
import cz.muni.fi.pv256.movio.uco396110.fragments.FilmsFragment;

public class MainActivity extends FilmActivity {
    private static final String STATE_DISPLAY_MODE = "displayMode";
    private int mCheckedMenuItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.films_list);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (mCheckedMenuItemId != 0) {
            MenuItem menuItem = menu.findItem(mCheckedMenuItemId);
            menuItem.setChecked(true);
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
}
