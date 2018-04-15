package com.example.root.realheart.Service;

import android.util.Log;

import com.example.root.realheart.Common.Common;
import com.example.root.realheart.Model.Token;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by root on 14/4/18.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService{

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String userName= FirebaseInstanceId.getInstance().getToken();
        if(Common.currentUser!=null)
            updateTokenToFirebase(userName);
    }

    private void updateTokenToFirebase(String userName) {

        Log.w("Inside MFS", "Line 26");
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference tokens=db.getReference("Abnormality");
        Token token=new Token(userName);//false bcauz this token is sent from client
        tokens.child(Common.currentUser.getPhone()).setValue(token);
    }
}
