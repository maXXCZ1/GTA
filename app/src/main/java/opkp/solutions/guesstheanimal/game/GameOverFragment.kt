package opkp.solutions.guesstheanimal.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import opkp.solutions.guesstheanimal.R
import opkp.solutions.guesstheanimal.StartupFragmentDirections
import opkp.solutions.guesstheanimal.databinding.FragmentGameOverBinding



/**
 * A simple [Fragment] subclass.
 * Use the [GameOverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameOverFragment : Fragment() {

    private val args: GameOverFragmentArgs by navArgs()
    private var goodAnswer: Int = 0
    private var badAnswer: Int = 0
    private var backButtonPressed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameOverBinding>(inflater, R.layout.fragment_game_over, container, false)

        Log.d("GameOverFragment", "GameOverFragment started, goodAnswer is $goodAnswer")
        goodAnswer = args.goodAnswer
        badAnswer = args.badAnswer

        Log.d("GameOverFragment", "GameOverFragment continues, goodAnswer is $goodAnswer")
        val finalScore = goodAnswer+badAnswer*-1

        binding.goodscoreValue.text = goodAnswer.toString()
        binding.badscoreValue.text = badAnswer.toString()

        binding.finalscoreValue.text = finalScore.toString()

        if(finalScore>4) {
            binding.finalscoreValue.setBackgroundColor(requireContext().getColor(R.color.goodScore_color))

        }else if (finalScore in 0..4) {
            binding.finalscoreValue.setBackgroundColor(requireContext().getColor(R.color.color_orange))
        } else {
            binding.finalscoreValue.setBackgroundColor(requireContext().getColor(R.color.badScore_color))
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            backButtonPressed = true
            var action = GameOverFragmentDirections.actionGameOverFragmentToStartupFragment(backButtonPressed)
            findNavController().navigate(action)
        }

        binding.playAgainButton.setOnClickListener{
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onPause() {
        Log.d("GameOverFragment", "goodAnswer is $goodAnswer, badAnswer is $badAnswer")
        super.onPause()
    }


}