package com.gatis.leksika;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;

import com.gatis.leksika.topscores.TopListAdapter;
import com.gatis.leksika.topscores.TopScore;
import com.gatis.leksika.topscores.TopSettings;

import java.util.ArrayList;
import java.util.List;

public class TopActivity extends TabActivity {

    List<TopScore> TopScoreListFour = new ArrayList<>();
    List<TopScore> TopScoreListFive = new ArrayList<>();
    String[] boards_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boards_array = getResources().getStringArray(R.array.board_size_choices_entries);
        //setContentView(R.layout.activity_main);

        // Set up the tabs
        TabHost host = getTabHost();
        LayoutInflater.from(this).inflate(R.layout.top_view, host.getTabContentView(), true);
        host.addTab(host.newTabSpec(boards_array[0]).setIndicator(boards_array[0]).setContent(R.id.four_ll));
        host.addTab(host.newTabSpec(boards_array[1]).setIndicator(boards_array[1]).setContent(R.id.five_ll));
        //read and fill top scores

        ListView tsListViewFour = (ListView) findViewById(R.id.four_lw);
        ListView tsListViewFive = (ListView) findViewById(R.id.five_lw);

        // get data from the table by the ListAdapter
        TopListAdapter customAdapterFour = new TopListAdapter(this, R.layout.top_list_item, TopScoreListFour);
        TopListAdapter customAdapterFive = new TopListAdapter(this, R.layout.top_list_item, TopScoreListFive);

        tsListViewFour.setAdapter(customAdapterFour);
        tsListViewFive.setAdapter(customAdapterFive);
        fillTopList();

        Button cb1 = (Button) findViewById(R.id.close_tops);
        cb1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void fillTopList() {

        TopSettings tseFour = new TopSettings(this,boards_array[0] );
        TopSettings tseFive = new TopSettings(this,boards_array[1] );

        TopScoreListFour.addAll(tseFour.getTopSettings());
        TopScoreListFive.addAll(tseFive.getTopSettings());

    }
}
