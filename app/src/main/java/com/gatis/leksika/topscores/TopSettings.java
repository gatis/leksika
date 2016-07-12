package com.gatis.leksika.topscores;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.gatis.leksika.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Created by roberts on 16.8.7.
 */
public class TopSettings extends ContextWrapper {

    public TopSettings(Context base, String board_id) {
        super(base);
        sharedPref = this.getSharedPreferences(getResources().
                getString(R.string.SharedPreferencesForTopScoreSettings), Context.MODE_PRIVATE);

        topScoreList = ReadTop(board_id);
    }

    private SharedPreferences sharedPref;
    private List<TopScore> topScoreList;

    private List<TopScore> ReadTop(String board_id){

        List<TopScore> ret = new ArrayList<>();

        for(int i=1; i < 11; i++) {

            ret.add(new TopScore(
                    sharedPref.getString(String.valueOf(i) + "_" + board_id + "_" + "name", ""),
                    sharedPref.getInt(String.valueOf(i) + "_" + board_id + "_" + "score_got", 0),
                    sharedPref.getInt(String.valueOf(i) + "_" + board_id + "_" + "score_possible", 0),
                    sharedPref.getLong(String.valueOf(i) + "_" + board_id + "_" + "scored_time", 0)
                    ));
        }

        Collections.sort(ret, Collections.<TopScore>reverseOrder());
        int i=1;
        for(TopScore a: ret) {
            a.setPlace(i);
            i++;
        }
        return  ret;
    }

    private void WriteTop(String board_id, String name, Integer score_got, Integer score_possible) {

        Long scoredTime = new Date().getTime();
        TopScore t;
        SharedPreferences.Editor editor = sharedPref.edit();

        if (isTopScore(score_got, score_possible)) {
            topScoreList.add(new TopScore(name, score_got, score_possible, scoredTime));
        }

        Collections.sort(topScoreList, Collections.reverseOrder());

        for(int i = 1; i< topScoreList.size()+1 && i<11; i++) {
            t = topScoreList.get(i - 1);
            editor.putString(i + "_" + board_id + "_name", t.getName());
            editor.putInt(i + "_" + board_id + "_score_got", t.getScoreGot());
            editor.putInt(i + "_" + board_id + "_score_possible", t.getScorePossible());
            editor.putLong(i + "_" + board_id + "_scored_time", t.getDateTs());
        }

        editor.commit();
    }

    public void putTopSetting(String board_id, String name, Integer score_got, Integer score_possible){
        WriteTop(board_id, name, score_got, score_possible);
    }

    public boolean isTopScore(Integer score_got, Integer score_possible){

        TopScore t = topScoreList.get(topScoreList.size() - 1);
        Double d1 = TopScore.getPercentage(t.getScoreGot(), t.getScorePossible());
        Double d2 = TopScore.getPercentage(score_got,score_possible);

        if (((d2 > d1) || topScoreList.size() < 10) && score_got > 0) {
            return true;
        } else {
            return false;
        }

    }

    public  List<TopScore> getTopSettings() {
        return topScoreList;
    }

    public void clearTopSettings(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }
}
