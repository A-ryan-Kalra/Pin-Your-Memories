package com.prototype_1.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> places=new ArrayList<String>();
  static  ArrayList<LatLng> locations=new ArrayList<LatLng>();
  static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView=(ListView)findViewById(R.id.listview);

        ArrayList<String>longitude=new ArrayList<>();
        ArrayList<String>latitude=new ArrayList<>();
        places.clear();
        latitude.clear();
        longitude.clear();
        locations.clear();

        SharedPreferences sharedPreferences=this.getSharedPreferences("com.prototype_1.memorableplaces", Context.MODE_PRIVATE);
        try {
            places= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new ArrayList<String>())));
            longitude= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longi",ObjectSerializer.serialize(new ArrayList<String>())));
            latitude= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lati",ObjectSerializer.serialize(new ArrayList<String>())));
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        if(places.size()>0 && latitude.size()>0 && longitude.size()>0)
        {
            if(places.size()==longitude.size()&& places.size()==latitude.size())
            {
                for(int i=0;i<latitude.size();i++)
                {
                    locations.add(new LatLng(Double.parseDouble(latitude.get(i)),Double.parseDouble(longitude.get(i))));
                }
            }
        }else {
            places.add("Add a new place...");
            locations.add(new LatLng(0,0));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("placeNumber",position);
                startActivity(intent);
            }
        });
         arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,places);
        listView.setAdapter(arrayAdapter);
    }
}