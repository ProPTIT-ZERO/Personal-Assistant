package club.mobile.d21.personalassistant.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.databinding.FragmentAddNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate

class BottomSheetAddNote(
    private val callBack : CallBackNote
) : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentAddNoteBinding
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
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initEvent()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initEvent() {
        binding.addBtn.setOnClickListener {
            if(!handleValidation()){
                return@setOnClickListener
            }
            callBack.add(LocalDate.of(binding.yearEditText.text.toString().toInt(),
                binding.monthEditText.text.toString().toInt(),
                binding.dayEditText.text.toString().toInt()),
                binding.noteEditText.text.toString())
            dismiss()
        }
    }
    private fun handleValidation(): Boolean {
        if(binding.dayEditText.text.toString().isEmpty()){
            binding.dayEditText.error = getString(R.string.please_enter_the_date)
            return false
        }
        if(binding.monthEditText.text.toString().isEmpty()){
            binding.monthEditText.error = getString(R.string.please_enter_the_month)
            return false
        }
        if(binding.yearEditText.text.toString().isEmpty()){
            binding.yearEditText.error = getString(R.string.please_enter_the_year)
            return false
        }
        if(binding.noteEditText.text.toString().isEmpty()){
            binding.noteEditText.error = getString(R.string.please_enter_your_note)
            return false
        }
        return true
    }
}
interface CallBackNote{
    fun add(date: LocalDate, note:String)
}