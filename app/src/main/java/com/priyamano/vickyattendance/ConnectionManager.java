package com.priyamano.vickyattendance;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.util.Log;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection con;


    public static Connection getConnection() {
        String _Server="118.91.234.206:1433",_database="MCET",_user="Faculty",_pass="Facultty@123";
        try {
            StrictMode.setThreadPolicy(new Builder().permitAll().build());
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            try {

                //con = DriverManager.getConnection("jdbc:jtds:sqlserver://"+_Server+";database="+_database+";user="+_user+";password="+_pass+";encrypt=false;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
                con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + _Server + ";databaseName=" + _database + ";user=" + _user + ";password=" + _pass + ";");
            } catch (SQLException e) {

                Log.d("connection","Failed to create the database connection." + e);
            }
        } catch (ClassNotFoundException e2) {
            Log.d("connection","Driver not found.");
        }
        return con;
    }
}
