package com.androida.currencyexchanger.core.fragment.utils.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import com.androida.currencyexchanger.databinding.LayoutLoaderBinding

class LoaderDialog(context: Context) : AlertDialog(context) {

    lateinit var binding: LayoutLoaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        binding = LayoutLoaderBinding.inflate(layoutInflater)
        this.setView(binding.root)
        this.setCancelable(false)
        super.onCreate(savedInstanceState)
    }

    fun showDialog(): LoaderDialog {
        this.show()
        binding.vLoader.playAnimation()
        return this
    }

}
