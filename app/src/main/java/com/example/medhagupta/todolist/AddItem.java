package com.example.medhagupta.todolist;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.app.ToolbarActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddItem extends AppCompatActivity {

    EditText title;
    EditText details;
    Button addItem;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        dbHandler = new DatabaseHandler(AddItem.this);

        title=(EditText)findViewById(R.id.editTextTitle);
        details=(EditText)findViewById(R.id.editTextDetails);
        addItem=(Button)findViewById(R.id.buttonAddItem);


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputTitle=title.getText().toString();
                String inputDetails=details.getText().toString();

                if(inputTitle.equals("")|| (inputDetails.equals("")))
                {
                    Toast.makeText(AddItem.this, "Please fill both the fields", Toast.LENGTH_SHORT).show();
                }

                else {

                    dbHandler.addItem(new ToDoList(inputTitle, inputDetails));
                    Toast.makeText(AddItem.this, "Data added successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddItem.this, MainActivity.class));

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
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
