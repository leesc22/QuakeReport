package com.github.leesc22.quakereport

/**
 * An {@link Earthquake} object contains information related to a single earthquake.
 *
 * Constructs a new {@link Earthquake} object.
 *
 * @param magnitude is the magnitude (size) of the earthquake
 * @param location is the city location of the earthquake
 * @param date is the date the earthquake happened
 */
class Earthquake constructor(var magnitude: Float, var location: String, var date: String)