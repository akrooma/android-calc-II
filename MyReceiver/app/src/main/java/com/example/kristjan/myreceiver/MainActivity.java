package com.example.kristjan.myreceiver;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TextView textView;
    private UOW uow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list);
        textView = (TextView) findViewById(R.id.text);

        uow = new UOW(getApplicationContext());
        displayOperations();
    }

    private void displayOperations() {
        String s = "All operations";
        textView.setText(s);

        OperationsAdapter operationsAdapter = new OperationsAdapter(this, uow.operationRepo.getCursorAll(), uow);
        listView.setAdapter(operationsAdapter);
    }

    private void displayDayStatistics() {
        String s = "Day statistics";
        textView.setText(s);

        DayStatisticsAdapter dayStatisticsAdapter = new DayStatisticsAdapter(this, uow.dayStatisticRepo.getCursorAll(), uow);
        listView.setAdapter(dayStatisticsAdapter);
    }

    private void displayOperationTypes() {
        String s = "Operand statistics";
        textView.setText(s);

        OperationTypesAdapter operationTypesAdapter = new OperationTypesAdapter(this, uow.operationTypeRepo.getCursorAll(), uow);
        listView.setAdapter(operationTypesAdapter);
    }

    private void refreshApp() {
        displayOperationTypes();
        displayDayStatistics();
        displayOperations();
    }

    public void deleteDbContent() {
        // confirmation dialog.
        new AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Do you really wish to clear the database?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    // if yes was clicked
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // database will be dropped and remade
                        uow.DropCreateDatabase();
                        // on screen message saying "Database cleared"
                        Toast.makeText(MainActivity.this, "Database cleared", Toast.LENGTH_SHORT).show();
                        refreshApp();
                    }})

                .setNegativeButton(android.R.string.no, null).show();
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
        if (id == R.id.action_delete) {
            deleteDbContent();
        } else if (id == R.id.action_refresh) {
            refreshApp();
        } else if (id == R.id.action_show_operations) {
            displayOperations();
        } else if (id == R.id.action_show_daysstats) {
            displayDayStatistics();
        } else if (id == R.id.action_show_operandstats) {
            displayOperationTypes();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshApp();
    }
}
