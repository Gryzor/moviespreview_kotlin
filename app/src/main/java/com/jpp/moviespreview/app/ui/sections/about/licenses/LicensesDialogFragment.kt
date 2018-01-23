package com.jpp.moviespreview.app.ui.sections.about.licenses

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.webkit.WebView
import com.jpp.moviespreview.R

/**
 * Created by jpp on 1/20/18.
 */
class LicensesDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(title: String, url: String): LicensesDialogFragment {
            val data = Bundle()
            data.putString(TITLE_KEY, title)
            data.putString(URL_KEY, url)
            val dialogFragment = LicensesDialogFragment()
            dialogFragment.arguments = data
            return dialogFragment
        }

        private val TITLE_KEY = "title_key"
        private val URL_KEY = "url_key"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.licenses_dialog_fragment, null) as WebView
        view.loadUrl(arguments.getString(URL_KEY))
        val builder = AlertDialog.Builder(activity)
        return builder.setTitle(arguments.getString(TITLE_KEY))
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .create()
    }
}