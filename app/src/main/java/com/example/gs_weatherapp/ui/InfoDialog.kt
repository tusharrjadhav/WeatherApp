package com.example.gs_weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.gs_weatherapp.R
import com.example.gs_weatherapp.databinding.FragmentInfoDialogBinding

class InfoDialog : DialogFragment() {

    private lateinit var binder: FragmentInfoDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.CustomDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = FragmentInfoDialogBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder.doneBtb.setOnClickListener {
            parentFragmentManager.popBackStack()
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            dismissAllowingStateLoss()
        }
    }

    companion object {
        fun newInstance(): InfoDialog {
            return InfoDialog()
        }
    }
}
