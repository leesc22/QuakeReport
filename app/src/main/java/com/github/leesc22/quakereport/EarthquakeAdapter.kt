package com.github.leesc22.quakereport

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
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
        // Format the magnitude to show 1 decimal place
        val formattedMagnitude = formatMagnitude(currentEarthquake.magnitude)
        // Display the magnitude of the current earthquake in that TextView
        magnitudeTextView.text = formattedMagnitude

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        val magnitudeCircle = magnitudeTextView.background as GradientDrawable

        // Get the appropriate background color based on the current earthquake magnitude
        val magnitudeColor: Int = getMagnitudeColor(currentEarthquake.magnitude)

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor)

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
     * Return the color for the magnitude circle based on the intensity of the earthquake.
     *
     * @param magnitude of the earthquake
     */
    private fun getMagnitudeColor(magnitude: Double): Int {
        val magnitudeColorResourceId: Int
        when (magnitude) {
            in 0 until 2 -> magnitudeColorResourceId = R.color.magnitude1
            in 2 until 3 -> magnitudeColorResourceId = R.color.magnitude2
            in 3 until 4 -> magnitudeColorResourceId = R.color.magnitude3
            in 4 until 5 -> magnitudeColorResourceId = R.color.magnitude4
            in 5 until 6 -> magnitudeColorResourceId = R.color.magnitude5
            in 6 until 7 -> magnitudeColorResourceId = R.color.magnitude6
            in 7 until 8 -> magnitudeColorResourceId = R.color.magnitude7
            in 8 until 9 -> magnitudeColorResourceId = R.color.magnitude8
            in 9 until 10 -> magnitudeColorResourceId = R.color.magnitude9
            else -> magnitudeColorResourceId = R.color.magnitude10plus
        }

        return ContextCompat.getColor(context, magnitudeColorResourceId)
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private fun formatMagnitude(magnitude: Double): String {
        return ("%.1f").format(magnitude)
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