package club.mobile.d21.personalassistant.ui.all_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.data.Priority
import club.mobile.d21.personalassistant.databinding.FragmentAllTaskBinding
import club.mobile.d21.personalassistant.ui.adapter.ClassifiedAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class AllTaskFragment : Fragment() {
    private var _binding: FragmentAllTaskBinding? = null
    private val binding get() = _binding!!
    private val allTaskViewModel: AllTaskViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllTaskBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager2()
        handleEvents()
    }
    private fun initViewPager2() {
        lifecycleScope.launch {
            val criticalTasks = allTaskViewModel.getCriticalTask()
            val importantTasks = allTaskViewModel.getImportantTask()
            val lowPriorityTasks = allTaskViewModel.getLowPriorityTask()
            val fragmentList = listOf(
                ClassifiedTaskFragment.newInstance(Priority.CRITICAL),
                ClassifiedTaskFragment.newInstance(Priority.IMPORTANT),
                ClassifiedTaskFragment.newInstance(Priority.LOW_PRIORITY)
            )
            val tabTitle = listOf(
                getString(R.string.critical), getString(R.string.important),
                getString(R.string.low_priority)
            )
            val adapter =
                activity?.supportFragmentManager?.let {
                    ClassifiedAdapter(
                        it,
                        lifecycle,
                        fragmentList
                    )
                }
            binding.viewPager2.adapter = adapter
            TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
                tab.text = tabTitle[position]
            }.attach()
        }
    }
    private fun handleEvents(){
        binding.addButton.setOnClickListener{
            val addTaskFragment = AddTaskFragment.newInstance(object : CallBackTask{
                override fun add(
                    task: String,
                    priority: Priority,
                    deadlineDay: LocalDate,
                    deadLineTime: LocalTime,
                    detail: String
                ) {
                    allTaskViewModel.addTask(task, priority, deadlineDay, deadLineTime, detail)
                }
            })
            val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
            bottomNavigationView?.visibility = View.GONE
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().remove(this)
                .setReorderingAllowed(true)
                .replace(R.id.nav_host_fragment_container, addTaskFragment)
                .addToBackStack(null)
                .commit()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}