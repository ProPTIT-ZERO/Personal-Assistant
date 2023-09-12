package club.mobile.d21.personalassistant.ui.all_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.databinding.FragmentAllNoteBinding
import club.mobile.d21.personalassistant.ui.adapter.NoteAdapter
import java.time.LocalDate

class AllNoteFragment : Fragment() {
    private var _binding: FragmentAllNoteBinding? = null
    private val binding get() = _binding!!
    private val allNoteViewModel: AllNoteViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllNoteBinding.inflate(inflater, container, false)
        val notelist: RecyclerView = binding.noteList
        allNoteViewModel.note.observe(viewLifecycleOwner) { list
            -> notelist.adapter = NoteAdapter(list)
        }
        notelist.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEvents()
    }
    private fun handleEvents(){
        binding.addButton.setOnClickListener{
            val addNoteFragment = AddNoteFragment.newInstance(object : CallBackNote {
                override fun add(date: LocalDate, note: String) {
                    allNoteViewModel.addNote(date,note)
                }
            })
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.nav_host_fragment_container, addNoteFragment)
                .addToBackStack(null)
                .commit()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}