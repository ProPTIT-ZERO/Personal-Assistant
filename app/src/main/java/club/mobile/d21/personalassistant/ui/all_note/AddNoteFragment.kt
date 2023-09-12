package club.mobile.d21.personalassistant.ui.all_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.databinding.FragmentAddNoteBinding
import java.time.LocalDate

class AddNoteFragment() : Fragment() {
    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var callBackNote: CallBackNote
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }
    private fun initEvent() {
        binding.addButton.setOnClickListener {
            if(!handleValidation()){
                return@setOnClickListener
            }
            callBackNote.add(LocalDate.of(binding.yearEditText.text.toString().toInt(),
                binding.monthEditText.text.toString().toInt(),
                binding.dayEditText.text.toString().toInt()),
                binding.noteEditText.text.toString())
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().remove(this).commit()
            fragmentManager.popBackStack()
        }
    }
    companion object {
        fun newInstance(callBackNote: CallBackNote): AddNoteFragment {
            val fragment = AddNoteFragment()
            fragment.callBackNote = callBackNote
            return fragment
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface CallBackNote{
    fun add(date: LocalDate, note:String)
}