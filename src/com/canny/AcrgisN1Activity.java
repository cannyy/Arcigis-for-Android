package com.canny;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnPanListener;
import com.esri.android.map.event.OnPinchListener;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;

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
	
	   Button m_btnZoomOut;
	    Button btn1ZoomIn;
	    Button btnLookCenter;
	    Button btnToPoint;
	    Button btnLookExtent;
	    Button btnSetCenter;
	    Point m_lastClickPoint;//上次单击的点
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		m_btnShow1 = (Button) findViewById(R.id.btnShow1);

		mMapView = (MapView) findViewById(R.id.map);
		// Add layer to MapView
		
		
		 // 处理放大按钮的事件
        m_btnZoomOut = (Button) findViewById(R.id.btnZoomOut);
        m_btnZoomOut.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                if (mMapView.isLoaded()) {
                    mMapView.zoomout();
                }
            }
        });

        // 处理缩小按钮的事件
        btn1ZoomIn = (Button) findViewById(R.id.btn1ZoomIn);
        btn1ZoomIn.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                if (mMapView.isLoaded()) {
                    mMapView.zoomin();
                }
            }
        });

        // 查看地图中心点坐标
        btnLookCenter = (Button) findViewById(R.id.btnLookCenter);
        btnLookCenter.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                if (mMapView.isLoaded()) {
                    /*
                     * Point getCenter() Returns the center of the MapView as an
                     * ArcGIS geometry Point.
                     */
                    Point p = mMapView.getCenter();
                    AlertMsg("地图中心点坐标(ArcGIS几何点)是：(%s,%s)", p.getX(), p.getY());
                }
            }
        });

        // 查看地图范围
        btnLookExtent = (Button) findViewById(R.id.btnLookExtent);
        btnLookExtent.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                if (mMapView.isLoaded()) {
                    /*
                     * Polygon getExtent() Returns a polygon comprising of four
                     * corners of map in map coordinates.
                     */
                    Polygon p = mMapView.getExtent();
                    int dimension = p.getDimension();
                    Geometry.Type type = p.getType();

                    AlertMsg("查看地图范围dimension=%s", dimension);
                }
            }
        });    
        
    
        // btnSetCenter
        btnSetCenter = (Button) findViewById(R.id.btnSetCenter);
        btnSetCenter.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                if (mMapView.isLoaded()) {
                    Point p = m_lastClickPoint;
                    if(p != null)
                    {
                        mMapView.centerAt(p, true);
                        AlertMsg("设定 地图中点为：  x=%s,y=%s", p.getX(),
                                p.getY());
                    }
                }
            }
        });

        // 坐标变换
        btnToPoint = (Button) findViewById(R.id.btnToPoint);
        btnToPoint.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                if (mMapView.isLoaded()) {
                    /*
                     * Point toMapPoint(float screenx, float screeny) A
                     * convenience method that will convert a device's screen
                     * coordinates to an ArcGIS geometry Point that has the same
                     * spatial coordinate system as the MapView's. Point
                     * toMapPoint(Point src) A convenience method that will
                     * convert a device's screen coordinates into an ArcGIS
                     * geometry Point that has the same spatial coordinate
                     * system as the MapView's. Point toScreenPoint(Point src) A
                     * convenience method that will convert an ArcGIS geometry
                     * Point from the MapView's spatial coordinate system into
                     * the device's screen coordinates.
                     */

                    // AlertMsg("查看地图范围dimension=%s,typ=%s",
                    // dimension,type.a());
                }
            }
        });

		

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
		
		
		 // 注册单击事件
        mMapView.setOnSingleTapListener(new OnSingleTapListener() {

            public void onSingleTap(float x, float y) {
                /*
                 * x - the x coordinate of the single tap location in screen
                 * pixels. y - the y coordinate of the single tap location in
                 * screen pixels.
                 */
                // x，y.都是屏幕像素坐标点
                if (mMapView.isLoaded()) {
                    AlertMsg("单击，屏幕像素坐标点：  x=%s,y=%s", x, y);

                    Point mapPoint = mMapView.toMapPoint(new Point(x, y));
                    AlertMsg("单击，地图坐标点：  x=%s,y=%s", mapPoint.getX(),
                            mapPoint.getY());
                    
                    m_lastClickPoint = mapPoint;
                    AlertMsg("设定 上次单击的点为：  x=%s,y=%s", mapPoint.getX(),
                            mapPoint.getY());
                }
                ;
            }
        });
        mMapView.setOnPanListener(new OnPanListener() {

            public void prePointerUp(float fromx, float fromy, float tox,
                    float toy) {
                // TODO Auto-generated method stub

            }

            public void prePointerMove(float fromx, float fromy, float tox,
                    float toy) {
                // TODO Auto-generated method stub

            }

            public void postPointerUp(float fromx, float fromy, float tox,
                    float toy) {
                // TODO Auto-generated method stub

            }

            public void postPointerMove(float fromx, float fromy, float tox,
                    float toy) {
                // TODO Auto-generated method stub

            }
        });

        mMapView.setOnPinchListener(new OnPinchListener() {

            public void prePointersUp(float x1, float y1, float x2, float y2,
                    double factor) {
                // TODO Auto-generated method stub

            }

            public void prePointersMove(float x1, float y1, float x2, float y2,
                    double factor) {
                // TODO Auto-generated method stub

            }

            public void prePointersDown(float x1, float y1, float x2, float y2,
                    double factor) {
                // TODO Auto-generated method stub

            }

            public void postPointersUp(float x1, float y1, float x2, float y2,
                    double factor) {
                // TODO Auto-generated method stub

            }

            public void postPointersMove(float x1, float y1, float x2,
                    float y2, double factor) {
                // TODO Auto-generated method stub

            }

            public void postPointersDown(float x1, float y1, float x2,
                    float y2, double factor) {
                // TODO Auto-generated method stub

            }
        });

        // 当mapView的状态改变时
        mMapView.setOnStatusChangedListener(new OnStatusChangedListener() {

            public void onStatusChanged(Object source, STATUS status) {
                if (status.equals(STATUS.INITIALIZATION_FAILED)) {
                    AlertMsg("mapView的状态改变时:%s", "初始化失败");
                }
                ;

                if (status.equals(STATUS.INITIALIZED)) {
                    AlertMsg("mapView的状态改变时:%s", "初始化完成");
                }
                ;
                if (status.equals(STATUS.LAYER_LOADED)) {
                    AlertMsg("mapView的状态改变时:%s", "图层加载完成");
                }
                ;

                if (status.equals(STATUS.LAYER_LOADING_FAILED)) {
                    AlertMsg("mapView的状态改变时:%s", "图层加载失败");
                }
                ;
            }
        });

        // 当缩放时
        mMapView.setOnZoomListener(new OnZoomListener() {

            public void preAction(float pivotX, float pivotY, double factor) {
                // TODO Auto-generated method stub

            }

            public void postAction(float pivotX, float pivotY, double factor) {
                AlertMsg("缩放状态变化，factor=:%s", factor);
            }
        });
    }

    private void AlertMsg(String str, Object... arg) {
        String msg = String.format(str, arg);
        Toast.makeText(this, msg,2).show();
        Log.i("AlertMsg", msg);
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