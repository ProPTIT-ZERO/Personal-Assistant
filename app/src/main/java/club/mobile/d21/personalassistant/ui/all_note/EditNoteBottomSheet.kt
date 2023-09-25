package club.mobile.d21.personalassistant.ui.all_note

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.data.Note
import club.mobile.d21.personalassistant.databinding.BottomSheetEditNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate
import java.util.Calendar

class EditNoteBottomSheet(selectedNote: Note) : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetEditNoteBinding
    private val allNoteViewModel: AllNoteViewModel by activityViewModels()
    private var note = selectedNote
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.setOnShowListener {
            val frameLayout: FrameLayout? =
                dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
            frameLayout?.setBackgroundResource(android.R.color.transparent)
        }
        binding = BottomSheetEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }

    private fun initEvent() {
        var selectedDate: LocalDate? = null
        binding.date.text = note.date
        binding.noteEditText.setText(note.content)
        binding.setDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(requireContext(),{
                    datePicker, year:Int, month:Int, day:Int ->
                selectedDate = LocalDate.of(year, month+1,day)
                binding.date.text = String.format("%4d-%2d-%2d",day,month+1,year)
            },calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }
        binding.changeButton.setOnClickListener {
            if(selectedDate == null || !handleValidation()){
                return@setOnClickListener
            }
            allNoteViewModel.editNote(Note(note.id, binding.noteEditText.text.toString(),
                selectedDate.toString()))
            dismiss()
        }
    }
    private fun handleValidation(): Boolean {
        if(binding.noteEditText.text.toString().isEmpty()){
            binding.noteEditText.error = getString(R.string.please_enter_your_note)
            return false
        }
        return true
    }
}