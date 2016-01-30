package cz.muni.fi.pv256.movio.uco396110;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import cz.muni.fi.pv256.movio.uco396110.data.FilmContract.FilmEntry;

public class FilmsCursorAdapter extends CursorAdapter {

    public FilmsCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.grid_single, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
//        if (convertView == null) {
//            if(BuildConfig.LOGGING_ENABLED) {
//                Log.i("", "inflate radku " + position);
//            }
//            ViewHolder holder = new ViewHolder();
//            holder.imageView = (ImageView) view.findViewById(R.id.grid_image);
//            view.setTag(holder);
//        } else {
//            if(BuildConfig.LOGGING_ENABLED) {
//                Log.i("", "recyklace radku " + position);
//            }
//        }
//        ViewHolder holder = (ViewHolder) view.getTag();
        ImageView posterImageView = (ImageView) view.findViewById(R.id.grid_image);
        String posterUrl = cursor.getString(cursor.getColumnIndexOrThrow(FilmEntry.COLUMN_COVER_PATH));
        Picasso.with(context).load(posterUrl).into(posterImageView);
    }

    private static class ViewHolder {
        ImageView imageView;
    }
}
