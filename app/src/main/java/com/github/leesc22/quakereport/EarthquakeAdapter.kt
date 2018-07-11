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
 * The part of the location string from the USGS service that we use to determine
 * whether or not there is a location offset present ("5km N of Cairo, Egypt").
 */
private const val LOCATION_SEPARATOR = " of "

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

        // Get the original location string from the Earthquake object,
        // which can be in the format of "5km N of Cairo, Egypt" or "Pacific-Antarctic Ridge".
        val originalLocation  = currentEarthquake.location

        // If the original location string (i.e. "5km N of Cairo, Egypt") contains
        // a primary location (Cairo, Egypt) and a location offset (5km N of that city)
        // then store the primary location separately from the location offset in 2 Strings,
        // so they can be displayed in 2 TextViews.
        var primaryLocation: String
        var locationOffset: String

        // Check whether the originalLocation string contains the " of " text
        if (originalLocation .contains(LOCATION_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the " of " text. We expect an array of 2 Strings, where
            // the first String will be "5km N of" and the second String will be "Cairo, Egypt".
            val parts  = originalLocation .split("(?<=$LOCATION_SEPARATOR)".toRegex())
            // Location offset should be "5km N of"
            locationOffset = parts[0]
            // Primary location should be "Cairo, Egypt"
            primaryLocation = parts[1]
        } else {
            // Otherwise, there is no " of " text in the originalLocation string.
            // Hence, set the default location offset to say "Near the".
            locationOffset = context.getString(R.string.near_the)
            // The primary location will be the full location string "Pacific-Antarctic Ridge".
            primaryLocation = originalLocation
        }

        // Find the TextView with view ID location_offset
        val locationOffsetTextView = listItemView!!.findViewById(R.id.location_offset_text_view) as TextView
        // Display the location offset of the current earthquake in that TextView
        locationOffsetTextView.text = locationOffset

        // Find the TextView with view ID primary_location
        val primaryLocationTextView = listItemView!!.findViewById(R.id.primary_location_text_view) as TextView
        // Display the location of the current earthquake in that TextView
        primaryLocationTextView.text = primaryLocation

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