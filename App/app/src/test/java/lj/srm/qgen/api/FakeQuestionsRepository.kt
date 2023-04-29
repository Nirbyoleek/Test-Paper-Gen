package lj.srm.qgen.api

import com.example.homeworkportal.data.QAResponse
import com.example.homeworkportal.data.Question
import com.example.homeworkportal.util.Resource
import okhttp3.RequestBody

class FakeQuestionsRepository : BaseQuestionsRepository {
    override suspend fun fetchQuestions(key: RequestBody): Resource<QAResponse> {
        return if(shouldReturnNetworkError){
            Resource.error("Error",null)
        }else{

            Resource.success(QAResponse(listOf(Question("","",""))))
        }
    }

    private var shouldReturnNetworkError = false

}