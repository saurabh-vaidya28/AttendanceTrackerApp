package com.example.attendancetracker.Student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.attendancetracker.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MarkAttendanceActivity extends AppCompatActivity implements OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        View.OnClickListener{

    private ProgressBar _progressBarAttendance;
    private TextView _mark_s_name,_mark_s_uid,_mark_s_college,_mark_s_dept,_mark_s_year,_mark_s_sem;
    private TextView _attenDetails;
    private Button _scan,_done;
    View viewLayout;

    private DatabaseReference databaseReference,_databaseAttendance;
    private Mark_Attendance_Database _attendanceDatabase;
    long maxId=0;

    private GoogleMap mMap;

    private GoogleApiClient googleApiClient;
    private Location location;
    private LocationManager locationManager;
    private LocationRequest locationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL=5000;
    private long FASTEST_INTERVAL=6000;
    private LocationManager mlocationManager;
    private LatLng latLng;
    private boolean isPermission;

    private FirebaseAuth pAuthS_mark;
    private DatabaseReference databaseReferenceS_mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_mark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("Mark Attendance");
        setActionBar(toolbar);

        _progressBarAttendance=(ProgressBar)findViewById(R.id.progressBarAttendance);
        _mark_s_name=(TextView) findViewById(R.id.mark_s_name);
        _mark_s_uid=(TextView) findViewById(R.id.mark_s_uid);
        _mark_s_college=(TextView)findViewById(R.id.mark_s_college);
        _mark_s_dept=(TextView)findViewById(R.id.mark_s_dept);
        _mark_s_year=(TextView)findViewById(R.id.mark_s_year);
        _mark_s_sem=(TextView)findViewById(R.id.mark_s_sem);

        _attenDetails=(TextView)findViewById(R.id.attenDetails);
        _scan=(Button)findViewById(R.id.scan);
        _done=(Button)findViewById(R.id.done);
        LayoutInflater layoutInflater=getLayoutInflater();
        viewLayout=layoutInflater.inflate(R.layout.custom_toast,(ViewGroup)findViewById(R.id.custom_layout));

        //for date
        Calendar calendar=Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        Date currentTime=Calendar.getInstance().getTime();

        _attendanceDatabase=new Mark_Attendance_Database();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Attendance_Organised");
        _databaseAttendance=FirebaseDatabase.getInstance().getReference().child("Attendance_Combined");

        if (requestSinglePermission()) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            googleApiClient =new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mlocationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

            checkLocation();
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxId=(dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pAuthS_mark = FirebaseAuth.getInstance();
        databaseReferenceS_mark=FirebaseDatabase.getInstance().getReference().child("Student_Details").child(pAuthS_mark.getUid());;

        databaseReferenceS_mark.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String markName=dataSnapshot.child("name").getValue().toString();
                _mark_s_name.setText(markName);
                String markUID=dataSnapshot.child("uid").getValue().toString();
                _mark_s_uid.setText(markUID);
                String markCollege=dataSnapshot.child("college").getValue().toString();
                _mark_s_college.setText(markCollege);
                String markDept=dataSnapshot.child("dept").getValue().toString();
                _mark_s_dept.setText(markDept);
                String markYear=dataSnapshot.child("year").getValue().toString();
                _mark_s_year.setText(markYear);
                String markSem=dataSnapshot.child("semester").getValue().toString();
                _mark_s_sem.setText(markSem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        _scan.setOnClickListener(this);
        _done.setOnClickListener(this);

    }

    private boolean checkLocation()
    {
        if(!isLocationEnabled())
        {
            showAlert();
        }
        return isLocationEnabled();
    }

    private void showAlert()
    {
        final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Location Settings is set to to 'Off'.\n Please Enable Location to "+
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                      startActivity(new Intent(MarkAttendanceActivity.this, StudentHomeActivity.class));
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled(){
        locationManager=(LocationManager)this.getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean requestSinglePermission()
    {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        isPermission=true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if(response.isPermanentlyDenied())
                        {
                            isPermission=false;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
        return isPermission;
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        if (latLng != null) {
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14F));
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        startLocationUpdates();
        location=LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if(location!=null)
        {
            startLocationUpdates();
        }
        else{
            Toast.makeText(this,"Location not detected",Toast.LENGTH_LONG).show();
        }
    }

    private void startLocationUpdates()
    {
        locationRequest=LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                locationRequest,this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        String msg="Updated Location: "+ Double.toString(location.getLatitude())+","+ Double.toString(location.getLongitude());

//        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

//        attenDetails _attendanceDatabaseLocation =new attenDetails(location.getLatitude(),location.getLongitude());

        latLng=new LatLng(location.getLatitude(),location.getLongitude());
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(googleApiClient!=null)
        {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(googleApiClient.isConnected())
        {
            googleApiClient.disconnect();
        }
    }

    private void scanDetails()
    {
        IntentIntegrator intentIntegrator = new IntentIntegrator(MarkAttendanceActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setPrompt("scanning");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
//        intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
        intentIntegrator.initiateScan();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        final IntentResult result=IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result!=null && result.getContents()!=null)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Scan Result")
                    .setMessage(result.getContents())
                    .setPositiveButton("OK!",new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
//                            ClipboardManager manager=(ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//                            ClipData data=ClipData.newPlainText("result",result.getContents());
//                            manager.setPrimaryClip(data);
                            _attenDetails.setText(result.getContents());
                            Toast.makeText(MarkAttendanceActivity.this,"Scanned Successfully",Toast.LENGTH_LONG).show();

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(MarkAttendanceActivity.this,"Scan again for attendance",Toast.LENGTH_LONG).show();
                        }
                    }

            ).create().show();
        }
        super.onActivityResult(requestCode,resultCode,data);

    }

    private void attendanceDone()
    {
        String details=_attenDetails.getText().toString();

        if(TextUtils.isEmpty(details))
        {
            Toast.makeText(MarkAttendanceActivity.this, "Click on Scan button", Toast.LENGTH_LONG).show();
            return;
        }
        if(location==null)
        {
            Toast.makeText(MarkAttendanceActivity.this,"Please turn on the location",Toast.LENGTH_LONG).show();
            return;
        }


        _attendanceDatabase.setName(_mark_s_name.getText().toString().trim());
        _attendanceDatabase.setUid(_mark_s_uid.getText().toString().trim());
        _attendanceDatabase.setCollege(_mark_s_college.getText().toString().trim());
        _attendanceDatabase.setDepartment(_mark_s_dept.getText().toString().trim());
        _attendanceDatabase.setSemester(_mark_s_sem.getText().toString().trim());
        _attendanceDatabase.setDate(DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime()));
//        _attendanceDatabase.setTime_date(new SimpleDateFormat("EEEE , dd-MM-yyyy hh:mm:ss a").format(Calendar.getInstance().getTime()));
        _attendanceDatabase.setTime(new SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(new Date()));
        _attendanceDatabase.setLec_details(_attenDetails.getText().toString().trim());
        _attendanceDatabase.setLatitude(Double.toString(location.getLatitude()));
        _attendanceDatabase.setLongitude(Double.toString(location.getLongitude()));

        _progressBarAttendance.setVisibility(View.VISIBLE);
        if(!(location==null) && !TextUtils.isEmpty(details)) {
            try {
                databaseReference.child(_mark_s_college.getText().toString()).child(_mark_s_dept.getText().toString())
                        .child(_mark_s_sem.getText().toString()).child(pAuthS_mark.getUid())
//                        .push().setValue(_attendanceDatabase);
                        .child(DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime())).push().setValue(_attendanceDatabase);
                _databaseAttendance.child(pAuthS_mark.getUid()).push().setValue(_attendanceDatabase);
                _progressBarAttendance.setVisibility(View.GONE);

                Toast toast1 = Toast.makeText(MarkAttendanceActivity.this,"Toast:Gravity.TOP",Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.CENTER,0,0);
                toast1.setView(viewLayout);
                toast1.show();
                _attenDetails.setText("");

            } catch (Exception e) {
                _progressBarAttendance.setVisibility(View.GONE);
                e.printStackTrace();
                Toast.makeText(MarkAttendanceActivity.this, "Attendance not done", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            _progressBarAttendance.setVisibility(View.GONE);
            Toast.makeText(MarkAttendanceActivity.this, "Attendance not done", Toast.LENGTH_LONG).show();
        }
    }

    private void closeKeyboard()
    {
        View view=this.getCurrentFocus();
        if(view!=null)
        {
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    @Override
    public void onClick(View view) {

        if(view==_scan)
        {
//            startActivity(new Intent(MarkAttendanceActivity.this,ScanActivity.class));
                scanDetails();
                closeKeyboard();
        }
        if(view==_done)
        {
            attendanceDone();
        }
    }
}
