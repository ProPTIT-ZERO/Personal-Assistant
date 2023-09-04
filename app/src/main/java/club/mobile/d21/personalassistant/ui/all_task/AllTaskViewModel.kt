package club.mobile.d21.personalassistant.ui.all_task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllTaskViewModel : ViewModel() {
    private var _text = MutableLiveData<String>().apply {
        value = "You can see all your task here"
    }
    val text: LiveData<String> = _text
}