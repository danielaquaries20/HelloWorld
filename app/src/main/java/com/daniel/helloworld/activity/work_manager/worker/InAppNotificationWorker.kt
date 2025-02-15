package com.daniel.helloworld.activity.work_manager.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.crocodic.core.data.model.AppNotification
import org.greenrobot.eventbus.EventBus

class InAppNotificationWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val inAppNotification = AppNotification(
            title = "Hallo Gais!",
            content = "Saya sedang belajar membuat notifikasi di dalam aplikasi"
        )

        EventBus.getDefault().post(inAppNotification)
        return Result.success()
    }
}