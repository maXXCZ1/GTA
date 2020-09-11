package opkp.solutions.guesstheanimal.game

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Gravity
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
    private var isSoundPlayerInitialized = false
    var soundPlaying: Boolean = false

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
        Log.d(
            "GameFragment",
            "Value of sound: $soundID, isSoundPlayerInitialized is $isSoundPlayerInitialized"
        )
        if(!isSoundPlayerInitialized){
            initializeSoundPlayer()
        }
        playSound()
        Log.d("GameFragment", "Value of isSoundPlayerInitialized: $isSoundPlayerInitialized")
//        isSoundPlayerInitialized = false
    }

    private fun initializeSoundPlayer() {
        Log.d(
            "GameFragment",
            "initializeSoundPlayer: started, isSoundPlayerInitialized is $isSoundPlayerInitialized"
        )
        soundPlayer = MediaPlayer.create(context, soundID)
        isSoundPlayerInitialized = true
    }

    private fun playSound() {
        soundPlayer.start()
    }

    private fun onClickAnimal(position: Int) {
        if (!isSoundPlayerInitialized && position != viewModel.randomInt) {
            val toast = Toast.makeText(activity, R.string.sound_button_toast, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM,0,10)
            toast.show()
//            initializeSoundPlayer()
        }
        if(position == viewModel.randomInt) {
            viewModel.onClickAnimal(position)
            if(isSoundPlayerInitialized) {
                soundPlayer.stop()
                soundPlayer.release()
            }
            isSoundPlayerInitialized = false
        } else {
            viewModel.onClickAnimal(position)
//            isSoundPlayerInitialized = false
        }
    }

    private fun gameFinished() {
        Log.d(
            "GameFragment",
            "Number of goodAnswers is ${viewModel.goodAnswer} , number of bad answers passed is ${viewModel.badAnswer}"
        )
        val action = GameFragmentDirections.actionGameFragmentToGameOverFragment(
            viewModel.goodAnswer,
            viewModel.badAnswer
        )
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
            Log.d("GameFragment", "soundPlayer.release() started")
            soundPlayer.release()
            Log.d("GameFragment", "soundPlayer.release() ended")
        }
    }
}