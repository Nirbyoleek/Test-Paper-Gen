package lj.srm.qgen.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import lj.srm.qgen.api.BaseQuestionsRepository
import lj.srm.qgen.data.QAResponse
import lj.srm.qgen.util.Resource
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        private val repository: BaseQuestionsRepository
): ViewModel() {


    val questionsData : MutableLiveData<Resource<QAResponse>> = MutableLiveData()

    fun extractText(document : PDDocument){
        questionsData.postValue(Resource.loading(null))
        CoroutineScope(Dispatchers.Default).launch{
            val pdfStripper = PDFTextStripper()
            pdfStripper.startPage = 0
            pdfStripper.endPage = document.numberOfPages
            val parsedText = pdfStripper.getText(document)
            Log.d("parsedText",parsedText)
            val key: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), parsedText)
            fetchQuestions(key)
        }
    }

    private fun fetchQuestions(key : RequestBody){
        viewModelScope.launch {
            try {
                //  if(hasInternetConnection()) {
                val response = repository.fetchQuestions(key)
                questionsData.postValue(response)
                //}
            }
            catch (e : Exception){
                questionsData.postValue(Resource.error(e.message.toString(), null))
            }

        }
    }



    fun hasInternetConnection(context: Context): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetworks = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetworks) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR)-> true
                capabilities.hasTransport(TRANSPORT_ETHERNET)->true
                else -> false
            }
        }
        else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    TYPE_WIFI-> true
                    TYPE_MOBILE->true
                    TYPE_ETHERNET->true
                    else -> false
                }

            }
        }
        return false
    }

}