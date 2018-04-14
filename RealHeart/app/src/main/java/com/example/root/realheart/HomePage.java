package com.example.root.realheart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.realheart.Common.Common;
import com.example.root.realheart.Model.Abnormality;
import com.example.root.realheart.Remote.APIService;
import com.example.root.realheart.Service.ListenToNotificationService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView txtUserName, txtName;

    public static int xValue;

    FirebaseDatabase database;
    DatabaseReference ecgReadings;
    DatabaseReference abnormalityReference;
    DatabaseReference userReference;

    GraphView graph;
    LineGraphSeries<DataPoint> series;

    APIService mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        mService =Common.getFCMService();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView=navigationView.getHeaderView(0);
        txtName=(TextView)headerView.findViewById(R.id.txtName);
        txtName.setText(Common.currentUser.getName());

        txtUserName=(TextView)headerView.findViewById(R.id.txtUserName);
        txtUserName.setText(Common.currentUser.getUserName());

        database = FirebaseDatabase.getInstance();
        ecgReadings = database.getReference("ECGReading");
        abnormalityReference = database.getReference("Abnormality");
        userReference= database.getReference("User");

        //Call Service
        Intent notificationService=new Intent(HomePage.this, ListenToNotificationService.class);
        startService(notificationService);

        //Call Service
        Intent abnormalityService=new Intent(HomePage.this, ListenToNotificationService.class);
        startService(abnormalityService);

        setGraphParameters();
        getECGValues();


    }

    private void getECGValues() {
        Log.w("Getting use Name", Common.currentUser.getUserName());


        Query lastQuery = ecgReadings.child(Common.currentUser.getUserName().toString()).orderByKey().limitToLast(100);

        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                xValue = 0;
                series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0, 0)
                });
                for (DataSnapshot dataValue:
                        dataSnapshot.getChildren()) {
                    //Log.w("Whichc val", xValue+""+dataValue.child("value").getValue().toString());
                    //Log.w("Values", xValue+" " +new Integer("7.000"));
                    //series = new LineGraphSeries<DataPoint>(new DataPoint[] {});
                    //new Integer(dataValue.getKey().toString())


                    String ecgReadings[] = dataValue.child("value").getValue().toString().split(",");
                    for (String value: ecgReadings
                         ) {
                        series.appendData(
                                new DataPoint( xValue++,Integer.parseInt(value)),
                                true,
                                500000
                            );
                    }
                    /**/
                }
                graph.removeAllSeries();
                graph.addSeries(series);
                //Log.w("Serise", series.getThickness()+"");


                //checkAbnormality();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /*private void checkAbnormality() {

        int noOfAbn = Integer.parseInt(Common.currentUser.getNoOfAbn());
        if (noOfAbn == 0){
            Common.currentUser.setNoOfAbn((++noOfAbn)+"");
            int heartRate = 50;
            Abnormality abnormality = new Abnormality(Common.currentUser.getUserName(),"Low Heart Rate", "Heart rate decresed to lowest level and is "+heartRate+" bpm", "false", noOfAbn+"");
            abnormalityReference.child((noOfAbn++)+"").setValue(abnormality);
            userReference.child(Common.currentUser.getUserName()).setValue(Common.currentUser);
            //sendAbnormalityNotification();
        }

    }*/

    private void setGraphParameters() {
        graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 0)
        });

        series.setAnimated(true);
        graph.setTitle("ECG Readings");
        graph.setFocusable(true);
        graph.setSoundEffectsEnabled(true);
        graph.getGridLabelRenderer().setHumanRounding(true);
        //graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        //graph.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.VERTICAL );
        //graph.getViewport().setDrawBorder(true);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        //graph.getGridLabelRenderer().setNumVerticalLabels(51);
        //graph.getGridLabelRenderer().setNumHorizontalLabels(10);
        //graph.setMinimumWidth(1);
        //graph.setScroll
        Viewport viewport = graph.getViewport();
        viewport.setXAxisBoundsManual(true);
        viewport.setMinX(0);
        viewport.setMaxX(120);
        viewport.setYAxisBoundsManual(true);
        viewport.setMaxY(1200);
        viewport.setMinY(-600);

        viewport.setScalableY(true);
        viewport.setScalable(true);
        viewport.setScrollableY(true);
        viewport.setScrollable(true);
    }


            @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();
        Toast.makeText(this, "Loging Id"+id, Toast.LENGTH_SHORT).show();
        if (id == R.id.nav_sensor) {

        } else if (id == R.id.nav_appointment) {
            //Snackbar.make(item.getActionView(), "Yet to implement", Snackbar.LENGTH_SHORT);
            Toast.makeText(this, "Yet to implement", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_reports) {
            //Snackbar.make(item.getActionView(), "Yet to implement", Snackbar.LENGTH_SHORT);
            Toast.makeText(this, "Yet to implement", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_bill) {
            //Snackbar.make(item.getActionView(), "Yet to implement", Snackbar.LENGTH_SHORT);
            Toast.makeText(this, "Yet to implement", Toast.LENGTH_SHORT).show();
        }else{

            Log.w("Why Log hpening !!", "Dont know");
            Intent signIn = new Intent(HomePage.this, SignIn.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(signIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
