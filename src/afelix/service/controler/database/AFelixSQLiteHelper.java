/**
 * 
 * @author bxs3514
 *
 * This is a android felix launcher.
 *
 * @lastEdit 11/23/2014
 * 
 */

package afelix.service.controler.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class AFelixSQLiteHelper extends SQLiteOpenHelper{
	
	private static final String TAG = "AFelixSQLiteHelper";
	
	private static final String DATABASE_NAME = "AndroidFelix.db";
	private static final int DATABASE_INITIAL_VERSION = 1;
	
	private String TABLE_CREATE_SQL;
	
	private InputStream initCreateStream;
	
	
	public AFelixSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_INITIAL_VERSION);
		
		try {
			initCreateStream = context.getResources().getAssets().open("InitFiles/DatabaseInit.txt");
			
			TABLE_CREATE_SQL = IOUtils.toString(initCreateStream, "utf-8");
			initCreateStream.close();
			
			
		} catch (IOException ie) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Can't open table inital file.\n", ie);
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d(TAG,"Database is about to create.");
		
		String[] tempSqlArray = TABLE_CREATE_SQL.split(";");
		
		for(String tempSql : tempSqlArray){
			if(!tempSql.equals("\\s+") && !tempSql.equals("\n")){
				try{
					db.execSQL(tempSql);
				}catch(SQLiteException se){
					
					Log.e(TAG, "Wrong SQL grammer:\n" + tempSql, se);
				}
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(TAG, "Upgrading database from version" + oldVersion + " to " + newVersion);
		
	}
}
