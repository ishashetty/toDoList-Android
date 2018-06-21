package com.example.acerpc.to_do_list;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class MainActivity extends AppCompatActivity {
    databaseHelper help;
    ListView lv;
    ArrayAdapter<String> aa;
    EditText work,rd;
    TextView taskname;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        help = new databaseHelper(this);
        Intent intent = new Intent();
        delete = (Button) findViewById(R.id.btnDelete);
        taskname = (TextView) findViewById(R.id.tname);
        lv = (ListView) findViewById(R.id.list_view);
        loadTaskList();
        notifyMe();
    }
    public void changeAdd(View v) {
        Intent intent = new Intent(MainActivity.this, IntentAdd.class);
        intent.putExtra("Flag2", 2);
        startActivityForResult(intent, 2);
    }
    public void onResume() {
        super.onResume();
        loadTaskList();
    }
    public void loadTaskList() {
        ArrayList<String> a2 = help.getAllTask();
        if (a2.isEmpty()) {
            a2.add("Nothing to show");
            aa = new ArrayAdapter<String>(this, R.layout.row1, R.id.tname, a2);
            lv.setAdapter(aa);
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
       // loadTaskList();

    }
    //function for checking whether the task is completed or not
    public void DeleteData(View view) {
        View parent=(View)view.getParent();
        TextView deletedata=(TextView)parent.findViewById(R.id.tname);
        String dataString=String.valueOf(deletedata.getText());
        Integer deletedRows= help.deleteData(dataString);
        if(deletedRows > 0)
            Snackbar.make(view, "Task Completed", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        else
            Snackbar.make(view, "Task Not Completed", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
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
        final CharSequence[] items={"Postpone","Pre-pone"};
        final EditText ed=new EditText(this);
        final ArrayList selected=new ArrayList();
        final  View parent=(View)view.getParent();
        AlertDialog dialog=new AlertDialog.Builder(this)
                .setTitle("Update Task")
                .setMessage("Enter the new task name:")
                .setView(ed)
                .setPositiveButton("Postpone", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextView deletedata=(TextView)parent.findViewById(R.id.tname);
                        String dataString=String.valueOf(deletedata.getText());
                        String s1=String.valueOf(ed.getText());
                        Toast.makeText(MainActivity.this,s1,Toast.LENGTH_LONG).show();
                        boolean isUpdate = false;
                        try {
                            isUpdate = help.update(
                                    dataString,s1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this,"Task preponed for tommorrow",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                        loadTaskList();
                    }
                })
                .setNegativeButton("Cancel",null)
                .create();
        dialog.show();
        return true;

    }
//notification showing
    public void notifyMe() {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd ");
        String s1 = sd.format(new Date());
        ArrayList<String> a2 = help.getTodayTask(s1);
       if (!a2.isEmpty())
        {
            Calendar calendar = Calendar.getInstance();
            //will notify during this hour of day
            calendar.set(Calendar.HOUR_OF_DAY,15);
            //will notify during this minute
            calendar.set(calendar.MINUTE,43);
            Uri SoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Intent intent = new Intent(this, Notification.class);
            intent.putExtra("Flag",1);
            //to send data to notification.class after some delay
            PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
           // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

        }
    }
}


