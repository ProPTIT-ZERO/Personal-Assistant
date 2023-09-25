package club.mobile.d21.personalassistant.ui.all_task

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import club.mobile.d21.personalassistant.data.Priority
import club.mobile.d21.personalassistant.databinding.FragmentDetailBinding
import club.mobile.d21.personalassistant.ui.adapter.TaskAdapter

class ClassifiedTaskFragment : Fragment() {
    private lateinit var priority: Priority
    companion object{
        fun newInstance(priority: Priority): ClassifiedTaskFragment {
            val args = Bundle()
            val fragment = ClassifiedTaskFragment()
            fragment.priority = priority
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var binding: FragmentDetailBinding
    private val allTaskViewModel: AllTaskViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val taskListBinding: RecyclerView = binding.listTask

        allTaskViewModel.task.observe(viewLifecycleOwner) { list ->
            val taskList = list.filter {it.priority == priority }
            taskListBinding.adapter = TaskAdapter(taskList,
                onDeleteClick = {selectedTask->
                    val alertDialogBuilder = AlertDialog.Builder(requireContext())
                    alertDialogBuilder.setTitle("Confirm")
                    alertDialogBuilder.setMessage("Are you sure you want to delete this task?")
                    alertDialogBuilder.setPositiveButton("YES") { _, _ ->
                        allTaskViewModel.deleteTask(selectedTask)
                    }
                    alertDialogBuilder.setNegativeButton("NO") { _, _ ->}
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
                    alertDialogBuilder.setPositiveButton("YES") { _, _ ->
                        selectedTask.id?.let { allTaskViewModel.doneTask(it) }
                    }
                    alertDialogBuilder.setNegativeButton("NO") { _, _ ->}
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                })
        }

        taskListBinding.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
}