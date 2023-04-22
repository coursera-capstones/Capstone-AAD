package com.coursera.aad.capstoneapp.database.dao;

import static com.coursera.aad.capstoneapp.utils.Constants.COLUMN_NAME;
import static com.coursera.aad.capstoneapp.utils.Constants.COUNTRIES_TABLE_NAME;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coursera.aad.capstoneapp.database.CountriesDbHelper;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class CountryDao {

    private Cursor cursor;
    private SQLiteDatabase mDB;
    private ContentValues contentValues;

    public CountryDao(CountriesDbHelper countriesDbHelper) {
        this.mDB = countriesDbHelper.getWritableDatabase();
        countriesDbHelper.onCreate(mDB);
    }

    public boolean insertAll(List<String> countries) {
        boolean res = false;
        try {
            for (String country : countries) {
                // Set content value
                setContentValue(country);

                // Insert country into database
                res = mDB.insertWithOnConflict(COUNTRIES_TABLE_NAME,
                        null, getContentValue(),
                        SQLiteDatabase.CONFLICT_REPLACE) > 0;

                Thread.sleep(200);
            }

            return res;
        } catch (Exception e) {
            Timber.e(e);

            return res;
        }
    }

    public List<String> readAll()  {
        List<String> countries = new ArrayList<>();
        try {
            cursor = this.mDB.rawQuery(
                    "SELECT * FROM " + COUNTRIES_TABLE_NAME,
                    null );
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    countries.add(cursorToEntity(cursor));
                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            Timber.e(e);
        }

        return countries;
    }

    public void deleteAll() {
        try {
            this.mDB.execSQL(
                    "DELETE FROM " + COUNTRIES_TABLE_NAME,
                    null );
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public String cursorToEntity(Cursor cursor) {
        String country = null;
        try {
            int idIndex;
            int countryNameIndex;

            if (cursor != null) {
                if (cursor.getColumnIndex(COLUMN_NAME) != -1) {
                    countryNameIndex = cursor.getColumnIndexOrThrow(
                            COLUMN_NAME);
                    country = cursor.getString(countryNameIndex);
                }

            }

        } catch (Exception e) {
            Timber.e(e);
        }

        return country;
    }

    private void setContentValue(String country) {
        try {
            contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, country);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private ContentValues getContentValue() {
        return contentValues;
    }
}
