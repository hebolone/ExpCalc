package Exp

import java.lang.Math.pow

open class CExp(iStartingLevel : Int) {
    //  *** PROPERTIES ***
    val Exp : Int
        get() = m_Exp
    val Level : Int
        get() = m_CurrentLevel
    val ExpToNextLevel : Int
        get() = m_FuncExp(m_CurrentLevel + 1) - m_Exp
    val MaxLevel : Int
        get() = MAX_LEVEL
    //  *** MEMBERS ***
    protected var m_FuncExp : (Int) -> Int = { m_FuncPow(it, 2) + m_FuncPow(3 * (it - 1), 2) - 1 }
    private val m_FuncPow : (Int, Int) -> Int = { base, exp -> pow(base.toDouble(), exp.toDouble()).toInt() }
    protected var m_CurrentLevel : Int = iStartingLevel
    protected var m_Exp : Int = m_FuncExp(m_CurrentLevel)
    private val MAX_LEVEL = 9999
    protected val MAX_EXP = m_FuncExp(MAX_LEVEL)
    //  *** METHODS ***
    init {
        if(iStartingLevel < 1 || iStartingLevel > MAX_LEVEL)
            throw IllegalArgumentException("Bad starting level, it has to be between 1 and $MAX_LEVEL")
        m_CurrentLevel = iStartingLevel
        m_Exp = m_FuncExp(m_CurrentLevel)
    }
    fun AddExp(iAmount : Int) : Pair<Int, Int> {
        //  Return levels gained and current level
        //  Add exp
        m_Exp += iAmount
        if(m_Exp >= MAX_EXP)
            m_Exp = MAX_EXP
        //  Check if level changed
        var levelsCounter : Int = m_CurrentLevel + 1
        var nextLevelExpNeeded = m_FuncExp(levelsCounter)
        while(nextLevelExpNeeded <= m_Exp) {
            nextLevelExpNeeded = m_FuncExp(++ levelsCounter)
        }
        val levelsGained = -- levelsCounter - m_CurrentLevel
        m_CurrentLevel += levelsGained
        return Pair(levelsGained, m_CurrentLevel)
    }
    fun SetFuncExp(iFunc : (Int) -> Int) {
        m_FuncExp = iFunc
    }
}
//-------------------------------------------------------------------------------------------------
class CExpProgressive(iStartingLevel : Int) : CExp(iStartingLevel) {
    //  *** EVENTS ***
    private val eventhandler_onLevelGained = EventHandler<Int>()
    val onLevelGained = Event(eventhandler_onLevelGained)
    private val eventhandler_onExpGained = EventHandler<Int>()
    val onExpGained = Event(eventhandler_onExpGained)
    private val eventhandler_OnExpProgressionEnd = EventHandler<Int>()
    val onExpProgressionEnd = Event(eventhandler_OnExpProgressionEnd)
    //  *** METHODS ***
    fun AddExpProgression(iAmount : Int) {
        var counter = 0
        while(counter < iAmount) {
            counter ++
            m_Exp ++
            LaunchOnExpGained(m_Exp)
            if(m_Exp >= MAX_EXP) {
                m_Exp = MAX_EXP
                return
            }
            //  Check if level changed
            val nextLevelExpNeeded = m_FuncExp(m_CurrentLevel + 1)
            if(m_Exp == nextLevelExpNeeded) {
                //  Increment level
                m_CurrentLevel ++
                //  Launch a message
                LaunchOnLevelGained(m_CurrentLevel)
            }
        }
        LaunchOnExpProgressionEnd(m_Exp)
    }
    private fun LaunchOnLevelGained(iCurrentLevel : Int) {
        eventhandler_onLevelGained(iCurrentLevel)
    }
    private fun LaunchOnExpGained(iCurrentExp : Int) {
        eventhandler_onExpGained(iCurrentExp)
    }
    private fun LaunchOnExpProgressionEnd(iFinalExp : Int) {
        eventhandler_OnExpProgressionEnd(iFinalExp)
    }
}