package com.example.umnlife

import android.app.Activity
import android.app.ProgressDialog.show
import android.content.Context
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

class SalamToast(context: Context?) : Toast(context) {
    fun showCustomToast(message: String, activity: Activity)
    {
        val layout = activity.layoutInflater.inflate (
            R.layout.toast_salam,
            activity.findViewById(R.id.toast_container)
        )

        // set the text of the TextView of the message
        val textView = layout.findViewById<TextView>(R.id.toast_text)
        textView.text = message

        // use the application extension function
        this.apply {
            setGravity(Gravity.BOTTOM, 0, 40)
            duration = Toast.LENGTH_LONG
            view = layout
            show()
        }
    }
}