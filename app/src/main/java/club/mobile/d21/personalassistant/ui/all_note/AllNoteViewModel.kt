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
    private var xyz = "Hello"
    init {
        viewModelScope.launch(Dispatchers.IO){
            xyz = "Goodbye"
            noteDao.clearAllNote()
            val defaultNote =
                Note(1, "Sinh nhật Quốc Anh",
                    instantConverter.fromStringToInstant("2023-10-20T00:00:00Z"))
            noteDao.addNote(defaultNote)
            //Dòng add note có chạy vì nếu bỏ add note đi thì lỗi xyz gán null luôn
            xyz = defaultNote.content.toString()
        }

    }
    private val _text = MutableLiveData<String>().apply {
        value = xyz
    }
    val text: LiveData<String> = _text
}