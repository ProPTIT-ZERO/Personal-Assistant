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

class AllNoteFragment : Fragment() {
    private var _binding: FragmentAllNoteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val allNoteViewModel = ViewModelProvider(this)[AllNoteViewModel::class.java]
        _binding = FragmentAllNoteBinding.inflate(inflater, container, false)
        val textView: TextView = binding.textView
        allNoteViewModel.text.observe(viewLifecycleOwner) { text ->
            textView.text = text
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}