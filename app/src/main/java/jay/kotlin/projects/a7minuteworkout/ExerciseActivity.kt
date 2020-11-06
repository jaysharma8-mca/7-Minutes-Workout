package jay.kotlin.projects.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener{

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 1

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null

    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_exercise_activity.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        tts = TextToSpeech(this, this)

        exerciseList = Constants.defaultExerciseList()
        setUpRestView()

        setupExerciseStatusRecyclerView()
    }

    private fun setRestProgressBar() {
        progressBar.progress = restProgress // at the beginning it value of progress is 0
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = 10 - restProgress
                tvTimer.text = (10 - restProgress).toString()


            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true) // Current Item is selected
                exerciseAdapter!!.notifyDataSetChanged()
                //tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()

                setUpExerciseView()
            }
        }.start()
    }

    private fun setUpRestView() {

        try{
            //val soundURI = Uri.parse("android.resource://jay.kotlin.projects.a7minuteworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        }
        catch (e:Exception){
            e.printStackTrace()
        }


        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE

        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()
        setRestProgressBar()

    }


    private fun setExerciseProgressBar() {

        progressBarExercise.progress =
            exerciseProgress // at the beginning it value of progress is 0
        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress = exerciseTimerDuration.toInt() - exerciseProgress
                tvExerciseTimer.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < 11){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true) // updating in the list that this exercise is completed
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                }
                else{
                    /*Toast.makeText(
                        this@ExerciseActivity,
                        "Congratulations! You have completed the 7 minutes workout..",
                        Toast.LENGTH_LONG
                    ).show()*/
                    finishAffinity()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)

                }

                //llRestView.visibility = View.GONE
                //llExerciseView.visibility = View.VISIBLE
            }
        }.start()
    }

    private fun setUpExerciseView() {
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())
        setExerciseProgressBar()
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }


    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player != null){
            player!!.stop()
        }
        super.onDestroy()
    }


    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The language specified is not supported")
            }
            else{
                Log.e("TTS", "Initialization Failed!!!")
            }
        }
    }

    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setupExerciseStatusRecyclerView() {
        rvExerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)

        rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun pauseTimer(){
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
        }
    }

    private fun customDialogForBackButton() {
        pauseTimer()
        val customDialog = Dialog(this)
        /*Set the screen content from a layout resource.
         The resource will be inflated, adding all top-level views to the screen.*/
        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)
        customDialog.tvYes.setOnClickListener {
            finishAffinity()
            customDialog.dismiss() // Dialog will be dismissed
        }
        customDialog.tvNo.setOnClickListener {
            customDialog.dismiss()
            setExerciseProgressBar()
        }
        //Start the dialog and display it on screen.
        customDialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }


}