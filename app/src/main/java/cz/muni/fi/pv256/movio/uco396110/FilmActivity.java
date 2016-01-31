package cz.muni.fi.pv256.movio.uco396110;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import cz.muni.fi.pv256.movio.uco396110.data.FilmManager;
import cz.muni.fi.pv256.movio.uco396110.fragments.FilmDetailFragment;
import cz.muni.fi.pv256.movio.uco396110.sync.FilmSyncStatusObserver;

public abstract class FilmActivity extends AppCompatActivity implements ActionsListener, FilmSyncStatusObserver.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve your accounts
        final Account[] accounts = AccountManager.get(this).getAccountsByType(getString(R.string.sync_account_type));

        // Register for sync status changes
        final FilmSyncStatusObserver observer = new FilmSyncStatusObserver(accounts, getString(R.string.content_authority), this);
        final Object providerHandle = ContentResolver.addStatusChangeListener(
                ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE |
                        ContentResolver.SYNC_OBSERVER_TYPE_PENDING, observer);
        // Pass in the handle so the observer can unregister itself from events when finished.
        // You could optionally save this handle at the Activity level but I prefer to
        // encapsulate everything in the observer and let it handle everything
        observer.setProviderHandle(providerHandle);
    }

    @Override
    public void onFilmSelected(FilmManager filmManager, long id) {
        Parcelable selectedFilm = filmManager.getFilm(id);

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

    @Override
    public void onFilmsLoaded(FilmManager filmManager, Long id) {
        if (findViewById(R.id.filmsFragmentHolder) != null) {
            FilmDetailFragment filmDetailFragment = (FilmDetailFragment) getFragmentManager().findFragmentById(R.id.movie_detail_fragment);
            filmDetailFragment.updateFilmDetailView(filmManager.getFilm(id));
        }
    }
}
