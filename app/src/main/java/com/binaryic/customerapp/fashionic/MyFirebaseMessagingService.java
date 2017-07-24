
package com.binaryic.customerapp.fashionic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.binaryic.customerapp.fashionic.activities.MainActivity;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

/**
 * Created by minakshi on 4/5/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e("onMessageReceived", "Message Notification : " + remoteMessage.getNotification());
            Log.e("onMessageReceived", "Message Notification : " + remoteMessage.getNotification().getTitle());
            Log.e("onMessageReceived", "Message Notification : " + remoteMessage.getNotification().getIcon());
            Log.e("onMessageReceived", "Message Notification : " + remoteMessage.getNotification().getClickAction());
            Log.e("onMessageReceived", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getIcon(), remoteMessage.getNotification().getClickAction());

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void sendNotification(String body, String title, String image, String action) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("action_type", action);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //  Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(body).setAutoCancel(true).setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setStyle(new NotificationCompat.BigTextStyle().bigText(body));
        Notification notification = notificationBuilder.build();
        if (image != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.custom_notification_layout);
                expandedView.setImageViewBitmap(R.id.imgNotifyBig, MyUtils.getBitmapFromURL(image));
                expandedView.setTextViewText(R.id.lblTitleNotify, title);
                expandedView.setTextViewText(R.id.lblMessageNotify, body);
                notification.bigContentView = expandedView;
            }
        } else {
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
