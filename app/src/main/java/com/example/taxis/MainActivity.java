package com.example.taxis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;

import android.app.ActionBar;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {

	private MapView osm;
	private MapController mc;
	private LocationManager locationManager;
	TextView miTexto1, miTexto2;
	double latitud=-16.509835;
	double longitud=-68.126505;
	String resultado="";
	private List<OverlayItem> mOverLays;
	private Context mContext;
	private Double menor=20000000000.0;
	private int npunto=0;
	Double varl=0.0;
	Double varlong=0.0;
	private ArrayList<String> v=new ArrayList<String>();
	private ArrayList<Double> lat=new ArrayList<Double>();
	private ArrayList<Double> lon=new ArrayList<Double>(); 
	private ArrayList<OverlayItem> anotherOverlayItemArray;

	private Button btncerca;
	
	private OnItemGestureListener<OverlayItem> myOnItemGestureListener=new OnItemGestureListener<OverlayItem>(){

		@Override
		public boolean onItemLongPress(int arg0, OverlayItem arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onItemSingleTapUp(int index, OverlayItem item) {
			// TODO Auto-generated method stub
			Toast.makeText(getBaseContext(),item.getSnippet()+"\n"+item.getPoint().getLatitude()+" : "+item.getPoint().getLongitude(), Toast.LENGTH_LONG).show();
			return true;
		
		}	
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        miTexto1 = (TextView)findViewById(R.id.textView1);
		miTexto2 = (TextView)findViewById(R.id.textView2);

		btncerca = (Button) findViewById(R.id.btncerca);

		btncerca.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ingresa();
			}
		});
        
        osm = (MapView) findViewById(R.id.mapView);
        osm.setTileSource(TileSourceFactory.MAPNIK);
        osm.setBuiltInZoomControls(true);
        osm.setMultiTouchControls(true);
        
        mc = (MapController) osm.getController();
        mc.setZoom(12);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        
        //LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		final Location localizacion = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if (localizacion != null){
			miTexto1.setText("Latitud: " + String.valueOf(localizacion.getLatitude()));
			miTexto2.setText("Longitud: " + String.valueOf(localizacion.getLongitude()));
		} else {
			miTexto1.setText("Sin datos de latitud.");
			miTexto2.setText("Sin datos de longitud.");
		}
		
		LocationListener locListener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location localizacion) {
				latitud=localizacion.getLatitude();
				longitud=localizacion.getLongitude();
				GeoPoint center = new GeoPoint(latitud, longitud);
		        mc.animateTo(center);
		        addMarker(center);
				miTexto1.setText("Latitud: " + String.valueOf(latitud));
				miTexto2.setText("Longitud: " + String.valueOf(longitud));
			}
		};
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locListener);	

		
    }
    
    
    public void  addMarker(GeoPoint center){
    	Marker marker = new Marker(osm);
    	marker.setPosition(center);
    	marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
    	marker.setIcon(getResources().getDrawable(R.drawable.persona_2));
    	
    	osm.getOverlays().clear();
    	osm.getOverlays().add(marker);
    	osm.invalidate();
    }
    
    public void setLocation(Location loc){
    	//obtiene la direccion a partir de la latitud y longitud
    	if(loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0){
    		try{
    			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    			List<Address> list = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
    			if(!list.isEmpty()){
    				Address address = list.get(0);
    				Toast.makeText(getBaseContext(), "Mi direccion es: "+address.getAddressLine(0), Toast.LENGTH_LONG).show();
    			}
    		}catch(IOException e){
    			e.printStackTrace();
    		}
    	}
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(getBaseContext(), "GPS Desactivado", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(getBaseContext(), "GPS Activado", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		if(locationManager != null){
			locationManager.removeUpdates(this);
		}
	}
	   private class ProcesoAsyncTask extends AsyncTask<String, Void, String> {
			@Override
			protected String doInBackground(String... urls) {
			return Bajar(urls[0]);
			}
			@Override
			protected void onPostExecute(String result) {
			Toast.makeText(getBaseContext(), "Informaci�n Recuperada",Toast.LENGTH_LONG).show();
			resultado=result;
			}
		}
		
		public static String Bajar(String url){
			InputStream inputStream = null;
			String result = "";
			try {
			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();
			// convert inputstream to string
			if(inputStream != null)
			result = convierteISaString(inputStream);
			else
			result = "Error en el Proceso!!!";
			} catch (Exception e) {
			Log.d("Error en InputStream", e.getLocalizedMessage());
			}
			return result;
			}
		private static String convierteISaString(InputStream bloque) throws IOException{
			BufferedReader puntero = new BufferedReader( new InputStreamReader(bloque));
			String linea = "";
			String resultado = "";
			while((linea = puntero.readLine()) != null)
			resultado += linea;
			puntero.close();
			return resultado;
			}

//para marcar los puntos
	public void actualizar(View vista){
		/*new ProcesoAsyncTask().execute("http://xxxxxxxxxx/");
		 slat.clear();
	        slon.clear();
	        lat.clear();
	        lon.clear();
	        osm.getOverlays().clear();
				
	        GeoPoint center = new GeoPoint(latitud, longitud);
	        mc.animateTo(center);
	        addMarker(center);
		 OnItemGestureListener<OverlayItem> myOnItemGestureListener=new OnItemGestureListener<OverlayItem>(){

			@Override
			public boolean onItemLongPress(int arg0, OverlayItem arg1) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onItemSingleTapUp(int index, OverlayItem item) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), item.getSnippet()+"\n"+ item.getSnippet()+"\n"+item.getPoint().getLatitudeE6()+" : "+item.getPoint().getLongitudeE6(), Toast.LENGTH_LONG).show();
				return true;
			}
		};   
	       String resp="";
	       int k=0;
				StringTokenizer st=new StringTokenizer(resultado,":;<>",false);
				
				while(st.hasMoreTokens()){
					resp=st.nextToken();
					v.add(resp);
					if(k==2 || k==7 || k==12 || k==17 || k==22 || k==27 || k==32 || k==37 || k==42 || k==47 || k==52 || k==57 || k==62 || k==67 || k==72 || k==77 || k==82 || k==87){
						slat.add(resp);
					}
					if(k==3 || k==8 || k==13 || k==18 || k==23 || k==28 || k==33 || k==38 || k==43 || k==48 || k==53 || k==58 || k==63 || k==68 || k==73 || k==78 || k==83 || k==88 ){
						slon.add(resp);
					}
					k++;
				}
		for (int i = 0; i < slat.size(); i++) {
			Double varl=Double.parseDouble(slat.get(i));
			lat.add(varl);
		}
		for (int i = 0; i < slon.size(); i++) {
			Double varl=Double.parseDouble(slon.get(i));
			lon.add(varl);
		}
*/
		//Toast.makeText(getBaseContext(),resultado+" ", 1).show();

        GeoPoint center = new GeoPoint(latitud, longitud);
        mc.animateTo(center);
        addMarker(center);
        Double varl=-16.509485;
        lat.add(varl);
        lat.add(-16.506784);
        lat.add(-16.503425);
        lat.add(-16.502418);
        lat.add(-16.512675);

        Double varl2=-68.129595;
        lon.add(varl2);
        lon.add(-68.126370);
        lon.add(-68.124452);
        lon.add(-68.128398);
        lon.add(-68.127278);
		
		anotherOverlayItemArray = new ArrayList<OverlayItem>(); 
		int n=0;
		for (int j = 0; j <lat.size(); j++) {
		
			if(lat.get(j)!=0){
				GeoPoint t = new GeoPoint(lat.get(j),lon.get(j));
				n=j+1;
				anotherOverlayItemArray.add( new OverlayItem("Punto"+n,"Punto "+n, 
				t));
			}
		}
		ItemizedOverlayWithFocus<OverlayItem> anotherItemizedIconOverlay = new ItemizedOverlayWithFocus<OverlayItem>( this, anotherOverlayItemArray, myOnItemGestureListener); 
		osm.getOverlays().add(anotherItemizedIconOverlay); 
		anotherItemizedIconOverlay.setFocusItemsOnTap(true); 
		
	}
	 public void taxicerca(View vista){

             npunto=0;
             Double distancia=0.0;

             GeoPoint center = new GeoPoint(latitud, longitud);
             mc.animateTo(center);
             addMarker(center);
             for (int j = 0; j < lat.size(); j++) {
                 distancia=Math.sqrt(Math.pow(lat.get(j)-latitud, 2)-Math.pow(lon.get(j)-longitud, 2));
                 if(distancia<menor){
                     menor=distancia;
                     varl=lat.get(j);
                     varlong=lon.get(j);
                     npunto=j;
                 }
             }
             //Toast.makeText(getBaseContext(),latitud+";"+longitud+";"+varl+";"+varlong+"",1 ).show();
             npunto=npunto+1;
             anotherOverlayItemArray.add( new OverlayItem("Punto "+npunto,"Punto "+npunto,new GeoPoint(varl,varlong)));
             ItemizedOverlayWithFocus<OverlayItem> anotherItemizedIconOverlay = new ItemizedOverlayWithFocus<OverlayItem>( this, anotherOverlayItemArray, myOnItemGestureListener);
             osm.getOverlays().add(anotherItemizedIconOverlay);
             anotherItemizedIconOverlay.setFocusItemsOnTap(true);
             Toast.makeText(getBaseContext(), "El punto mas cercano es: "+npunto+"\n"+varl+";"+varlong,Toast.LENGTH_LONG ).show();

		}

	public void ingresa(){

		if(latitud==0 && longitud==0){
			Toast.makeText(getApplicationContext(),"No se obtuvo la ubicacion", Toast.LENGTH_LONG).show();
		}else{

			Intent i = new Intent(getApplicationContext(),SolicitarActivity.class);
			i.putExtra("lat",latitud);
			i.putExtra("long",longitud);
			startActivity(i);

		}
	}

    
}
