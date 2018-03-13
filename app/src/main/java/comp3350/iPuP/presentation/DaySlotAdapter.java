package comp3350.iPuP.presentation;

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
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;

public class DaySlotAdapter extends ArrayAdapter<TimeSlot>
{
    DateFormatter df;
    AccessParkingSpots dataAccess;

    DaySlotAdapter(Context context, ArrayList<TimeSlot> slots)
    {
        super(context, 0, slots);
        df = new DateFormatter();
        dataAccess = new AccessParkingSpots();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TimeSlot slot = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tv = convertView.findViewById(R.id.textViewRow1);
        tv.setText(String.format(convertView.getResources().getString(R.string.hostview_Start), df.getDateTimeFormat().format(slot.getStart())));
        tv = convertView.findViewById(R.id.textViewRow2);
        tv.setText(String.format(convertView.getResources().getString(R.string.hostview_End), df.getDateTimeFormat().format(slot.getEnd())));

        if (position % 2 == 0)
            convertView.setBackgroundResource(R.color.colorWhite);
        else
            convertView.setBackgroundResource(R.color.colorLightGrey);

        Button b = convertView.findViewById(R.id.buttonListItem);
        b.setText(convertView.getResources().getString(R.string.delete));
        b.setTag(position);
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = (int) view.getTag();
                TimeSlot slot = getItem(position);
                dataAccess.deleteDaySlot(slot.getSlotID());
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
                TimeSlot slot = getItem(position);

                Intent hostViewDayIntent = new Intent(view.getContext(), HostViewTimeActivity.class);
                hostViewDayIntent.putExtra("slotID", slot.getSlotID());
                view.getContext().startActivity(hostViewDayIntent);
            }
        });

        return convertView;
    }
}
