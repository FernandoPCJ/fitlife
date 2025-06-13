package com.fernando.fitlife.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

object NotificationScheduler {

    /**
     * Agenda uma notificação para o horário especificado.
     * @param context Contexto da aplicação.
     * @param triggerAtMillis Horário em milissegundos para disparar a notificação.
     */
    fun scheduleNotification(context: Context, triggerAtMillis: Long) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    /**
     * Cancela a notificação agendada.
     * @param context Contexto da aplicação.
     */
    fun cancelNotification(context: Context) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    /**
     * Agenda uma notificação recorrente (diária).
     * @param context Contexto da aplicação.
     * @param triggerAtMillis Horário inicial para disparar a notificação.
     */
    fun scheduleDailyNotification(context: Context, triggerAtMillis: Long) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            AlarmManager.INTERVAL_DAY, // Repetir a notificação a cada 24 horas
            pendingIntent
        )
    }
}
