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
	boolean m_HitState = true;
	boolean qh = false;
	ArcGISTiledMapServiceLayer tileLayer01 = new ArcGISTiledMapServiceLayer(
			"http://services.arcgisonline.com/arcgis/rest/services/ESRI_StreetMap_World_2D/MapServer");
	ArcGISTiledMapServiceLayer tileLayer02 = new ArcGISTiledMapServiceLayer(
			"http://services.arcgisonline.com/arcgis/rest/services/ESRI_Imagery_World_2D/MapServer");

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		m_btnShow1 = (Button) findViewById(R.id.btnShow1);

		mMapView = (MapView) findViewById(R.id.map);
		// Add layer to MapView

		m_btnShow1.setOnClickListener(new OnClickListener() {
			

			public void onClick(View v) {
				// TODO Auto-generated method stub
				m_HitState = !m_HitState;
				tileLayer02.setVisible(m_HitState);
				if (qh ==true) {
					m_btnShow1.setText("显示地图");
					qh = !qh;
				}
				else {
					m_btnShow1.setText("显示影像");
					qh = !qh;
				}
			}
		});

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