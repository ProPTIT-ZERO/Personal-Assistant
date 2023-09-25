package club.mobile.d21.personalassistant.ui.all_task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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
            val defaultTask = Task(null,"Báo cáo tiến độ app", Priority.CRITICAL,
                LocalDate.of(2023,9,25).toString(),
                LocalTime.of(20,0,0).toString(),"Code app")
            if(taskDAO.getRowCount()==0) {
                taskDAO.addTask(defaultTask)
            }
            _task.postValue(taskDAO.getAll())
        }
    }
    internal fun addTask(
        task:String, priority: Priority,
        date:LocalDate, time: LocalTime, detail:String){
        viewModelScope.launch(Dispatchers.IO) {
            taskDAO.addTask(
                Task(null, task, priority, date.toString(), time.toString(), detail)
            )
            _task.postValue(taskDAO.getAll())
        }
    }
    internal fun deleteTask(deletedTask: Task){
        viewModelScope.launch(Dispatchers.IO) {
            viewModelScope.launch(Dispatchers.IO) {
                taskDAO.deleteTask(deletedTask)
                _task.postValue(taskDAO.getAll())
            }
        }
    }
    internal fun editTask(editedTask: Task){
        viewModelScope.launch(Dispatchers.IO) {
            viewModelScope.launch(Dispatchers.IO) {
                taskDAO.updateTask(editedTask)
                _task.postValue(taskDAO.getAll())
            }
        }
    }
    internal fun doneTask(ID: Int){
        viewModelScope.launch(Dispatchers.IO) {
            viewModelScope.launch(Dispatchers.IO) {
                taskDAO.markTaskAsDone(ID)
                _task.postValue(taskDAO.getAll())
            }
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
    internal suspend fun getTodayTask(): List<Task> {
        return withContext(Dispatchers.IO) {
            taskDAO.getTasksForTodayAndTomorrow()
        }
    }
    val task: LiveData<List<Task>> = _task
}