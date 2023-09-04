package club.mobile.d21.personalassistant.ui.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import club.mobile.d21.personalassistant.databinding.FragmentTodayBinding

class TodayFragment : Fragment() {
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val todayViewModel = ViewModelProvider(this)[TodayViewModel::class.java]
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val textView: TextView = binding.textView
        todayViewModel.text.observe(viewLifecycleOwner){
            textView.text = it
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}