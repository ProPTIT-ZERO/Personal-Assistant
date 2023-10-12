package club.mobile.d21.personalassistant.ui.today

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import club.mobile.d21.personalassistant.data.Priority
import club.mobile.d21.personalassistant.databinding.FragmentTodayBinding
import club.mobile.d21.personalassistant.ui.adapter.NoteAdapter
import club.mobile.d21.personalassistant.ui.adapter.TaskAdapter
import club.mobile.d21.personalassistant.ui.all_note.AllNoteViewModel
import club.mobile.d21.personalassistant.ui.all_note.EditNoteBottomSheet
import club.mobile.d21.personalassistant.ui.all_task.AllTaskViewModel
import club.mobile.d21.personalassistant.ui.all_task.EditTaskBottomSheet

class TodayFragment : Fragment() {
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!
    private val allNoteViewModel: AllNoteViewModel by activityViewModels()
    private val allTaskViewModel: AllTaskViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater,container,false)
        val noteList: RecyclerView = binding.noteList
        allNoteViewModel.note4today.observe(viewLifecycleOwner) { list ->
            Log.d(allNoteViewModel.note4today.value.toString(),"Hehe")
            noteList.adapter = NoteAdapter(list,
            onEditClick = { selectedNote ->
                val bottomSheet = EditNoteBottomSheet(selectedNote)
                bottomSheet.show(childFragmentManager, bottomSheet.tag) }
            ) { selectedNote ->
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setTitle("Confirm")
                alertDialogBuilder.setMessage("Are you sure you want to delete this note?")
                alertDialogBuilder.setPositiveButton("YES") { _, _ ->
                    allNoteViewModel.deleteNote(selectedNote)
                }
                alertDialogBuilder.setNegativeButton("NO") { _, _ -> }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
        noteList.layoutManager = LinearLayoutManager(context)

        val taskList: RecyclerView = binding.taskList
        allTaskViewModel.task4today.observe(viewLifecycleOwner) { list ->
            taskList.adapter = TaskAdapter(list,
                onDeleteClick = {selectedTask->
                    val alertDialogBuilder = AlertDialog.Builder(requireContext())
                    alertDialogBuilder.setTitle("Confirm")
                    alertDialogBuilder.setMessage("Are you sure you want to delete this task?")
                    alertDialogBuilder.setPositiveButton("YES") { _, _, ->
                        allTaskViewModel.deleteTask(selectedTask)
                    }
                    alertDialogBuilder.setNegativeButton("NO"){_,_,->}
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                },
                onEditClick = {selectedTask->
                    val bottomSheet = EditTaskBottomSheet(selectedTask)
                    bottomSheet.show(childFragmentManager, bottomSheet.tag)
                },
                onDoneClick = {selectedTask->
                    val alertDialogBuilder = AlertDialog.Builder(requireContext())
                    alertDialogBuilder.setTitle("Confirm")
                    alertDialogBuilder.setMessage("Are you sure this task has been completed?")
                    alertDialogBuilder.setPositiveButton("YES") { _, _, ->
                        selectedTask.id?.let { allTaskViewModel.doneTask(it) }
                    }
                    alertDialogBuilder.setNegativeButton("NO"){_,_,->}
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                },
                onUndoneClick = {selectedTask->
                    val alertDialogBuilder = AlertDialog.Builder(requireContext())
                    alertDialogBuilder.setTitle("Confirm")
                    alertDialogBuilder.setMessage("Are you sure you want to mark this task as incomplete?")
                    alertDialogBuilder.setPositiveButton("YES") { _, _, ->
                        selectedTask.id?.let { allTaskViewModel.undoneTask(it) }
                    }
                    alertDialogBuilder.setNegativeButton("NO"){_,_,->}
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                })
        }
        taskList.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}