package com.example.crud.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.crud.R
import com.example.crud.admin.model.PlaceDetails
import com.example.crud.admin.model.PlaceName
import com.example.crud.model.menu.PlaceListItem

class PlaceListAdapter(context: Context,
                       carList: List<PlaceDetails?>,
                       private var onPlaceClick: OnClickPlace,
                       private var languageType:String)
    : RecyclerView.Adapter<PlaceListItemViewHolder>()
{
    private var mContext: Context = context
    private val content : List<PlaceDetails?> = carList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlaceListItemViewHolder(inflater,parent)
    }

    override fun getItemCount(): Int {
        return content.size
    }

    override fun onBindViewHolder(holder: PlaceListItemViewHolder, position: Int) {
        val menuContent: PlaceDetails? = content[position]
        holder.bind(mContext,menuContent,languageType)
        holder.itemView.setOnClickListener{
            onPlaceClick.onClick(menuContent!!.nameEn!!)
        }
    }
}
interface OnClickPlace{
    fun onClick(name:String)
}

class PlaceListItemViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.layout_pllace_liist,parent,false)){

    private var placeName: TextView = itemView.findViewById(R.id.txtPlaceName)
    private var placeImage: ImageView = itemView.findViewById(R.id.ivPlaceImage)

    fun bind(context: Context, placeListData: PlaceDetails?,languageType: String){

        if (languageType == "en"){
            placeName.text = placeListData!!.nameEn
        }else{
            placeName.text = placeListData!!.nameBn
        }

        Glide.with(context).load(placeListData.imageLink)
            .placeholder(R.drawable.item3).into(placeImage)
    }
}