package com.polich.kneecap.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.polich.kneecap.*
import com.polich.kneecap.AllAnswerPositiveMap.answerPositiveMap
import com.polich.kneecap.data.BedBugs
import com.polich.kneecap.data.BedBugs.bedBugsList
import com.polich.kneecap.data.Event
import com.polich.kneecap.data.LevelDif.easyLevelEvent
import com.polich.kneecap.data.LevelDif.hardLevelEvent
import com.polich.kneecap.data.LevelDif.levelSection
import com.polich.kneecap.data.LevelDif.mediumLevelEvent
import com.polich.kneecap.data.MethodsStruggle
import com.polich.kneecap.data.TemporaryObject.playerScore
import kotlin.random.Random
import kotlin.random.nextInt

class EventFragment : Fragment() {
    var current_level= mutableListOf<Event>()
    var current_level_left = current_level
    var current_good_answer = mutableSetOf<MethodsStruggle>()
    var current_bad_answer = mutableSetOf<MethodsStruggle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val level = requireArguments().getString("level")
        current_level = levelToCurrentLevel(level)
        current_level_left = current_level

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        Log.e("data",current_level.toString())

        val titleEvent : TextView = view.findViewById(R.id.titleEvent)
        val descriptionEvent : TextView = view.findViewById(R.id.descriptionEvent)
        val btnDoneAnswer : MaterialButton = view.findViewById(R.id.done)
        val radioGroup : RadioGroup = view.findViewById(R.id.groupSolutions)

        val rad1 : RadioButton = view.findViewById(R.id.one_answer)
        val rad2 : RadioButton = view.findViewById(R.id.two__answer)
        val rad3 : RadioButton = view.findViewById(R.id.three_answer)
        val rad4 : RadioButton = view.findViewById(R.id.four_answer)

        var randIndex=0
        if (current_level_left.size<=1){randIndex = 0}
        else {var randIndex = Random.nextInt(0 until current_level_left.size-1)}

        val level = requireArguments().getString("level")
        val current_event = getRandomEvent(randIndex, level)

        for (key in answerPositiveMap.keys){
            if (key.cataclysmName == current_event.eventName)
                rad2.text = answerPositiveMap.get(key)?.methodName.toString()
            /*for (i in 0..current_bad_answer.size-1){
                rad1.text = current_bad_answer.toString()
            }*/
        }
        radioGroup.setOnCheckedChangeListener (
            RadioGroup.OnCheckedChangeListener { group, checkID ->
                val radButton : RadioButton = view.findViewById(checkID)
                Log.e("idCheck in RadioGroup", checkID.toString())

                /*if (checkID == R.id.two__answer){
                    playerScore = current_event.DealDamage(playerScore, current_event.importance)
                    Toast.makeText(requireContext(), "Верно!", LENGTH_SHORT).show()
                } else {
                    playerScore = current_event.GivePlayerScore(playerScore, current_event.importance)
                    Toast.makeText(requireContext(), "Неверно!", LENGTH_SHORT).show()
                }*/
            }
        )

        val year = requireArguments().getString("POLE")
        year.let {
            when(year){
                    "1" -> current_good_answer = levelSection[0].goodAnswers
                    "2" -> current_good_answer = levelSection[1].goodAnswers
                    "3" -> current_good_answer = levelSection[2].goodAnswers
            }
            when(year){
                "1" -> current_bad_answer = levelSection[0].badAnswer
                "2" -> current_bad_answer = levelSection[1].badAnswer
                "3" -> current_bad_answer = levelSection[2].badAnswer
            }

        }

        titleEvent.text = current_event.eventName
        descriptionEvent.text = current_event.description
        if (current_level_left.size>0){
            current_level_left.removeAt(randIndex)
        }
        btnDoneAnswer.setOnClickListener{
            val id: Int = radioGroup.checkedRadioButtonId
            if (id != -1){
                val checkRadio: RadioButton = view.findViewById(id)
                //Toast.makeText(requireContext(), checkRadio.text, LENGTH_SHORT).show()
            }
            if (id == R.id.two__answer){
                playerScore = current_event.DealDamage(playerScore, current_event.importance)
                Toast.makeText(requireContext(), "Верно!", LENGTH_SHORT).show()
                //Log.e("Score", )
            } else {
                playerScore = current_event.GivePlayerScore(playerScore, current_event.importance)
                Toast.makeText(requireContext(), "Неверно!", LENGTH_SHORT).show()
            }
            playerScore = current_event.GivePlayerScore(playerScore, current_event.importance)
            //else{current_event.DealDamage(playerScore, current_event.importance)}
            val bundle = Bundle()
            bundle.putString("level",requireArguments().getString("level"))
            view?.findNavController()?.navigate(R.id.action_eventFragment_to_gameFragment, bundle)
        }

        return view
    }
    fun getRandomEvent(randIndex:Int, level:String?): Event {
        var current_level = current_level_left
        if (current_level_left.size<=0){current_level.add(mediumLevelEvent[randIndex])}
        return current_level[randIndex]
    }

    fun levelToCurrentLevel(level:String?):MutableList<Event>{
        if(level != null){
            when(level){
                "easy" -> current_level = easyLevelEvent
                "medium" -> current_level = mediumLevelEvent
                "hard" ->  current_level = hardLevelEvent
            }
        }
        return current_level
    }

    /*fun randomAnswer(nowEventName: String): MutableList<Any> {
        val allListAnswer = answerPositiveMap
        val randInt = (0 until allListAnswer.size).random()

        fun randomAnswerGet(nowEventName: String){
            val list = setOf<MethodsAll>()
            list.plus(allMethods.random().methodName)
            list.plus(allMethods.random().methodName)
            list.plus(allMethods.random().methodName)
            //list.plus()
            for (i in 0 until answerPositiveMap.size){
                if (nowEventName == answerPositiveMap[i].)
            }
            return
        }
        //Log.e("data", )
        return list
    }*/
}