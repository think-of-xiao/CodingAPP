package com.example.administrator.myapplication.adapter

import android.content.Context
import android.view.ViewGroup
import android.view.View
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.administrator.myapplication.R
import com.example.administrator.myapplication.view.cardstackviewlib.CardStackView
import com.example.administrator.myapplication.view.cardstackviewlib.StackAdapter


class TestStackAdapter(mContext: Context): StackAdapter<Int>(mContext) {
    override fun bindView(data: Int?, position: Int, holder: CardStackView.ViewHolder?) {
        if (holder is ColorItemLargeHeaderViewHolder) {
            holder.onBind(data, position)
        }
        if (holder is ColorItemWithNoHeaderViewHolder) {
            holder.onBind(data, position)
        }
        if (holder is ColorItemViewHolder) {
            holder.onBind(data, position)
        }
    }

    override fun onCreateView(parent: ViewGroup?, viewType: Int): CardStackView.ViewHolder {
        val view: View
        return when (viewType) {
            R.layout.list_card_item_larger_header -> {
                view = layoutInflater.inflate(R.layout.list_card_item_larger_header, parent, false)
                ColorItemLargeHeaderViewHolder(view)
            }
            R.layout.list_card_item_with_no_header -> {
                view = layoutInflater.inflate(R.layout.list_card_item_with_no_header, parent, false)
                ColorItemWithNoHeaderViewHolder(view)
            }
            else -> {
                view = layoutInflater.inflate(R.layout.list_card_item, parent, false)
                ColorItemViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        /*return when (position) {
            6 -> //TODO TEST LARGER ITEM
                R.layout.list_card_item_larger_header
            10 -> R.layout.list_card_item_with_no_header
            else -> R.layout.list_card_item
        }*/
        return R.layout.list_card_item_larger_header //试试只用一个布局
    }

    internal class ColorItemViewHolder(view: View) : CardStackView.ViewHolder(view) {
        var mLayout: View
        var mContainerContent: View
        var mTextTitle: TextView

        init {
            mLayout = view.findViewById(R.id.frame_list_card_item)
            mContainerContent = view.findViewById(R.id.container_list_content)
            mTextTitle = view.findViewById<TextView>(R.id.text_list_card_title)
        }

        override fun onItemExpand(b: Boolean) {
            mContainerContent.visibility = if (b) View.VISIBLE else View.GONE
        }

        fun onBind(data: Int?, position: Int) {
            mLayout.background.setColorFilter(ContextCompat.getColor(context, data!!), PorterDuff.Mode.SRC_IN)
            mTextTitle.text = position.toString()
        }

    }

    internal class ColorItemWithNoHeaderViewHolder(view: View) : CardStackView.ViewHolder(view) {
        var mLayout: View
        var mTextTitle: TextView

        init {
            mLayout = view.findViewById(R.id.frame_list_card_item)
            mTextTitle = view.findViewById<TextView>(R.id.text_list_card_title)
        }

        override fun onItemExpand(b: Boolean) {}

        fun onBind(data: Int?, position: Int) {
            mLayout.background.setColorFilter(ContextCompat.getColor(context, data!!), PorterDuff.Mode.SRC_IN)
            mTextTitle.text = position.toString()
        }

    }

    internal class ColorItemLargeHeaderViewHolder(view: View) : CardStackView.ViewHolder(view) {
        var mLayout: View = view.findViewById(R.id.frame_list_card_item)
        var mContainerContent: View = view.findViewById(R.id.container_list_content)
        var mTextTitle: TextView = view.findViewById<TextView>(R.id.text_list_card_title)

        override fun onItemExpand(b: Boolean) {
            mContainerContent.visibility = if (b) View.VISIBLE else View.GONE
        }

        override fun onAnimationStateChange(state: Int, willBeSelect: Boolean) {
            super.onAnimationStateChange(state, willBeSelect)
            if (state == CardStackView.ANIMATION_STATE_START && willBeSelect) {
                onItemExpand(true)
            }
            if (state == CardStackView.ANIMATION_STATE_END && !willBeSelect) {
                onItemExpand(false)
            }
        }

        fun onBind(data: Int?, position: Int) {
            mLayout.background.setColorFilter(ContextCompat.getColor(context, data!!), PorterDuff.Mode.SRC_IN)
            mTextTitle.text = position.toString()

            itemView.findViewById<ImageButton>(R.id.bnt)
                    .setOnClickListener { Toast.makeText(context, "窝点击了imageButton", Toast.LENGTH_LONG).show() }
            itemView.findViewById<TextView>(R.id.text_view)
                    .setOnClickListener {
                (itemView.parent as CardStackView)
                        .performItemClick(this@ColorItemLargeHeaderViewHolder)
            }
        }

    }
}