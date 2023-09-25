package club.mobile.d21.personalassistant.ui.all_task

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
                    allTaskViewModel.deleteTask(selectedTask)
                },
                onEditClick = {selectedTask->
                    allTaskViewModel.editTask(selectedTask)
                },
                onDoneClick = {selectedTask->
                    selectedTask.id?.let { allTaskViewModel.doneTask(it) }
                })
        }

        taskListBinding.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
}