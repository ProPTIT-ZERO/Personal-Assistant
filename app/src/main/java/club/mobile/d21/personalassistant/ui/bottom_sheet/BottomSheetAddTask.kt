package club.mobile.d21.personalassistant.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import club.mobile.d21.personalassistant.data.Priority
import club.mobile.d21.personalassistant.databinding.FragmentAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetAddTask(
    private val callBack: CallBackTask
) : BottomSheetDialogFragment(){
    private lateinit var binding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    }
}
interface CallBackTask{
    fun add(task: String, priority: Priority,
            deadlineDay: String, deadlineTime: String, detail:String)
}