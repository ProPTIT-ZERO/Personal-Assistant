package club.mobile.d21.personalassistant.ui.bottom_sheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.databinding.FragmentAddNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetAddNote(
    private val callBack : CallBack,
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
            callBack.add(String.format("%s-%s-%sT00:00:00Z",binding.yearEditText.text.toString(),
                binding.monthEditText.text.toString(), binding.dayEditText.text.toString()),
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
interface CallBack{
    fun add(date:String, note:String)
}