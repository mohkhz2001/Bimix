package com.mohammadkz.bimix.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mohammadkz.bimix.R;

import java.util.List;

public class ContactFragment extends Fragment {

    private static final int zoom = 15;

    View view;
    LinearLayout info_center, info_kianpars, info_zeiton;
    ImageView center, kianpars, zeiton;
    boolean center_office = false, kianpars_office = false, zeiton_office = false;
    MapView center_map, ziton_map, kianpars_map;
    GoogleMap map;


    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact, container, false);


        initViews();
        permission();
        controllerViews(savedInstanceState);

        return view;
    }

    // init views
    private void initViews() {
        info_center = view.findViewById(R.id.info_center);
        info_kianpars = view.findViewById(R.id.info_kianpars);
        info_zeiton = view.findViewById(R.id.info_zeiton);
        center = view.findViewById(R.id.center);
        kianpars = view.findViewById(R.id.kianpars);
        zeiton = view.findViewById(R.id.zeiton);
        center_map = view.findViewById(R.id.center_map);
        ziton_map = view.findViewById(R.id.ziton_map);
        kianpars_map = view.findViewById(R.id.kianpars_map);

    }

    // views controller ==>  get Bundle for map view onCreate
    private void controllerViews(Bundle savedInstanceState) {
        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (center_office) {
                    center.setImageResource(R.drawable.ic_arrow_drop_down);
                    info_center.setVisibility(View.GONE);
                    center_office = false;
                } else {
                    center.setImageResource(R.drawable.ic_arrow_drop_up);
                    info_center.setVisibility(View.VISIBLE);
                    center_office = true;
                    center_map.onCreate(savedInstanceState);
                    setMapView(center_map, new LatLng(31.344937, 48.684890)); //  show the map
//                    center_map.getMapAsync(new OnMapReadyCallback() {
//                        @Override
//                        public void onMapReady(GoogleMap googleMap) {
//                            center_googleMap = googleMap;
//
//                            center_googleMap.getUiSettings().setMyLocationButtonEnabled(false);
//
//                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
//                                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                // TODO: Consider calling
//                                //    ActivityCompat#requestPermissions
//                                // here to request the missing permissions, and then overriding
//                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                //                                          int[] grantResults)
//                                // to handle the case where the user grants the permission. See the documentation
//                                // for ActivityCompat#requestPermissions for more details.
//                                return;
//                            }
//
//                            center_googleMap.setMyLocationEnabled(true);
//
//                            //in old Api Needs to call MapsInitializer before doing any CameraUpdateFactory call
//                            try {
//                                MapsInitializer.initialize(getContext());
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            LatLng latLng = new LatLng(31.344937, 48.684890);
//                            MarkerOptions markerOptions = new MarkerOptions();
//                            markerOptions.position(latLng);
//                            center_googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
//                            center_googleMap.getUiSettings().setAllGesturesEnabled(false);
//                            center_googleMap.addMarker(markerOptions);
//                            center_googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//                            center_map.onStart();
//                        }
//                    });

                }

            }
        });

        kianpars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kianpars_office) {
                    kianpars.setImageResource(R.drawable.ic_arrow_drop_down);
                    info_kianpars.setVisibility(View.GONE);
                    kianpars_office = false;
                } else {
                    kianpars.setImageResource(R.drawable.ic_arrow_drop_up);
                    info_kianpars.setVisibility(View.VISIBLE);
                    kianpars_office = true;

                    kianpars_map.onCreate(savedInstanceState);
                    setMapView(kianpars_map, new LatLng(31.3537648611404, 48.688206652114864));
                }
            }
        });

        zeiton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zeiton_office) {
                    zeiton.setImageResource(R.drawable.ic_arrow_drop_down);
                    info_zeiton.setVisibility(View.GONE);
                    zeiton_office = false;
                } else {
                    zeiton.setImageResource(R.drawable.ic_arrow_drop_up);
                    info_zeiton.setVisibility(View.VISIBLE);
                    zeiton_office = true;

                    ziton_map.onCreate(savedInstanceState);
                    setMapView(ziton_map, new LatLng(31.351651,48.729532));
                }

            }
        });
    }

    // runtime permission
    private void permission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };

        TedPermission.with(getContext())
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).check();
    }

    /*
     map views setup
     get view and lat long location to set the location marker
     */
    private void setMapView(MapView mapView, LatLng latLng) {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                map.getUiSettings().setMyLocationButtonEnabled(false);

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                map.setMyLocationEnabled(true);

                //in old Api Needs to call MapsInitializer before doing any CameraUpdateFactory call
                try {
                    MapsInitializer.initialize(getContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
                map.getUiSettings().setAllGesturesEnabled(false);
                map.addMarker(markerOptions);
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mapView.onStart();
            }
        });
    }

}