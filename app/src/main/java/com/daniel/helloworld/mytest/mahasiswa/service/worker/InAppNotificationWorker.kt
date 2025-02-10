package com.daniel.helloworld.mytest.mahasiswa.service.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.crocodic.core.data.model.AppNotification
import org.greenrobot.eventbus.EventBus

class InAppNotificationWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        // Do the work here--in this case, show notification.
        val inAppNotification = AppNotification(
            title = "Hallo Gais!",
            content = "Saya sedang belajar membuat notifikasi di dalam aplikasi."
        )
        EventBus.getDefault().post(inAppNotification)

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}