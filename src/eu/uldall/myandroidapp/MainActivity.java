package eu.uldall.myandroidapp;

import java.util.List;

import android.location.LocationManager;
import android.net.TrafficStats;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	{
	    	TextView text = (TextView) findViewById(R.id.textView1);
	    	text.setText("Traffic stats:" + 
	    	" Mobile receive: " + TrafficStats.getMobileRxBytes() + 
	    	" Mobile sent: " + TrafficStats.getMobileTxBytes());
    	}
    	
    	{
    		LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    		List<String> providers = locManager.getProviders(false);
    		
	    	TextView text = (TextView) findViewById(R.id.textView2);
	    	text.setText("Current location providers: " + providers);
    	}
    	
    	{
    		TelephonyManager teleManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
    		
    		teleManager.listen(new ListenToPhoneState(), PhoneStateListener.LISTEN_CALL_STATE);
    		
	    	TextView text = (TextView) findViewById(R.id.textView3);
	    	text.setText("Tele stats:" + 
	    	" Country iso: " + teleManager.getNetworkCountryIso() + 
	    	" Operator: " + teleManager.getNetworkOperatorName() + 
	    	" Sim iso: " + teleManager.getSimCountryIso() +
	    	" Is roaming: " + teleManager.isNetworkRoaming());
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private class ListenToPhoneState extends PhoneStateListener {

        public void onCallStateChanged(int state, String incomingNumber) {
            Log.i("telephony-example", "State changed: " + stateName(state));
        }

        String stateName(int state) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE: return "Idle";
                case TelephonyManager.CALL_STATE_OFFHOOK: return "Off hook";
                case TelephonyManager.CALL_STATE_RINGING: return "Ringing";
            }
            return Integer.toString(state);
        }
    }
}
