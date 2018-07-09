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
        val earthquakes = arrayListOf<Earthquake>()
        earthquakes.add(Earthquake(7.2F,"San Francisco", "Feb 2, 2016"))
        earthquakes.add(Earthquake(6.1F,"London", "July 20, 2015"))
        earthquakes.add(Earthquake(3.9F,"Tokyo", "Nov 10, 2014"))
        earthquakes.add(Earthquake(5.4F,"Mexico City", "May 3, 2014"))
        earthquakes.add(Earthquake(2.8F,"Moscow", "Jan 31, 2013"))
        earthquakes.add(Earthquake(4.9F,"Rio de Janeiro", "Aug 19, 2012"))
        earthquakes.add(Earthquake(1.6F,"Paris", "Oct 30, 2011"))

        // Find a reference to the {@link ListView} in the layout
        val earthquakeListView = findViewById<ListView>(R.id.list)

        // Create a new {@link ArrayAdapter} of earthquakes
        val adapter = EarthquakeAdapter(this, earthquakes)

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.adapter = adapter
    }
}
