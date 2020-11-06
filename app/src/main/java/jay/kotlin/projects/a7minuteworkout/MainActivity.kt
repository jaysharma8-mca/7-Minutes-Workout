package jay.kotlin.projects.a7minuteworkout

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCenter.start(
            application, "e09e7d5e-59bc-427e-bcc0-5f39f35699d2",
            Analytics::class.java, Crashes::class.java
        )

        //AppCenter.setLogLevel(Log.VERBOSE);

    }

    fun llStart(view: View) {
        //Toast.makeText(this, "Button Pressed", Toast.LENGTH_LONG).show()
        val intent = Intent(this, ExerciseActivity::class.java)
        startActivity(intent)
        finishAffinity()
        //Crashes.generateTestCrash()
    }

    fun bmiActivity(view: View) {
        val intent = Intent(this, BmiActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    fun historyActivity(view: View) {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}