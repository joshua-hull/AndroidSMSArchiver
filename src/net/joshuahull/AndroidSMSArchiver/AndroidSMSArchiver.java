package net.joshuahull.AndroidSMSArchiver;

import android.app.Activity;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
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
	do{
	
	}while(sms_cursor.moveToNext());
    }
}
