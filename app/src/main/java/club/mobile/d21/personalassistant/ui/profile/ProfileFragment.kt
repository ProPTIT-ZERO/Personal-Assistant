package club.mobile.d21.personalassistant.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import club.mobile.d21.personalassistant.databinding.FragmentProfileBinding

class ProfileFragment :Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        initEvent()
    }
    private fun initEvent(){
        binding.toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
    }
}