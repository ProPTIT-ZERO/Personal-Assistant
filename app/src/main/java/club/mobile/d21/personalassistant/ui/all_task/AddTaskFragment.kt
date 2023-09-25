package club.mobile.d21.personalassistant.ui.all_task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.data.Priority
import club.mobile.d21.personalassistant.databinding.FragmentAddTaskBinding
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH
import java.util.Calendar.YEAR
import java.util.Calendar.getInstance

class AddTaskFragment: Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var callBackTask: CallBackTask
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }
    private fun initEvent(){
        var selectedDate: LocalDate? = null
        var selectedTime: LocalTime? = null
        binding.setDate.setOnClickListener {
            val calendar = getInstance()
            val datePickerDialog = DatePickerDialog(requireContext(),{
                //Những cái trong đây sẽ thay đổi khi pick date
                datePicker, year:Int, month:Int, day:Int ->
                selectedDate = LocalDate.of(year,month+1,day)
                binding.date.text = String.format("%02d / %02d / %04d",day,month,year)
                //3 dòng tiếp là giá trị mặc định khi mở dialog
            },calendar.get(YEAR),
                calendar.get(MONTH),
                calendar.get(DAY_OF_MONTH))
            datePickerDialog.show()
        }
        binding.setTime.setOnClickListener {
            val currentTime = LocalTime.now()
            val timePickerDialog = TimePickerDialog(requireContext(),{
                //Những cái trong đây sẽ thay đổi khi pick time
                timePicker, hour:Int, minute:Int ->
                selectedTime = LocalTime.of(hour, minute)
                binding.time.text = String.format("%02d : %02d",hour,minute)
                //3 dòng tiếp là giá trị mặc định khi mở dialog
            },currentTime.hour,
                currentTime.minute,
                true)
            timePickerDialog.show()
        }
        var priority: Priority? = null
        binding.choosePriority.setOnCheckedChangeListener{ group, checkID ->
            when (checkID) {
                R.id.chooseCritical ->{
                    priority = Priority.CRITICAL
                }
                R.id.chooseImportant ->{
                    priority = Priority.IMPORTANT
                }
                R.id.chooseLowPriority ->{
                    priority = Priority.LOW_PRIORITY
                }
            }
        }
        binding.createButton.setOnClickListener {
            if(selectedDate == null || selectedTime == null ||
                priority == null|| !handleValidation()){
                return@setOnClickListener
            }
            callBackTask.add(binding.taskEditText.text.toString(), priority!!,
                selectedDate!!, selectedTime!!, binding.detailEditText.text.toString())
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().remove(this).commit()
            fragmentManager.popBackStack()
        }
    }
    companion object{
        fun newInstance(callBackTask: CallBackTask): AddTaskFragment{
            val fragment = AddTaskFragment()
            fragment.callBackTask = callBackTask
            return fragment
        }
    }
    private fun handleValidation(): Boolean {
        if(binding.taskEditText.text.toString().isEmpty()){
            binding.taskEditText.error = getString(R.string.please_enter_your_task)
            return false
        }
        return true
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
interface CallBackTask{
    fun add(
        task: String, priority: Priority, deadlineDay: LocalDate,
        deadLineTime: LocalTime, detail:String)
}