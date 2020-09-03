package opkp.solutions.guesstheanimal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import opkp.solutions.guesstheanimal.databinding.FragmentStartupBinding


/**
 * A simple [Fragment] subclass.
 * Use the [StartupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartupFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  DataBindingUtil.inflate<FragmentStartupBinding>(inflater, R.layout.fragment_startup, container,false)

        val startanim = AnimationUtils.loadAnimation(context, R.anim.startup_anim)

        val logo = binding.animalsLogo

        logo.startAnimation(startanim)
//        animationFinished(startanim)
        if (startanim.hasEnded()) {
            findNavController().navigate(StartupFragmentDirections.actionStartupFragmentToGameFragment())
        }
        return binding.root
    }

//    private fun animationFinished(animation: Animation) {
//        Log.d("StartupFragment", "animationFinished started")
//
//        Log.d("StartupFragment", "animationFinished ended")
//    }

}