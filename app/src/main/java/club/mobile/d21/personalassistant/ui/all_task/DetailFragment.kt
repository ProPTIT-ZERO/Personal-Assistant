package club.mobile.d21.personalassistant.ui.all_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import club.mobile.d21.personalassistant.data.Task
import club.mobile.d21.personalassistant.databinding.FragmentDetailBinding
import club.mobile.d21.personalassistant.ui.adapter.TaskAdapter

class DetailFragment : Fragment() {
    private lateinit var list:List<Task>
    companion object{
        fun newInstance(list: List<Task>): DetailFragment {
            val args = Bundle()
            val fragment = DetailFragment()
            fragment.list = list
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var binding: FragmentDetailBinding
    private val allTaskViewModel: AllTaskViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val taskList: RecyclerView = binding.listTask

        allTaskViewModel.task.observe(viewLifecycleOwner){ list ->
            taskList.adapter = TaskAdapter(list)
        }
        allTaskViewModel.task.postValue(list)
        taskList.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
}