package com.daniel.helloworld.mytest.mahasiswa.service.fcm

import android.util.Log
import com.crocodic.core.data.model.AppNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.greenrobot.eventbus.EventBus

class TrialFirebaseMsgService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("firebase-token - 2", token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val inAppNotification = AppNotification(
            title = message.notification?.title,
            content = message.notification?.body
        )
        EventBus.getDefault().post(inAppNotification)
    }

}