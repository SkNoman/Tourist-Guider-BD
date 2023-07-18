package com.example.crud.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crud.R
import com.example.crud.admin.model.PlaceName
import com.example.crud.model.menu.PlaceListItem

class PlaceListAdapter(context: Context, carList: List<PlaceName?>, private var onPlaceClick: OnClickPlace)
    : RecyclerView.Adapter<PlaceListItemViewHolder>()
{
    private var mContext: Context = context
    private val content : List<PlaceName?> = carList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlaceListItemViewHolder(inflater,parent)
    }

    override fun getItemCount(): Int {
        return content.size
    }

    override fun onBindViewHolder(holder: PlaceListItemViewHolder, position: Int) {
        val menuContent: PlaceName? = content[position]
        holder.bind(mContext,menuContent)
      /*  holder.itemView.setOnClickListener{
            onPlaceClick.onClick(menuContent!!.placeId!!)
        }*/
    }
}
interface OnClickPlace{
    fun onClick(id:Int)
}

class PlaceListItemViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.layout_pllace_liist,parent,false)){

    private var placeName: TextView = itemView.findViewById(R.id.txtPlaceName)
    private var hotelCount: TextView = itemView.findViewById(R.id.txtHotelsCount)
    private var placeImage: ImageView = itemView.findViewById(R.id.ivPlaceImage)

    fun bind(context: Context, placeListData: PlaceName?){
        placeName.text = placeListData!!.name

     /*   Glide.with(context).load(placeListData.placeImage)
            .placeholder(R.drawable.item3).into(placeImage)*/
    }
}