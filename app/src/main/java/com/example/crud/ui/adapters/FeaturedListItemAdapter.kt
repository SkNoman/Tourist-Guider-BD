package com.example.crud.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crud.R
import com.example.crud.model.dashboard.FeaturedItem

class FeaturedListItemAdapter(
    context: Context,
    featuredList: ArrayList<FeaturedItem>,
    private var onClickPopularPlace: OnClickPopularPlace
):RecyclerView.Adapter<FeaturedItemViewHolder>(){

    private var mContext: Context = context
    private val content: ArrayList<FeaturedItem> = featuredList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FeaturedItemViewHolder(inflater,parent)
    }

    override fun onBindViewHolder(holder: FeaturedItemViewHolder, position: Int) {
        val featuredContent: FeaturedItem = content[position]
        holder.bind(mContext,featuredContent)
        holder.itemView.setOnClickListener{
            onClickPopularPlace.onClickPopularPlace(featuredContent.id)
        }
    }

    override fun getItemCount(): Int {
        return content.size
    }
    interface OnClickPopularPlace{
        fun onClickPopularPlace(id:Int)
    }
}

class FeaturedItemViewHolder(inflater: LayoutInflater,parent: ViewGroup):
RecyclerView.ViewHolder(inflater.inflate(R.layout.item_featured_menu_list,parent,false)){

    private var title: TextView = itemView.findViewById(R.id.fTitle)
    private var icon: ImageView = itemView.findViewById(R.id.fImage)
    fun bind(context: Context,featuredItem: FeaturedItem){
        title.text = featuredItem.title
        Glide.with(context).load(featuredItem.image)
            .placeholder(R.drawable.preloader).into(icon)
    }
}
