package lj.srm.qgen.ui.app;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import lj.srm.qgen.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{

    var animationTheme : Int ?= null
    var hasBeenHandled : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(R.style.Theme_HomeworkPortal)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav.menu.getItem(1).isEnabled = false
        val navView: BottomNavigationView = findViewById(R.id.bottomNav)
        val navController = findNavController(R.id.fragment2)
        navView.setupWithNavController(navController)
        CoroutineScope(Dispatchers.Default).launch {
            PDFBoxResourceLoader.init(applicationContext)
        }



    }

//@lowjunkie - github


}
