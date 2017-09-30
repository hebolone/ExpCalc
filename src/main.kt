import Exp.*

fun main(args : Array<String>) {
    /*val exp = CExp(1)
    println("Starting from level ${exp.Level} (exp point: ${exp.Exp}, exp to next level: ${exp.ExpToNextLevel})")
    print("Insert amount of exp: ")
    var userInput = readLine()!!
    while(userInput != "") {
        val (levelsGained, currentLevel) = exp.AddExp(userInput.toInt())
        println("Current exp: ${exp.Exp}, current level: $currentLevel, levels gained: $levelsGained, exp to next level: ${exp.ExpToNextLevel}")
        userInput = readLine()!!
    }*/

    val exp = CExpProgressive(1)
    //exp.SetFuncExp { it + 4 }
    exp.onLevelGained += { OnMessageLevelGained(it) }
    exp.onExpGained += { OnMessageExpGained(it) }
    exp.onExpProgressionEnd += { OnMessageExpProgressionEnd(it) }
    println("Starting from level ${exp.Level} (exp point: ${exp.Exp}, exp to next level: ${exp.ExpToNextLevel})")
    print("Insert amount of exp: ")
    var userInput = readLine()!!
    while(userInput != "") {
        exp.AddExpProgression(userInput.toInt())
        println("Current exp is: ${exp.Exp}, exp to next level: ${exp.ExpToNextLevel}")
        userInput = readLine()!!
    }
}

fun OnMessageLevelGained(iMessage : Int) {
    println()
    println("Level gained, current level is: $iMessage")
}
fun OnMessageExpGained(iMessage : Int) {
    print("=")
}
fun OnMessageExpProgressionEnd(iMessage : Int) {
    println()
}