package club.mobile.d21.personalassistant.ui.all_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import club.mobile.d21.personalassistant.R
import club.mobile.d21.personalassistant.databinding.FragmentAllTaskBinding
import club.mobile.d21.personalassistant.ui.DetailFragment
import club.mobile.d21.personalassistant.ui.adapter.UrgencyAdapter
import com.google.android.material.tabs.TabLayoutMediator

class AllTaskFragment : Fragment() {
    private var _binding: FragmentAllTaskBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllTaskBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun initViewPager2() {
        val fragmentList = listOf(
            DetailFragment.newInstance(getString(R.string.critical)),
            DetailFragment.newInstance(getString(R.string.important)),
            DetailFragment.newInstance(getString(R.string.low_priority))
        )
        val tabTitle = listOf(
            getString(R.string.critical), getString(R.string.important),
            getString(R.string.low_priority)
        )
        val adapter =
            activity?.supportFragmentManager?.let { UrgencyAdapter(it, lifecycle, fragmentList) }
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) {tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager2()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}