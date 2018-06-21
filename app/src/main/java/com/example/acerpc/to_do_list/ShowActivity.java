package com.example.acerpc.to_do_list;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ShowActivity extends AppCompatActivity {
    databaseHelper help;
    ArrayAdapter<String> aa;
    EditText work,rd;
    TextView taskname;
    Button delete;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = new Intent();
        help = new databaseHelper(this);
        delete = (Button) findViewById(R.id.btnDelete);
        taskname = (TextView) findViewById(R.id.tname);
        lv = (ListView) findViewById(R.id.list_view);
        loadTaskList();
    }

    private void loadTaskList() {
        ArrayList<String> a2 = help.getAllTask();
        if (a2.isEmpty()) {
            Toast.makeText(ShowActivity.this, "Nothing To Show!!!", Toast.LENGTH_LONG).show();
        } else {
            if (aa == null) {
                aa = new ArrayAdapter<String>(this, R.layout.row, R.id.tname, a2);
                lv.setAdapter(aa);
            } else {
                aa.clear();
                aa.addAll(a2);
                aa.notifyDataSetChanged();
            }

        }
        loadTaskList();
    }
    //function for checking whether the task is completed or not
    public void DeleteData(View view) {
                       View parent=(View)view.getParent();
                        TextView deletedata=(TextView)parent.findViewById(R.id.tname);
                        String dataString=String.valueOf(deletedata.getText());
        Toast.makeText(ShowActivity.this,dataString,Toast.LENGTH_LONG).show();
                       Integer deletedRows= help.deleteData(dataString);
                        if(deletedRows > 0)
                            Toast.makeText(ShowActivity.this,"Task Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ShowActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                        loadTaskList();
                    }
    //function to show details of the task
    public void showDetails(View view){
        View parent=(View)view.getParent();
        taskname=(TextView)parent.findViewById(R.id.tname);
        String ts=String.valueOf(taskname.getText());
        ArrayList<String>a = help.getAllTaskDtae( ts);
        if(a.isEmpty() ) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        if(a.contains(ts)) {
            buffer.append("Task Name :- "+ a.get(0)+"\n");
            buffer.append("Task Date :- "+ a.get(1)+"\n");
            buffer.append("Remainder Date :"+ a.get(2)+"\n\n");
        }

         //Show all data
        showMessage("Data",buffer.toString());
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    //function to update the task or postpone the task
    public boolean updateData(View view) {
        final EditText ed=new EditText(this);
        final  View parent=(View)view.getParent();
        AlertDialog dialog=new AlertDialog.Builder(this)
                .setTitle("Update Task")
                .setMessage("Enter the task date")
                .setView(ed)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       TextView deletedata=(TextView)parent.findViewById(R.id.tname);
                       String dataString=String.valueOf(deletedata.getText());
                        String s1=String.valueOf(ed.getText());
                        Toast.makeText(ShowActivity.this,s1,Toast.LENGTH_LONG).show();
                        boolean isUpdate = false;
                        try {
                            isUpdate = help.update(
                                    dataString,s1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(isUpdate == true)
                            Toast.makeText(ShowActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ShowActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                        loadTaskList();
                    }
                })
                .setNegativeButton("Cancel",null)
                .create();
                dialog.show();
        return true;

        }

}














