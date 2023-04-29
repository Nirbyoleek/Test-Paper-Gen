package lj.srm.qgen.api

import lj.srm.qgen.data.QAResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface QuestionsAPI {

    @Multipart
    @POST("texttoquestion")
    suspend fun getQuestions(
            @Part("key") key: RequestBody
    ): Response<QAResponse>




}
