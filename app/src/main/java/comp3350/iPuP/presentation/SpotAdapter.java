package comp3350.iPuP.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.iPuP.R;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;

public class SpotAdapter extends ArrayAdapter<ParkingSpot>
{
    Activity activity;

    SpotAdapter(Context context, ArrayList<ParkingSpot> spots)
    {
        super(context, 0, spots);
        activity = (Activity)context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ParkingSpot spot = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tv = convertView.findViewById(R.id.textViewRow1);
        tv.setText(String.format(convertView.getResources().getString(R.string.hostview_Address), spot.getAddress()));
        tv = convertView.findViewById(R.id.textViewRow2);
        tv.setText(String.format(convertView.getResources().getString(R.string.hostview_Rate), spot.getRate()));

        if (position % 2 == 0)
            convertView.setBackgroundResource(R.color.colorWhite);
        else
            convertView.setBackgroundResource(R.color.colorLightGrey);

        Button b = convertView.findViewById(R.id.buttonListItem);
        b.setText(convertView.getResources().getString(R.string.modify));
        b.setTag(position);
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = (int) view.getTag();
                ParkingSpot spot = getItem(position);

                Intent hostModifyIntent = new Intent(view.getContext(), HostModifyActivity.class);
                hostModifyIntent.putExtra("spotID", spot.getSpotID());
                view.getContext().startActivity(hostModifyIntent);
            }
        });

        LinearLayout lo = convertView.findViewById(R.id.list_item_layout);
        lo.setTag(position);
        lo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = (int) view.getTag();
                ParkingSpot spot = getItem(position);

                Intent hostViewDayIntent = new Intent(view.getContext(), HostViewDayActivity.class);
                hostViewDayIntent.putExtra("spotID", spot.getSpotID());
                activity.startActivityForResult(hostViewDayIntent,0);
            }
        });

        return convertView;
    }
}
