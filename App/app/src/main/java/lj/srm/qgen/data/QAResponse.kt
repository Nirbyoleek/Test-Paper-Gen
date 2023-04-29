package lj.srm.qgen.data

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class QAResponse(
        val `data`: List<Question>,
) : Serializable

