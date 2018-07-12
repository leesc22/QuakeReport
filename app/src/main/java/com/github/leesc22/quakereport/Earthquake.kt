package com.github.leesc22.quakereport

/**
 * An {@link Earthquake} object contains information related to a single earthquake.
 *
 * Constructs a new {@link Earthquake} object.
 *
 * @param magnitude is the magnitude (size) of the earthquake
 * @param location is the location where the earthquake happened
 * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
 * earthquake happened
 */
class Earthquake constructor(var magnitude: Double, var location: String, var timeInMilliseconds: Long)