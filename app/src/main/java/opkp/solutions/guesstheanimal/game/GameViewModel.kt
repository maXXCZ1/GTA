package opkp.solutions.guesstheanimal.game

import opkp.solutions.guesstheanimal.R
import opkp.solutions.guesstheanimal.dataclass.Animal

class GameViewModel {

    private val animalList: MutableList<Animal> = mutableListOf(
        Animal("buffalo", R.drawable.ic_buffalo, R.raw.buffalo),
        Animal("canarybird", R.drawable.ic_canary_bird, R.raw.canarybird),
        Animal("cat", R.drawable.ic_cat, R.raw.cat),
        Animal("cows", R.drawable.ic_cow, R.raw.cows),
        Animal("dog", R.drawable.ic_dog, R.raw.canarybird),
        Animal("donkey", R.drawable.ic_donkey, R.raw.donkey),
        Animal("duck", R.drawable.ic_duck, R.raw.duck),
        Animal("elephant", R.drawable.ic_elephant, R.raw.elephant),
        Animal("goat", R.drawable.ic_goat, R.raw.goat),
        Animal("goose", R.drawable.ic_goose, R.raw.goose),
        Animal("horse", R.drawable.ic_horse, R.raw.horse),
        Animal("owl", R.drawable.ic_owl, R.raw.owl),
        Animal("pig", R.drawable.ic_pig, R.raw.pig),
        Animal("rhinoceros", R.drawable.ic_rhinoceros, R.raw.rhinoceros),
        Animal("rooster", R.drawable.ic_seagull, R.raw.seagull),
        Animal("seagull", R.drawable.ic_sheep, R.raw.sheeps),
        Animal("sheeps", R.drawable.ic_tiger, R.raw.tiger),
        Animal("tiger", R.drawable.ic_turkey, R.raw.turkey),
        Animal("turkey", R.drawable.ic_wolf, R.raw.wolf),
        Animal("wolf", R.drawable.ic_canary_bird, R.raw.canarybird)
    )
}