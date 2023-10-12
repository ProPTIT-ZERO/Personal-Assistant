package club.mobile.d21.personalassistant.ui.all_note

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import club.mobile.d21.personalassistant.data.AppDatabase
import club.mobile.d21.personalassistant.data.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class AllNoteViewModel (application: Application) : AndroidViewModel(application) {
    private val data = AppDatabase.getNoteDatabase(application.applicationContext)
    private val noteDao = data.noteDAO()
    private val _note = MutableLiveData<List<Note>>()
    private val _note4today = MutableLiveData<List<Note>>()
    init {
        viewModelScope.launch(Dispatchers.IO){
            val defaultNote =
                Note(null, "Hello world",
                    LocalDate.now().toString())
            if(noteDao.getRowCount()==0) {
                noteDao.addNote(defaultNote)
            }
            _note.postValue(noteDao.getAll())
            _note4today.postValue(noteDao.getNotesForTodayAndTomorrow())
        }
    }
    internal fun addNote(date:LocalDate, note:String){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.addNote(
                Note(null, note, date.toString())
            )
            _note.postValue(noteDao.getAll())
            _note4today.postValue(noteDao.getNotesForTodayAndTomorrow())
        }
    }
    internal fun editNote(updatedNote: Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.updateNote(updatedNote)
            _note.postValue(noteDao.getAll())
            _note4today.postValue(noteDao.getNotesForTodayAndTomorrow())
        }
    }
    internal fun deleteNote(deletedNote: Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteNote(deletedNote)
            _note.postValue(noteDao.getAll())
            _note4today.postValue(noteDao.getNotesForTodayAndTomorrow())
        }
    }
    val note: LiveData<List<Note>> = _note
    val note4today: LiveData<List<Note>> = _note4today
}