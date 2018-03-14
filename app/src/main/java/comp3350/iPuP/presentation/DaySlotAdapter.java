package comp3350.iPuP.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.TimeSlot;

public class DaySlotAdapter extends ArrayAdapter<TimeSlot>
{
    private DateFormatter df;
    private long spotID;
    private AccessParkingSpots dataAccess;
    private ListView list;
    private Activity activity;

    DaySlotAdapter(Context context, ArrayList<TimeSlot> slots, long spotID)
    {
        super(context, 0, slots);
        df = new DateFormatter();
        dataAccess = new AccessParkingSpots();
        this.spotID = spotID;
        this.activity = (Activity)context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        TimeSlot daySlot = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        list = (ListView)parent;

        TextView tv = convertView.findViewById(R.id.textViewListRow1);
        assert daySlot != null;
        tv.setText(String.format(convertView.getResources().getString(R.string.info_start), df.getDateTimeFormat().format(daySlot.getStart())));
        tv = convertView.findViewById(R.id.textViewListRow2);
        tv.setText(String.format(convertView.getResources().getString(R.string.info_end), df.getDateTimeFormat().format(daySlot.getEnd())));

        if (position % 2 == 0)
            convertView.setBackgroundResource(R.color.colorWhite);
        else
            convertView.setBackgroundResource(R.color.colorLightGrey);

        Button b = convertView.findViewById(R.id.buttonListItem);
        b.setText(convertView.getResources().getString(R.string.delete));
        if (daySlot.getIsBooked())
        {
            b.setBackgroundResource(R.color.colorTextHint);
        }
        else
        {
            b.setTag(position);
            b.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int position = (int) view.getTag();
                    TimeSlot slot = getItem(position);
                    try
                    {
                        assert slot != null;
                        if (!dataAccess.deleteDaySlot(slot.getSlotID()))
                        {
                            ArrayAdapter<TimeSlot> adapter = (ArrayAdapter<TimeSlot>) list.getAdapter();
                            adapter.remove(slot);
                        }
                        else
                        {
                            activity.setResult(Activity.RESULT_CANCELED);
                            activity.finish();
                        }
                    }
                    catch (DAOException daoe)
                    {
                        Toast.makeText(view.getContext(), daoe.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        LinearLayout lo = convertView.findViewById(R.id.list_item_layout);
        lo.setTag(position);
        lo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = (int) view.getTag();
                TimeSlot slot = getItem(position);

                Intent hostViewTimeIntent = new Intent(view.getContext(), HostViewTimeActivity.class);
                hostViewTimeIntent.putExtra(activity.getResources().getString(R.string.extra_slotID), slot.getSlotID());
                hostViewTimeIntent.putExtra(activity.getResources().getString(R.string.extra_spotID), spotID);
                activity.startActivityForResult(hostViewTimeIntent, 0);
            }
        });

        return convertView;
    }
}
