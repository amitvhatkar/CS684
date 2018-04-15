package com.example.root.realheart.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.root.realheart.Common.Common;
import com.example.root.realheart.Model.Abnormality;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChkAbnormalityService extends Service implements ChildEventListener{

    FirebaseDatabase db;
    DatabaseReference abnormalityReference;
    DatabaseReference user;
    DatabaseReference ecgReading;

    public ChkAbnormalityService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        db = FirebaseDatabase.getInstance();
        abnormalityReference = db.getReference("Abnormality");
        user = db.getReference("user");
        ecgReading = db.getReference("ECGReading");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ecgReading.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Log.w("Changed Values", dataSnapshot.getKey());
        checkAbnormality();
    }

    private void checkAbnormality() {

        int noOfAbn = Integer.parseInt(Common.currentUser.getNoOfAbn());
        if (noOfAbn == 0){
            Common.currentUser.setNoOfAbn((++noOfAbn)+"");
            int heartRate = 50;
            Abnormality abnormality = new Abnormality(Common.currentUser.getUserName(),"Low Heart Rate", "Heart rate decresed to lowest level and is "+heartRate+" bpm", "false", noOfAbn+"");
            abnormalityReference.child((noOfAbn++)+"").setValue(abnormality);
            user.child(Common.currentUser.getUserName()).setValue(Common.currentUser);
            //sendAbnormalityNotification();
        }

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
