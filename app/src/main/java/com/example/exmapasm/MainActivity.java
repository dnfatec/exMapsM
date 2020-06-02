package com.example.exmapasm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

private static boolean LOCATION_PERMISSION_REQUEST_CODE = false;
public GoogleMap mapa;
public LatLng localizacao = new LatLng(-23.951137, -46.339025);
private Button btPosicao;
private GeoDataClient geoDataClient;
private FusedLocationProviderClient mfusedLocationProviderClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.nossoMapa);

        mapFragment.getMapAsync(MainActivity.this);
        geoDataClient= Places.getGeoDataClient(MainActivity.this, null);
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        metodoBotao();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    private void metodoBotao()
    {
        btPosicao=(Button)findViewById(R.id.btnAtualizarPosicao);
        btPosicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableMyLocation();
                atualizaSuaLocalizacao();
            }
        });
    }

    private void enableMyLocation()
    {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        !=PackageManager.PERMISSION_GRANTED) &&
        (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        !=PackageManager.PERMISSION_GRANTED))
        {
            LOCATION_PERMISSION_REQUEST_CODE=PermissionUtils.validate(this, 1,Manifest.permission.ACCESS_FINE_LOCATION);
            LOCATION_PERMISSION_REQUEST_CODE=PermissionUtils.validate(this, 1, Manifest.permission.ACCESS_COARSE_LOCATION);
            atualizaSuaLocalizacao();
        }
    }

    private void atualizaSuaLocalizacao(){
        try {
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mapa.addMarker(new MarkerOptions().position(latLng).title("OI"));
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 19);
                    mapa.animateCamera(update);
                    mapa.setMapType(1);

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1,0, locationListener);
        }
        catch (SecurityException e){
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
      this.mapa = googleMap;
      mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
      CameraUpdate update = CameraUpdateFactory.newLatLngZoom(localizacao, 19);
      mapa.animateCamera(update);
      mapa.addMarker(new MarkerOptions().position(localizacao).title("SFC"));

    }
}
