package com.github.leesc22.quakereport

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class EarthquakeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earthquake)

        // Create a fake list of earthquake locations.
        val earthquakes = arrayListOf<String>()
        earthquakes.add("San Francisco")
        earthquakes.add("London")
        earthquakes.add("Tokyo")
        earthquakes.add("Mexico City")
        earthquakes.add("Moscow")
        earthquakes.add("Rio de Janeiro")
        earthquakes.add("Paris")

        // Find a reference to the {@link ListView} in the layout
        val earthquakeListView = findViewById<ListView>(R.id.list)

        // Create a new {@link ArrayAdapter} of earthquakes
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, earthquakes)

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.adapter = adapter
    }
}
