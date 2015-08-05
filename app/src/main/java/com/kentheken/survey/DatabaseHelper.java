package com.kentheken.survey;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kentheken.survey.models.Item;
import com.kentheken.survey.models.ResponseScale;
import com.kentheken.survey.models.Survey;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SurveyDatabaseHelper";
    private static final int DB_VERSION = 1;

    private static final String TABLE_SESSION_RESPONSES = "session_responses";
    private static final String TABLE_ITEMS = "items";

    private static final String COL_ID = "_id";
    private static final String COL_TEXT = "text";
    private static final String COL_ITEM_ID = "item_id";
    private static final String COL_RESPONSE_OPTION_ID = "response_option_id";

    private SQLiteDatabase mDatabase;
    private final Context mContext;
    private final String mDbName;
    private String mPath;

    public SQLiteDatabase getDb() {
        return mDatabase;
    }

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context activity or application context
     * @param dbName filename for database
     */
    public DatabaseHelper(Context context, String dbName) {
        super(context, dbName, null, DB_VERSION);
        Log.i(TAG, "init");
        mDbName = dbName;
        mContext = context;
        openDatabase();
    }   
    
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDatabase() {
        Log.i(TAG, "createDatabase");
        boolean dbExists = checkDatabase();

        if (!dbExists) {
            // By calling this method, an empty database will be created into the default system path
            // of your application so we are able to overwrite that database with ours.
            getReadableDatabase();

            try {
                copyDatabase();
            } catch (IOException e) {
                Log.e(TAG, "Couldn't copy database", e);
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDatabase() {
        Log.i(TAG, "checkDatabase");
        SQLiteDatabase database = null;

        try {
            database = SQLiteDatabase.openDatabase(getPath(), null, SQLiteDatabase.OPEN_READONLY);
        } catch(SQLiteException e) {
            Log.e(TAG, "Error while checking db");                
        }

        if (database != null)
            database.close();

        return database != null;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transferring bytestream.
     * */
    private void copyDatabase() throws IOException {
        Log.i(TAG, "copyDatabase");
        //Open your local db as the input stream
        InputStream myInput = mContext.getAssets().open(mDbName);

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(getPath());

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private String getPath() {
        if (mPath == null) {
            mPath = mContext.getDatabasePath(mDbName).getPath();
            Log.i(TAG, "Path: " + mPath);
        }
        return mPath;
    }

    public SQLiteDatabase openDatabase() throws SQLException {
        Log.i(TAG, "openDatabase");
        //Open the database          
        if (mDatabase == null) {
            createDatabase();
            mDatabase = SQLiteDatabase.openDatabase(getPath(), null, SQLiteDatabase.OPEN_READONLY);
        }
        return mDatabase;
    }

    @Override
    public synchronized void close() {
        Log.i(TAG, "close");
        if (mDatabase != null)
            mDatabase.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade");
    }

    // public helper methods to access and get content from the database.
    public ResponseScale getItemResponseScale(Item item) {
        Cursor cursor = mDatabase.rawQuery("SELECT ro._id, ro.text FROM response_options ro " +
                "JOIN response_scale_response_options rsro ON ro._id = rsro.response_option_id " +
                "JOIN items i ON i.response_scale_id = rsro.response_scale_id " +
                "WHERE i._id = ? ORDER BY rsro.sequence", new String[]{Integer.toString(item.getId())});
        ResponseScale scale = new ResponseScale();
        if (cursor != null) {
            try {
                cursor.moveToFirst();
                do {
                    int optionId = cursor.getInt(cursor.getColumnIndex(COL_ID));
                    String optionText = cursor.getString(cursor.getColumnIndex(COL_TEXT));
                    scale.addOption(optionId, optionText);
                } while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
        }
        return scale;
    }
    
    public Survey getSurvey() {
        Survey survey = Survey.get(mContext);
        if (survey.getItemCount() > 0) // com.kentheken.survey already populated
            return survey;
        Cursor cursor = mDatabase.query(TABLE_ITEMS, new String[] {COL_ID, COL_TEXT}, null, null,
                null, null, "RANDOM()", "5");
        if (cursor != null) {
            try {
                cursor.moveToFirst();
                do {
                    int itemId = cursor.getInt(cursor.getColumnIndex(COL_ID));
                    String itemText = cursor.getString(cursor.getColumnIndex(COL_TEXT));
                    survey.addItem(itemId, itemText);
                } while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
        }        
        return survey;
    }

    public void setSession() {
        ContentValues values = new ContentValues();
        for (Item item : Survey.get(mContext).getItems()) {
            values.put(COL_ITEM_ID, item.getId());
            values.put(COL_RESPONSE_OPTION_ID, item.getResponse().getId());
            getWritableDatabase().insert(TABLE_SESSION_RESPONSES, null, values);
        }
    }

    public ResponseScale getOptionStats(Item item) {
        ResponseScale scale = getItemResponseScale(item);
        Cursor cursor = mDatabase.query(TABLE_SESSION_RESPONSES,
                new String[] {COL_RESPONSE_OPTION_ID, "COUNT(*)"}, COL_ITEM_ID + " = ?",
                new String[] {String.valueOf(item.getId())}, COL_RESPONSE_OPTION_ID, null, null);
        if (cursor != null) {
            try {
                cursor.moveToFirst();
                do {
                    int optionId = cursor.getInt(cursor.getColumnIndex(COL_RESPONSE_OPTION_ID));
                    int optionCount = cursor.getInt(cursor.getColumnIndex("COUNT(*)"));
                    scale.getOption(optionId).setCount(optionCount);
                } while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
        }
        return scale;
    }

    public Item getRandomItem() {
        Cursor cursor = mDatabase.query(TABLE_ITEMS, new String[]{COL_ID, COL_TEXT}, null, null,
                null, null, "RANDOM()", "1");
        String itemText = null;
        int itemId = -1;
        if (cursor != null) {
            try {
                cursor.moveToFirst();
                itemText = cursor.getString(cursor.getColumnIndex("text"));
                itemId = cursor.getInt(cursor.getColumnIndex("_id"));
            } finally {
                cursor.close();
            }
        }
        Log.i(TAG, "Retrieved item #" + itemId + ": " + itemText);
        return new Item(itemId, itemText);
    }
}