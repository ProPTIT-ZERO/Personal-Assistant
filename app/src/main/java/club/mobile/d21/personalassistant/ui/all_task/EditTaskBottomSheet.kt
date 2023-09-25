package club.mobile.d21.personalassistant.ui.all_task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.data.Priority
import club.mobile.d21.personalassistant.data.Task
import club.mobile.d21.personalassistant.databinding.BottomSheetEditTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

class EditTaskBottomSheet (selectedTask: Task): BottomSheetDialogFragment(){
    private lateinit var binding: BottomSheetEditTaskBinding
    private val allTaskViewModel: AllTaskViewModel by activityViewModels()
    private val task = selectedTask
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setOnShowListener{
            val frameLayout: FrameLayout? =
                dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
            frameLayout?.setBackgroundResource(android.R.color.transparent)
        }
        binding = BottomSheetEditTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }
    private fun initEvent(){
        var selectedDate: LocalDate? = null
        var selectedTime: LocalTime? = null
        var priority = task.priority
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
        binding.date.text = task.deadlineDay
        binding.time.text = task.deadlineTime
        binding.taskEditText.setText(task.task)
        binding.detailEditText.setText(task.detail)
        binding.setDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(requireContext(),{
                //Những cái trong đây sẽ thay đổi khi pick date
                    datePicker, year:Int, month:Int, day:Int ->
                selectedDate = LocalDate.of(year,month+1,day)
                binding.date.text = String.format("%02d / %02d / %04d",day,month+1,year)
                //3 dòng tiếp là giá trị mặc định khi mở dialog
            },calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
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
        binding.changeButton.setOnClickListener {
            if(selectedDate == null || selectedTime == null ||
                priority == null|| !handleValidation()){
                return@setOnClickListener
            }
            allTaskViewModel.editTask(Task(task.id,binding.taskEditText.text.toString(),priority,
                selectedDate.toString(),selectedTime.toString(),binding.detailEditText.text.toString()))
            dismiss()
        }
    }
    private fun handleValidation(): Boolean {
        if(binding.taskEditText.text.toString().isEmpty()){
            binding.taskEditText.error = getString(R.string.please_enter_your_task)
            return false
        }
        return true
    }
}