package jay.kotlin.projects.a7minuteworkout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

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
        hideKeyboard()
        calculateBMI()
    }

    private fun calculateBMI() {

        if(rbMetricUnits.isChecked){
            when {
                etMetricUnitWeight.text.toString().trim().isEmpty() -> {
                    Toast.makeText(this, "Please enter Weight", Toast.LENGTH_SHORT).show()
                }
                etMetricUnitHeight.text.toString().trim().isEmpty() -> {
                    Toast.makeText(this, "Please enter height", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val weight = etMetricUnitWeight.text.toString()
                    val height = etMetricUnitHeight.text.toString()
                    val heightInMeter = height.toFloat() / 100

                    val calculatedBMI = weight.toInt() / (heightInMeter * heightInMeter)
                    //Toast.makeText(this, calculatedBMI.toString(), Toast.LENGTH_SHORT).show()

                    //tvBMIValue.text = calculatedBMI.toString()

                    displayBMIResult(calculatedBMI)
                }
            }
        }
        else{
            if(etUsUnitWeight.text.toString().trim().isEmpty()){
                Toast.makeText(this, "Please enter Weight", Toast.LENGTH_SHORT).show()
            }
            else if(etUsUnitHeightFeet.text.toString().trim().isEmpty()){
                Toast.makeText(this, "Please enter Feet", Toast.LENGTH_SHORT).show()
            }
            else if(etUsUnitHeightInch.text.toString().trim().isEmpty()){
                Toast.makeText(this, "Please enter Inch", Toast.LENGTH_SHORT).show()
            }
            else{
                val usUnitHeightValueFeet: String = etUsUnitHeightFeet.text.toString()
                val usUnitHeightValueInch: String = etUsUnitHeightInch.text.toString()
                val usUnitWeightValue: Float = etUsUnitWeight.text.toString().toFloat()

                val heightValue = usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12
                val calculatedBMI = 703 * (usUnitWeightValue / (heightValue * heightValue))

                displayBMIResult(calculatedBMI)
            }
        }

        /**/

    }

    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        tvYourBMI.visibility = View.VISIBLE
        tvBMIValue.visibility = View.VISIBLE
        tvBMIType.visibility = View.VISIBLE
        tvBMIDescription.visibility = View.VISIBLE

        // This is used to round of the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue // Value is set to TextView
        tvBMIType.text = bmiLabel // Label is set to TextView
        tvBMIDescription.text = bmiDescription // Description is set to TextView
    }

    private fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    fun showUsUnits(view: View) {
        llMetricUnitsView.visibility = View.GONE
        llUsUnitsView.visibility = View.VISIBLE

        tvYourBMI.visibility = View.GONE
        tvBMIValue.visibility = View.GONE
        tvBMIType.visibility = View.GONE
        tvBMIDescription.visibility = View.GONE

        tvYourBMI.text = ""
        tvBMIType.text = ""
        tvBMIValue.text = ""
        tvBMIDescription.text = ""
        etUsUnitWeight.text?.clear()
        etUsUnitHeightFeet.text?.clear()
        etUsUnitHeightInch.text?.clear()

    }

    fun showMetricUnits(view: View) {
        llUsUnitsView.visibility = View.GONE
        llMetricUnitsView.visibility = View.VISIBLE

        tvYourBMI.visibility = View.GONE
        tvBMIValue.visibility = View.GONE
        tvBMIType.visibility = View.GONE
        tvBMIDescription.visibility = View.GONE

        tvYourBMI.text = ""
        tvBMIType.text = ""
        tvBMIValue.text = ""
        tvBMIDescription.text = ""

        etMetricUnitWeight.text?.clear()
        etMetricUnitHeight.text?.clear()
    }


}