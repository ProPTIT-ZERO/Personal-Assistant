package club.mobile.d21.personalassistant.ui.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodayViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
       value = "You can see your task today here"
    }
    val text: LiveData<String> = _text
}