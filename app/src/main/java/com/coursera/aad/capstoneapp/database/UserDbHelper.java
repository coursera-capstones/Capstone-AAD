package com.coursera.aad.capstoneapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import timber.log.Timber;

import static com.coursera.aad.capstoneapp.utils.Constants.DB_NAME;
import static com.coursera.aad.capstoneapp.utils.Constants.DB_VERSION;
import static com.coursera.aad.capstoneapp.utils.Constants.DROP_USER_TABLE;
import static com.coursera.aad.capstoneapp.utils.Constants.userTableScript;

public class UserDbHelper extends SQLiteOpenHelper {

    public UserDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Execute Script
            db.execSQL(userTableScript);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        try {
            db.execSQL(DROP_USER_TABLE);
            onCreate(db);
        } catch (Exception e) {
            Timber.e(e);
        }
    }
}
