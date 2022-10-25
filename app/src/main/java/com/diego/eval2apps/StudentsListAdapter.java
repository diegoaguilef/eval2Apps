package com.diego.eval2apps;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.diego.eval2apps.models.Student;

import java.util.List;

public class StudentsListAdapter extends ArrayAdapter<Student> {
    private Activity context;
    List<Student> students;

    public StudentsListAdapter(Activity context, List<Student> students) {
        super(context,R.layout.list_view_item,students);
        this.context = context;
        this.students = students;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_view_item,null,true);

        TextView txtName = listViewItem.findViewById(R.id.txtItemName);
        TextView txtValue = listViewItem.findViewById(R.id.txtItemValue);
        txtName.setTextSize(15);
        txtValue.setTextSize(15);

        Student student = students.get(position);
        txtName.setText(student.getName());
        txtValue.setText(student.getCourse());

        return listViewItem;
    }

}
