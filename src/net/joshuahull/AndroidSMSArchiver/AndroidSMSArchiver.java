package net.joshuahull.AndroidSMSArchiver;

import android.app.Activity;
import android.os.Bundle;

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
	
}
