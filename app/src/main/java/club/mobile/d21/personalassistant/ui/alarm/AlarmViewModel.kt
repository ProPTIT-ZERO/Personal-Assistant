package club.mobile.d21.personalassistant.ui.alarm

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import club.mobile.d21.personalassistant.data.Alarm
import club.mobile.d21.personalassistant.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.Calendar

class AlarmViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val data = AppDatabase.getAlarmDatabase(context)
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val intent = Intent(application.applicationContext, AlarmReceiver::class.java)
    private val alarmDAO = data.alarmDAO()
    private val _alarm = MutableLiveData<List<Alarm>>()
    init{
        viewModelScope.launch(Dispatchers.IO) {
            _alarm.postValue(alarmDAO.getAll())
        }
    }
    internal fun addAlarm(newAlarm: Alarm){
        viewModelScope.launch(Dispatchers.IO){
            alarmDAO.addAlarm(newAlarm)
            val id = alarmDAO.getAlarmsWithSameTimeAndName(newAlarm.time!!,newAlarm.name!!).id!!
            setAlarm(context, calculateAlarmTime(LocalTime.parse(newAlarm.time)),id)
            _alarm.postValue(alarmDAO.getAll())
        }
    }
    internal fun deleteAlarm(oldAlarm: Alarm){
        viewModelScope.launch(Dispatchers.IO){
            alarmDAO.deleteAlarm(oldAlarm)
            _alarm.postValue(alarmDAO.getAll())
        }
    }
    internal fun turnOnAlarm(alarm: Alarm){
        viewModelScope.launch(Dispatchers.IO){
            alarmDAO.turnOnAlarm(alarm.id)
            setAlarm(context,calculateAlarmTime(LocalTime.parse(alarm.time!!)),alarm.id!!)
            _alarm.postValue(alarmDAO.getAll())
        }
    }
    internal fun turnOffAlarm(alarm: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            alarmDAO.turnOffAlarm(alarm.id)
            cancelAlarm(context,alarm.id!!)
            val serviceIntent = Intent(context, AlarmMusic::class.java)
            serviceIntent.putExtra("Alarm","Off")
            context.startService(serviceIntent)
            _alarm.postValue(alarmDAO.getAll())
        }
    }
    val alarm: LiveData<List<Alarm>> = _alarm

    private fun calculateAlarmTime(selectedTime: LocalTime): Long {
        val now = LocalTime.now()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, selectedTime.hour)
        calendar.set(Calendar.MINUTE, selectedTime.minute)
        calendar.set(Calendar.SECOND, 0)
        if (selectedTime.isBefore(now)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        return calendar.timeInMillis
    }
    private fun setAlarm(context: Context, timeInMillis: Long, requestID: Int) {
        val pendingIntent =
            PendingIntent.getBroadcast(context, requestID, intent, PendingIntent.FLAG_IMMUTABLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val canScheduleExact = alarmManager.canScheduleExactAlarms()
            if (!canScheduleExact) {
                Toast.makeText(context,"Can't schedule exact",Toast.LENGTH_SHORT).show()
            }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
        } else {
            Toast.makeText(context,"Your android version is below 12",Toast.LENGTH_SHORT).show()
        }
    }
    private fun cancelAlarm(context: Context,requestID: Int) {
        val pendingIntent = PendingIntent.getBroadcast(context, requestID, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)
    }
}
