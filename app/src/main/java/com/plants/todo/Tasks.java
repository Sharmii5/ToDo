package com.plants.todo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.plants.todo.Adapter.ToDoAdapter;
import com.plants.todo.Model.ToDoModel;
import com.plants.todo.Utils.DatabaseHandler;

import java.util.Collections;
import java.util.List;


public class Tasks extends AppCompatActivity implements DialogCloseListener {

    private DatabaseHandler db;
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;
    private List<ToDoModel> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        // Initialize the database handler
        db = new DatabaseHandler(this);
        db.openDatabase();

        // Set up the RecyclerView for displaying tasks
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db, Tasks.this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        // Set up swipe-to-delete functionality for tasks
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        // Set up the "Add Task" floating action button
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
        // Retrieve all tasks from the database and display them in reverse order
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

    }
    // Method invoked when the dialog is closed
    @Override
    public void handleDialogClose(DialogInterface dialog){
        // Refresh the task list and update the adapter when a task is added or modified
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}
