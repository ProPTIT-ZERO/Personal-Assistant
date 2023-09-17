package club.mobile.d21.personalassistant.ui.adapter

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import club.mobile.d21.personalassistant.ui.all_task.DetailFragment

class UrgencyAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val fragments: List<DetailFragment>
) : FragmentStateAdapter(fragmentManager, lifecycle){
    override fun createFragment(position: Int): DetailFragment = fragments[position]
    override fun getItemCount(): Int = fragments.size
}