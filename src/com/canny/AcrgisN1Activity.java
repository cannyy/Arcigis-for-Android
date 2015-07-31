package com.canny;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;

public class AcrgisN1Activity extends Activity {

	MapView mMapView;
	Button m_btnShow1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		m_btnShow1 = (Button) findViewById(R.id.btnShow1);
		m_btnShow1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		mMapView = (MapView) findViewById(R.id.map);
		// Add layer to MapView
		ArcGISTiledMapServiceLayer tileLayer01 = new ArcGISTiledMapServiceLayer(
				"http://services.arcgisonline.com/arcgis/rest/services/ESRI_StreetMap_World_2D/MapServer");
		ArcGISTiledMapServiceLayer tileLayer02 = new ArcGISTiledMapServiceLayer(
				"http://services.arcgisonline.com/arcgis/rest/services/ESRI_Imagery_World_2D/MapServer");
		mMapView.addLayer(tileLayer01);
		mMapView.addLayer(tileLayer02);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.unpause();
	}

}