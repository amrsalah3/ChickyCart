package com.narify.ecommerce.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet

@SuppressLint("SetTextI18n")
class ExpandableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyle) {

    private var bufferType: BufferType? = null
    private var isTrimmed = true
    private var fullText: CharSequence = ""

    init {
        setOnClickListener {
            text = if (isTrimmed) fullText
            else fullText.subSequence(0, 100).toString() + "..."

        }
    }


    override fun setText(text: CharSequence?, type: BufferType?) {
        fullText = text ?: ""
        bufferType = type
        super.setText(text)
    }

    private fun resizeTextView(text: CharSequence?) {
        super.setText(text, bufferType)
    }
}