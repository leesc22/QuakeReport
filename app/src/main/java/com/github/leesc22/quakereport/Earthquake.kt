package com.github.leesc22.quakereport

/**
 * An {@link Earthquake} object contains information related to a single earthquake.
 *
 * Constructs a new {@link Earthquake} object.
 *
 * @param magnitude is the magnitude (size) of the earthquake
 * @param location is the location where the earthquake happened
 * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
 *                           earthquake happened
 * @param url is the website URL to find more details about the earthquake
 */
class Earthquake constructor(var magnitude: Double, var location: String, var timeInMilliseconds: Long, var url: String)