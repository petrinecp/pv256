package cz.muni.fi.pv256.movio.uco396110;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cz.muni.fi.pv256.movio.uco396110.model.Film;

/**
 * Created by peter on  18.10. .
 */
public class FilmAdapter extends BaseAdapter {

    private Context mContext;
    private List<Film> mData;

    public FilmAdapter(Context context, List<Film> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Log.i("", "inflate radku " + position);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_single, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.grid_image);
            holder.textView = (TextView) convertView.findViewById(R.id.grid_text);
            convertView.setTag(holder);
        } else {
            Log.i("","recyklace radku " + position);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.textView.setText(mData.get(position).toString());
        return convertView;
    }
}
