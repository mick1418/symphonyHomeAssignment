package com.michaellaguerre.symphony.ui.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.michaellaguerre.symphony.core.extensions.loadFromUrl
import com.michaellaguerre.symphony.domain.entities.Author
import com.michaellaguerre.symphony.ui.views.AuthorDetailView
import javax.inject.Inject

class AuthorsPagingAdapter
@Inject constructor() :PagingDataAdapter<Author, AuthorsPagingAdapter.AuthorViewHolder>(AUTHOR_COMPARATOR) {


    internal var clickListener: (Author?) -> Unit = { _ -> }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AuthorViewHolder {
        return AuthorViewHolder(AuthorDetailView(parent.context))
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {

        val params = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        holder.itemView.layoutParams = params

        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        holder.bind(item, clickListener)
    }


    //**********************************************************************************************
    // VIEW HOLDERS
    //**********************************************************************************************

    class AuthorViewHolder(itemView: AuthorDetailView) : RecyclerView.ViewHolder(itemView) {

        fun bind(author: Author?, clickListener: (Author?) -> Unit) {
            itemView as AuthorDetailView
            itemView.binding.authorAvatarImageView.loadFromUrl(author?.avatarUrl ?: "")
            itemView.binding.authorNameTextView.text = author?.name
            itemView.binding.authorEmailTextView.text = author?.email
            itemView.binding.authorNickNameTextView.text = author?.userName
            itemView.setOnClickListener { clickListener(author) }
        }
    }


    companion object {

        val AUTHOR_COMPARATOR = object : DiffUtil.ItemCallback<Author>() {
            override fun areContentsTheSame(oldItem: Author, newItem: Author): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Author, newItem: Author): Boolean =
                oldItem.id == newItem.id
        }
    }
}