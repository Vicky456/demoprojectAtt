package com.priyamano.vickyattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText user=findViewById(R.id.user);
        final EditText pass=findViewById(R.id.pass);

        Button but=findViewById(R.id.but);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("login","working"+user.getText()+pass.getText());
                new loginTask().execute(user.getText().toString(),pass.getText().toString());
            }
        });
    }



    @SuppressLint("StaticFieldLeak")
    private class loginTask extends AsyncTask<String, Void, String> {


        Connection con;
        ResultSet rs;


        @Override
        protected String doInBackground(String... strings) {
            try {

                con = ConnectionManager.getConnection();

                rs = con.createStatement().executeQuery("SELECT RollNumber FROM Student where Rollnumber='"+strings[0]+"' and Password='"+strings[1]+"';");


            } catch (Exception e) {
                Log.d("login",e.toString());
            }


            return "Executed";
        }

        ArrayList<String> list=new ArrayList();

        @Override
        protected void onPostExecute(String result) {


            try {

                if (rs.next()) {



                    Log.d("login success",rs.getString(1));

                    String roll=rs.getString(1);


                        if(list.contains(roll.trim())) {
                            startActivity(new Intent(login.this, MainActivity.class));
                            data.rollno=roll;
                            finish();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"login failed your not a vicky friend",Toast.LENGTH_LONG).show();




                }else{
                    Toast.makeText(getApplicationContext(),"login failed",Toast.LENGTH_LONG).show();
                }



                rs.close();
                con.close();





            } catch (Exception e) {

                    Log.d("login error",e.toString());

            }
        }


            @Override
        protected void onPreExecute() {

                list.add("16BCS316");
                list.add("16BCS315");
                list.add("16BCS314");
                list.add("16BCS308");
                list.add("16BCS312");
                list.add("16BCS306");
                list.add("16BCS304");
                list.add("15BCS002");
                list.add("15BCS102");
                list.add("15BCS030");
                list.add("15BCS094");
                list.add("15BCS046");
            }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }




}
