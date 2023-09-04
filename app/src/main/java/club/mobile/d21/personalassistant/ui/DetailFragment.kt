package club.mobile.d21.personalassistant.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import club.mobile.d21.personalassistant.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var text=""
    companion object{
        fun newInstance(text:String):DetailFragment{
            val args = Bundle()
            val fragment = DetailFragment()
            fragment.text = text
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listTask.text = text
    }
}