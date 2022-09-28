package com.example.workoutapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityExerciseBinding
import com.example.workoutapp.databinding.DialogCustomBackConfirmationBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    private val nameOfExercise: Array<String> = arrayOf("Jumping Jacks", "Wall Sit", "Push Up", "Abdominal Crunches", "Step Up", "Air Squat", "Chair Dip", "Plank", "High Knees", "Lunges", "Push Up With Rotation", "Side Planks")

    private val describeOfExercise: Array<String> = arrayOf("Stand with your feet together, your knees slightly bent, and your arms to your sides. Jump, spreading your legs sideways, and stretch your arms sideways and over your head. Jump again, and go back to the initial position.", "As the name suggests, you will need to start by standing with your back against a wall. Spread your legs shoulder-width apart and plant your feet to the ground. Sit down slowly till your thighs are at a 90-degree angle to the floor, go back to the initial position, and repeat.", "Get down on the floor on all fours with your hands placed slightly wider than your shoulders. Straighten your limbs and then lower your body until your chest almost touches the ground. Push yourself back up and repeat.", "Lie down on the floor on your back and with your feet planted on the ground, bend your knees. Place your hands behind your head, contract your abdominal muscles, and slowly curl your torso off the floor. Now slowly lower your torso back to the starting position and repeat.", "To do this exercise, you will need a sturdy chair or bench. Put the chair in front of you and stand with your legs spread slightly more than shoulder-width apart. Step onto the chair with one foot and raise your other leg, stepping up with that foot so that both your feet are on the chair. Slowly lower your legs back to the starting position and repeat the process.", "Stand with your legs hip-width apart, your feet planted to the floor, and your arms raised straight before you. With your spine in a neutral position, squat down until your thighs are at a 90-degree angle to the floor. Slowly raise your body back to the initial position and repeat for 30 seconds.", "You will need a chair or bench for this exercise as well. Facing away from the chair, place your palms on it with your fingers facing forward. Bend your elbows slightly and raise your torso until your elbows are extended and slowly go back down.", "Start by lowering your body to the push-up position. Place your forearms on the floor with your hands facing forward. Squeeze your glutes, contract your abdominals, and make sure that your body is in a straight line. Hold this position for 30 secs.", "Stand with your feet hip-width apart and slowly raise your left knee until it almost touches your chest. Lower it down and raise your right knee this time. Continue this movement as fast as you can for 30 seconds.", "Start in a standing position. Step forward with one foot and slowly bend both knees until your rear knee almost touches the ground. Go back to the starting position and do the same for the other leg and repeat the process.", "Start by doing a standard push-up. As you get to the top of the movement, rotate your body and lift and extend your left arm overhead. Slowly rotate back to the previous position and repeat the process on the other side.", "Lie on your left side with your legs extended and put your torso's weight on your left forearm. Raise your torso and hips and hold the position for a couple of seconds and repeat on the other side.")

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)
        if(supportActionBar != null)
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        exerciseList = ExerciseConstants.defaultExerciseList()

        tts = TextToSpeech(this, this)
        tts!!.setSpeechRate(0.7F)

        setupRestView(6000)
        setupExerciseStatusRecyclerView()
    }

    private fun customDialogForBackButton()
    {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        customDialog.show()

        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }
        dialogBinding.btnYes.setOnClickListener {
            finish()
            onBackPressed()
        }
    }

    private fun setupExerciseStatusRecyclerView()
    {
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setupRestView(CDtime: Long)
    {
        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE

        binding?.tvUpcomingLabel?.text = "Next exercise:\n${nameOfExercise[currentExercisePosition+1]}"

        if(restTimer != null)
        {
            restTimer?.cancel()
            restProgress = 0
        }

        val speakText = binding?.tvUpcomingLabel?.text.toString()
        speakOut(speakText)

        setRestProgressBar(CDtime)
    }

    private fun setRestProgressBar(CDtime: Long)
    {
        binding?.progressBar?.progress = restProgress

        restTimer = object: CountDownTimer(CDtime, 1000)
        {
            override fun onTick(p0: Long)
            {
                restProgress++
                binding?.progressBar?.progress = CDtime.toInt()/1000 - restProgress
                binding?.tvTimer?.text = (CDtime.toInt()/1000 - restProgress).toString()
            }

            override fun onFinish()
            {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setupExerciseView(31000)
            }
        }.start()
    }

    private fun setupExerciseView(CDtime: Long)
    {
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE

        if(exerciseTimer != null)
        {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()

        val speakText = "${nameOfExercise[currentExercisePosition]}, ${describeOfExercise[currentExercisePosition]}"
        speakOut(speakText)

        setExerciseProgressBar(CDtime)
    }

    private fun setExerciseProgressBar(CDtime: Long)
    {
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object: CountDownTimer(CDtime, 1000)
        {
            override fun onTick(p0: Long)
            {
                exerciseProgress++
                binding?.progressBarExercise?.progress = CDtime.toInt()/1000 - exerciseProgress
                binding?.tvTimerExercise?.text = (CDtime.toInt()/1000 - exerciseProgress).toString()
            }

            override fun onFinish()
            {
                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()

                if(currentExercisePosition < exerciseList?.size!! - 1)
                {
                    setupRestView(6000)
                }
                else
                {
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }.start()
    }

    override fun onInit(status: Int)
    {
        if (status == TextToSpeech.SUCCESS)
        {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS","The Language specified is not supported!")
            }
        }
        else
        {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text: String)
    {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    override fun onDestroy()
    {
        super.onDestroy()

        if(restTimer != null)
        {
            restTimer?.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null)
        {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        binding = null
    }

    val dateTimeId = System.currentTimeMillis().toString()

    override fun onStop() {
        super.onStop()
        addDateTimeData("${(currentExercisePosition+1)}/12", dateTimeId)
    }

    override fun onResume() {
        super.onResume()
        FirebaseDatabase.getInstance().getReference(obj)
            .child("timeID${dateTimeId}").removeValue()
    }
}