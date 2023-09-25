package club.mobile.d21.personalassistant.ui.today

import android.app.Application
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import club.mobile.d21.personalassistant.data.AppDatabase
import club.mobile.d21.personalassistant.data.Note
import club.mobile.d21.personalassistant.data.Task
import club.mobile.d21.personalassistant.ui.all_note.AllNoteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class TodayViewModel(application: Application) : AndroidViewModel(application) {
    private val data = AppDatabase.getTaskDatabase(application.applicationContext)
    private val noteDao = data.noteDAO()
    private val _note = MutableLiveData<List<Note>>()
    private val taskDao = data.taskDAO()
    private val _task = MutableLiveData<List<Task>>()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            _note.postValue(noteDao.getAll())
            _task.postValue(taskDao.getTasksForTodayAndTomorrow())
        }
    }
    internal fun editNote(updatedNote: Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.updateNote(updatedNote)
            _note.postValue(noteDao.getNotesForTodayAndTomorrow())
        }
    }
    internal fun deleteNote(deletedNote: Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteNote(deletedNote)
            _note.postValue(noteDao.getNotesForTodayAndTomorrow())
        }
    }
    val note: LiveData<List<Note>> = _note
    val task: LiveData<List<Task>> = _task
}