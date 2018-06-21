package com.example.acerpc.to_do_list;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class IntentAdd extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    EditText work,rd;
    Button Addlist,notify,but;
    databaseHelper help;
    Boolean result;
    EditText ed;
    EditText time;
    int  year_x,month_x,day_x;
    static final int dialog_box=0;
    ArrayAdapter<String> aa;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_add);
        Intent intent=new Intent();
        final Calendar cal=Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);
        showDialogBox();
        help=new databaseHelper(this);
        work=(EditText)findViewById(R.id.work);
        Addlist=(Button)findViewById(R.id.add_List);
        but=(Button)findViewById(R.id.time);
        ed=(EditText)findViewById(R.id.rd);
        time=(EditText)findViewById(R.id.ed);
        addToList();


    }
    public void showDialogBox()
    {
        notify=(Button)findViewById(R.id.notify);
        notify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showDialog(dialog_box);
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if(id==dialog_box)
            return new DatePickerDialog(this,dpickerListener,year_x,month_x,day_x);
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener=new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            year_x=i;
            month_x=i1+1;
            day_x=i2;
         //   Toast.makeText(IntentAdd.this,year_x+ "/"+month_x+"/"+day_x, Toast.LENGTH_SHORT).show();
            if(month_x<10){
                ed.setText(year_x+"-"+0+month_x+"-"+day_x);
            }
            else{
                ed.setText(year_x+"-"+month_x+"-"+day_x);
            }
            //Calendar cal=Calendar.getInstance();
             //int hour=cal.get(Calendar.HOUR_OF_DAY);
            //int min=cal.get(Calendar.MINUTE);
           // TimePickerDialog timepick=new TimePickerDialog(IntentAdd.this,IntentAdd.this,hour,min, DateFormat.is24HourFormat(this));
        }

    };

    public void addToList()
    {
        Addlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=String.valueOf(ed.getText());
                String message=work.getText().toString();

                result= help.insertData(message,s);
                if(result==true)
                    Toast.makeText(getApplicationContext(),"Added your wish",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }
}
