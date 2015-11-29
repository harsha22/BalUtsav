package com.example.haatmakuri.uiexp;

import com.example.haatmakuri.uiexp.LocationHelper.LocationResult;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class MainActivity extends Activity {

	final String TAG = "MainActivity.java";

	Button buttonStart;
	Button buttonStop;

	LocationResult locationResult;
	LocationHelper locationHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// initialize buttons
		buttonStart = (Button) findViewById(R.id.buttonStart);
		buttonStop = (Button) findViewById(R.id.buttonStop);

		// set button on click listeners
		buttonStart.setOnClickListener(new OnClickListenerButtonStart());
		buttonStop.setOnClickListener(new OnClickListenerButtonEnd());

		// to get location updates, initialize LocationResult
		this.locationResult = new LocationResult() {
			@Override
			public void gotLocation(Location location) {

				//Got the location!
				if (location != null) {

					double latitude = location.getLatitude();
					double longitude = location.getLongitude();

					double proximity = distance(latitude, longitude, 12.922148238618393, 77.6810425257973);
					Log.e(TAG, "lat: " + latitude + ", long: " + longitude);
					Toast.makeText(getApplicationContext(), "lat: " + latitude + ", long: " + longitude, Toast.LENGTH_SHORT).show();
					if (proximity <= 30.0) {
						Log.e(TAG, "In range");
						Toast.makeText(getApplicationContext(), "In range", Toast.LENGTH_SHORT).show();
					} else {
						Log.e(TAG, "Out of range");
						Toast.makeText(getApplicationContext(), "You are out of range by distance : " + proximity, Toast.LENGTH_SHORT).show();
					}
					// here you can save the latitude and longitude values
					// maybe in your text file or database

				} else {
					Log.e(TAG, "Location is null.");
				}

			}

		};

		// initialize our useful class,
		this.locationHelper = new LocationHelper();
	}

	// prevent exiting the app using back pressed
	// so getting user location can run in the background
	@Override
	public void onBackPressed() {

		new AlertDialog.Builder(MainActivity.this)
				.setTitle("User Location App")
				.setMessage("This will end the app. Use the home button instead.")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						dialog.cancel();
					}
				}).show();

	}

	/**
	 * This function returns distance between 2 coordinate points in meters
	 *
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	private static double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515 * 1.609344 * 1000;
		return (dist);
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

}