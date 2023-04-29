package lj.srm.qgen.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import lj.srm.qgen.R
import lj.srm.qgen.data.Question
import kotlinx.android.synthetic.main.question_card.view.*


class QuestionsAdapter : RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>() {

    inner class QuestionsViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)


    private val differCallback = object : DiffUtil.ItemCallback<Question>(){
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }

    }

    private var differ = AsyncListDiffer(this,differCallback)

    var submitted : Boolean = false
    var questions: List<Question>
        get() = differ.currentList
        set(value) = differ.submitList(value)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {

        return QuestionsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.question_card,parent,false ))
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        val question = questions[position]
        val q2 = questions
        holder.itemView.apply {
            tvQuestion.text = question.question

            tvAnswer.addTextChangedListener{
                if(tvAnswer.isFocused && tvAnswer.text.isNotEmpty() && position == holder.adapterPosition){
                    q2[position].text = tvAnswer.text.toString()
                    questions = q2
                    Log.d("Fit",question.question)
                }
            }

            setOnClickListener{
                onItemClickListener?.let { it(question, position) }
            }
            if(submitted){
                tvAnswer.setText(question.answers)
            }else{
                tvAnswer.setText(question.text)
            }
        }
    }

    private var onItemClickListener : ((Question, Int) -> Unit) ?= null

    fun setOnItemClickListener(listener:(Question, Int)->Unit){
        onItemClickListener = listener
    }

}


