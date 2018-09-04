package com.priyamano.vickyattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class home extends AppCompatActivity {

    String data2[];
    Spinner s ;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        new Listdata().execute("");

        s=findViewById(R.id.spinner);


        Button btn=findViewById(R.id.btnView);

        recyclerView=findViewById(R.id.list);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"data : "+s.getSelectedItem().toString()+" "+data2[s.getSelectedItemPosition()],Toast.LENGTH_LONG).show();


                new Listdata1().execute(data2[s.getSelectedItemPosition()].trim());


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

                rs = con.createStatement().executeQuery("SELECT Convert(Char(11),AttendanceDate) + '\n' + DATENAME(DW,AttendanceDate),AttendanceDate  FROM Student_Attendance_DayWise A, BatchAcademicSchedule B, BatchCourse C, Student D  WHERE A.RollNumber=D.RollNumber and D.Batch=C.BatchCode and D.Semester=C.CurrentSemester and B.BatchCode=C.BatchCode and C.CurrentSemester=B.Semester and D.RollNumber = '" + data.rollno + "' and B.SemSDate<=AttendanceDate ORDER BY AttendanceDate DESC ");


            } catch (Exception e) {
                Log.d("login",e.toString());
            }


            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            ArrayList<String> list=new ArrayList();
            ArrayList<String> listD=new ArrayList();


            try {

                while (rs.next()) {


                    //Log.d("login success", rs.getString(1));

                    list.add(rs.getString(1));
                    listD.add(rs.getString(2).trim());


                }

                String data[]=new String[list.size()];
                data2=new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    data[i]=list.get(i);
                    data2[i]=listD.get(i);
                }


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, data);
                adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                s.setAdapter(adapter);









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

    private class Listdata1 extends AsyncTask<String, Void,String> {
        Connection con;
        ResultSet rs;


        @Override
        protected String doInBackground(String... strings) {
            try {

                con = ConnectionManager.getConnection();

                rs = con.createStatement().executeQuery("select top 30 D.AttendenceID,D.Present,C.CSubjectName,C.ShortName,S.StaffName from dbo.Student_Attendance_Detail D,dbo.Staff S,dbo.Student_Attendance_Header H ,dbo.CourseSubject C where D.attendenceid=H.attendenceid and D.rollnumber='"+data.rollno+"' and H.attendencedate ='"+strings[0]+"' and H.faculty=S.staffid and C.csubjectcode=H.subjectcode order by H.attendencedate desc");


            } catch (Exception e) {
                Log.d("login",e.toString());
            }


            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            ArrayList<pojo> list=new ArrayList();



            try {

                while (rs.next()) {


                   pojo o=new pojo(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));


                    list.add(o);



                }



                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(new SimpleAdapter(recyclerView,list));

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
    private class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {
        private static final int UNSELECTED = -1;

        private RecyclerView recyclerView;
        private int selectedItem = UNSELECTED;
        private  ArrayList<pojo> list;
        SimpleAdapter(RecyclerView recyclerView,ArrayList<pojo> list) {
            this.recyclerView = recyclerView;
            this.list=list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_data, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder  {
            private TextView id;
            private TextView name;
            private TextView substaff;
            private TextView type;
            private LinearLayout linearLayout;


            ViewHolder(View itemView) {
                super(itemView);

                id=itemView.findViewById(R.id.id);
                name=itemView.findViewById(R.id.name);
                substaff=itemView.findViewById(R.id.substaff);
                type=itemView.findViewById(R.id.type);
                linearLayout=itemView.findViewById(R.id.listplat);
            }

            void bind() {
                int position = getAdapterPosition();

                final pojo o=list.get(position);
                id.setText(o.Attendenceld);
                type.setText(o.Present);
                substaff.setText(o.StaffName);
                name.setText(o.CSubjectName);

                if(o.Present.trim().equalsIgnoreCase("A")){
                  type.setTextColor(Color.RED);
                }else if(o.Present.trim().equalsIgnoreCase("O")){
                    //linearLayout.setBackgroundColor(Color.BLUE);
                    type.setTextColor(Color.BLUE);
                }

                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i=new Intent(home.this.getApplicationContext(),update.class);

                        i.putExtra("ID",o.Attendenceld);
                        i.putExtra("PER",o.Present);
                        i.putExtra("SNAME",o.CSubjectName);
                        i.putExtra("SONAME",o.ShortName);
                        i.putExtra("SFNAME",o.StaffName);

                        startActivity(i);
                    }
                });

            }


        }
    }



    private class pojo{
        String Attendenceld,Present,CSubjectName,ShortName,StaffName;

        pojo(String Attendenceld,String Present,String CSubjectName,String ShortName,String StaffName){
            this.Attendenceld=Attendenceld;
            this.Present=Present;
            this.CSubjectName=CSubjectName;
            this.ShortName=ShortName;
            this.StaffName=StaffName;
        }

    }


}
