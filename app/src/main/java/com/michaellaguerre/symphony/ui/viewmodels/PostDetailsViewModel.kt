package com.michaellaguerre.symphony.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.michaellaguerre.symphony.core.platform.BaseViewModel
import com.michaellaguerre.symphony.domain.entities.Comment
import com.michaellaguerre.symphony.domain.entities.Post
import com.michaellaguerre.symphony.domain.interactors.GetPostComments
import javax.inject.Inject

class PostDetailsViewModel(post: Post) : BaseViewModel() {

    @Inject
    lateinit var getPostComments: GetPostComments


    var post: MutableLiveData<Post> = MutableLiveData()
    lateinit var comments: LiveData<PagingData<Comment>>


    //**********************************************************************************************
    // INITIALIZATION
    //**********************************************************************************************

    init {
        this.post = MutableLiveData(post)
    }

    //**********************************************************************************************
    // ACTIONS
    //**********************************************************************************************

    fun loadCommentsForPost(postId: Int) {
        this.comments = getPostComments.invoke(GetPostComments.Params(postId))
    }


    //**********************************************************************************************
    // FACTORY
    //**********************************************************************************************

    class Factory(private val post: Post) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            try {
                return modelClass.getConstructor(Post::class.java).newInstance(post)
            } catch (e: Exception) {
                throw RuntimeException(
                    "Cannot create instance of$modelClass: it should have a (val post: Post) constructor",
                    e
                )
            }
        }
    }
}