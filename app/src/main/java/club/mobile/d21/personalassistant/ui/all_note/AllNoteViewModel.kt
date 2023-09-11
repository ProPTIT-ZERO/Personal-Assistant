package club.mobile.d21.personalassistant.ui.all_note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import club.mobile.d21.personalassistant.data.AppDatabase
import club.mobile.d21.personalassistant.data.InstantConverter
import club.mobile.d21.personalassistant.data.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllNoteViewModel (application: Application) : AndroidViewModel(application) {
    private val instantConverter = InstantConverter()
    private val data = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java, "database-note"
    )
        .addTypeConverter(instantConverter)
        .build()
    private val noteDao = data.noteDAO()
    private val _text = MutableLiveData<String>()
    init {
        viewModelScope.launch(Dispatchers.IO){
            noteDao.clearAllNote()
            val defaultNote =
                Note(1, "Sinh nhật Quốc Anh",
                    instantConverter.fromStringToInstant("2023-10-20T00:00:00Z"))
            if(noteDao.getNoteById(1) == null) {
                noteDao.addNote(defaultNote)
            }
            _text.postValue(noteDao.getNoteById(1).content.toString())
        }
    }
    internal fun addNote(date:String, note:String){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.addNote(
                Note(noteDao.getRowCount()+1, note, instantConverter.fromStringToInstant(date))
            )
            _text.postValue(noteDao.getNoteById(noteDao.getRowCount()).content.toString())
        }
    }
    val text: LiveData<String> = _text
}