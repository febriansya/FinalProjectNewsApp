package com.example.finalprojectnewsapp.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.finalprojectnewsapp.R
import com.example.finalprojectnewsapp.data.model.Article
import com.example.finalprojectnewsapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val article = bundle?.getParcelable<Article>("article")
        binding.title.text = article?.title
        binding.description.text = article?.description
        Glide.with(this).load(article?.urlToImage).into(binding.imageView2)
    }
}