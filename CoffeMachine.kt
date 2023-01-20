package machine

fun main() {
    val coffeMachine = CoffeMachine()
    coffeMachine.print()
    do {
        val ans = readln()
        coffeMachine.input(ans)
    } while (ans != "exit")
}

class CoffeMachine(
    var water:Int = 400,
    var milk:Int = 540,
    var beans:Int = 120,
    var cups:Int = 9,
    var money:Int = 550,
    var state: State = State.NULL
) {
    fun input(input: String) {
        when (state) {
            State.REMAINING -> remaining()
            State.TAKE -> take()
            State.FILL -> fill(input)
            State.BUY -> buy(input)
            State.NULL -> action(input)
        }
    }
    fun action(input: String) {
        state = when (input) {
            "buy" -> State.BUY
            "fill" -> State.FILL
            "take" -> State.TAKE
            "remaining" -> State.REMAINING
            else -> State.EXIT
        }
        input(input)
    }
    fun makeCoffe(coffe: Coffe) {
        println(when {
            water < coffe.water -> "Sorry, not enough water!"
            milk < coffe.milk -> "Sorry, not enough milk!"
            beans < coffe.beans -> "Sorry, not enough beans!"
            cups < 1 -> "Sorry, not enough cups!"
            else -> {
                cups--
                water -= coffe.water
                milk -= coffe.milk
                beans -= coffe.beans
                money += coffe.price
                "I have enough resources, making you a coffee!"
            }
        })
    }
    fun chooseCoffe(input: Int) {
        when (input) {
            1 -> makeCoffe(Coffe.ESPRESSO)
            2 -> makeCoffe(Coffe.LATTE)
            3 -> makeCoffe(Coffe.CAPPUCCINO)
        }
    }
    fun buy(input: String) {
        when (state.num) {
            0 -> {
                println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
                state.num++;
            }
            1 -> {
                if (input != "back") chooseCoffe(input.toInt())
                state.num = 0
                stateNull()
            }
        }
    }
    fun fill(input: String) {
        when (state.num) {
            0 -> {
                println("Write how many ml of water you want to add:")
                state.num++;
            }
            1 -> {
                water += input.toInt()
                println("Write how many ml of milk you want to add:")
                state.num++;
            }
            2 -> {
                milk += input.toInt()
                println("Write how many grams of coffee beans you want to add:")
                state.num++;
            }
            3 -> {
                beans+= input.toInt()
                println("Write how many disposable cups you want to add:")
                state.num++;
            }
            4 -> {
                cups += input.toInt()
                state.num = 0;
                stateNull()
            }
        }
    }
    fun stateNull() {
        state = State.NULL
        print()
    }
    fun take() {
        println("I gave you $$money")
        money = 0
        stateNull()
    }
    fun remaining() {
        println("""
        
        The coffee machine has:
        $water ml of water
        $milk ml of milk
        $beans g of coffee beans
        $cups disposable cups
        $$money of money
        """.trimIndent())
        stateNull()
    }
    fun print() {
        println("\nWrite action (buy, fill, take, remaining, exit): ")
    }
}

enum class State(var num: Int = 0) {
    BUY, FILL, TAKE, REMAINING, NULL, EXIT
}

enum class Coffe(val water: Int, val milk: Int, val beans: Int, val price: Int) {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6)
}
