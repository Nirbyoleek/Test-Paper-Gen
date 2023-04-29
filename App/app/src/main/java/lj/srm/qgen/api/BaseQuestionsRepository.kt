package lj.srm.qgen.api

import lj.srm.qgen.util.Resource
import lj.srm.qgen.data.QAResponse
import okhttp3.RequestBody

interface BaseQuestionsRepository {

    suspend fun fetchQuestions(key : RequestBody): Resource<QAResponse>
}