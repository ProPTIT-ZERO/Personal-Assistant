package club.mobile.d21.personalassistant.ui.alarm

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import club.mobile.d21.personalassistant.data.Alarm
import club.mobile.d21.personalassistant.databinding.FragmentAddAlarmBinding
import java.time.LocalTime

class AddAlarmFragment : Fragment() {
    private var _binding: FragmentAddAlarmBinding? = null
    private val binding get() = _binding!!
    private lateinit var callBackAddAlarm: CallBackAddAlarm
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }
    private fun initEvent() {
        var selectedTime: LocalTime? = null
        binding.setTime.setOnClickListener {
            val currentTime = LocalTime.now()
            val timePickerDialog = TimePickerDialog(requireContext(),{
                //Những cái trong đây sẽ thay đổi khi pick time
                    _, hour:Int, minute:Int ->
                selectedTime = LocalTime.of(hour, minute)
                binding.time.text = String.format("%02d : %02d",hour,minute)
                //3 dòng tiếp là giá trị mặc định khi mở dialog
            },currentTime.hour,
                currentTime.minute,
                true)
            timePickerDialog.show()
        }
        binding.addButton.setOnClickListener {
            if(selectedTime == null){
                return@setOnClickListener
            }
            callBackAddAlarm.add(Alarm(null,binding.nameEditText.text.toString()
                ,selectedTime.toString()))
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().remove(this).commit()
            fragmentManager.popBackStack()
        }
    }
    companion object {
        fun newInstance(callBackAddAlarm: CallBackAddAlarm): AddAlarmFragment {
            val fragment = AddAlarmFragment()
            fragment.callBackAddAlarm = callBackAddAlarm
            return fragment
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
interface CallBackAddAlarm{
    fun add(alarm: Alarm)
}