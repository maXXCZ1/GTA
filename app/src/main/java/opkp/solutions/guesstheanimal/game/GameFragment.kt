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
import java.util.*


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
    private var isSoundPlayerInitialized = false

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
//        soundPlayer = MediaPlayer()
        viewModel.eventGameFinished.observe(viewLifecycleOwner,
            { hasFinished -> if (hasFinished) gameFinished() })

        binding.soundButton.setOnClickListener { onClickSound() }

        Log.d("GameFragment", "issoundplayerInitialized is $isSoundPlayerInitialized")

        binding.picture1.setOnClickListener { onClickAnimal(0) }
        binding.picture2.setOnClickListener { onClickAnimal(1) }
        binding.picture3.setOnClickListener { onClickAnimal(2) }
        binding.picture4.setOnClickListener { onClickAnimal(3) }

        return binding.root
    }

    private fun onClickSound() {
        Log.d("GameFragment", "Value of sound: $soundID")
        soundPlayer = MediaPlayer.create(context, soundID)
        soundPlayer.start()
        isSoundPlayerInitialized = true
    }


    private fun onClickAnimal(position: Int) {
        if(position == viewModel.randomInt) {
            Log.d("GameFragment", "Position = $position +  viewModel.randomInt = ${viewModel.randomInt}")
            soundPlayer.stop()
        viewModel.onClickAnimal(position)
        } else {
            viewModel.onClickAnimal(position)
        }
    }

    private fun gameFinished() {
        Log.d("GameFragment", "Number of goodAnswers is ${viewModel.goodAnswer} , number of bad answers passed is ${viewModel.badAnswer}")
        val action = GameFragmentDirections.actionGameFragmentToGameOverFragment(viewModel.goodAnswer, viewModel.badAnswer)
        Toast.makeText(activity, "Game Finished", Toast.LENGTH_SHORT).show()
        findNavController().navigate(action)
        viewModel.resetFinishValue()
    }


    override fun onResume() {
        super.onResume()
        viewModel.goodAnswer = 0
        viewModel.badAnswer = 0
        viewModel.initialize()
    }


    override fun onPause() {
        Log.d("GameFragment", "onPause started")
        super.onPause()
        if(isSoundPlayerInitialized) {
            soundPlayer.stop()
        }
    }
}