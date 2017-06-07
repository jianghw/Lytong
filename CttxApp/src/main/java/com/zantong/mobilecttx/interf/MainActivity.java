package com.zantong.mobilecttx.interf;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;

public class MainActivity extends Activity {
//	private Toast mToast;
//	private BMapManager mBMapManager;
//	private MapView mMapView = null;
//	private MapController mMapController = null;
//
//
//	private LocationClient mLocClient;
//	private LocationData mLocData;
//	//��λͼ��
//	private	LocationOverlay myLocationOverlay = null;
//
//	private boolean isRequest = false;//�Ƿ��ֶ���������λ
//	private boolean isFirstLoc = true;//�Ƿ��״ζ�λ
//
//	private PopupOverlay mPopupOverlay  = null;//��������ͼ�㣬����ڵ�ʱʹ��?
//	private View viewCache;
//	private BDLocation location;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		//ʹ�õ�ͼsdkǰ���ȳ�ʼ��BMapManager�����������?setContentView()�ȳ�ʼ��
//		mBMapManager = new BMapManager(this);
//
//		//��һ��������API key,
//		//�ڶ��������ǳ����¼���������������ͨ��������������?��֤����ȣ���Ҳ���Բ��������ص��ӿ�?
//		mBMapManager.init("7ae13368159d6a513eaa7a17b9413b4b", new MKGeneralListenerImpl());
//		setContentView(R.layout.activity_main);
//
//		//�����ť�ֶ������?
//		((Button) findViewById(R.id.request)).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				requestLocation();
//			}
//		});
//
//		mMapView = (MapView) findViewById(R.id.bmapView); //��ȡ�ٶȵ�ͼ�ؼ�ʵ��
//        mMapController = mMapView.getController(); //��ȡ��ͼ������
//        mMapController.enableClick(true);   //���õ�ͼ�Ƿ���Ӧ����¼�?
//        mMapController.setZoom(14);   //���õ�ͼ���ż���
//        mMapView.setBuiltInZoomControls(true);   //��ʾ�������ſؼ�
//
//        viewCache = LayoutInflater.from(this).inflate(R.layout.pop_layout, null);
//        mPopupOverlay = new PopupOverlay(mMapView ,new PopupClickListener() {
//
//			@Override
//			public void onClickedPopup(int arg0) {
//				mPopupOverlay.hidePop();
//			}
//		});
//
//
//
//        mLocData = new LocationData();
//
//
//        //ʵ������λ����LocationClient����������߳�������?
//        mLocClient = new LocationClient(getApplicationContext());
//		mLocClient.registerLocationListener(new BDLocationListenerImpl());//ע�ᶨλ�����ӿ�
//
//		/**
//		 * ���ö�λ����
//		 */
//		LocationClientOption option = new LocationClientOption();
//		option.setOpenGps(true); //��GPRS
//		option.setAddrType("all");//���صĶ�λ���������ַ���?
//		option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ��?,Ĭ��ֵgcj02
//		option.setScanSpan(5000); //���÷���λ����ļ��ʱ��Ϊ5000ms
//		option.disableCache(false);//��ֹ���û��涨λ
////		option.setPoiNumber(5);    //��෵��?POI����
////		option.setPoiDistance(1000); //poi��ѯ����
////		option.setPoiExtraInfo(true);  //�Ƿ���ҪPOI�ĵ绰�͵�ַ����ϸ��Ϣ
//
//		mLocClient.setLocOption(option);
//		mLocClient.start();  //	���ô˷�����ʼ��λ
//
//		//��λͼ���ʼ��?
//		myLocationOverlay = new LocationOverlay(mMapView);
//		//���ö�λ����
//	    myLocationOverlay.setData(mLocData);
//
//	    myLocationOverlay.setMarker(getResources().getDrawable(R.drawable.location_arrows));
//
//	    //��Ӷ�λͼ��?
//	    mMapView.getOverlays().add(myLocationOverlay);
//	    myLocationOverlay.enableCompass();
//
//	    //�޸Ķ�λ���ݺ�ˢ��ͼ����Ч
//	    mMapView.refresh();
//
//	}
//
//
//	/**
//	 * ��λ�ӿڣ���Ҫʵ����������
//	 * @author xiaanming
//	 *
//	 */
//	public class BDLocationListenerImpl implements BDLocationListener {
//
//		/**
//		 * �����첽���صĶ�λ�����������?BDLocation���Ͳ���
//		 */
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			if (location == null) {
//				return;
//			}
//
//			MainActivity.this.location = location;
//
//			mLocData.latitude = location.getLatitude();
//			mLocData.longitude = location.getLongitude();
//			//�������ʾ��λ�����?����accuracy��ֵΪ0����
//			mLocData.accuracy = location.getRadius();
//			mLocData.direction = location.getDerect();
//
//			//����λ�������õ���λͼ����
//            myLocationOverlay.setData(mLocData);
//            //����ͼ������ִ��ˢ�º���Ч
//            mMapView.refresh();
//
//
//
//            if(isFirstLoc || isRequest){
//				mMapController.animateTo(new GeoPoint(
//						(int) (location.getLatitude() * 1e6), (int) (location
//								.getLongitude() * 1e6)));
//
//				showPopupOverlay(location);
//
//				isRequest = false;
//            }
//
//            isFirstLoc = false;
//		}
//
//		/**
//		 * �����첽���ص�POI��ѯ�����������?BDLocation���Ͳ���
//		 */
//		@Override
//		public void onReceivePoi(BDLocation poiLocation) {
//
//		}
//
//	}
//
//
//	/**
//	 * �����¼���������������ͨ��������������?��֤�����?
//	 * @author xiaanming
//	 *
//	 */
//	public class MKGeneralListenerImpl implements MKGeneralListener{
//
//		/**
//		 * һЩ����״̬�Ĵ�����ص�����?
//		 */
//		@Override
//		public void onGetNetworkState(int iError) {
//			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
//				showToast("���������������?");
//            }
//		}
//
//		/**
//		 * ��Ȩ�����ʱ����õĻص�����
//		 */
//		@Override
//		public void onGetPermissionState(int iError) {
//			if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
//				showToast("API KEY����, ���飡");
//            }
//		}
//
//	}
//
//	//
//	private class LocationOverlay extends MyLocationOverlay{
//
//		public LocationOverlay(MapView arg0) {
//			super(arg0);
//		}
//
//		@Override
//		protected boolean dispatchTap() {
//			showPopupOverlay(location);
//			return super.dispatchTap();
//		}
//
//		@Override
//		public void setMarker(Drawable arg0) {
//			super.setMarker(arg0);
//		}
//
//
//
//	}
//
//
//
//	private void showPopupOverlay(BDLocation location){
//		 TextView popText = ((TextView)viewCache.findViewById(R.id.location_tips));
//		 popText.setText("[�ҵ�λ��]\n" + location.getAddrStr());
//		 mPopupOverlay.showPopup(getBitmapFromView(popText),
//					new GeoPoint((int)(location.getLatitude()*1e6), (int)(location.getLongitude()*1e6)),
//					10);
//	}
//
//
//
//	/**
//	 * �ֶ�����λ�ķ���
//	 */
//	public void requestLocation() {
//		isRequest = true;
//
//		if(mLocClient != null && mLocClient.isStarted()){
//			showToast("���ڶ�λ......");
//			mLocClient.requestLocation();
//		}else{
//			Log.d("LocSDK3", "locClient is null or not started");
//		}
//	}
//
//	@Override
//	protected void onResume() {
//    	//MapView������������Activityͬ������activity����ʱ�����?MapView.onPause()
//		mMapView.onResume();
//		super.onResume();
//	}
//
//
//
//	@Override
//	protected void onPause() {
//		//MapView������������Activityͬ������activity����ʱ�����?MapView.onPause()
//		mMapView.onPause();
//		super.onPause();
//	}
//
//	@Override
//	protected void onDestroy() {
//		//MapView������������Activityͬ������activity����ʱ�����?MapView.destroy()
//		mMapView.destroy();
//
//		//�˳�Ӧ�õ���BMapManager��destroy()����
//		if(mBMapManager != null){
//			mBMapManager.destroy();
//			mBMapManager = null;
//		}
//
//		//�˳�ʱ���ٶ�λ
//        if (mLocClient != null){
//            mLocClient.stop();
//        }
//
//		super.onDestroy();
//	}
//
//
//
//	 /**
//     * ��ʾToast��Ϣ
//     * @param msg
//     */
//    private void showToast(String msg){
//        if(mToast == null){
//            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
//        }else{
//            mToast.setText(msg);
//            mToast.setDuration(Toast.LENGTH_SHORT);
//        }
//        mToast.show();
//    }
//
//	/**
//	 *
//	 * @param view
//	 * @return
//	 */
//	public static Bitmap getBitmapFromView(View view) {
//		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//        view.buildDrawingCache();
//        Bitmap bitmap = view.getDrawingCache();
//        return bitmap;
//	}
	
}
