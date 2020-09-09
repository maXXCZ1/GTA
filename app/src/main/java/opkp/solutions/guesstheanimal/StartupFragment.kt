package opkp.solutions.guesstheanimal



import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import opkp.solutions.guesstheanimal.databinding.FragmentStartupBinding


/**
 * A simple [Fragment] subclass.
 * Use the [StartupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class StartupFragment : Fragment(){

    var shouldAnimate = true

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

        val player = MediaPlayer.create(context, R.raw.dog)
        val anim = AnimationUtils.loadAnimation(context, R.anim.startup_anim)
        val logo = binding.animalsLogo

        if (shouldAnimate) {
            binding.playButton.visibility = GONE
            player.start()
            logo.startAnimation(anim)
        }

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                shouldAnimate = false
                findNavController().navigate(StartupFragmentDirections.actionStartupFragmentToGameFragment())
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })

        binding.playButton.setOnClickListener{
            (findNavController().navigate(StartupFragmentDirections.actionStartupFragmentToGameFragment()))
        }
        return binding.root
    }

}