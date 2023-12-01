package com.example.progresscontainer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isVisible
import kotlin.properties.Delegates

private const val MAX_CHILD_COUNT = 3

class ProgressContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val layoutLoading: ViewGroup
    private val layoutNotice: ViewGroup

    init {
        val root = LayoutInflater.from(context).inflate(
            R.layout.view_progress_container, this, true
        )
        layoutLoading = root.findViewById(R.id.layoutLoading)
        layoutNotice = root.findViewById(R.id.layoutNotice)
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        if (childCount > MAX_CHILD_COUNT) {
            throw IllegalStateException("ProgressContainer can host only one direct child")
        }
    }

    private fun findContentView(): View? = children.firstOrNull {
        it.id != R.id.layoutLoading && it.id != R.id.layoutNotice
    }

    var state: State by Delegates.observable(State.Loading) { _, _, state ->
        when (state) {
            is State.Loading -> {
                layoutLoading.isVisible = true
                layoutNotice.isVisible = false
                findContentView()?.isVisible = false
            }

            is State.Notice -> {
                val text: TextView = findViewById(R.id.textView)
                layoutLoading.isVisible = false
                layoutNotice.isVisible = true
                findContentView()?.isVisible = false
                text.text = state.message

            }

            is State.Success -> {
                layoutLoading.isVisible = false
                layoutNotice.isVisible = false
                findContentView()?.isVisible = true
            }
        }
    }

    sealed class State {
        object Loading : State()
        data class Notice(val message: String) : State()
        object Success : State()
    }
}