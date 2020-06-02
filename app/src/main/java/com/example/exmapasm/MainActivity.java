package com.example.exmapasm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.widget.Button;

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
        //metodoBotao();



    }

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
    public void onMapReady(GoogleMap googleMap) {
      this.mapa = googleMap;
      mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
      CameraUpdate update = CameraUpdateFactory.newLatLngZoom(localizacao, 19);
      mapa.animateCamera(update);
      mapa.addMarker(new MarkerOptions().position(localizacao).title("SFC"));

    }
}
