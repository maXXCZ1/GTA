package opkp.solutions.guesstheanimal.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import opkp.solutions.guesstheanimal.R
import opkp.solutions.guesstheanimal.dataclass.Animal

class GameViewModel : ViewModel() {

    private val listSize = 0..3
    var randomInt = listSize.random()
    var animalSound = 0
    var goodAnswer = 0
    var badAnswer = 0
    lateinit var animalSelection: MutableList<Animal>
    lateinit var animalList: MutableList<Animal>

    private val _picture1 = MutableLiveData<Int>()
    val picture1: LiveData<Int>
        get() = _picture1

    private val _picture2 = MutableLiveData<Int>()
    val picture2: LiveData<Int>
        get() = _picture2

    private val _picture3 = MutableLiveData<Int>()
    val picture3: LiveData<Int>
        get() = _picture3

    private val _picture4 = MutableLiveData<Int>()
    val picture4: LiveData<Int>
        get() = _picture4

    private val _buttonSound = MutableLiveData<Int>()
    val buttonSound: LiveData<Int>
        get() = _buttonSound

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    private val _rightAnimalClicked = MutableLiveData<Boolean>()
    val rightAnimalClicked: LiveData<Boolean>
        get() = _rightAnimalClicked

    init {
        initialize()
    }

    fun initialize() {
         animalList =  mutableListOf(
            Animal("alligator", R.drawable.ic_aligator, R.raw.aligator),
            Animal("bear", R.drawable.ic_bear, R.raw.bear),
            Animal("buffalo", R.drawable.ic_buffalo, R.raw.buffalo),
            Animal("canarybird", R.drawable.ic_canary_bird, R.raw.canarybird),
            Animal("camel", R.drawable.ic_camel, R.raw.camel),
            Animal("cat", R.drawable.ic_cat, R.raw.cat),
            Animal("cougar", R.drawable.ic_cougar, R.raw.cougar),
            Animal("cows", R.drawable.ic_cow, R.raw.cows),
            Animal("cricket", R.drawable.ic_cricket, R.raw.cricket),
            Animal("dog", R.drawable.ic_dog, R.raw.dog),
            Animal("dolphin", R.drawable.ic_dolphin, R.raw.dolphin),
            Animal("donkey", R.drawable.ic_donkey, R.raw.donkey),
            Animal("duck", R.drawable.ic_duck, R.raw.duck),
            Animal("elephant", R.drawable.ic_elephant, R.raw.elephant),
            Animal("frog", R.drawable.ic_frog, R.raw.frog),
            Animal("goat", R.drawable.ic_goat, R.raw.goat),
            Animal("goose", R.drawable.ic_goose, R.raw.goose),
            Animal("horse", R.drawable.ic_horse, R.raw.horse),
            Animal("owl", R.drawable.ic_owl, R.raw.owl),
            Animal("pig", R.drawable.ic_pig, R.raw.pig),
            Animal("raccoon", R.drawable.ic_raccoon, R.raw.raccoon),
            Animal("rhinoceros", R.drawable.ic_rhinoceros, R.raw.rhinoceros),
            Animal("rooster", R.drawable.ic_rooster, R.raw.rooster),
            Animal("seagull", R.drawable.ic_seagull, R.raw.seagull),
            Animal("sealion", R.drawable.ic_seal, R.raw.sealion),
            Animal("sheeps", R.drawable.ic_sheep, R.raw.sheeps),
            Animal("squirrel", R.drawable.ic_squirrel, R.raw.squirrel),
            Animal("tiger", R.drawable.ic_tiger, R.raw.tiger),
            Animal("turkey", R.drawable.ic_turkey, R.raw.turkey),
            Animal("whale", R.drawable.ic_whale, R.raw.whale),
            Animal("wolf", R.drawable.ic_wolf, R.raw.wolf),
            Animal("zebra", R.drawable.ic_zebra, R.raw.zebra)
        )
        _picture1.value = 0
        _picture1.value = 0
        _picture1.value = 0
        _picture1.value = 0
        _buttonSound.value = 0
        goodAnswer = 0
        badAnswer = 0


        resetList()
        pickAnimals()
    }

    private fun resetList() {
        animalList.shuffle()
        Log.d("GameViewModel", "Animal list size is ${animalList.size}")
    }

    private fun pickAnimals() {
        if (animalList.size >= 4) {
            _eventGameFinished.value = false

            Log.d("GameViewModel", "_eventGameFinished = ${_eventGameFinished.toString()} and _rightAnimalClicked = ${_rightAnimalClicked.toString()}")
            // pick 4 animals to list
            randomInt = listSize.random()
            val anim1 = animalList.removeAt(0)
            val anim2 = animalList.removeAt(0)
            val anim3 = animalList.removeAt(0)
            val anim4 = animalList.removeAt(0)

            Log.d("GameViewModel", "Animal list after selection is ${animalList.size}")
            animalSelection  = mutableListOf(
                anim1, anim2, anim3, anim4
            )
            Log.d("GameViewModel", "Animal selection list size is ${animalSelection.size}")

            // get random animal and assign it's sound to variable

            Log.d("GameViewModel", "RandomInt is $randomInt")
            animalSound = animalSelection[randomInt].sound
            Log.d("GameViewModel", "Button sound is ${animalSelection[randomInt].sound}")

            // assign values to observable variables
            _buttonSound.value = animalSound
            _picture1.value = animalSelection[0].image
            _picture2.value = animalSelection[1].image
            _picture3.value = animalSelection[2].image
            _picture4.value = animalSelection[3].image

        } else {
            _eventGameFinished.value = true
        }
    }

    fun onClickAnimal (position: Int) {
        //assign wrong/right answer picture to clicked imageView
        Log.d("GameViewModel", "onAnimalClick started: position is $position, randomInt is $randomInt")
        if(position != randomInt) {
            when (position) {
                0 -> _picture1.value = R.drawable.ic_red_cross3
                1 -> _picture2.value = R.drawable.ic_red_cross3
                2 -> _picture3.value = R.drawable.ic_red_cross3
                3 -> _picture4.value = R.drawable.ic_red_cross3
           }
           badAnswer++
        }else {
            when(position) {
                0 -> _picture1.value = R.drawable.ic_smiley
                1 -> _picture2.value = R.drawable.ic_smiley
                2 -> _picture3.value = R.drawable.ic_smiley
                3 -> _picture4.value = R.drawable.ic_smiley
            }
            goodAnswer++
            Log.d("GameViewModel", "onAnimalClick ended: goodAnswer is $goodAnswer, badAnswer is $badAnswer")
            _rightAnimalClicked.value = true
            GlobalScope.launch(Dispatchers.Main) {
                delay(500)
                _rightAnimalClicked.value = false
                pickAnimals()
            }
        }
    }

    fun resetFinishValue() {
        _eventGameFinished.value = false
    }

}