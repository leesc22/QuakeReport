package com.github.leesc22.quakereport

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView


class EarthquakeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earthquake)

        // Get the list of earthquakes from {@link QueryUtils}
        val earthquakes = QueryUtils.extractEarthquakes()

        // Find a reference to the {@link ListView} in the layout
        val earthquakeListView = findViewById<ListView>(R.id.list)

        // Create a new adapter that takes the list of earthquakes as input
        val adapter = EarthquakeAdapter(this, earthquakes)

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.adapter = adapter

        earthquakeListView.setOnItemClickListener { _, _, i, _ ->
            // Find the current earthquake that was clicked on
            val currentEarthquake = adapter.getItem(i)

            // Convert the String URL into a URI object (to pass into the Intent constructor)
            val earthquakeUri  = Uri.parse(currentEarthquake.url)

            // Create a new intent to view the earthquake URI
            val websiteIntent = Intent(Intent.ACTION_VIEW, earthquakeUri)

            // Send the intent to launch a new activity
            startActivity(websiteIntent)
        }
    }
}
