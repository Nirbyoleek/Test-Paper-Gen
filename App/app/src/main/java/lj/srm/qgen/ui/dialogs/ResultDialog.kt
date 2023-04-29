package lj.srm.qgen.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import lj.srm.qgen.R
import lj.srm.qgen.adapters.QuestionsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_score_card.*
import javax.inject.Inject

@AndroidEntryPoint
class ResultDialog : DialogFragment() {

    @Inject
    lateinit var questionsAdapter: QuestionsAdapter

    companion object {
        //

        const val TAG = "DialogSlot"
        var score: Int? = null

        fun newInstance(
                _score: Int,
        ): ResultDialog {
            score = _score
            return ResultDialog()
        }

    }



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_score_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvScore.text = score.toString()
        btnKey.setOnClickListener {
            questionsAdapter.submitted = true
            questionsAdapter.notifyDataSetChanged()
            dismiss()
        }

        }


    }



//    override fun onStart() {
//        super.onStart()
//        dialog?.window?.setLayout(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT
//        )
//    }
