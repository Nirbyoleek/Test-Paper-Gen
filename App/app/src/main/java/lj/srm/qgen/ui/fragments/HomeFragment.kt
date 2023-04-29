package lj.srm.qgen.ui.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import lj.srm.qgen.R
import lj.srm.qgen.ui.MainViewModel
import lj.srm.qgen.ui.app.MainActivity
import lj.srm.qgen.util.Status
import com.google.android.material.snackbar.Snackbar
import com.tom_roush.pdfbox.pdmodel.PDDocument
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment()
{

    @Inject
    lateinit var glide : RequestManager

    lateinit var mainViewModel : MainViewModel

    var imageView2: ImageView? = null
    var imageView: ImageView? = null

    private var pdf : Uri ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        imageView2 = requireActivity().findViewById<View>(R.id.imageView2) as ImageView
        imageView = requireActivity().findViewById<View>(R.id.imageView) as ImageView
        modeImageShift()

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        Log.d("ID","result.message.toString()")

        (activity as MainActivity).fab.setOnClickListener {
           accessFiles()
        }
        subscribeToObservers()


    }

    private fun subscribeToObservers(){
        mainViewModel.questionsData.observe(viewLifecycleOwner){ result->
            when(result.status){
                Status.SUCCESS -> {
                    loadingIv.visibility = View.INVISIBLE
                    loadingContainer.visibility = View.INVISIBLE
                    (activity as MainActivity).fab.isEnabled = true
                    val serialized = result.data!!
                    val bundle = Bundle().apply {
                        putSerializable("qaResponse", serialized)
                    }

                    if (!(activity as MainActivity).hasBeenHandled) {
                        findNavController().navigate(R.id.action_homeFragment_to_questionsFragment, bundle)
                        (activity as MainActivity).hasBeenHandled = true
                    }
                }
                Status.ERROR -> {
                    loadingIv.visibility = View.INVISIBLE
                    loadingContainer.visibility = View.INVISIBLE
                    (activity as MainActivity).fab.isEnabled = true
                    Log.d("ID-Error",result.message.toString())


                    Snackbar.make(rootHome, "An unknown error occured", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.LTGRAY)
                            .setTextColor(Color.BLACK)
                            .setActionTextColor(Color.BLACK)
                            .setAction("Try again") {
                                pdf?.let { beginExtraction(it) }
                            }
                            .show()
                }
                Status.LOADING -> {
                    loadingIv.visibility = View.VISIBLE
                    loadingContainer.visibility = View.VISIBLE
                    (activity as MainActivity).fab.isEnabled = false
                    glide.load((activity as MainActivity).animationTheme).into(loadingIv)
                }
            }
        }
    }

    private fun accessFiles(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "application/pdf"
        startActivityForResult(Intent.createChooser(intent, "Select file"), 1);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)


        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null ){
            (activity as MainActivity).hasBeenHandled = false
            pdf = data.data
            pdf?.let{
                if(!mainViewModel.hasInternetConnection(requireContext())){
                    Snackbar.make(rootHome, "No Internet Connection", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.LTGRAY)
                            .setTextColor(Color.BLACK)
                            .show()
                }else{
                    beginExtraction(it)
                }

            }
        }
    }

    private fun beginExtraction(it: Uri) = CoroutineScope(Dispatchers.IO).launch{
        val inputStream: InputStream = requireActivity().contentResolver.openInputStream(it)!!
        val document = PDDocument.load(inputStream);
        mainViewModel.extractText(document)
    }

    private fun modeImageShift()
    {
        when (imageView2?.context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                (activity as MainActivity).animationTheme = R.drawable.loading_animation_dark
                imageView2!!.setImageResource(R.drawable.arrow_dark)
                imageView!!.setImageResource(R.drawable.lines_dark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                (activity as MainActivity).animationTheme = R.drawable.loading_animation_light
                imageView2!!.setImageResource(R.drawable.arrow_light)
                imageView!!.setImageResource(R.drawable.lines_light)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {

            }
        }
    }

}