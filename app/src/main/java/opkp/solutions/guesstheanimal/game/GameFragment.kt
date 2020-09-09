package opkp.solutions.guesstheanimal.game

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import opkp.solutions.guesstheanimal.R
import opkp.solutions.guesstheanimal.databinding.FragmentGameBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [Game.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var soundPlayer: MediaPlayer
    private var soundID: kotlin.Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)


//        TODO maybe implament later
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        viewModel.picture1.observe(viewLifecycleOwner, binding.picture1::setImageResource)
        viewModel.picture2.observe(viewLifecycleOwner, binding.picture2::setImageResource)
        viewModel.picture3.observe(viewLifecycleOwner, binding.picture3::setImageResource)
        viewModel.picture4.observe(viewLifecycleOwner, binding.picture4::setImageResource)

        viewModel.buttonSound.observe(
            viewLifecycleOwner,
            { newSoundID -> soundID = newSoundID }
        )

        viewModel.eventGameFinished.observe(viewLifecycleOwner,
            { hasFinished -> if (hasFinished) gameFinished() })

        binding.soundButton.setOnClickListener { onClickSound() }

        binding.picture1.setOnClickListener { onClickAnimal(0) }
        binding.picture2.setOnClickListener { onClickAnimal(1) }
        binding.picture3.setOnClickListener { onClickAnimal(2) }
        binding.picture4.setOnClickListener { onClickAnimal(3) }

        return binding.root
    }

    private fun onClickSound() {
        soundPlayer = MediaPlayer.create(context, soundID)
        Log.d("GameFragment", "Value of sound: $soundID")
        soundPlayer.start()
    }

    private fun onClickAnimal(position: Int) {
        viewModel.onClickAnimal(position)
    }

    private fun gameFinished() {
        Toast.makeText(activity, "Game Finished", Toast.LENGTH_SHORT).show()
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())

    }

    override fun onResume() {
        super.onResume()
        viewModel.initialize()
    }

    override fun onPause() {
        Log.d("GameFragment", "onPause started")
        super.onPause()
        soundPlayer.stop()
    }
}