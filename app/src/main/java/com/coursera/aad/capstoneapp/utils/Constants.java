package com.coursera.aad.capstoneapp.utils;

import com.coursera.aad.capstoneapp.models.Statistic;
import com.coursera.aad.capstoneapp.models.User;

public class Constants {
    // Base url
    public static final String API_BASE_URL = "https://covid-193.p.rapidapi.com/";
    /* Database properties */
    // Database version
    public static final int DB_VERSION = 1;

    // Date format
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    // Database name
    public static final String DB_NAME = "CAPSTONE_DB_NAME";

    // Tables name
    public static final String USER_TABLE_NAME = User.class.getSimpleName().toLowerCase() + "_tb";
    public static final String COUNTRIES_TABLE_NAME = "countries_tb";
    public static final String STATISTICS_TABLE_NAME = Statistic.class.getSimpleName().toLowerCase() + "_tb";

    // User table column
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FULL_NAME = "fullName";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_IS_SIGN_IN = "sign_in";

    // Script
    public static final String userTableScript = "CREATE TABLE IF NOT EXISTS "
            + USER_TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_FULL_NAME + " TEXT, "
            + COLUMN_EMAIL + " TEXT NOT NULL, "
            + COLUMN_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_STATUS + " INTEGER, "
            + COLUMN_IS_SIGN_IN + " BOOLEAN) ";
    // Script
    public static final String countriesTableScript = "CREATE TABLE IF NOT EXISTS "
            + COUNTRIES_TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL UNIQUE ) ";

    // Drop table script
    public static final String DROP_USER_TABLE =
            "DROP TABLE IF EXISTS " + USER_TABLE_NAME;

    // Intents extra keys
    public static final String RECEIVER_EXTRA = "receiver";
    public static final String COMMAND_EXTRA = "command";
    public static final String QUERY_EXTRA = "query";
    public static final String RESULTS_EXTRA = "results";
    public static final String COUNTRY_EXTRA = "country";
    public static final String DAY_EXTRA = "day";

    // Country content provider authority
    public static final String AUTHORITY = "com.coursera.aad.capstoneapp.country.provider";
    public static final int ALL_COUNTRIES = 1;
    public static final int SINGLE_COUNTRY = 2;
}
