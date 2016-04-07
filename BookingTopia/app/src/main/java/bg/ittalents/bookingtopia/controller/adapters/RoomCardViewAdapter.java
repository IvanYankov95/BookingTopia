//package bg.ittalents.bookingtopia.controller.adapters;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import bg.ittalents.bookingtopia.R;
//
///**
// * Created by Preshlen on 4/7/2016.
// */
//public class RoomCardViewAdapter extends RecyclerView.Adapter<HotelsCardViewAdapter.CustomViewHolder> {
//
//
//
//
//    protected class CustomViewHolder extends RecyclerView.ViewHolder {
//
//        private LinearLayout linearLayoutStars;
//        private RelativeLayout relativeLayout;
//
//        private TextView price;
//        private TextView maxGuests;
//        private ImageView extras;
//        private ImageView star2;
//        private ImageView star3;
//        private ImageView star4;
//        private ImageView star5;
//        private ImageView star6;
//        private ImageView star7;
//        private ImageView image;
//
//        private TextView rating;
//        private TextView startingRating;
//        private TextView finishRating;
//        private TextView description;
//
//        CustomViewHolder(View view) {
//            super(view);
//
//
//            relativeLayout = (RelativeLayout) view.findViewById(R.id.card_view_row_inner_layout_room);
//
//            image = (ImageView) view.findViewById(R.id.room_image);
//            //TODO fill room infovc
//            price = (TextView) view.findViewById(R.id.room_price);
//            maxGuests = (TextView) view.findViewById(R.id.max_guests);
//            extras = (ImageView) view.findViewById(R.id.extras);
//
//            startingRating = (TextView) view.findViewById(R.id.starting_rating);
//            finishRating = (TextView) view.findViewById(R.id.ending_rating);
//
//            rating = (TextView) view.findViewById(R.id.rating);
//            description = (TextView) view.findViewById(R.id.description);
//        }
//    }
//}
