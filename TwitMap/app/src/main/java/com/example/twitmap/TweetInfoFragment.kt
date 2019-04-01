package com.example.twitmap


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.twitmap.interfaces.IGoogleMarkerData
import java.lang.ref.WeakReference

class TweetInfoFragment : AppCompatDialogFragment() {

    private val isShowing: Boolean
        get() = dialog != null && dialog.isShowing

    private val negativeButton: WeakReference<Button>
        get() {
            val dlg = dialog as AlertDialog
            return WeakReference(dlg.getButton(DialogInterface.BUTTON_NEGATIVE))
        }

    private var onOk: ((IGoogleMarkerData) -> Unit)? = null

    override fun onDestroyView()
    {
        if (dialog != null && retainInstance)
            dialog.setDismissMessage(null)
        super.onDestroyView()
    }


    override fun show(manager: FragmentManager, tag: String)
    {
        try
        {
            manager.beginTransaction().add(this, tag).commitAllowingStateLoss()
        }
        catch (e: IllegalStateException)
        {
            e.printStackTrace()
        }
    }

    override fun dismiss() {
        try {
            if (fragmentManager != null)
            {
                fragmentManager
                    .beginTransaction()
                    .remove(this)
                    .commitAllowingStateLoss()
            }
        }
        catch (e: IllegalStateException)
        {
            e.printStackTrace()
        }

    }

    override fun onSaveInstanceState(outState: Bundle?)
    {
        outState?.putBoolean(SHOULD_DISMISS_DIALOG, isShowing)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        val factory = LayoutInflater.from(activity)
        val parentView =
            factory.inflate(R.layout.fragment_tweet_info, null)

        val tweet = parentView.findViewById<TextView>(R.id.shortTweet)
        if(tweet != null)
            setText(tweet)

        if (parentFragment == null)
            retainInstance = true

        if (savedInstanceState != null && !savedInstanceState.isEmpty)
        {
            val shouldDismiss =
                savedInstanceState.getBoolean(SHOULD_DISMISS_DIALOG, false)
            if (shouldDismiss)
                dismiss()
        }

        return initAlertDialog(marker, parentView)
    }

    private fun setText(textView: TextView)
    {
        val sb = StringBuilder()
        sb.appendln("User ${marker.userName} says,")
        sb.appendln(marker.description)
        sb.appendln()
        val s = marker.hashTags.joinToString()
        sb.appendln(marker.hashTags.joinToString())

        textView.text = sb.toString()
    }

    private fun initAlertDialog(marker: IGoogleMarkerData, parentView: View): AlertDialog
    {
        val alert = AlertDialog.Builder(activity)

        // Set an EditText view to get user input
        alert.setView(parentView)

        alert.setPositiveButton("More..") { _, _ ->
            onOk?.invoke(marker)
        }

        alert.setCancelable(true)

        val alertDialog = alert.create()
        //negativeButton.get()?.visibility = View.GONE
        alertDialog.setCanceledOnTouchOutside(false)
        return alertDialog
    }

    private lateinit var marker: IGoogleMarkerData

    companion object
    {
        const val SHOULD_DISMISS_DIALOG = "shouldDismissDialog"
        private const val TAG = "TweetInfoFragment"
        fun showDialog(fragmentManager: FragmentManager,
                       marker: IGoogleMarkerData,
                       onOkCallback: (IGoogleMarkerData) -> Unit)
        {
            val dlg = TweetInfoFragment()
            dlg.marker = marker
            dlg.onOk = onOkCallback
            dlg.show(fragmentManager, TAG)
        }
    }
}
