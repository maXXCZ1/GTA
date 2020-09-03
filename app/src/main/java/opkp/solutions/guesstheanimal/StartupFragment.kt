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
        val binding = DataBindingUtil.inflate<FragmentStartupBinding>(
            inflater,
            R.layout.fragment_startup,
            container,
            false
        )
        val anim = AnimationUtils.loadAnimation(context, R.anim.startup_anim)
        val logo = binding.animalsLogo

        logo.startAnimation(anim)

        anim.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
//                Log.d("StartupFragment", "onAnimationStart started")
            }

            override fun onAnimationEnd(p0: Animation?) {
                Log.d("StartupFragment", "onAnimationEnd started")
                findNavController().navigate(StartupFragmentDirections.actionStartupFragmentToGameFragment())
                Log.d("StartupFragment", "onAnimationEnd ended")
            }

            override fun onAnimationRepeat(p0: Animation?) {
//                Log.d("StartupFragment", "onAnimationRepeat started")
            }
        })
//
//        logo.setOnClickListener(){
//            findNavController().navigate(StartupFragmentDirections.actionStartupFragmentToGameFragment())
//        }

        return binding.root
    }
}
