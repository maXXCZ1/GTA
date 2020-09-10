package opkp.solutions.guesstheanimal.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import opkp.solutions.guesstheanimal.R
import opkp.solutions.guesstheanimal.dataclass.Animal

class GameViewModel : ViewModel() {

    val listSize = 0..3
    var randomInt: Int = 0
    var animalSound = 0
    lateinit var animalSelection: MutableList<Animal>
    private var goodAnswer = 0
    private var badAnswer = 0
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


    init {
        initialize()
    }

    fun initialize() {
         animalList =  mutableListOf(
            Animal("buffalo", R.drawable.ic_buffalo, R.raw.buffalo),
            Animal("canarybird", R.drawable.ic_canary_bird, R.raw.canarybird),
            Animal("cat", R.drawable.ic_cat, R.raw.cat),
            Animal("cows", R.drawable.ic_cow, R.raw.cows),
            Animal("dog", R.drawable.ic_dog, R.raw.dog),
            Animal("donkey", R.drawable.ic_donkey, R.raw.donkey),
            Animal("duck", R.drawable.ic_duck, R.raw.duck),
            Animal("elephant", R.drawable.ic_elephant, R.raw.elephant),
            Animal("goat", R.drawable.ic_goat, R.raw.goat),
            Animal("goose", R.drawable.ic_goose, R.raw.goose),
            Animal("horse", R.drawable.ic_horse, R.raw.horse),
            Animal("owl", R.drawable.ic_owl, R.raw.owl),
            Animal("pig", R.drawable.ic_pig, R.raw.pig),
            Animal("rhinoceros", R.drawable.ic_rhinoceros, R.raw.rhinoceros),
            Animal("rooster", R.drawable.ic_rooster, R.raw.rooster),
            Animal("seagull", R.drawable.ic_seagull, R.raw.seagull),
            Animal("sheeps", R.drawable.ic_sheep, R.raw.sheeps),
            Animal("tiger", R.drawable.ic_tiger, R.raw.tiger),
            Animal("turkey", R.drawable.ic_turkey, R.raw.turkey),
            Animal("wolf", R.drawable.ic_wolf, R.raw.wolf)
        )
        _picture1.value = 0
        _picture1.value = 0
        _picture1.value = 0
        _picture1.value = 0
        _buttonSound.value = 0

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
//            Log.d("GameViewModel", "Animal list size is ${animalList.size}")
            // pick 4 animals to list
            val anim1 = animalList.removeAt(0)
            val anim2 = animalList.removeAt(0)
            val anim3 = animalList.removeAt(0)
            val anim4 = animalList.removeAt(0)

            Log.d("GameViewModel", "Animal list after selection is ${animalList.size}")
            animalSelection  = mutableListOf(
                anim1, anim2, anim3, anim4
            )
            Log.d("GameViewModel", "Animal selection list size is ${animalSelection.size}")
            randomInt = listSize.random()
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
            next()
        }
    }

    fun next() {
        Log.d("GameViewModel", "Number of good answers: $goodAnswer" + " Number of bad answers: $badAnswer")
        pickAnimals()
    }
    fun resetFinishValue() {
        _eventGameFinished.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameViewModel", "onCleared called")
    }
}