package com.example.medhagupta.todolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<ToDoList> doList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListAdapter lAdapter;
    private DatabaseHandler dbHandler;
    ImageButton addButton;
    int item_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitleTextAppearance(this, R.style.MyTitleTextApperance);
        setSupportActionBar(toolbar);

        dbHandler = new DatabaseHandler(MainActivity.this);

        addButton=(ImageButton)findViewById(R.id.addItem);


        doList=dbHandler.getAllItems();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        lAdapter = new ListAdapter(doList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       // recyclerView.setAdapter(lAdapter);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, AddItem.class);
                startActivity(in);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ToDoList list = doList.get(position);
                //Toast.makeText(getApplicationContext(), list.getId() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, ViewDetails.class);

                Bundle extras = new Bundle();
                extras.putInt("ItemId", list.getId());
                extras.putInt("ItemPosition", position);
                i.putExtras(extras);

                //i.putExtra("ItemId", list.getId());
                startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

                ToDoList list = doList.get(position);
                item_id = list.getId();

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                // Setting Dialog Title
                alertDialog.setTitle("Confirm Delete...");

                // Setting Dialog Message
                alertDialog.setMessage("Do you want to delete this task?");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.delete1);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // Write your code here to invoke YES event

                        ToDoList task = dbHandler.getItem(item_id);
                        dbHandler.deleteItem(task);
                        Toast.makeText(getApplicationContext(), "Task deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();


            }
        }));

        recyclerView.setAdapter(lAdapter);

        lAdapter.notifyDataSetChanged();


        //prepareListData();

    }

    private void prepareListData() {

        String selectQuery = "SELECT  * FROM doList";
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ToDoList item = new ToDoList(cursor.getString(1), cursor.getString(2), cursor.getInt(0));
                doList.add(item);
            } while (cursor.moveToNext());
        }


        /*if(doList.isEmpty())
        {

        }
        else {
            doList=dbHandler.getAllItems();
            ToDoList list = doList.get(0);
            Toast.makeText(MainActivity.this, list.getTitle(), Toast.LENGTH_SHORT).show();
        }*/
        lAdapter.notifyDataSetChanged();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
