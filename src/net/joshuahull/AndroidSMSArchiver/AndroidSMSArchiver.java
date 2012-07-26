package net.joshuahull.AndroidSMSArchiver;

import android.app.Activity;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;
import android.net.Uri;
import java.util.Arrays;
import java.util.Collections;
import android.util.Log;
import org.json.JSONObject;
import org.json.JSONArray;


public class AndroidSMSArchiver extends Activity
{
    private static final String TAG = "AndroidSMSArchiver";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void startBackup (View view) {
	JSONObject file = new JSONObject(); 
	
	Log.d(TAG,"Inflating backup view.");
	setContentView(R.layout.backup);
	
	Log.d(TAG,"Getting thread_cursor");
	Cursor thread_cursor = getContentResolver().query(Uri.parse("content://sms/conversations"),null,null,null,null);
	thread_cursor.moveToFirst();
	
	TextView t = new TextView(this);
	t = (TextView)findViewById(R.id.backing_up_info);
	t.setText("Threads found: " + thread_cursor.getCount() + "\n");
	
	int[] threadIds = new int[thread_cursor.getCount()];
	
	Log.d(TAG,"Gathering threadIds[].");
	//Gather thread Ids into threadIds[].
	for(int i = 0; i < thread_cursor.getCount(); i++) {
		threadIds[i]=thread_cursor.getInt(thread_cursor.getColumnIndex("thread_id"));
		thread_cursor.moveToNext();	
    	}
	
	Log.d(TAG,"Found " + threadIds.length + " threads.");
	//For Each thread ID get list of texts with matching thread ID.
	JSONArray thread = new JSONArray();
	for(int i = 0; i < threadIds.length; i++) {
		Cursor conversation_cursor = getContentResolver().query(Uri.parse("content://sms/conversations/" + threadIds[i]) ,null,null,null,null);
		try{
			thread.put(threadIds[i]);
		} catch (Exception e) {
			Log.e(TAG,e.toString());
		}
		
	}
	Log.d(TAG,"Finished!!");
	t.append("Finished backing up!!\n");
	String debug_file_string = "";
	try{
	file.put("_id",thread);
	} catch (Exception e) {
		Log.e(TAG,e.toString());
	}
	try{
		debug_file_string = file.toString(4);
	} catch (Exception e) {
		Log.e(TAG,e.toString());
	}
	t.append(debug_file_string);
    }	
}
