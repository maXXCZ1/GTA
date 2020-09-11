package opkp.solutions.guesstheanimal.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_game_over.*
import opkp.solutions.guesstheanimal.R
import opkp.solutions.guesstheanimal.databinding.FragmentGameOverBinding



/**
 * A simple [Fragment] subclass.
 * Use the [GameOverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameOverFragment : Fragment() {

    val args: GameOverFragmentArgs by navArgs()
    private var goodAnswer: Int = 0
    private var badAnswer: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameOverBinding>(inflater, R.layout.fragment_game_over, container, false)

        //TODO recieve arguments from bundle
//        val goodAnswer = GameFragmentArgs.fromBundle(arguments).returnBack
        goodAnswer = args.goodAnswer
        badAnswer = args.badAnswer

        val finalScore = goodAnswer+badAnswer*-1

        binding.goodscoreValue.text = goodAnswer.toString()
        binding.badscoreValue.text = badAnswer.toString()

        binding.finalscoreValue.text = finalScore.toString()

        if(finalScore>0) {
            binding.finalscoreValue.setBackgroundColor(resources.getColor(R.color.goodScore_color))

        }else if (finalScore == 0) {
            binding.finalscoreValue.setBackgroundColor(resources.getColor(R.color.color_orange))
        } else {
            binding.finalscoreValue.setBackgroundColor(resources.getColor(R.color.badScore_color))
        }

        binding.playAgainButton.setOnClickListener{
            findNavController().popBackStack()}

        return binding.root
    }

    override fun onPause() {
        Log.d("GameOverFragment", "goodAnswer is $goodAnswer, badAnswer is $badAnswer")
        super.onPause()
    }
}