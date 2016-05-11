package com.example.peter.pikusup;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Peter on 2016-04-28.
 */
public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getName();
    private static Connection connection;
    Button b1,b2;
    EditText ed1;

    TextView tx1;
    int counter = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(Button)findViewById(R.id.button);
        ed1=(EditText)findViewById(R.id.editText);

        b2=(Button)findViewById(R.id.button2);
        tx1=(TextView)findViewById(R.id.textView3);
        tx1.setVisibility(View.GONE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"ed1 "+ed1);
                Log.d(TAG, "login ...... "+ed1.getText().toString());
                if(ed1 !=null && !ed1.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                    //validate this username
                    String login = ed1.getText().toString();
                    Log.d(TAG, "login " + login);
                    if(login(login)){
                        Intent intent = new Intent("android.intent.action.MemberList");
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), "There is no username "+login,Toast.LENGTH_SHORT).show();

                        tx1.setVisibility(View.VISIBLE);
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), "Please Provide Login",Toast.LENGTH_SHORT).show();

                    tx1.setVisibility(View.VISIBLE);

                    counter--;
                    tx1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        b1.setEnabled(false);
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static void getConnection(){

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            //ERROR MYQL JDBC Driver

            System.out.println("-------- ERROR MYSQL JDBC Driver ------------");
            e.printStackTrace();

            connection = null;
        }

        System.out.println("MySQL JDBC Driver Registered!");
        // Gets the URL from the UI's text field.
//        String stringUrl = urlText.getText().toString();
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(stringUrl);
        } else {
            textView.setText("No network connection available.");
        }
    }

    private class ConnectToServerTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {

            try{
            connection = DriverManager
                                .getConnection("jdbc:mysql://ec2-54-174-254-66.compute-1.amazonaws.com:3306/IDOLDB", "username", "password");

                    } catch (SQLException e) {
                        //ERROR Connection Failed! Check output console
                        e.printStackTrace();
                        connection = null;
                    }


            return null;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            dismissLoadingDialog();
        }
    }


    public boolean login(String username)  {
        showLoadingDialog();
        boolean userExists = false;


        MainActivity.getConnection();
        dismissLoadingDialog();
        if (connection != null) {
//            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            try{
                stmt = connection.createStatement();
                //TODO must change , security vunerability sql injection
                rs = stmt.executeQuery("SELECT * FROM IDOLDB.TAXI_USER where USER_NAME='"+username+"'");
                while (rs.next()) {
                    String id = rs.getString("ID");
                    String userName = rs.getString("USER_NAME");
                    System.out.println("ID: " + id + ", User Name: " + userName	);
                }
            }
            catch (SQLException e){
                //ERROR getting user info
                e.printStackTrace();
                return userExists;
            }

        } else {
            System.out.println("Failed to make connection!");
            return userExists;
        }

        userExists = true;
        return userExists;
    }

    public void showLoadingDialog() {

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
    }

    public void dismissLoadingDialog() {

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

}
