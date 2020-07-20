package com.example.todoapplication.activity;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.todoapplication.R;
import com.example.todoapplication.adapter.Adapter;
import com.example.todoapplication.model.SingletonTasks;
import com.example.todoapplication.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NewTaskDialog.NewTaskDialogListener, Adapter.OnItemLongClickListener {

    RecyclerView recyclerView;
    RadioButton radioButtonAll;
    RadioButton radioButtonTODO;
    RadioButton radioButtonDone;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        radioButtonAll = findViewById(R.id.radioButtonAll);
        radioButtonTODO = findViewById(R.id.radioButtonTODO);
        radioButtonDone = findViewById(R.id.radioButtonDone);

        radioButtonAll.setChecked(true);

        //Adapter configuration
        adapter = new Adapter(this, this);


        //RecyclerView Configuration
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.filter("All");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewTaskDialog();
            }
        });

        radioButtonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.filter(radioButtonAll.getText().toString());
            }
        });

        radioButtonTODO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.filter(radioButtonTODO.getText().toString());
            }
        });

        radioButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.filter(radioButtonDone.getText().toString());
            }
        });

    }

    @Override
    public void applyText(String newTask) {
        SingletonTasks.getInstance().taskList.add(new Task(newTask));
        updateList();
    }

    private void updateList(){
        if(radioButtonAll.isChecked())
            adapter.filter(radioButtonAll.getText().toString());
        else if(radioButtonTODO.isChecked())
            adapter.filter(radioButtonTODO.getText().toString());
        else {
            adapter.filter(radioButtonDone.getText().toString());
        }
    }

    public void openNewTaskDialog(){
        NewTaskDialog newTaskDialog = new NewTaskDialog();
        newTaskDialog.show(getSupportFragmentManager(), "new task dialog");
    }

    @Override
    public void onItemLongClicked(final int position) {
        final Task task = SingletonTasks.getInstance().taskListFiltered.get(position);

        new AlertDialog.Builder(this)
                .setTitle("Delete task")
                .setMessage("Are you sure you want to delete " + task.getDescription() + " ?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UUID id = task.getId();
                        Task taskToBeRemoved = null;
                        for (Task task : SingletonTasks.getInstance().taskList){
                            if(task.getId() == id){
                                taskToBeRemoved = task;
                            }
                        }
                        SingletonTasks.getInstance().taskList.remove(taskToBeRemoved);
                        Toast.makeText(MainActivity.this, "Task removed", Toast.LENGTH_SHORT).show();
                        updateList();
                    }
                })
        .show();

    }
}
