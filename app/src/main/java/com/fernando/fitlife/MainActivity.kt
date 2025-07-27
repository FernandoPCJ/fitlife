package com.fernando.fitlife

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.fernando.fitlife.ui.FitLifeApp
import com.fernando.fitlife.viewmodel.FavoritosViewModel
import com.fernando.fitlife.viewmodel.FavoritosPersonalViewModel
import com.fernando.fitlife.viewmodel.SettingsViewModel
import com.fernando.fitlife.viewmodel.AuthViewModel
import com.fernando.fitlife.viewmodel.TrainerViewModel
import com.fernando.fitlife.viewmodel.WorkoutsViewModel
import com.fernando.fitlife.worker.SyncWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private val favoritosViewModel: FavoritosViewModel by viewModels()
    private val favoritosPersonalViewModel: FavoritosPersonalViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val trainerViewModel: TrainerViewModel by viewModels()
    private val workoutsViewModel: WorkoutsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        scheduleSync()

        setContent {
            FitLifeApp(
                favoritosViewModel = favoritosViewModel,
                favoritosPersonalViewModel = favoritosPersonalViewModel,
                settingsViewModel = settingsViewModel,
                authViewModel = authViewModel,
                trainerViewModel = trainerViewModel,
                workoutsViewModel = workoutsViewModel
            )
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id",
                "Lembretes",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun scheduleSync() {
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(this).enqueue(syncRequest)
    }

    fun scheduleAlarm(triggerAtMillis: Long) {
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
    }
}