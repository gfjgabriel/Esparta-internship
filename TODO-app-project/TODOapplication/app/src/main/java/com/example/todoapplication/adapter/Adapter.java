package com.example.todoapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.R;
import com.example.todoapplication.model.SingletonTasks;
import com.example.todoapplication.model.Task;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private OnItemLongClickListener onItemLongClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }

    private Context c;

    public Adapter(Context c, OnItemLongClickListener onItemLongClickListener) {
        this.c = c;
        SingletonTasks.getInstance().taskListFiltered = new ArrayList<>();
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list, parent, false);
        return new MyViewHolder(listItem, onItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final Task task = SingletonTasks.getInstance().taskListFiltered.get(position);
        holder.checkBoxTask.setText(task.getDescription());
        holder.checkBoxTask.setChecked(task.isDone());
        if(task.isDone())
            holder.checkBoxTask.setPaintFlags(holder.checkBoxTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        else
            holder.checkBoxTask.setPaintFlags(holder.checkBoxTask.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        holder.checkBoxTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(task.isDone()){
                    SingletonTasks.getInstance().taskListFiltered.get(position).setDone(false);
                    holder.checkBoxTask.setPaintFlags(holder.checkBoxTask.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }else{
                    SingletonTasks.getInstance().taskListFiltered.get(position).setDone(true);
                    holder.checkBoxTask.setPaintFlags(holder.checkBoxTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return SingletonTasks.getInstance().taskListFiltered != null ? SingletonTasks.getInstance().taskListFiltered.size() : 0;
    }

    // My View Holder Class
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        CheckBox checkBoxTask;
        OnItemLongClickListener onItemLongClickListener;

        public MyViewHolder(@NonNull View itemView, final OnItemLongClickListener onItemLongClickListener) {
            super(itemView);

            checkBoxTask = itemView.findViewById(R.id.checkBoxTask);
            this.onItemLongClickListener = onItemLongClickListener;
            checkBoxTask.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickListener.onItemLongClicked(getAdapterPosition());
                    return false;
                }
            });


        }

        @Override
        public boolean onLongClick(View v) {
            onItemLongClickListener.onItemLongClicked(getAdapterPosition());
            return true;
        }
    }

    public void filter(final String status) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                SingletonTasks.getInstance().taskListFiltered.clear();
                if (status.equals("All")) {
                    SingletonTasks.getInstance().taskListFiltered.addAll(SingletonTasks.getInstance().taskList);
                } else if(status.equals("TODO")){
                    for (Task task : SingletonTasks.getInstance().taskList) {
                        if(!task.isDone())
                            SingletonTasks.getInstance().taskListFiltered.add(task);
                    }
                }else if(status.equals("Done")){
                    for (Task task : SingletonTasks.getInstance().taskList) {
                        if(task.isDone())
                            SingletonTasks.getInstance().taskListFiltered.add(task);
                    }
                }
                ((Activity) c).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();

    }

}
