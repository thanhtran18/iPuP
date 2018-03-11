package comp3350.iPuP.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.iPuP.R;
import comp3350.iPuP.objects.ParkingSpot;

public class SpotAdapter extends ArrayAdapter<ParkingSpot>
{
    public SpotAdapter(Context context, ArrayList<ParkingSpot> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ParkingSpot spot = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        RelativeLayout layout = convertView.findViewById(R.id.list_item_layout);
        if (position % 2 == 0)
            layout.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
        else
            layout.setBackgroundColor(getContext().getResources().getColor(R.color.colorLightGrey));

        TextView tv = (TextView) convertView.findViewById(R.id.textViewRow1);
        tv.setText(spot.getAddress());
        tv = (TextView) convertView.findViewById(R.id.textViewRow2);
        tv.setText(String.valueOf(spot.getRate()));
        return convertView;
    }
}
