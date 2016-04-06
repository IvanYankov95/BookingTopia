//package bg.ittalents.bookingtopia;
//
///**
// * Created by Preshlen on 4/6/2016.
// */
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CompoundButton;
//
//
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
//public class HotelsCardViewAdapter extends RecyclerView.Adapter<HotelsCardViewAdapter.CustomViewHolder> {
//
//
//    private class CustomViewHolder{
//        private TextView eventName;
//        private TextView eventLocation;
//        private TextView eventDate;
//        private CheckBox isInFavs;
//        CustomViewHolder(View v){
//            eventName = (TextView) v.findViewById(R.id.event_list_name);
//            eventDate = (TextView) v.findViewById(R.id.event_list_date);
//            eventLocation = (TextView) v.findViewById(R.id.event_list_location);
//            isInFavs = (CheckBox) v.findViewById(R.id.is_in_fav);
//        }
//    }
//
//    private Context context;
//    private ArrayList<Event> events;
//
//    //    TODO Move eventDataSource away
//    private EventDao eventDataSource;
//    private List<Long> listEventIds;
//
//
//    public AdapterShowEvents(Context context, ArrayList<Event> events){
//        super(context, R.layout.event_list_element, events);
//
//        SharedPreferences getUserId = ((Activity) context).getPreferences(Context.MODE_PRIVATE);
//        userId = getUserId.getLong("user_id", -1);
//
//        eventDataSource = new EventDataSource(getContext());
//        ((EventDataSource)eventDataSource).open();
//        this.context = context;
//        this.events = events;
//        listEventIds = new ArrayList<>();
//        listEventIds = eventDataSource.selectAllFavEventsIdsForUserId(userId);
//        ((EventDataSource)eventDataSource).close();
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View row = convertView;
//        CustomViewHolder holder= null;
//        if (row == null){
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            row = inflater.inflate(R.layout.event_list_element,parent,false);
//            holder = new CustomViewHolder(row);
//            row.setTag(holder);
//        }else{
//            holder = (CustomViewHolder) row.getTag();
//        }
//
//        if (listEventIds.contains(events.get(position).getEventId())){
//            row.setBackgroundResource(R.color.yellow);
//            holder.isInFavs.setChecked(true);
//        }
//        holder.eventName.setText(events.get(position).getEventName());
//        holder.eventDate.setText(DateFormater.from_yyyyMMdd_To_dMMMyyyy(events.get(position).getEventDate()));
//        holder.eventLocation.setText(events.get(position).getEventLocation());
//        return row;
//    }
//}