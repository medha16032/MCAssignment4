package com.example.medhagupta.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class ViewDetails extends ActionBarActivity {

    private DatabaseHandler dbHandler;
    ImageButton editTask;
    ImageButton deleteTask;

    int item_id;

    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHandler = new DatabaseHandler(ViewDetails.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        mViewPager  = (ViewPager) findViewById(R.id.pager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        editTask=(ImageButton)findViewById(R.id.editItem);
        deleteTask=(ImageButton)findViewById(R.id.deleteItem);

        Intent intent = getIntent();
        //int intValue = intent.getIntExtra("ItemId", 0);
        Bundle extras = intent.getExtras();
        item_id = extras.getInt("ItemId");
        int item_pos = extras.getInt("ItemPosition");

        //Toast.makeText(ViewDetails.this, "Item id:"+item_id+" Item position:"+item_pos, Toast.LENGTH_SHORT).show();

        mViewPager.setAdapter(new CustomPagerAdapter(this));
        mViewPager.setCurrentItem(item_pos);

        editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewDetails.this, EditItem.class);
                Bundle extras = new Bundle();
                extras.putInt("ItemId", item_id);
                //extras.putInt("ItemPosition",position);
                i.putExtras(extras);
                startActivity(i);
            }
        });

        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewDetails.this);

                // Setting Dialog Title
                alertDialog.setTitle("Confirm Delete...");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want delete this?");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.delete1);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        // Write your code here to invoke YES event

                        ToDoList task=dbHandler.getItem(item_id);
                        dbHandler.deleteItem(task);
                        Toast.makeText(getApplicationContext(), "Task deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ViewDetails.this, MainActivity.class));
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
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_details, menu);
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
