package lj.srm.qgen.api

import lj.srm.qgen.data.QAResponse
import lj.srm.qgen.util.Resource
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class QuestionsRepository @Inject constructor(
        private val questionsAPI: QuestionsAPI
): BaseQuestionsRepository{

     override suspend fun fetchQuestions(key: RequestBody): Resource<QAResponse> {
          return handleResponse(questionsAPI.getQuestions(key))
     }
     private fun handleResponse(response : Response<QAResponse>) : Resource<QAResponse>{
          if(response.isSuccessful){
               response.body()?.let {
                    return Resource.success(it)
               }
          }
          return Resource.error(response.message(),null)
     }
}

