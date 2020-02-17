package com.example.lifehacktesttask

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehacktesttask.application.Application
import com.example.lifehacktesttask.mvp.model.Company
import com.example.lifehacktesttask.ui.activities.InfoActivity
import com.squareup.picasso.Picasso

class CompanyAdapter : RecyclerView.Adapter<CompanyAdapter.ViewHolder>() {
    private val resultList: MutableList<Company> = ArrayList()

    fun setData(result: List<Company>) {
        resultList.clear()
        resultList.addAll(result)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.cardview_company_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return resultList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(resultList[position])
        holder.cardView.setOnClickListener { v: View? ->
            showNewsActivity(
                v!!.context,
                resultList[position].id
            )
        }
    }

    private fun showNewsActivity(context: Context, parameter: String) {
        val intent = Intent(context, InfoActivity::class.java)
        intent.putExtra("id", parameter)
        startActivity(context, intent, null)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val image: ImageView = itemView.findViewById(R.id.image)
        val cardView: CardView = itemView.findViewById(R.id.card_view)

        fun bind(model: Company) {
            title.text = model.name
            val baseUrl = Application.context.resources.getString(R.string.base_url)
            val imageUrl = baseUrl + model.img

            Picasso.get()
                .load(imageUrl)
                .error(R.drawable.image_not_found)
                .fit()
                .into(image)

        }
    }
}