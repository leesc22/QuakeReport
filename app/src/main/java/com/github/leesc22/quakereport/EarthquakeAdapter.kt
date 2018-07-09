package com.github.leesc22.quakereport

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * An {@link EarthquakeAdapter} knows how to create a list item layout for each earthquake
 * in the data source (a list of {@link Earthquake} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView to be displayed to the user.
 */
class EarthquakeAdapter(context:Activity, earthquakes:ArrayList<Earthquake>) : ArrayAdapter<Earthquake>(context, 0, earthquakes) {

    /**
     * Returns a list item view that displays information about the earthquake at the given position
     * in the list of earthquakes.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Check if there is an existing list item view (called covertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        // Find the earthquake at the given position in the list of earthquakes.
        val currentEarthquake = getItem(position)

        val magnitudeTextView = listItemView!!.findViewById(R.id.magnitude_text_view) as TextView
        magnitudeTextView.text = currentEarthquake.magnitude.toString()

        val placeTextView = listItemView!!.findViewById(R.id.location_text_view) as TextView
        placeTextView.text = currentEarthquake.location

        val dateTextView = listItemView!!.findViewById(R.id.date_text_view) as TextView
        dateTextView.text = currentEarthquake.date

        // Return the list item view that is now showing the appropriate data
        return listItemView
    }
}