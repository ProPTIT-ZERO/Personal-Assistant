package club.mobile.d21.personalassistant.ui.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, AlarmMusic::class.java)
        serviceIntent.putExtra("Alarm","On")
        context.startService(serviceIntent)
    }
}