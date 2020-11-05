package jay.kotlin.projects.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmi.*

class BmiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setSupportActionBar(toolbar_bmi_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "CALCULATE BMI"

        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun calculateBmi(view: View) {
        val weight = etMetricUnitWeight.text.toString()
        val height = etMetricUnitHeight.text.toString()
        val heightInMeter = height.toFloat()/100

        val calculatedBMI = weight.toInt() / (heightInMeter * heightInMeter)
        Toast.makeText(this,calculatedBMI.toString(),Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }


}