package club.mobile.d21.personalassistant.ui.all_note

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.databinding.FragmentAddNoteBinding
import java.time.LocalDate
import java.util.Calendar.*

class AddNoteFragment : Fragment() {
    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var callBackAddNote: CallBackAddNote
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }
    private fun initEvent() {
        var selectedDate: LocalDate? = null
        binding.setDate.setOnClickListener {
            val calendar = getInstance()
            val datePickerDialog = DatePickerDialog(requireContext(),{
                    datePicker, year:Int, month:Int, day:Int ->
                    selectedDate = LocalDate.of(year, month+1,day)
                binding.date.text = String.format("%2d / %2d / %4d",day,month,year)
            },calendar.get(YEAR),
                calendar.get(MONTH),
                calendar.get(DAY_OF_MONTH))
            datePickerDialog.show()
        }
        binding.addButton.setOnClickListener {
            if(selectedDate == null || !handleValidation()){
                return@setOnClickListener
            }
            callBackAddNote.add(selectedDate!!,
                binding.noteEditText.text.toString())
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().remove(this).commit()
            fragmentManager.popBackStack()
        }
    }
    companion object {
        fun newInstance(callBackAddNote: CallBackAddNote): AddNoteFragment {
            val fragment = AddNoteFragment()
            fragment.callBackAddNote = callBackAddNote
            return fragment
        }
    }
    private fun handleValidation(): Boolean {
        if(binding.noteEditText.text.toString().isEmpty()){
            binding.noteEditText.error = getString(R.string.please_enter_your_note)
            return false
        }
        return true
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface CallBackAddNote{
    fun add(date: LocalDate, note:String)
}