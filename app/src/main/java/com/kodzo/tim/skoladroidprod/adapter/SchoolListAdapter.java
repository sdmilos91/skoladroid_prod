package com.kodzo.tim.skoladroidprod.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kodzo.tim.skoladroidprod.R;
import com.kodzo.tim.skoladroidprod.model.School;

import java.text.DecimalFormat;
import java.util.List;


public class SchoolListAdapter extends BaseAdapter {

    private View view;
    private Context context;
    private List<School> mSchools;

    public SchoolListAdapter (View view1, Context context1, List<School> schools) {

        view = view1;
        context = context1;
        mSchools = schools;
    }

    @Override
    public int getCount() {
        return mSchools.size();
    }

    @Override
    public Object getItem(int position) {
        return mSchools.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater li = LayoutInflater.from(context);
            v = li.inflate(R.layout.schools_list_item, null, false);

        }

        TextView name = (TextView) v.findViewById(R.id.schoolName);
        TextView city = (TextView) v.findViewById(R.id.schoolCity);
        TextView dist = (TextView) v.findViewById(R.id.schoolDist);

        name.setText(mSchools.get(position).getName());
        city.setText(mSchools.get(position).getCity());
        double a = mSchools.get(position).getDistance()/1000;
        if(a > 0) {
//            DecimalFormat twoDForm = new DecimalFormat("#.##");
//            a = Double.valueOf(twoDForm.format(a));
            dist.setText("(" + Double.toString(a) + " км)");
        }else{
            dist.setText(mSchools.get(position).getType());
        }


        if (position%2 == 0) {
            LinearLayout li = (LinearLayout) v.findViewById(R.id.listContainer);
            li.setBackgroundColor(Color.parseColor("#c4e1b8bb"));
        }
        else {
            LinearLayout li = (LinearLayout) v.findViewById(R.id.listContainer);
            li.setBackgroundColor(Color.parseColor("#eee1b8bb"));
        }
        return v;
    }
}
