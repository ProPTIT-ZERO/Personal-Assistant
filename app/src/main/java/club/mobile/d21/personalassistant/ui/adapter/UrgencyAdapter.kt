package club.mobile.d21.personalassistant.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class UrgencyAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val fragments : List<Fragment>
) : FragmentStateAdapter(fragmentManager, lifecycle){
    override fun createFragment(position: Int): Fragment = fragments[position]
    override fun getItemCount(): Int = fragments.size
}