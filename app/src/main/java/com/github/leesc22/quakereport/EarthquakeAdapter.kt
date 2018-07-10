package com.github.leesc22.quakereport

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import java.text.SimpleDateFormat
import java.util.*


/**
 * An {@link EarthquakeAdapter} knows how to create a list item layout for each earthquake
 * in the data source (a list of {@link Earthquake} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView to be displayed to the user.
 *
 * Constructs a new {@link EarthquakeAdapter}.
 *
 * @param context of the app
 * @param earthquakes is the list of earthquakes, which is the data source of the adapter
 */
class EarthquakeAdapter(context:Context, earthquakes:List<Earthquake>) : ArrayAdapter<Earthquake>(context, 0, earthquakes) {

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

        // Find the TextView with view ID magnitude
        val magnitudeTextView = listItemView!!.findViewById(R.id.magnitude_text_view) as TextView
        // Display the magnitude of the current earthquake in that TextView
        magnitudeTextView.text = currentEarthquake.magnitude.toString()

        // Find the TextView with view ID location
        val placeTextView = listItemView!!.findViewById(R.id.location_text_view) as TextView
        // Display the location of the current earthquake in that TextView
        placeTextView.text = currentEarthquake.location

        // Create a new Date object from the time in milliseconds of the earthquake
        val dateObject = Date(currentEarthquake.timeInMilliseconds)

        // Find the TextView with view ID date
        val dateTextView = listItemView!!.findViewById(R.id.date_text_view) as TextView
        // Format the date string (i.e. "Mar 3, 1984")
        val formattedDate = formatDate(dateObject)
        // Display the date of the current earthquake in that TextView
        dateTextView.text = formattedDate

        // Find the TextView with view ID time
        val timeTextView = listItemView!!.findViewById(R.id.time_text_view) as TextView
        // Format the time string (i.e. "4:30PM")
        val formattedTime = formatTime(dateObject)
        // Display the date of the current earthquake in that TextView
        timeTextView.text = formattedTime

        // Return the list item view that is now showing the appropriate data
        return listItemView
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private fun formatDate(dateObject: Date): String {
        val dateFormat = SimpleDateFormat("LLL dd, yyyy", Locale.US)
        return dateFormat.format(dateObject)
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private fun formatTime(dateObject: Date): String {
        val timeFormat = SimpleDateFormat("h:mm a", Locale.US)
        return timeFormat.format(dateObject)
    }
}