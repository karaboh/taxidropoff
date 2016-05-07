package com.example.peter.pikusup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
    Button b1,b2;
    EditText ed1,ed2;

    TextView tx1;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(Button)findViewById(R.id.button);
        ed1=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText2);

        b2=(Button)findViewById(R.id.button2);
        tx1=(TextView)findViewById(R.id.textView3);
        tx1.setVisibility(View.GONE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1 !=null && ed1.getText() !=null && MainActivity.login(ed2.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent("android.intent.action.MemberList");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();

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
    
    	private static Connection getConnection(){
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			//ERROR MYQL JDBC Driver
			System.out.println("-------- MERROR MYQL JDBC Driver ------------");
	e.printStackTrace();
			
			return null;
		}

		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;

		try {
			connection = DriverManager
			.getConnection("jdbc:mysql://ec2-54-174-254-66.compute-1.amazonaws.com:3306/IDOLDB","username", "password");

		} catch (SQLException e) {
			//ERROR Connection Failed! Check output console
			e.printStackTrace();
			return null;
		}
		return connection;
	}
	
	public static boolean login(String username)  {
		boolean userExists = false;
		
		
		 Connection connection = MainActivity.getConnection();
		if (connection != null) {
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			try{
			stmt = connection.createStatement();
			//TODO must change , security vunerability sql injection
			rs = stmt.executeQuery("SELECT * FROM IDOLDB.TAXI_USER where USER_NAME='"+username+"'");
			while (rs.next()) {
				String id = rs.getString("ID");
				String userName = rs.getString("USER_NAME");
				System.out.println("ID: " + id + ", User Name: " + username	);
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

}
