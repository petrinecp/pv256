package cz.muni.fi.pv256.movio.uco396110;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleAdapter;

import java.util.List;

public class FilmAdapter extends BaseAdapter implements StickyGridHeadersSimpleAdapter {

    private Context mContext;
    private List<FilmAdapterData> mData;
    private LayoutInflater mInflater;

    public FilmAdapter(Context context, List<FilmAdapterData> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position).getFilm();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public long getHeaderId(int position) {
        return mData.get(position).getCategory().ordinal();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.header, parent, false);
            holder = new HeaderViewHolder();
            holder.textView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        Resources resources = parent.getContext().getResources();
        FilmAdapterData item = mData.get(position);
        String headerText;
        switch (item.getCategory()) {
            case IN_THEATRES:
                headerText = resources.getString(R.string.header_in_theatres);
                break;
            default:
            case MOST_POPULAR:
                headerText = resources.getString(R.string.header_most_popular);
                break;
        }

        holder.textView.setText(headerText);

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if(BuildConfig.LOGGING_ENABLED) {
                Log.i("", "inflate radku " + position);
            }
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_single, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.grid_image);
            convertView.setTag(holder);
        } else {
            if(BuildConfig.LOGGING_ENABLED) {
                Log.i("", "recyklace radku " + position);
            }
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        String posterUrl = mData.get(position).getFilm().getCoverPath();
        Picasso.with(parent.getContext()).load(posterUrl).into(holder.imageView);
        return convertView;
    }

    protected class HeaderViewHolder {
        public TextView textView;
    }
}
