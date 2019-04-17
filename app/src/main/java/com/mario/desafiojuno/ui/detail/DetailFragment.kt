package com.mario.desafiojuno.ui.detail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.facebook.drawee.generic.RoundingParams
import com.mario.desafiojuno.R
import com.mario.desafiojuno.data.local.entity.Item
import com.mario.desafiojuno.util.toDate
import kotlinx.android.synthetic.main.detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val viewModel by viewModel<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val item = arguments?.getParcelable<Item>("ITEM")

        showDetail(item)

    }

    private fun showDetail(item: Item?) {
        item?.let {
            txtNameDetail.text = item.name
            txtDescriptionDetail.text = item.description
            txtStarDetail.text = item.stargazersCount.toString()
            txtForkDetail.text = item.forks.toString()
            txtUpdateDetail.text = item.updatedAt.toDate()
            txtLanguageDetail.text = String.format(resources.getString(R.string.language), item.language)

            val roundingParams = RoundingParams.fromCornersRadius(5f)
            roundingParams.roundAsCircle = true
            imgAvatarDetail.hierarchy.roundingParams = roundingParams
            imgAvatarDetail.setImageURI(Uri.parse(item.owner.avatarUrl))
        }
    }

}
