package lj.srm.qgen.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import lj.srm.qgen.R
import kotlinx.android.synthetic.main.about_card.view.*
import kotlinx.android.synthetic.main.question_card.view.*

class AboutAdapter : RecyclerView.Adapter<AboutAdapter.AboutViewHolder>() {



    inner class AboutViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)

    private val names = listOf(
            "Tabrez Mohammed",
            "Samarpan Das",
            "S Ashwin Raja",
            "Aryan Nikhil",
            "Samaresh Samanta",
            "Nirbyoleek Das",
    )

    private val images = listOf<Int>(
            R.drawable.tabrez,
            R.drawable.samarpan,
            R.drawable.ashwin,
            R.drawable.aryan,
            R.drawable.samaresh,
            R.drawable.nirbyoleek
    )
    private val githubLinks = listOf<String>(
            "https://github.com/lowjunkie",
            "https://github.com/SamarpanDas",
            "https://github.com/ashraja941",
            "https://github.com/Violent-Idiot",
            "https://github.com/nectro",
            "https://github.com/Nirbyoleek"


    )

    private val linkedinLinks = listOf<String>(
            "https://www.linkedin.com/in/tabrez-mohammed-1773781b3/",
            "https://www.linkedin.com/in/samarpan-das",
            "https://www.linkedin.com/in/ashwin-raja-68950918b/",
            "https://www.linkedin.com/in/aryan-nikhil-962666190/",
            "https://www.linkedin.com/in/samaresh-samanta-255a5b1b7/",
            "https://in.linkedin.com/in/nirbyoleek-das-1421161b9"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutAdapter.AboutViewHolder {
        return AboutViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.about_card,parent,false ))
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder:AboutAdapter.AboutViewHolder, position: Int) {
        holder.itemView.apply {
            tvCard.text = names[position]
            ivCard.setImageResource(images[position])

            ivGithubLogo.setOnClickListener {
                startActivity(context,Intent(Intent.ACTION_VIEW, Uri.parse(githubLinks[position])), null)
            }
            ivLinkedinLogo.setOnClickListener {
                startActivity(context,Intent(Intent.ACTION_VIEW, Uri.parse(linkedinLinks[position])), null)
            }

            }
    }

    private var onItemClickListener : ((Map<String,Any>) -> Unit)?= null

    fun setOnItemClickListener(listener:(Map<String,Any>)->Unit){
        onItemClickListener = listener
    }

}