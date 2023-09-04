package club.mobile.d21.personalassistant.ui.all_note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room.databaseBuilder
import club.mobile.d21.personalassistant.data.AppDatabase
import club.mobile.d21.personalassistant.data.Note
import kotlinx.coroutines.launch
import java.time.Instant

class AllNoteViewModel (application: Application) : AndroidViewModel(application) {
    private val data = databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java, "database-note"
    ).build()
    private val noteDao = data.noteDAO()
    private val defaultNote =
        Note(1, "Sinh nhật Quốc Anh", Instant.parse("2023-10-20T00:00:00Z"))
    init {
        viewModelScope.launch {
            initDatabase()
        }
    }
    private fun initDatabase() {
        if (noteDao.getNoteById(1) == null) {
            noteDao.addNote(defaultNote)
        }
    }
    private var _text = MutableLiveData<String>().apply {
        value = noteDao.getNoteById(1).toString()
    }
    val text: LiveData<String> = _text
}