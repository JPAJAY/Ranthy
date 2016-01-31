/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package src.main.java.com.Ajay.Messenger;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService extends GcmListenerService {
    private static final String TAG = "MyGcmListenerService";
    public static String fromAddr,meet,toAddr,date,time,Invite_id,state,message;
    DatabaseHelper mydb;
    /**
     * Called when message is received.
     *
//     * @param from SenderID of the sender.
//     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]

    @Override
    public void onMessageReceived(String from, Bundle data) {
        mydb = new DatabaseHelper(this);
        // invite act            ac/re  act
        fromAddr = data.getString("fromAddr");//welcome.myName Ajay       Acc/Rej  Acc
        toAddr = data.getString("toAddr");//Spinner   Bhoo                  reciverfrom  Ajay
        date = data.getString("Date");//datepicker    /date                 Date date
        time = data.getString("Time");//timepicker     time//////                Time   time
        meet= data.getString("meet");
        message = "Invite From " + fromAddr + " For  " + date + " at " + time;
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }
        if (!Invite.name.equals(toAddr)) {
            System.out.println("                                                        " +
                    "Returned                                  " +
                    "        ");
            return;

        }
            if (MyGcmListenerService.fromAddr.equals("Accepted")) {
                Accept(message);
                state = fromAddr.toString();
                b();
                System.out.println("in acc/rej"+from+toAddr+date+time);
            }

             else if( MyGcmListenerService.fromAddr.equals("Rejected")){
                Reject(message);
                state = fromAddr.toString();
                b();
            }
            else {
                invite(message);//sched
                state = "Invite Received";
                db();
//                System.out.println("in inv"+from+toAddr+date+time);
            }
            System.out.println(message);
        }
    public void db(){
        mydb.setMeetings(fromAddr.toString(),
                toAddr.toString(),
                date.toString(),
                time.toString(),
                "30 Minutes",
                state.toString()

                );
    }
    public void b(){
       mydb.UpdateMeetings(
               meet, state.toString()
        );

    }

    // [START_EXCLUDE]
    /**
     * Production applications would usually process the message here.
     * Eg: - Syncing with server.
     *     - Store message in local database.
     *     - Update UI.
     */

    /**
     * In some cases it may be useful to show a notification indicating to the user
     * that a message was received.
     */

    // [END_EXCLUDE]

    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void invite(String message) {
        Intent intent = new Intent(this, Reply.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("Me2 Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void Accept(String message) {
        Intent intent = new Intent(this, Scheduled.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("Me2 Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    private void Reject(String message) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("Me2 Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
