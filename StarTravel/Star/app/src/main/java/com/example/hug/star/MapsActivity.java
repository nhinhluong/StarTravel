package com.example.hug.star;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hug.star.direction.DirectionFinder;
import com.example.hug.star.direction.DirectionFinderListener;
import com.example.hug.star.direction.Route;
import com.example.hug.star.location.GetAddressOfLocation;
import com.example.hug.star.nearby.Readjson;
import com.example.hug.star.nearby.Readnextpage;
import com.example.hug.star.nearby.Urlnearby;
import com.example.hug.star.nearby.iconAndStringsearch;
import com.example.hug.star.realtime.getPointsandManeuver;
import com.example.hug.star.setting.SettingActivity;
import com.example.hug.star.setting.LocationHot;
import com.example.hug.star.direction.changedistanceandduration;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements DirectionFinderListener,OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, TextToSpeech.OnInitListener {

    private GoogleMap mMap;
    private Button btnSearch;
    private Button btndistance;
    private FloatingActionButton ftActionbtn1;
    private FloatingActionButton ftActionbtn0;
    private FloatingActionButton ftActionbtnsetting;
    private GoogleApiClient mGoogleApiClient;
    private FloatingActionButton informationft;
    private FloatingActionButton informationfttext;
    private ScrollView scrollertextinfor;
    private TextView tvinfor;
    private String textInformation;
    private boolean informationftstt = false;
    private boolean informationfttextstt = false;

    private Location mLastLocation;
    private static double LatDevice;
    private static double LngDevice;
    private static double Latcome, Latcomeold, Lato, Latoold;
    private static double Lngcome, Lngcomeold,Lngo, Lngoold ;
    private static Marker Makergps;
    private static Marker Makerfind;
    private static List<Marker> Makernearby = new ArrayList<>();
    static String textreadold;

    private static final int MY_PERMISSION_REQUEST_FINE_LOCATION = 101;
    private static final int MY_PERMISSION_REQUEST_COARSE_LOCATION = 102;
    private boolean permissiontf = false;
    private int duration;
    private double distance;

    private List<Polyline> polylinePaths = new ArrayList<>();
    private PolylineOptions polylineOptions;
    private static CountDownTimer countTime;

    TextToSpeech tts;
    static String textread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ///////////////////
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        //// Fixing Later Map loading Delay
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MapView mv = new MapView(getApplicationContext());
                    mv.onCreate(null);
                    mv.onPause();
                    mv.onDestroy();
                } catch (Exception ignored) {

                }
            }
        }).start();
        //// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //// action
        btndistance = (Button)findViewById(R.id.buttondistance);
        btndistance.setVisibility(View.INVISIBLE);
        //// information
        scrollertextinfor = (ScrollView) findViewById(R.id.ScrollViewtextinformation);
        scrollertextinfor.setVisibility(View.INVISIBLE);
        tvinfor = (TextView) findViewById(R.id.textviewinformation);
        informationfttext = (FloatingActionButton)findViewById(R.id.floatingActionButtonInfortext);
        informationfttext.setVisibility(View.INVISIBLE);
        informationft = (FloatingActionButton)findViewById(R.id.floatingActionButtonInformation);
        informationft.setVisibility(View.INVISIBLE);
        informationft.setEnabled(false);
        //
        informationft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (informationftstt == false){
                    informationftstt = true;
                    textread = textInformation;
                    textoSpeech();
                    informationft.setIcon(R.drawable.delete);
                }else{
                    informationftstt = false;
                    tts.stop();
                    informationft.setIcon(R.drawable.infosign);
                }
            }
        });
        //
        informationfttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (informationfttextstt == false){
                    informationfttextstt = true;
                    tvinfor.setText(textInformation);
                    scrollertextinfor.setVisibility(View.VISIBLE);
                    informationfttext.setIcon(R.drawable.delete);
                }else{
                    informationfttextstt = false;
                    scrollertextinfor.setVisibility(View.INVISIBLE);
                    informationfttext.setIcon(R.drawable.infotext);
                }
            }
        });
        //// create and set BoomMenu
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setDrawingCacheBackgroundColor(16);
        bmb.setButtonEnum(ButtonEnum.SimpleCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_6_3);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_6_3);
        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++) {
            bmb.addBuilder(new SimpleCircleButton.Builder()
                    .normalImageRes(iconAndStringsearch.icons[i])
                    .buttonRadius(100)
                    .imageRect(new Rect(50,45,150,150))
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            try {
                                if(Makernearby != null){
                                    for (Marker maker : Makernearby) {
                                        maker.remove();
                                    }
                                    Makernearby.clear();
                                }
                                if(SettingActivity.resfultvalue == 40){
                                    textread = "please watting";
                                    textoSpeech();
                                }
                                searchNearby(iconAndStringsearch.types[index],iconAndStringsearch.names[index]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    })

                    );
        }
        ////
        ftActionbtn1 = (FloatingActionButton) findViewById(R.id.ft_ac1);
        ftActionbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // real time
                if(polylineOptions != null) {
                    getPointsandManeuver realtime = new getPointsandManeuver();
                    textreadold = "d";
                    textread = "can't find way";
                    polylineOptions.color(Color.BLUE);
                    if (Lato == Latoold && Lngo == Lngoold && Latcome == Latcomeold && Lngcome == Lngcomeold) {
                        //if(1!=1){
                        //System.out.println("do nothing");
                    } else {
                        try {
                            Latoold = Lato;
                            Lngoold = Lngo;
                            Latcomeold = Latcome;
                            Lngcomeold = Lngcome;
                            //realtime.setRealTime(10.029757,105.763336,10.025753,105.769222);
                            realtime.setRealTime(Lato, Lngo, Latcome, Lngcome);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!getPointsandManeuver.maneuver.isEmpty()) {
                        countTime = new CountDownTimer(86400000, 4000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                getMyLocation();

                                for (int i = 0; i < getPointsandManeuver.maneuver.size(); i++) {
                                    if (mLastLocation != null) {
                                        //if(1==1){
                                        //Toast.makeText(getApplicationContext(),String.valueOf(LatDevice - Double.parseDouble(String.valueOf(getPointsandManeuver.Latpoint.get(i)))),Toast.LENGTH_LONG).show();
                                        if (Math.abs(LatDevice - Double.parseDouble(String.valueOf(getPointsandManeuver.Latpoint.get(i)))) < 2.5E-4
                                                && Math.abs(LngDevice - Double.parseDouble(String.valueOf(getPointsandManeuver.Lngpoint.get(i)))) < 2.5E-4) {
                                            textread = (String) getPointsandManeuver.maneuver.get(i);
                                            Toast.makeText(getApplicationContext(), textread, Toast.LENGTH_LONG).show();
                                            if (textread != textreadold) {
                                                textreadold = textread;
                                                textoSpeech();
                                            }
                                            ///// check first read
                                        }
                                        if (Math.abs(LatDevice - Latcome) < 3.0E-4 && Math.abs(LngDevice - Lngcome) < 3.0E-4) {
                                            textread = "Ok We arrived";
                                            if (textread != textreadold) {
                                                textreadold = textread;
                                                textoSpeech();
                                                cancel();
                                            }
                                        }
                                    } else {
                                        System.out.println("null location");
                                    }
                                }
                            }

                            @Override
                            public void onFinish() {
                                System.out.println("stop count");
                            }
                        }.start();
                    } else {
                        System.out.println("data null maneuver");
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Not enough data",Toast.LENGTH_LONG).show();
                }}
        });
        //// button search
        btnSearch = (Button) findViewById(R.id.buttonsearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    try {
                        if(Makerfind != null){
                            Makerfind.remove();
                        }
                        onSearch(v);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //// button getlocation
        ftActionbtn0 = (FloatingActionButton)findViewById(R.id.ft_ac0);
        ftActionbtn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyLocation();
                if (mLastLocation != null){
                    ////// get String address
                    GetAddressOfLocation getaddress = new GetAddressOfLocation();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LatDevice,LngDevice), 18));
                    try {
                        textread = getaddress.getAddress(LatDevice,LngDevice);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    textread = "Can't find your location" ;
                }
                textoSpeech();
            }
        });
        ////button setting
        ftActionbtnsetting = (FloatingActionButton)findViewById(R.id.ft_setting);
        ftActionbtnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSetting = new Intent(MapsActivity.this, SettingActivity.class);
                startActivity(toSetting);
            }
        });
     //////////////////////////// end onCreate
    }
    /////////////////////
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    //////////////////////////// get user's location//////////////////////////////////////////////
    private void getMyLocation() {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if(Makergps != null){
                Makergps.remove();
            }
            if (mLastLocation != null) {
                LatDevice = mLastLocation.getLatitude();
                LngDevice = mLastLocation.getLongitude();

                    /////// create maker gps
                    LatLng gps = new LatLng(LatDevice, LngDevice);
                    Makergps = mMap.addMarker(new MarkerOptions().position(gps).title("You are here"));
                    mGoogleApiClient.clearDefaultAccountAndReconnect();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Please open gps or network !",
                        Toast.LENGTH_LONG).show();
            }
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(),
                    "SecurityException:\n" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
    //////////////////////////////search nearby///////////////////////////////////////////////////////////////////
    public void searchNearby(String type, String name) throws JSONException, InterruptedException, IOException {
        if(mLastLocation != null) {
            Readjson read = new Readjson();
            Urlnearby h = new Urlnearby();
            String a = h.createUrlnearby(String.valueOf(LatDevice),String.valueOf(LngDevice) ,
                    type, name, String.valueOf(SettingActivity.radiusvalue));
            Readnextpage r = new Readnextpage();
            ArrayList<String> hug = r.getNextpage(a);
                    for (String el : hug) {
                TimeUnit.SECONDS.sleep(1);
                JSONObject newjson = read.readJsonFromUrl(el);
                        if(newjson.getString("status").equals("ZERO_RESULTS")){
                            Toast.makeText(getApplicationContext(),"Nothing in radius",Toast.LENGTH_LONG).show();
                        }
                JSONArray jsA = (JSONArray) newjson.get("results");
                for (int i = 0; i < jsA.length(); i++) {
                    JSONObject jsAs = (JSONObject) jsA.get(i);
                    JSONObject geometry = (JSONObject) jsAs.get("geometry");
                    JSONObject location = (JSONObject) geometry.get("location");
                    LatLng latLng = new LatLng(location.getDouble("lat"), location.getDouble("lng"));
                    Makernearby.add(mMap.addMarker(new MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))
                            .title(jsAs.getString("name"))));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                }
            }
        }else{
            Toast.makeText(getApplicationContext(), "Can't get your location", Toast.LENGTH_SHORT).show();
        }
    }
    /////////////////////////////////////////// search location////////////////////////////////////////////////////
    public void onSearch(View v) throws JSONException, IOException {
        EditText location_s = (EditText) findViewById(R.id.editTextsearch);
        String location = String.valueOf(location_s.getText());
        String urlSearch = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+location+"&key=AIzaSyCCDsvUVReOoz2wBfPVP9X_jwG6pBDEWok";
        Readjson read = new Readjson();
        JSONObject JS = read.readJsonFromUrl(urlSearch.replaceAll(" ","%20"));
        JSONArray result = JS.getJSONArray("results");
        if(result.length()>0) {
            JSONObject LF = result.getJSONObject(0);
            JSONObject geometry = LF.getJSONObject("geometry");
            JSONObject LFlocation = geometry.getJSONObject("location");
            LatLng latLng = new LatLng(LFlocation.getDouble("lat"), LFlocation.getDouble("lng"));
            Makerfind = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder2))
                    .title(LF.getString("name")));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        }else {
            Toast.makeText(getApplicationContext(), "Can't find", Toast.LENGTH_SHORT).show();
        }
    }
    ///////////////////////////////////////////////Text to speech//////////////////////////////
    public void textoSpeech(){
        tts = new TextToSpeech(MapsActivity.this,  this);
        tts.setPitch(1.0f);
        tts.setSpeechRate(0.8f);
        tts.setLanguage(Locale.US);
    }
/////////////////////////////////////////////////////////////////////////////////
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    //@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.791263,106.623794),6));
        mMap.getUiSettings().setMapToolbarEnabled(false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_FINE_LOCATION);
            }else{
                permissiontf = true;
            }
            return;
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                    try {
                        if(polylinePaths != null) {
                            for (Polyline line : polylinePaths) {
                                line.remove();
                            }
                            polylinePaths.clear();
                        }
                        ////
                        for (int i = 0; i < LocationHot.LHlat.length; i++) {
                            System.out.println(Math.abs(marker.getPosition().latitude - LocationHot.LHlat[i]));
                            if (Math.abs(marker.getPosition().latitude - LocationHot.LHlat[i]) < 5.0E-4 &&
                                    Math.abs(marker.getPosition().longitude - LocationHot.LHlng[i]) < 5.0E-4) {
                                informationft.setVisibility(View.VISIBLE);
                                informationfttext.setVisibility(View.VISIBLE);
                                textInformation = LocationHot.content[i];
                                informationft.setEnabled(true);
                                break;
                            } else {
                                informationft.setVisibility(View.INVISIBLE);
                                informationfttext.setVisibility(View.INVISIBLE);
                            }
                        }
                        ///
                        if(marker.getPosition().latitude != LatDevice &&
                                marker.getPosition().longitude != LngDevice) {
                            Latcome = marker.getPosition().latitude;
                            Lngcome = marker.getPosition().longitude;
                            Lato = LatDevice;
                            Lngo = LngDevice;
                            new DirectionFinder((DirectionFinderListener) MapsActivity.this,
                                    String.valueOf(LatDevice) + "," + String.valueOf(LngDevice),
                                    String.valueOf(Latcome)+ "," + String.valueOf(Lngcome)).execute();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                return false;
            }
        });
        //////////////////////////

       //////////////////////end oncreate
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }
    @Override
    public void onConnectionSuspended(int i) {
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onDirectionFinderStart() {

    }
    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        polylinePaths = new ArrayList<>();
        int get1 = 0;
        for (Route route : routes) {
            polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.RED).
                    width(10);
            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));
            polylinePaths.add(mMap.addPolyline(polylineOptions));

            if (get1 == 0) {
                btndistance.setText("distance = "+route.distance.text+"  "+
                        "duration = " + route.duration.text);
                get1++;
            }
        }

        btndistance.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(tts != null){
            tts.shutdown();
        }
        if(countTime != null){
            countTime.cancel();
        }

    }
    @Override
    public void onInit(int status) {
        tts.speak(textread, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSION_REQUEST_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    permissiontf = true;
                }else{
                    permissiontf = false;
                    Toast.makeText(getApplicationContext(), "This app requires location permission to be granted", Toast.LENGTH_LONG)
                            .show();
                }
                break;
            case MY_PERMISSION_REQUEST_COARSE_LOCATION:
                break;
        }
    }
}
