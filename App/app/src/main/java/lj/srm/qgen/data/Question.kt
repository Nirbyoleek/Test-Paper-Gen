package lj.srm.qgen.data

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Question(
        val answers: String,
        val question: String,
        var text : String = ""
): Serializable
