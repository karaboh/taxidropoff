package com.example.peter.pikusup;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Peter on 2016-04-28.
 */
public class MemberList extends ActionBarActivity {
    private ListView myList;
    public static final int REG_REQ_CODE = 55;
    public static final int REG_RESULT_CODE = 56;
    public static final int Home = 111;
    public static final int School = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        myList = (ListView) findViewById(R.id.mylist);
        getAllFromDB();
        registerForContextMenu(myList);
        ActionBar actionBar = getSupportActionBar();
    }

    private void getAllFromDB() {
        MyDB myDB = new MyDB(this);
        myDB.openDB();
        Cursor cursor = myDB.getAllUsers();
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.simplelistbox, cursor, new String[]{MyDB.STUDENT_NAME, MyDB.STUDENT_TELL},
                new int[]{R.id.nameholder});
        myList.setAdapter(simpleCursorAdapter);
        //cursor.close(); // Do not close if SimpleCursorAdapter is used
        myDB.closeDB();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Is the child delivered to");
        menu.add(Menu.NONE, School, 0, "Delivered to School");
        menu.add(Menu.NONE, Home, 0, "Delivered to Home");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case School:
                Intent intent = new Intent(this, DeliveredToSchool.class);
                startActivity(intent);
                break;
            case Home:
                Intent intent1 = new Intent(this, DeliveredToHome.class);
                startActivity(intent1);
                break;
        }
        return super.onContextItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REG_REQ_CODE) {
            if (resultCode == REG_RESULT_CODE) {
                getAllFromDB();
            }
        }

    }
}
