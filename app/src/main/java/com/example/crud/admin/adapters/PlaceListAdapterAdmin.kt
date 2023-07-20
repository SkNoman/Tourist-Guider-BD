package com.example.crud.admin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crud.R
import com.example.crud.admin.model.PlaceDetails
import com.example.crud.databinding.PlaceListItemAdminBinding

class PlaceListAdapterAdmin(context: Context,
                            carList: List<PlaceDetails?>,
                            private var onClickDelete: OnClickDelete,
                            private var onClickEdit: OnClickEdit)
    : RecyclerView.Adapter<PlaceListItemViewHolder>()
{
    private var mContext: Context = context
    private val content : List<PlaceDetails?> = carList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PlaceListItemAdminBinding.inflate(inflater, parent, false)
        return PlaceListItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return content.size
    }

    override fun onBindViewHolder(holder: PlaceListItemViewHolder, position: Int) {
        val menuContent: PlaceDetails? = content[position]
        holder.bind(mContext,menuContent)

        holder.binding.ivDeleteAdmin.setOnClickListener{
            onClickDelete.onClickDelete(menuContent!!.placeKey!!, menuContent.divisionEn!!)
        }
        holder.binding.ivEditAdmin.setOnClickListener{
            onClickEdit.onClickEdit(menuContent!!.placeKey!!)
        }

    }
}
interface OnClickDelete{
    fun onClickDelete(placeKey:String,division:String)
}

interface OnClickEdit{
    fun onClickEdit(placeKey:String)
}

class PlaceListItemViewHolder(val binding: PlaceListItemAdminBinding):
    RecyclerView.ViewHolder(binding.root){


    fun bind(context: Context, placeListData: PlaceDetails?){
        binding.txtPlaceNameAdmin.text = placeListData!!.nameEn
        binding.txtDistrictAdmin.text = placeListData.districtEn
        Glide.with(context).load(placeListData.imageLink)
            .placeholder(R.drawable.item3).into(binding.ivPlaceImageAdmin)

    }
}