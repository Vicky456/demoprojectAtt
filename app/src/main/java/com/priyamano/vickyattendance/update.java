package com.priyamano.vickyattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class update extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        final String Id= getIntent().getStringExtra("ID");
        String PER= getIntent().getStringExtra("PER");
        String SNAME= getIntent().getStringExtra("SNAME");
        String SONAME= getIntent().getStringExtra("SONAME");
        String SFNAME= getIntent().getStringExtra("SFNAME");



        TextView textView=findViewById(R.id.Aid);
        textView.setText(Id);

        TextView textView1=findViewById(R.id.sname);
        textView1.setText(SONAME);

        TextView textView2=findViewById(R.id.Staffn);
        textView2.setText(SFNAME);

        TextView textView3=findViewById(R.id.name);
        textView3.setText(SNAME);


        Button btn=findViewById(R.id.btn);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();



                if(rb.getText().equals("Present")){
                    String d="P";
                    new Listdata().execute(d,Id);
                }else if(rb.getText().equals("Absent")){
                    String d="A";
                    new Listdata().execute(d,Id);
                }else if(rb.getText().equals("od")){
                    String d="O";
                    new Listdata().execute(d,Id);
                }

            }
        });



    }

    private class Listdata extends AsyncTask<String, Void,String> {
        Connection con;
        ResultSet rs;


        @Override
        protected String doInBackground(String... strings) {
            try {

                con = ConnectionManager.getConnection();

                rs = con.createStatement().executeQuery("update dbo.Student_Attendance_Detail set Present='"+strings[0]+"' where rollnumber='"+data.rollno+"' and attendenceid='"+strings[1]+"'");


            } catch (Exception e) {
                Log.d("login",e.toString());
            }


            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            try {




                    finish();



                rs.close();
                con.close();





            } catch (Exception e) {

                Log.d("login error",e.toString());

            }
        }


        @Override
        protected void onPreExecute() {


        }

        @Override
        protected void onProgressUpdate(Void... values) {}





    }
}
