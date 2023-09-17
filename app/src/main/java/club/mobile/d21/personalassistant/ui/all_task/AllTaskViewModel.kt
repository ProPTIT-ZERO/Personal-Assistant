package club.mobile.d21.personalassistant.ui.all_task

import android.app.Application
import android.widget.Spinner
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import club.mobile.d21.personalassistant.data.AppDatabase
import club.mobile.d21.personalassistant.data.Priority
import club.mobile.d21.personalassistant.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime

class AllTaskViewModel(application: Application): AndroidViewModel(application) {
    private val data = AppDatabase.getTaskDatabase(application.applicationContext)
    private val taskDAO = data.taskDAO()
    private val _task = MutableLiveData<List<Task>>()
    init{
        viewModelScope.launch(Dispatchers.IO) {
            val defaultTask = Task(1,"Làm sinh nhật Quốc Anh", Priority.CRITICAL,
                LocalDate.of(2023,10,20).toString(),
                LocalTime.of(0,0,0).toString(),"Mua bánh kẹo")
            if(taskDAO.getRowCount()==0) {
                taskDAO.addTask(defaultTask)
            }
        }
    }
    internal fun addTask(
        task:String, priority: Priority,
        date:LocalDate, time: LocalTime, detail:String){
        viewModelScope.launch(Dispatchers.IO) {
            taskDAO.addTask(
                Task(taskDAO.getRowCount() + 1, task,
                    priority, date.toString(), time.toString(), detail)
            )
        }
    }
    internal suspend fun getCriticalTask(): List<Task> {
        return withContext(Dispatchers.IO) {
            taskDAO.getCriticalTasks()
        }
    }
    internal suspend fun getImportantTask(): List<Task> {
        return withContext(Dispatchers.IO) {
            taskDAO.getImportantTasks()
        }
    }
    internal suspend fun getLowPriorityTask(): List<Task> {
        return withContext(Dispatchers.IO) {
            taskDAO.getLowPriorityTasks()
        }
    }
    var task: MutableLiveData<List<Task>> = _task
}