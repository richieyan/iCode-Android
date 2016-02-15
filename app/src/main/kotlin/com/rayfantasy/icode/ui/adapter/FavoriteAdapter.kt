package com.rayfantasy.icode.ui.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.rayfantasy.icode.R
import com.rayfantasy.icode.databinding.ItemRecyclerCodeListBinding
import com.rayfantasy.icode.extension.inflate
import com.rayfantasy.icode.extension.loadPortrait
import com.rayfantasy.icode.model.ICodeTheme
import com.rayfantasy.icode.postutil.bean.CodeGood
import com.rayfantasy.icode.ui.activity.BlocksActivity
import com.rayfantasy.icode.ui.activity.UserActivity
import com.rayfantasy.icode.util.ms2RelativeDate
import kotlinx.android.synthetic.main.item_recycler_code_list.view.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity

class FavoriteAdapter(val activity: Activity, var codeGoods: MutableList<CodeGood>, onLoadingMore: () -> Unit) :
        LoadMoreAdapter<FavoriteAdapter.NormalViewHolder>(activity, onLoadingMore) {
    override val normalItemCount: Int
        get() = codeGoods.size

    override fun onBindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val codeGood = codeGoods[position]

        holder.title.text = codeGood.title
        holder.subTitle.text = codeGood.subtitle
        holder.time.text = ms2RelativeDate(activity, codeGood.createAt!!)
        holder.username.text = codeGood.username
        holder.pic.loadPortrait(codeGood.username)
        holder.pic.onClick {
            activity.startActivity<UserActivity>("username" to codeGood.username.toString())
        }
        holder.binding.highlight = codeGood.highlight ?: false
        /*if (codeGood.highlight ?: false) {
            holder.title.setTextColor(Color.RED)
            holder.username.setTextColor(Color.RED)
            holder.subTitle.setTextColor(Color.RED)
            holder.username.append("被管理员临时高亮!")
        }*/

        holder.bg.onClick {
            activity.startActivity<BlocksActivity>("codeGood" to codeGood)
        }
    }

    override fun onCreateNormalViewHolder(parent: ViewGroup, viewType: Int)
            = NormalViewHolder(parent.inflate(R.layout.item_recycler_code_list))

    class NormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pic = itemView.pic
        val username = itemView.username
        val time = itemView.time
        val title = itemView.title
        val subTitle = itemView.sub_title
        val bg = itemView.element_bg
        val binding: ItemRecyclerCodeListBinding

        init {
            binding = ItemRecyclerCodeListBinding.bind(itemView)
            binding.theme = ICodeTheme
        }
    }
}