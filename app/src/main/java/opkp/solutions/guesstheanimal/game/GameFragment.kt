package opkp.solutions.guesstheanimal.game

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_game.*
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

    private lateinit var viewModel: GameViewModel
    private lateinit var soundPlayer: MediaPlayer
    private var soundID = 0
    private var isSoundPlayerInitialized = false
    private var isRotated = false
    private var goodAnswer = 0
    private var badAnswer = 0

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
        Log.d("GameFragment", "isRotated: $isRotated, badAnswer: $badAnswer")

        if (savedInstanceState != null) {
            isRotated = savedInstanceState.getBoolean("isRotated")
            badAnswer = savedInstanceState.getInt("badAnswer")
            goodAnswer = savedInstanceState.getInt("goodAnswer")
        }
        Log.d("GameFragment", "isRotated: $isRotated, badAnswer: $badAnswer")

        if (!isRotated) {
            viewModel.initialize()
        }
        Log.d("GameFragment", "isRotated: $isRotated, badAnswer: $badAnswer")

        viewModel.picture1.observe(viewLifecycleOwner, binding.picture1::setImageResource)
        viewModel.picture2.observe(viewLifecycleOwner, binding.picture2::setImageResource)
        viewModel.picture3.observe(viewLifecycleOwner, binding.picture3::setImageResource)
        viewModel.picture4.observe(viewLifecycleOwner, binding.picture4::setImageResource)

        viewModel.buttonSound.observe(
            viewLifecycleOwner,
            { newSoundID -> soundID = newSoundID }
        )

        viewModel.rightAnimalClicked.observe(viewLifecycleOwner,
            { isClicked ->
                if (isClicked) {
                    binding.soundButton.visibility = View.INVISIBLE
                    binding.picture1.isEnabled = false
                    binding.picture2.isEnabled = false
                    binding.picture3.isEnabled = false
                    binding.picture4.isEnabled = false
                } else {
                    binding.soundButton.visibility = VISIBLE
                    binding.picture1.isEnabled = true
                    binding.picture2.isEnabled = true
                    binding.picture3.isEnabled = true
                    binding.picture4.isEnabled = true
                }
            })

        binding.picture1.setOnClickListener { onClickAnimal(0) }
        binding.picture2.setOnClickListener { onClickAnimal(1) }
        binding.picture3.setOnClickListener { onClickAnimal(2) }
        binding.picture4.setOnClickListener { onClickAnimal(3) }

        viewModel.eventGameFinished.observe(viewLifecycleOwner,
            { hasFinished -> if (hasFinished) gameFinished() })

        binding.soundButton.setOnClickListener { onClickSound() }

        badAnswer = viewModel.badAnswer
        goodAnswer = viewModel.goodAnswer
        Log.d("GameFragment", "isRotated: $isRotated, badAnswer: $badAnswer, goodAnswer: $goodAnswer")

        return binding.root
    }

    private fun onClickSound() {
        Log.d(
            "GameFragment",
            "Onclicksound started: value of sound: $soundID, isSoundPlayerInitialized is $isSoundPlayerInitialized"
        )
        if(!isSoundPlayerInitialized){
            initializeSoundPlayer()
        }
        playSound()
        Log.d("GameFragment", "onClickSound ended: value of isSoundPlayerInitialized: $isSoundPlayerInitialized")
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("GameFragment", "onSaveInstanceState started, isRotated is $isRotated")
        outState.putBoolean("isRotated", true)
        outState.putInt("badAnswer", badAnswer)
        outState.putInt("goodAnswer", goodAnswer)
        Log.d("GameFragment", "onSaveInstanceState ended, isRotated is $isRotated")
    }



    override fun onResume() {
        super.onResume()
        Log.d("GameFragment", "onResume started")
//        viewModel.goodAnswer = 0
//        viewModel.badAnswer = 0
        initializeSoundPlayer()
    }


    override fun onPause() {
        Log.d("GameFragment", "onPause started")
        super.onPause()
        isRotated = false
        if(isSoundPlayerInitialized) {
            soundPlayer.stop()
        }
    }
}