package com.example.dhktpm15a_nhom06_todoapp.adaper;




import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.constraintlayout.widget.ConstraintLayout;


import com.example.dhktpm15a_nhom06_todoapp.R;
import com.example.dhktpm15a_nhom06_todoapp.model.Task;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends BaseAdapter {
    private Context context;
    private int idLayout;
    private List<Task> listTask;
    private int positionSelect = -1;

    public TaskAdapter(Context context, int idLayout, List<Task> listTask) {
        this.context = context;
        this.idLayout = idLayout;
        this.listTask = listTask;
    }


    public List<Task> getListTask() {
        return listTask;
    }

    public void setListTask(List<Task> listTask) {

        this.listTask = listTask;
    }

    @Override
    public int getCount() {
        if (listTask.size() != 0 && !listTask.isEmpty()) {
            return listTask.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(idLayout, parent, false);
        }

        TextView tvContent = (TextView) convertView.findViewById(R.id.txtContent);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        TextView tvTime = (TextView) convertView.findViewById(R.id.txtTime);
        TextView tvDay = (TextView) convertView.findViewById(R.id.txtDay);
        TextView tvMonth = (TextView) convertView.findViewById(R.id.txtMonth);
        TextView tvThu = (TextView) convertView.findViewById(R.id.txtThu);




        final ConstraintLayout linearLayout = (ConstraintLayout ) convertView.findViewById(R.id.idLinearLayout);
        final Task language = listTask.get(position);

        if (listTask != null && !listTask.isEmpty()) {


            tvTitle.setText(language.getTitle());
            tvContent.setText(language.getContent());
            tvTime.setText(language.getDate().toString().substring(11,16));
            tvThu.setText(language.getDate().toString().substring(0,3));
            tvMonth.setText(language.getDate().toString().substring(4,7));
            tvDay.setText(language.getDate().toString().substring(8,10));

        }
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                positionSelect = position;
//              //  notifyDataSetChanged();
////                Intent i = new Intent(context, DetailDonutActivity.class);
////
////                Bundle data1 = new Bundle();
////                data1.putSerializable("donut",listLanguage.get(position) );
////                i.putExtras(data1);
////
////                context.startActivity(i);
//            }
//        });

        if (positionSelect == position) {
            linearLayout.setBackgroundColor(Color.rgb(249, 235, 200));
        } else {
            linearLayout.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }

}