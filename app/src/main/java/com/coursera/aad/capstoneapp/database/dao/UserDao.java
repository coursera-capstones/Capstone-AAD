package com.coursera.aad.capstoneapp.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coursera.aad.capstoneapp.database.UserDbHelper;
import com.coursera.aad.capstoneapp.models.User;

import timber.log.Timber;

import static com.coursera.aad.capstoneapp.utils.Constants.COLUMN_EMAIL;
import static com.coursera.aad.capstoneapp.utils.Constants.COLUMN_FULL_NAME;
import static com.coursera.aad.capstoneapp.utils.Constants.COLUMN_ID;
import static com.coursera.aad.capstoneapp.utils.Constants.COLUMN_IS_SIGN_IN;
import static com.coursera.aad.capstoneapp.utils.Constants.COLUMN_PASSWORD;
import static com.coursera.aad.capstoneapp.utils.Constants.COLUMN_STATUS;
import static com.coursera.aad.capstoneapp.utils.Constants.USER_TABLE_NAME;

public class UserDao {

    private Cursor cursor;
    private SQLiteDatabase mDB;
    private ContentValues contentValues;

    public UserDao(UserDbHelper userDbHelper) {
        this.mDB = userDbHelper.getWritableDatabase();
    }

    public boolean insert(User user) {
        try {
            // Set content value
            setContentValue(user);

            // Insert user into database
            return mDB.insert(USER_TABLE_NAME,
                    null, getContentValue()) > 0;
        } catch (Exception e) {
            Timber.e(e);

            return false;
        }
    }

    public User read(int id) {
        User user = null;
        try {
            cursor = this.mDB.rawQuery(
                    "SELECT * FROM "
                            + USER_TABLE_NAME
                            + " WHERE "
                            + COLUMN_ID + " = " + id,
                    null );
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    user = cursorToEntity(cursor);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            Timber.e(e);
        }
        return user;
    }

    public User authUser(String email, String password) {
        User user = null;
        try {
            cursor = this.mDB.rawQuery(
                    "SELECT * FROM "
                            + USER_TABLE_NAME
                            + " WHERE "
                            + COLUMN_EMAIL + " = ? "
                            + " AND " + COLUMN_PASSWORD + " = ? ",
                    new String[]{email, password} );
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    user = cursorToEntity(cursor);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            Timber.e(e);
        }
        return user;
    }

    public boolean update(User user) {
        try {
            // Set content value
            setContentValue(user);

            // Update user in database
            return this.mDB.update(USER_TABLE_NAME, getContentValue(),
                    COLUMN_ID + " = ? ",
                    new String[] { Integer.toString(user.getId()) }) > 0;
        } catch (Exception e) {
            Timber.e(e);

            return false;
        }
    }

    public int delete(User user) {
        try {
            return this.mDB.delete(USER_TABLE_NAME, COLUMN_ID + " = ? ",
                    new String[] { Integer.toString(user.getId()) });
        } catch (Exception e) {
            Timber.e(e);
            return -1;
        }
    }

    protected User cursorToEntity(Cursor cursor) {
        User user = null;
        try {
            int idIndex;
            int userNameIndex;
            int emailIndex;

            if (cursor != null) {
                user = new User();
                if (cursor.getColumnIndex(String.valueOf(COLUMN_ID)) != -1) {
                    idIndex = cursor.getColumnIndexOrThrow(String.valueOf(COLUMN_ID));
                    user.setId(cursor.getInt(idIndex));
                }
                if (cursor.getColumnIndex(COLUMN_FULL_NAME) != -1) {
                    userNameIndex = cursor.getColumnIndexOrThrow(
                            COLUMN_FULL_NAME);
                    user.setFullName(cursor.getString(userNameIndex));
                }
                if (cursor.getColumnIndex(COLUMN_EMAIL) != -1) {
                    emailIndex = cursor.getColumnIndexOrThrow(
                            COLUMN_EMAIL);
                    user.setEmail(cursor.getString(emailIndex));
                }

            }

        } catch (Exception e) {
            Timber.e(e);
        }

        return user;
    }

    private void setContentValue(User user) {
        try {
            contentValues = new ContentValues();
            contentValues.put(COLUMN_FULL_NAME, user.getFullName());
            contentValues.put(COLUMN_EMAIL, user.getEmail());
            contentValues.put(COLUMN_PASSWORD, user.getPassword());
            contentValues.put(COLUMN_STATUS, user.getStatus());
            contentValues.put(COLUMN_IS_SIGN_IN, user.getSignIn());
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private ContentValues getContentValue() {
        return contentValues;
    }
}
