package bg.ittalents.bookingtopia.controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import bg.ittalents.bookingtopia.R;

/**
 * Created by Preshlen on 4/7/2016.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.CustomViewHolder> {

    private Activity activity;
    private ArrayList<byte[]> images = new ArrayList<>();

    public ImageAdapter(Activity activity, ArrayList<byte[]> dataSource) {
        this.activity = activity;
        this.images = dataSource;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.image_rec_view_row, parent, false);
        CustomViewHolder holder = new CustomViewHolder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        Log.e("image pos " , "" +position);
        byte[] image = images.get(position);
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.image.setImageBitmap(bmp);

    }

    @Override
    public int getItemCount() {

        return images.size();
    }

    public void notifyAdapter() {
        notifyDataSetChanged();
    }

    protected class CustomViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearLayoutStars;
        private ImageView image;

        CustomViewHolder(View view) {
            super(view);

            linearLayoutStars = (LinearLayout) view.findViewById(R.id.image_rec_view_row_layout);

            image = (ImageView) view.findViewById(R.id.hotel_image_rec_view);

        }
    }


}


