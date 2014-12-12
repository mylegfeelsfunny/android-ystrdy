package com.tobros.hatebyte.ystrdy;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tobros.hatebyte.ystrdy.alarm.AlarmReceiver;
import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDatabase;
import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordEGI;


public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    RecordEGI recordEGI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve a PendingIntent to perform a broadcast
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10000, pendingIntent);

        RecordDatabase recordDatabase = new RecordDatabase(getApplicationContext(), "testLocationRecord.db");

//        mockRecordEGI = mock(RecordEGI.class);
        recordEGI = new RecordEGI(recordDatabase);



    }

    public void resetDB(View view) {
        recordEGI.clearDatabase();
        Toast.makeText(this, "I'm running. + \n" + recordEGI.numNowRecords() + " records", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();

//        Intent service = new Intent(this, GPSService.class);
//        service.putExtra(GPSService.UPDATE_RATE, 5000);
//        startService(service);
//        bindService(new Intent(ILocationRecordInterface.class.getName()), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    // service connect interface
    ILocationRecordInterface locationRecordInterface = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            locationRecordInterface = ILocationRecordInterface.Stub.asInterface(service);

//            try {
//                Location l = locationRecordInterface.getLastLocation();
//                Log.i(TAG, l.getLatitude() + ", " + l.getLongitude());
//            } catch (RemoteException e) {
//                Log.e(TAG, "RemoteException : "+e);
//            } catch (NullPointerException nulle) {
//                Log.e(TAG, "NullPointerException : "+nulle);
//            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            locationRecordInterface = null;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
