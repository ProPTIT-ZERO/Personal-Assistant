package club.mobile.d21.personalassistant.ui.all_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import club.mobile.d21.personalassistant.databinding.FragmentAllNoteBinding
import club.mobile.d21.personalassistant.ui.bottom_sheet.BottomSheetAddNote
import club.mobile.d21.personalassistant.ui.bottom_sheet.CallBack

class AllNoteFragment : Fragment() {
    private var _binding: FragmentAllNoteBinding? = null
    private val binding get() = _binding!!
    private val allNoteViewModel: AllNoteViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllNoteBinding.inflate(inflater, container, false)
        val textView: TextView = binding.textView
        allNoteViewModel.text.observe(viewLifecycleOwner) { text ->
            textView.text = text
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEvents()
    }
    private fun handleEvents(){
        binding.addButton.setOnClickListener{
            BottomSheetAddNote(object : CallBack{
                override fun add(date: String, note: String) {
                    allNoteViewModel.addNote(date,note)
                }
            }
            ).show(childFragmentManager,"Hello bottom")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}