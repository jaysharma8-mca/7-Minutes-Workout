package jay.kotlin.projects.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun llStart(view: View) {
        //Toast.makeText(this, "Button Pressed", Toast.LENGTH_LONG).show()
        val intent = Intent(this, ExerciseActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    fun bmiActivity(view: View) {
        val intent = Intent(this, BmiActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}