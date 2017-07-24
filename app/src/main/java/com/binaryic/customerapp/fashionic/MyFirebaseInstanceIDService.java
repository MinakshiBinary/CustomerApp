
package com.binaryic.customerapp.fashionic;

import android.util.Log;

import com.binaryic.customerapp.fashionic.common.MyApplication;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by minakshi on 4/5/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("FCMTOKEN", "Refreshed token: " + refreshedToken);
        MyApplication.setting.edit().putString("fcm_token", refreshedToken).commit();
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
       // sendRegistrationToServer(refreshedToken);
    }
}
