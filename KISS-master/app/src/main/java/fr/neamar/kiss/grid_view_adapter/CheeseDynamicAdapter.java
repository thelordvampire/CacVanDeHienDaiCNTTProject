package fr.neamar.kiss.grid_view_adapter;

/**
 * Author: alex askerov
 * Date: 9/9/13
 * Time: 10:52 PM
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import fr.neamar.kiss.R;
import fr.neamar.kiss.grid_view.BaseDynamicGridAdapter;

import java.util.List;
import fr.neamar.kiss.model.App;

/**
 * Author: alex askerov
 * Date: 9/7/13
 * Time: 10:56 PM
 */
public class CheeseDynamicAdapter extends BaseDynamicGridAdapter {

    private List<App> listApp;

//    public CheeseDynamicAdapter(Context context, List<?> items, int columnCount) {
//        super(context, items, columnCount);
//
//        this.listApp = (List<App>)items;
//    }

    public CheeseDynamicAdapter(Context context, List<App> items, int columnCount) {
        super(context, items, columnCount);

        this.listApp = (List<App>)items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheeseViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid, null);
            holder = new CheeseViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CheeseViewHolder) convertView.getTag();
        }

//        App app = listApp.get(position);
//        holder.build(app);
//        holder.build(getItem(position).toString());
        holder.build(getItem(position));
        return convertView;
    }

    private class CheeseViewHolder {
        private TextView titleText;
        private ImageView image;

        private CheeseViewHolder(View view) {
            titleText = (TextView) view.findViewById(R.id.item_title);
            image = (ImageView) view.findViewById(R.id.item_img);
        }

        void build(String title) {
            titleText.setText(title);
            image.setImageResource(R.drawable.ic_launcher);
        }

        void build(App app) {
            titleText.setText(app.getName());
            image.setImageDrawable(app.getIcon());
        }

    }
}