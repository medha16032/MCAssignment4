package com.example.medhagupta.todolist;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditItem extends ActionBarActivity {

    private DatabaseHandler dbHandler;
    EditText updatedTitle;
    EditText updatedDetails;
    Button submit;
    int item_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHandler = new DatabaseHandler(EditItem.this);

        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        updatedTitle=(EditText)findViewById(R.id.editTextUpdatedTitle);
        updatedDetails=(EditText)findViewById(R.id.editTextUpdatedDetails);
        submit=(Button)findViewById(R.id.buttonSubmit);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        item_id = extras.getInt("ItemId");
        //Toast.makeText(EditItem.this, "item id:"+item_id, Toast.LENGTH_SHORT).show();

       ToDoList task=dbHandler.getItem(item_id);
        updatedTitle.setText(task.getTitle());
        updatedDetails.setText(task.getDetails());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titleUpdated=updatedTitle.getText().toString();
                String detailsUpdated=updatedDetails.getText().toString();
                if(titleUpdated.equals("")|| (detailsUpdated.equals("")))
                {
                    Toast.makeText(EditItem.this, "Please fill both the fields", Toast.LENGTH_SHORT).show();
                }

                else {

                    ToDoList task=new ToDoList(titleUpdated,detailsUpdated, item_id);
                    dbHandler.updateItem(task);

                    Toast.makeText(EditItem.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditItem.this, MainActivity.class));


                }



            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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
