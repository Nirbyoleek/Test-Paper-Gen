package lj.srm.qgen.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import lj.srm.qgen.R
import lj.srm.qgen.adapters.QuestionsAdapter
import lj.srm.qgen.ui.app.MainActivity
import lj.srm.qgen.ui.dialogs.ResultDialog
import lj.srm.qgen.util.BounceEdgeEffectFactory
import lj.srm.qgen.util.Constants.SEARCH_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_score_card.*
import kotlinx.android.synthetic.main.fragment_questions.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class QuestionsFragment : Fragment() {

    @Inject
    lateinit var questionsAdapter: QuestionsAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()

        var args :QuestionsFragmentArgs = QuestionsFragmentArgs.fromBundle(requireArguments())
        var questions = args.qaResponse?.data
        if (questions != null) {
            questionsAdapter.questions = questions
        }
        questionsAdapter.setOnItemClickListener { question, i ->
             //noop
        }

        var job : Job?= null
        etSearch.addTextChangedListener{editable->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        var filtered = questions?.filter {
                            var query = editable.toString().trim()
                            it.question.contains(query, ignoreCase = true)
                        }
                        if (filtered != null) {
                            questionsAdapter.questions = filtered
                        }
                    } else{
                        if (questions != null) {
                            questionsAdapter.questions = questions

                        }

                    }
                }
            }
        }


        (activity as MainActivity).fab.setOnClickListener {
            val questions = questionsAdapter.questions
            var score = 0
            questions.forEach {
                if (it?.answers?.toLowerCase()?.trim() == it?.text?.toLowerCase()?.trim()){
                    score+=1
                }
            }
            val dialog = ResultDialog.newInstance(
                    _score = score
            )

            dialog.show(childFragmentManager, ResultDialog.TAG)


        }
    }

    private fun setupRV() {
        rvQuestions.apply {
            adapter = questionsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            edgeEffectFactory = BounceEdgeEffectFactory()
        }
    }

    override fun onResume() {
        (activity as MainActivity).fab.setImageResource(R.drawable.ic_check)
        Log.d("TAT","YES")
        super.onResume()
    }


    override fun onPause() {
        (activity as MainActivity).fab.setImageResource(R.drawable.ic_add)
        questionsAdapter.submitted = false
        questionsAdapter.notifyDataSetChanged()
        super.onPause()
    }
}