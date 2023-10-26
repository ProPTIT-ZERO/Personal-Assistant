package club.mobile.d21.personalassistant

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import club.mobile.d21.personalassistant.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_container)
        AppBarConfiguration(
            setOf(
                R.id.navigation_today,R.id.navigation_task,
                R.id.navigation_note,R.id.navigation_alarm)
            )
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{
                _, destination, _ ->
            when(destination.id){
                R.id.navigation_today ->{
                    binding.toolbar.title = getString(R.string.today)
                }
                R.id.navigation_task ->{
                    binding.toolbar.title = getString(R.string.all_task)
                }
                R.id.navigation_note->{
                    binding.toolbar.title = getString(R.string.all_note)
                }
                R.id.navigation_alarm->{
                    binding.toolbar.title = getString(R.string.alarm)
                }
            }
            if (destination.id != R.id.navigation_profile) {
                binding.navView.visibility = View.VISIBLE
            } else
                binding.navView.visibility = View.GONE
        }
        setupActions()
    }
    private fun setupActions() {
        binding.iconProfile.setOnClickListener {
            findNavController(R.id.nav_host_fragment_container).navigate(R.id.navigation_profile)
        }
    }
}