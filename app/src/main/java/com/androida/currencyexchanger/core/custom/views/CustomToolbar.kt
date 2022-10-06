package com.androida.currencyexchanger.core.custom.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.androida.currencyexchanger.R
import com.androida.currencyexchanger.databinding.LayoutCustomToolbarBinding

class CustomToolbar
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private val inflater = LayoutInflater.from(context)
    private val binding = LayoutCustomToolbarBinding.inflate(inflater, this, true)

    init {
        attrs?.let {
            val styledAttributes = context.obtainStyledAttributes(
                it, R.styleable.CustomToolbar, 0, 0
            )
            styledAttributes.getString(R.styleable.CustomToolbar_toolbar_title)
                ?.let { title ->
                    binding.tvToolbarTitle.text = title
                }
            styledAttributes.recycle()
        }
    }

}