package cvdevelopers.takehome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cvdevelopers.githubstalker.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_fragment_container, UsersFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
        }
    }

}
