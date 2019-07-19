package ru.farhodhaknazarov.newsapi.adapters

import android.content.Context
import android.graphics.*
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import ru.farhodhaknazarov.newsapi.R
import ru.farhodhaknazarov.newsapi.repo.entities.Article


class NewsRecyclerViewAdapter(var articleDataset: ArrayList<Article>, context: Context?) :
    RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder>() {

    val picasso: Picasso = Picasso.with(context)

    class NewsViewHolder(val newsItemView: LinearLayout) : RecyclerView.ViewHolder(newsItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val newsItemView =
            LayoutInflater.from(parent.context).inflate(R.layout.news_item_view, parent, false) as LinearLayout

        return NewsViewHolder(newsItemView)
    }

    override fun getItemCount(): Int = articleDataset.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val title = holder.newsItemView.findViewById<TextView>(R.id.news_title)
        val author = holder.newsItemView.findViewById<TextView>(R.id.news_author)
        val news_description = holder.newsItemView.findViewById<TextView>(R.id.news_description)
        val news_source = holder.newsItemView.findViewById<TextView>(R.id.news_source)
        val publishedAt = holder.newsItemView.findViewById<TextView>(R.id.news_publishedAt)
        val imageView: ImageView = holder.newsItemView.findViewById<TextView>(R.id.news_image) as ImageView
        val article = articleDataset[position]

        title.text = article.title
        author.text = article.author
        news_description.text = article.description
        publishedAt.text = article.publishedAt
        news_source.text = article.source.name

        try {
            picasso
                .load(article.urlToImage)
                .into(imageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class CircularTransformation(radius: Int) : Transformation {

        var mRadius = 10

        init {
            this.mRadius = radius
        }

        override fun transform(source: Bitmap): Bitmap {
            val output = Bitmap.createBitmap(
                source.width, source.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(output)

            val paint = Paint()
            val rect = Rect(0, 0, source.width, source.height)

            paint.isAntiAlias = true
            paint.isFilterBitmap = true
            paint.isDither = true

            canvas.drawARGB(0, 0, 0, 0)

            paint.color = Color.parseColor("#BAB399")

            if (mRadius == 0) {
                canvas.drawCircle(
                    source.width / 2 + 0.7f, source.height / 2 + 0.7f,
                    source.width / 2 - 1.1f, paint
                )
            } else {
                canvas.drawCircle(
                    source.width / 2 + 0.7f, source.height / 2 + 0.7f,
                    mRadius.toFloat(), paint
                )
            }

            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

            canvas.drawBitmap(source, rect, rect, paint)

            if (source != output) {
                source.recycle()
            }
            return output
        }

        override fun key(): String {
            return "circular$mRadius"
        }
    }
}