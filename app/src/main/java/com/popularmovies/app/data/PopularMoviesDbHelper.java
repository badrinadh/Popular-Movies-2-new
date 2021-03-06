package com.popularmovies.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.popularmovies.app.data.PopularMoviesContract.MovieEntry;
import com.popularmovies.app.data.PopularMoviesContract.MovieReviewEntry;
import com.popularmovies.app.data.PopularMoviesContract.MovieTrailerEntry;

public class PopularMoviesDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "popular_movies.db";

    public PopularMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY," +
                MovieEntry.COLUMN_MOVIE_ID + " INTEGER UNIQUE NOT NULL, " +
                MovieEntry.COLUMN_IS_ADULT + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_BACK_DROP_PATH + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL," +
                MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_IS_VIDEO + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                MovieEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_RUNTIME + " REAL, " +
                MovieEntry.COLUMN_STATUS + " TEXT " +
                " );";

        final String SQL_CREATE_TRAILER_TABLE = "CREATE TABLE " + MovieTrailerEntry.TABLE_NAME + " (" +
                MovieTrailerEntry._ID + " INTEGER PRIMARY KEY," +
                MovieTrailerEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieTrailerEntry.COLUMN_TRAILER_ID + " TEXT NOT NULL, " +
                MovieTrailerEntry.COLUMN_ISO_369_1 + " TEXT NOT NULL, " +
                MovieTrailerEntry.COLUMN_KEY + " TEXT NOT NULL, " +
                MovieTrailerEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                MovieTrailerEntry.COLUMN_SITE + " TEXT NOT NULL, " +
                MovieTrailerEntry.COLUMN_SIZE + " TEXT NOT NULL, " +
                MovieTrailerEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                MovieTrailerEntry.COLUMN_DATE + " INTEGER NOT NULL, " +

                // Set up the movie_id column as a foreign key to movie table.
                " FOREIGN KEY (" + MovieTrailerEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MovieEntry.TABLE_NAME + " (" + MovieEntry.COLUMN_MOVIE_ID + ") " +

                " UNIQUE (" + MovieTrailerEntry.COLUMN_MOVIE_ID + ", " +
                MovieTrailerEntry.COLUMN_TRAILER_ID + ") ON CONFLICT REPLACE);";


        final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE " + MovieReviewEntry.TABLE_NAME + " (" +
                MovieReviewEntry._ID + " INTEGER PRIMARY KEY," +
                MovieReviewEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieReviewEntry.COLUMN_REVIEW_ID + " TEXT NOT NULL, " +
                MovieReviewEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                MovieReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
                MovieReviewEntry.COLUMN_URL + " TEXT NOT NULL, " +
                MovieReviewEntry.COLUMN_DATE + " INTEGER NOT NULL, " +

                // Set up the movie_id column as a foreign key to movie table.
                " FOREIGN KEY (" + MovieReviewEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MovieEntry.TABLE_NAME + " (" + MovieEntry.COLUMN_MOVIE_ID + ") " +

                " UNIQUE (" + MovieReviewEntry.COLUMN_MOVIE_ID + ", " +
                MovieReviewEntry.COLUMN_REVIEW_ID + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TRAILER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_REVIEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieTrailerEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieReviewEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
