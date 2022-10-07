package com.androida.currencyexchanger.core.fragment.utils

import android.view.LayoutInflater
import android.view.ViewGroup

typealias ViewBindingFactory<T> = (LayoutInflater, ViewGroup?, Boolean) -> T