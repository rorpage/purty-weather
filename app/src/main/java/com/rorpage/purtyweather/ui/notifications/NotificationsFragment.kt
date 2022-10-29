package com.rorpage.purtyweather.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rorpage.purtyweather.R

class NotificationsFragment : Fragment() {
    private val notificationsViewModel: NotificationsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView = root.findViewById<TextView>(R.id.text_notifications)
        notificationsViewModel.text.observe(viewLifecycleOwner, { s -> textView.text = s })
        return root
    }
}
