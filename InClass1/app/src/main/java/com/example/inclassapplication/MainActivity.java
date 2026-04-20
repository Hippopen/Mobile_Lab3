package com.example.inclassapplication;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Construct the data source (Page 14)
        ArrayList<User> arrayOfUsers = new ArrayList<User>();

        // Create the adapter to convert the array to views (Page 14)
        UsersAdapter adapter = new UsersAdapter(this, arrayOfUsers);

        // Attach the adapter to a ListView (Page 14)
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(adapter);

        // Add item to adapter (Page 15)
        User newUser1 = new User("Harry", "San Diego");
        adapter.add(newUser1);
        User newUser2 = new User("Marla", "San Francisco");
        adapter.add(newUser2);
        User newUser3 = new User("Sarah", "San Marco");
        adapter.add(newUser3);
    }
}
