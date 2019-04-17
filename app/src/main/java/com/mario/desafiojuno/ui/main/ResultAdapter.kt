package com.mario.desafiojuno.ui.main

import android.content.Context
import android.net.Uri
import br.com.mario.desafioeadbox.ui.base.adapter.BaseViewHolder
import com.facebook.drawee.generic.RoundingParams
import com.mario.desafiojuno.data.local.entity.Item
import com.mario.desafiojuno.ui.base.adapter.BaseListAdapter
import com.mario.desafiojuno.util.toDate
import kotlinx.android.synthetic.main.item_result.view.*


class ResultAdapter(callbacks: (Item) -> Unit) : BaseListAdapter<Item>(callbacks) {

    override fun getListItemView(context: Context): BaseViewHolder<Item> = ViewHolder(context)

    inner class ViewHolder(context: Context?) : BaseViewHolder<Item>(context) {
        override fun layoutResId(): Int = com.mario.desafiojuno.R.layout.item_result

        override fun bind(item: Item) {
            txtName.text = item.name
            txtStar.text = item.stargazersCount.toString()
            txtFork.text = item.forks.toString()
            txtUpdate.text = item.updatedAt.toDate()

            txtLanguage.text = item.language

            val roundingParams = RoundingParams.fromCornersRadius(5f)
            roundingParams.roundAsCircle = true
            imgAvatar.hierarchy.roundingParams = roundingParams
            imgAvatar.setImageURI(Uri.parse(item.owner.avatarUrl))

        }
    }
}
