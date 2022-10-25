package com.diego.eval2apps;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.diego.eval2apps.models.Score;
import com.diego.eval2apps.models.Student;

import java.util.List;

public class ScoresListAdapter extends ArrayAdapter<Score> {
    private Activity context;
    List<Score> scores;

    public ScoresListAdapter(Activity context, List<Score> scores) {
        super(context,R.layout.list_view_item,scores);
        this.context = context;
        this.scores = scores;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_view_item,null,true);

        TextView txtName = listViewItem.findViewById(R.id.txtItemName);
        TextView txtValue = listViewItem.findViewById(R.id.txtItemValue);
        txtName.setTextSize(15);
        txtValue.setTextSize(15);

        Score score = scores.get(position);
        txtName.setText(score.getEvalDate().toString());
        txtValue.setText(String.valueOf(score.getScore()));

        return listViewItem;
    }
}
