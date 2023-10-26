package club.mobile.d21.personalassistant.ui.alarm

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.data.Alarm
import club.mobile.d21.personalassistant.databinding.FragmentAlarmBinding
import club.mobile.d21.personalassistant.ui.adapter.AlarmAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class AlarmFragment : Fragment() {
    private var _binding: FragmentAlarmBinding? = null
    private val binding get() = _binding!!
    private val alarmViewModel: AlarmViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmBinding.inflate(inflater,container,false )
        val alarmList: RecyclerView = binding.alarmList
        alarmViewModel.alarm.observe(viewLifecycleOwner){list->
            alarmList.adapter = AlarmAdapter(list,
                onDeleteClick = { selectedAlarm ->
                    val alertDialogBuilder = AlertDialog.Builder(requireContext())
                    alertDialogBuilder.setTitle("Confirm")
                    alertDialogBuilder.setMessage("Are you sure you want to delete this alarm?")
                    alertDialogBuilder.setPositiveButton("YES") { _, _ ->
                        alarmViewModel.deleteAlarm(selectedAlarm)
                    }
                    alertDialogBuilder.setNegativeButton("NO") { _, _ -> }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                },
                onTurnOffClick = {selectedAlarm->
                    alarmViewModel.turnOffAlarm(selectedAlarm)
                },
                onTurnOnClick = {selectedAlarm->
                    alarmViewModel.turnOnAlarm(selectedAlarm)
                })
        }
        alarmList.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEvent()
    }
    private fun handleEvent(){
        binding.addButton.setOnClickListener {
            val addAlarmFragment =  AddAlarmFragment.newInstance(object : CallBackAddAlarm{
                override fun add(alarm: Alarm) {
                    alarmViewModel.addAlarm(alarm)
                }
            })
            val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
            bottomNavigationView?.visibility = View.GONE
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.nav_host_fragment_container, addAlarmFragment)
                .addToBackStack(null)
                .commit()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}