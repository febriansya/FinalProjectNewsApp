package com.example.finalprojectnewsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectnewsapp.R
import com.example.finalprojectnewsapp.data.model.Article
import com.example.finalprojectnewsapp.databinding.ItemHeadlineBinding
import com.example.finalprojectnewsapp.utils.toDateConverter

class HeadlineAdapter(
    val headlineList: ArrayList<Article>
) : RecyclerView.Adapter<HeadlineAdapter.HeadlineViewHolder>() {

    inner class HeadlineViewHolder(holder: ItemHeadlineBinding) :
        RecyclerView.ViewHolder(holder.root) {
        val title = holder.headlineTitle
        val image = holder.imageView
        val source = holder.author
        val date = holder.date
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeadlineAdapter.HeadlineViewHolder {
        val itemHeadlineBinding =
            ItemHeadlineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeadlineViewHolder(itemHeadlineBinding)
    }

    override fun onBindViewHolder(holder: HeadlineAdapter.HeadlineViewHolder, position: Int) {
        val article = headlineList[position]
        holder.title.text = article.title
        holder.date.text = article.publishedAt.toDateConverter(article.publishedAt)
        if (article.urlToImage == null) {
            holder.image.setImageResource(R.drawable.img_not_found)
        } else {
            val img = Glide.with(holder.image)
                .load(article.urlToImage)
                .centerCrop()
                .into(holder.image)
        }
        holder.source.text = article.source.name

    }

    override fun getItemCount(): Int {
        return headlineList.size
    }

}