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
	
	Log.d(TAG,"Iterating through threadIds[] to get time stamps.");
	//For Each thread ID get list of texts with matching thread ID.
	for(int i = 0; i < threadIds.length; i++) {
		Cursor conversation_cursor = getContentResolver().query(Uri.parse("content://sms/conversations/" + threadIds[i]) ,null,null,null,null);
		String[] timeStamps = new String[conversation_cursor.getCount()];
		int[] sortedIds = new int[conversation_cursor.getCount()];
		
		//Get time stamps for all messages with current thread_id
		for(int j = 0; j < conversation_cursor.getCount(); j++) {
			timeStamps[j] = conversation_cursor.getString(conversation_cursor.getColumnIndex("date"));
			conversation_cursor.moveToNext();
		}
		//Sort time stamps into descending order.
		Arrays.sort(timeStamps, Collections.reverseOrder());
		
		//Go through time stamps looking for matching message.
		for(int j = 0; j < timeStamps.length; j++) {
			conversation_cursor.moveToFirst();
			do {
				if ( conversation_cursor.getString(conversation_cursor.getColumnIndex("date")) == timeStamps[j]) {
					sortedIds[j] = conversation_cursor.getInt(conversation_cursor.getColumnIndex("_id"));
				}
			} while(conversation_cursor.moveToNext());
		}
	}
    }	
}
