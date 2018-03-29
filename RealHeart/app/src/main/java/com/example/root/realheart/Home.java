package com.example.root.realheart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.root.realheart.Common.Common;
import com.example.root.realheart.Model.ECGReading;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity {

    private LineChart lineChart;
    private boolean plotData;
    private Thread thread;

    public static int xValue;
    ECGReading ecgValues;
    FirebaseDatabase database;
    DatabaseReference ecgReadings;

    GraphView graph;
    LineGraphSeries<DataPoint> series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = FirebaseDatabase.getInstance();
        ecgReadings = database.getReference("ECGReading");

        graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 0)
        });
        series.setAnimated(true);
        graph.setTitle("ECG Readings");
        graph.setFocusable(true);
        graph.setSoundEffectsEnabled(true);
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        //graph.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.VERTICAL );
        //graph.getViewport().setDrawBorder(true);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setNumVerticalLabels(11);
        graph.getGridLabelRenderer().setNumHorizontalLabels(10);
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMaxY(15);
        viewport.setMinY(-15);

        viewport.setScrollableY(true);




        getECGValues();
        //setGraph();
        //startPlot();


    }


    public void getECGValues() {

        Log.w("Getting use Name", Common.currentUser.getUserName());


        Query lastQuery = ecgReadings.child(Common.currentUser.getUserName().toString()).orderByKey().limitToLast(10);

        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataValue:
                dataSnapshot.getChildren()) {
                    Log.w("Values", new Integer(dataValue.getKey().toString())+"" +new Integer(dataValue.child("value").getValue().toString()));
                    //series = new LineGraphSeries<DataPoint>(new DataPoint[] {});
                    //new Integer(dataValue.getKey().toString())
                    series.appendData(
                            new DataPoint( xValue++,new Integer(dataValue.child("value").getValue().toString())),
                            true,
                            10
                    );
                    graph.addSeries(series);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}