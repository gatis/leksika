package com.gatis.leksika.topscores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.gatis.leksika.R;

public class TopListAdapter extends ArrayAdapter<TopScore> {

    public TopListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public TopListAdapter(Context context, int resource, List<TopScore> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.top_list_item, null);
        }

        TopScore p = getItem(position);

        if (p != null) {

            TextView tt0 = (TextView) v.findViewById(R.id.tw_place);
            TextView tt1 = (TextView) v.findViewById(R.id.tw_name);
            TextView tt2 = (TextView) v.findViewById(R.id.tw_score);
            TextView tt3 = (TextView) v.findViewById(R.id.tw_date);

            if (tt0 != null) {
                tt0.setText(p.getPlace().toString());
            }

            if (tt1 != null) {
                tt1.setText(p.getName());
            }

            if (tt2 != null ) {

                if (p.getScorePossible() == 0) {
                    tt2.setText("");
                } else {
                    tt2.setText( TopScore.getPercentage(p.getScoreGot(),p.getScorePossible()) +
                            "% ("+ p.getScoreGot().toString() + "/" + p.getScorePossible().toString() + ")" );
                }
            }

            if (tt3 != null) {

                if (p.getDateTs() == 0) {
                    tt3.setText("");
                } else {
                    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date(p.getDateTs()));
                    tt3.setText(date);
                }

            }
        }

        return v;
    }



}
