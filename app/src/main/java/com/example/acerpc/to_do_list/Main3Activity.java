package com.example.acerpc.to_do_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {
    databaseHelper help;
    ArrayAdapter<String> aa;
    EditText work,rd;
    TextView show, dateshow,taskname;
    Button delete;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent intent = new Intent();
        help = new databaseHelper(this);
        lv=(ListView)findViewById(R.id.listView);
        loadTaskList();
    }
    private void loadTaskList(){
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd ");
        String s1=sd.format(new Date());
        Toast.makeText(Main3Activity.this, s1, Toast.LENGTH_LONG).show();
        ArrayList<String> a2 = help.getTodayTask(s1);
            if (aa == null) {
                aa = new ArrayAdapter<String>(this, R.layout.row, R.id.tname, a2);
                lv.setAdapter(aa);
            } else {
                aa.clear();
                aa.addAll(a2);
                aa.notifyDataSetChanged();
            }

        }
    public void DeleteData(View view) {
        View parent=(View)view.getParent();
        TextView deletedata=(TextView)parent.findViewById(R.id.tname);
        String dataString=String.valueOf(deletedata.getText());
        Integer deletedRows= help.deleteData(dataString);
        if(deletedRows > 0)
            Toast.makeText(Main3Activity.this,"Task Deleted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(Main3Activity.this,"Task not Deleted",Toast.LENGTH_LONG).show();
        loadTaskList();
    }

    }

