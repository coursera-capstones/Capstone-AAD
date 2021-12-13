package com.coursera.aad.capstoneapp.database.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.coursera.aad.capstoneapp.database.CountriesDbHelper;
import com.coursera.aad.capstoneapp.database.dao.CountryDao;

import timber.log.Timber;

import static com.coursera.aad.capstoneapp.utils.Constants.ALL_COUNTRIES;
import static com.coursera.aad.capstoneapp.utils.Constants.AUTHORITY;
import static com.coursera.aad.capstoneapp.utils.Constants.COLUMN_ID;
import static com.coursera.aad.capstoneapp.utils.Constants.COUNTRIES_TABLE_NAME;
import static com.coursera.aad.capstoneapp.utils.Constants.SINGLE_COUNTRY;

public class CountryDBContentProvider extends ContentProvider {

    public static final Uri CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + COUNTRIES_TABLE_NAME);
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "countries", ALL_COUNTRIES);
        uriMatcher.addURI(AUTHORITY, "countries/#", SINGLE_COUNTRY);
    }
    public SQLiteDatabase mDb;
    private CountryDao countryDao;
    private CountriesDbHelper countriesDbHelper;

    public CountryDBContentProvider() { }

    @Override
    public boolean onCreate() {
        try {
            // get access to the database helper
            countriesDbHelper = new CountriesDbHelper(getContext());
        } catch (Exception e) {
            Timber.e(e);
        }

        return true;
    }

    /**
     * The query() method must return a Cursor object, or if it fails,
     * throw an Exception. If you are using an SQLite database as your data storage,
     * you can simply return the Cursor returned by one of the query() methods of the
     * SQLiteDatabase class. If the query does not match any rows, you should return a
     * Cursor instance whose getCount() method returns 0. You should return null only
     * if an internal error occurred during the query process.
     * @param uri The content uri
     * @param projection Projection
     * @param selection Selection
     * @param selectionArgs Selection arguments
     * @param sortOrder Selection's order
     * @return Cursor, otherwise null if an internal error
     * occurred during the process
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

            SQLiteDatabase db = countriesDbHelper.getWritableDatabase();
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(COUNTRIES_TABLE_NAME);

            switch (uriMatcher.match(uri)) {
                case ALL_COUNTRIES:
                    //do nothing
                    break;
                case SINGLE_COUNTRY:
                    String id = uri.getPathSegments().get(1);
                    queryBuilder.appendWhere(COLUMN_ID + "=" + id);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
            }

        return queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
    }

    /**
     * Return the MIME type corresponding to a content URI
     * @param uri Content URI
     * @return MIME_Type
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALL_COUNTRIES:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY + ".countries";
            case SINGLE_COUNTRY:
                return "vnd.android.cursor.item/vnd." + AUTHORITY + ".countries";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    /**
     * The insert() method adds a new row to the appropriate table, using the values
     * in the ContentValues argument. If a column name is not in the ContentValues argument,
     * you may want to provide a default value for it either in your provider code or in
     * your database schema.
     * @param uri The uri
     * @param values The values to insert
     * @return Uri
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = countriesDbHelper.getWritableDatabase();
        if (uriMatcher.match(uri) != ALL_COUNTRIES) {
           throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        long id = db.insert(COUNTRIES_TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(CONTENT_URI + "/" + id);
    }

    /**
     * The delete() method deletes rows based on the seletion or if an id is
     *  provided then it deleted a single row. The methods returns the numbers
     *  of records delete from the database. If you choose not to delete the data
     *  physically then just update a flag here.
     * @param uri Content uri
     * @param selection Query selection
     * @param selectionArgs Selection args
     * @return Number of records delete from the database
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        SQLiteDatabase db = countriesDbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_COUNTRIES:
                //do nothing
                break;
            case SINGLE_COUNTRY:
                String id = uri.getPathSegments().get(1);
                selection = COLUMN_ID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int deleteCount = db.delete(COUNTRIES_TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return deleteCount;
    }

    /**
     * The update method() is same as delete() which updates multiple rows
     * based on the selection or a single row if the row id is provided. The
     * update method returns the number of updated rows.
     * @param uri The content uri
     * @param values The new value
     * @param selection The query selection
     * @param selectionArgs The selection args
     * @return Number of updated rows
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = countriesDbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_COUNTRIES:
                //do nothing
                break;
            case SINGLE_COUNTRY:
                String id = uri.getPathSegments().get(1);
                selection = COLUMN_ID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int updateCount = db.update(COUNTRIES_TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;
    }
}
