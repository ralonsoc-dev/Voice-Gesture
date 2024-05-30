package com.example.voicegesture.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.voicegesture.R

class LanguagesArrayAdapter(context: Context, languageList: List<Language>) : ArrayAdapter<Language>(context, 0, languageList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {

        val country = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        val languageImage: ImageView = view.findViewById(R.id.boxImage)
        val languageName: TextView = view.findViewById(R.id.boxName)

        if (country != null) {
            languageImage.setImageResource(country.image)
            languageName.text = country.name
        }


        return view
    }
}