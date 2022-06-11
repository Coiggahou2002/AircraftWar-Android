package com.example.myapplication.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.Arrays;
import java.util.List;

public class StandingListAdapter extends BaseAdapter {

    private static final List<String> header
            = Arrays.asList("Name", "Score", "Time");

    Context context;
    private final List<StandingEntry> data;

    public StandingListAdapter(Context context, List<StandingEntry> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if(position == 0) {
            return header;
        }
        else {
            return data.get(position - 1);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder holder;
        if(convertView == null || !(convertView.getTag() instanceof ItemViewHolder)) {
            holder = new ItemViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.standing_item, parent, false);

            holder.nameText = convertView.findViewById(R.id.name_text);
            holder.scoreText = convertView.findViewById(R.id.score_text);
            holder.timeText = convertView.findViewById(R.id.time_text);
            holder.deleteButton = convertView.findViewById(R.id.entry_delete_button);

            holder.deleteButton.setOnClickListener(v -> {
                data.remove(position - 1);
                notifyDataSetChanged();
            });

            convertView.setTag(holder);
        }
        else {
            holder = (ItemViewHolder) convertView.getTag();
        }

        if(position == 0) {
            holder.nameText.setText(header.get(0));
            holder.scoreText.setText(header.get(1));
            holder.timeText.setText(header.get(2));
            holder.deleteButton.setVisibility(View.INVISIBLE);
        }
        else {
            holder.nameText.setText(data.get(position - 1).name);
            holder.scoreText.setText(data.get(position - 1).score);
            holder.timeText.setText(data.get(position - 1).time);
        }

        return convertView;
    }

    public static final class ItemViewHolder {
        TextView nameText, scoreText, timeText;
        Button deleteButton;
    }

    public static final class HeadViewHolder {
        TextView nameText, scoreText, timeText;
    }
}
