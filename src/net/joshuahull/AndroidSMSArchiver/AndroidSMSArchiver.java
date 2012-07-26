package net.joshuahull.AndroidSMSArchiver;

import android.app.Activity;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;
import android.net.Uri;


public class AndroidSMSArchiver extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void startBackup (View view) {
	setContentView(R.layout.backup);
	Cursor sms_cursor = getContentResolver().query(Uri.parse("content://sms/inbox"),null,null,null,null);
	sms_cursor.moveToFirst();
	TextView t = new TextView(this);
	t = (TextView)findViewById(R.id.backing_up_info);
	t.setText("Found " + sms_cursor.getCount() + " messages\n");
	do {
		t.append(sms_cursor.getString(sms_cursor.getColumnIndex("address")) + " " + sms_cursor.getString(sms_cursor.getColumnIndex("body")) + "\n");
    	} while (sms_cursor.moveToNext());	
	}	
}
