package com.coursera.aad.capstoneapp.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coursera.aad.capstoneapp.models.Statistic;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.coursera.aad.capstoneapp.utils.Constants.COLUMN_ID;
import static com.coursera.aad.capstoneapp.utils.Constants.COLUMN_NAME;
import static com.coursera.aad.capstoneapp.utils.Constants.STATISTICS_TABLE_NAME;

public class StatisticsDao {

    private Cursor cursor;
    private SQLiteDatabase mDB;
    private ContentValues contentValues;

    public boolean insertAll(List<Statistic> statistics) {
        boolean res = false;
        try {
            for (Statistic statistic : statistics) {
                // Set content value
                setContentValue(statistic);

                // Insert statistic into database
                res = mDB.insert(STATISTICS_TABLE_NAME,
                        null, getContentValue()) > 0;
            }

            return res;
        } catch (Exception e) {
            Timber.e(e);

            return res;
        }
    }

    public List<Statistic> readAll()  {
        List<Statistic> statistics = new ArrayList<>();
        try {
            cursor = this.mDB.rawQuery(
                    "SELECT * FROM " + STATISTICS_TABLE_NAME,
                    null );
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    statistics.add(cursorToEntity(cursor));
                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            Timber.e(e);
        }

        return statistics;
    }

    public int deleteAll() {
        try {
            return this.mDB.delete(STATISTICS_TABLE_NAME,
                    null, null);
        } catch (Exception e) {
            Timber.e(e);
            return -1;
        }
    }

    protected Statistic cursorToEntity(Cursor cursor) {
        Statistic statistic = null;
        try {
            int idIndex;
            int countryNameIndex;

            if (cursor != null) {
                statistic = new Statistic();
                if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                    idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                    statistic.setId(cursor.getInt(idIndex));
                }
                if (cursor.getColumnIndex(COLUMN_NAME) != -1) {
                    countryNameIndex = cursor.getColumnIndexOrThrow(
                            COLUMN_NAME);
//                    statistic.setName(cursor.getString(countryNameIndex));
                }

            }

        } catch (Exception e) {
            Timber.e(e);
        }

        return statistic;
    }

    private void setContentValue(Statistic statistic) {
        try {
            contentValues = new ContentValues();
//            contentValues.put(COLUMN_NAME, statistic.getName());
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private ContentValues getContentValue() {
        return contentValues;
    }
}
