package lj.srm.qgen.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import lj.srm.qgen.R
import lj.srm.qgen.ui.MainViewModel
import lj.srm.qgen.ui.app.MainActivity
import lj.srm.qgen.util.HorizontalBounceEdgeEffectFactory
import lj.srm.qgen.util.Status
import com.google.android.material.snackbar.Snackbar
import com.tom_roush.pdfbox.pdmodel.PDDocument
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lj.srm.qgen.adapters.AboutAdapter
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class AboutFragment : Fragment() {

    @Inject
    lateinit var glide : RequestManager

    lateinit var mainViewModel : MainViewModel
    private var pdf : Uri ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        setupRV()
        (activity as MainActivity).fab.setOnClickListener {
            accessFiles()
        }
        subscribeToObservers()

    }

    private fun subscribeToObservers(){
        mainViewModel.questionsData.observe(viewLifecycleOwner){ result->
            when(result.status){
                Status.SUCCESS -> {
                    Log.d("Det", "SUCCESS:" + result.data.toString())
                    loadingIvAbout.visibility = View.INVISIBLE
                    loadingContainerAbout.visibility = View.INVISIBLE
                    (activity as MainActivity).fab.isEnabled = true
                    
                    val serialized = result.data!!
                    val bundle = Bundle().apply {
                        putSerializable("qaResponse", serialized)
                    }

                    if(!(activity as MainActivity).hasBeenHandled){
                        findNavController().navigate(R.id.action_aboutFragment_to_questionsFragment,bundle)
                        (activity as MainActivity).hasBeenHandled = true
                    }
                }
                Status.ERROR -> {
                    Log.d("Det", "Error:" + result.message.toString())
                    loadingIvAbout.visibility = View.INVISIBLE
                    loadingContainerAbout.visibility = View.INVISIBLE
                    (activity as MainActivity).fab.isEnabled = true
                    Snackbar.make(rootAbout,"An unknown error occured", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.LTGRAY)
                            .setTextColor(Color.BLACK)
                            .setActionTextColor(Color.BLACK)
                            .setAction("Try again"){
                                pdf?.let { beginExtraction(it) }
                            }
                            .show()
                }
                Status.LOADING -> {
                    Log.d("Det", "LOADING")
                    loadingIvAbout.visibility = View.VISIBLE
                    loadingContainerAbout.visibility = View.VISIBLE
                    (activity as MainActivity).fab.isEnabled = false
                    glide.load((activity as MainActivity).animationTheme).into(loadingIvAbout)
                }
            }
        }
    }

    private fun accessFiles(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "application/pdf"
        startActivityForResult(Intent.createChooser(intent, "Select file"), 1);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null ){
            (activity as MainActivity).hasBeenHandled = false
            pdf = data.data
            pdf?.let{
                if(!mainViewModel.hasInternetConnection(requireContext())){
                    Snackbar.make(rootHome,"No Internet Connection", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.LTGRAY)
                            .setTextColor(Color.BLACK)
                            .show()
                    return@let
                }
                beginExtraction(it)
            }
        }
    }

    private fun beginExtraction(it : Uri) = CoroutineScope(Dispatchers.IO).launch{
        val inputStream: InputStream = requireActivity().contentResolver.openInputStream(it)!!
        val document = PDDocument.load(inputStream);
        mainViewModel.extractText(document)
    }

    private fun setupRV() {
        rvAbout.apply {
            adapter = AboutAdapter()
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            edgeEffectFactory = HorizontalBounceEdgeEffectFactory()
        }
    }

}