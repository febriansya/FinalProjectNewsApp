package com.example.finalprojectnewsapp.adapter

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectnewsapp.R
import com.example.finalprojectnewsapp.data.model.Article
import com.example.finalprojectnewsapp.databinding.ItemEverythingBinding

class EverythingAdapter(
    private val everythingList: List<Article>,
) : RecyclerView.Adapter<EverythingAdapter.EverythingViewHolder>() {

    private var onClickListener: OnClickListener? = null

    inner class EverythingViewHolder(holder: ItemEverythingBinding) :
        RecyclerView.ViewHolder(holder.root) {

        val image = holder.image
        val title = holder.tvTitle
        val desc = holder.tvDesc
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EverythingAdapter.EverythingViewHolder {
        val itemEverythingBinding =
            ItemEverythingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EverythingViewHolder(itemEverythingBinding)
    }

    override fun onBindViewHolder(holder: EverythingAdapter.EverythingViewHolder, position: Int) {
        val article = everythingList[position]
        if (article.urlToImage == null) {
            holder.image.setImageResource(R.drawable.img_not_found)
        } else {
            Glide.with(holder.itemView).load(article.urlToImage)
                .into(holder.image)
            holder.title.text = article.title
            holder.desc.text = article.description
        }

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, article)
        }

    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Article)
    }

    override fun getItemCount(): Int {
        return everythingList.size
    }

}
