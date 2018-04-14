package com.example.root.realheart.Common;

import com.example.root.realheart.Model.User;
import com.example.root.realheart.Remote.APIService;
import com.example.root.realheart.Remote.RetrofitClient;

/**
 * Created by root on 28/3/18.
 */

public class Common {
    public  static User currentUser;

    private static final String BASE_URL="https://fcm.googleapis.com/";
    /*private static final String fcmUrl="https://fcm.googleapis.com/";

    public static String getFcmUrl() {
        return fcmUrl;
    }*/

    public static APIService getFCMService()
    {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
