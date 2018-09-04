package com.priyamano.vickyattendance;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Listdata extends AsyncTask<String, Void,String> {
        Connection con;
        ResultSet rs;


        @Override
        protected String doInBackground(String... strings) {
            try {

                con = ConnectionManager.getConnection();

                rs = con.createStatement().executeQuery("SELECT Convert(Char(11),AttendanceDate) + '\n' + DATENAME(DW,AttendanceDate)  FROM Student_Attendance_DayWise A, BatchAcademicSchedule B, BatchCourse C, Student D  WHERE A.RollNumber=D.RollNumber and D.Batch=C.BatchCode and D.Semester=C.CurrentSemester and B.BatchCode=C.BatchCode and C.CurrentSemester=B.Semester and D.RollNumber = '" + data.rollno + "' and B.SemSDate<=AttendanceDate ORDER BY AttendanceDate DESC ");


            } catch (Exception e) {
                Log.d("login",e.toString());
            }


            return "Executed";
        }

        ArrayList<String> list=new ArrayList();

        @Override
        protected void onPostExecute(String result) {


            try {

                while (rs.next()) {


                    Log.d("login success", rs.getString(1));

                    list.add(rs.getString(1));


                }

                String data[]=new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    data[i]=list.get(i);
                }









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
