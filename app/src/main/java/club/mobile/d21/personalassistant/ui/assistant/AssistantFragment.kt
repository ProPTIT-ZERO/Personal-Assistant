package club.mobile.d21.personalassistant.ui.assistant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import club.mobile.d21.personalassistant.databinding.FragmentAssistantBinding

class AssistantFragment : Fragment() {
    private var _binding: FragmentAssistantBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val assistantViewModel= ViewModelProvider(this)[AssistantViewModel::class.java]
        _binding = FragmentAssistantBinding.inflate(inflater,container,false )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}