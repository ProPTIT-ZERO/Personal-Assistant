package club.mobile.d21.personalassistant.ui.assistant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AssistantViewModel : ViewModel() {
    private var _text = MutableLiveData<String>().apply {
        value = "This is your assistant"
    }
    val text: LiveData<String> = _text
}