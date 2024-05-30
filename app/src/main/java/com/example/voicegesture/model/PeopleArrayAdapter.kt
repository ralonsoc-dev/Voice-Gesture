package com.example.voicegesture.model
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.voicegesture.R

class PeopleArrayAdapter(context: Context, personList: List<Person>) : ArrayAdapter<Person>(context, 0, personList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {

        val country = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        val personImage: ImageView = view.findViewById(R.id.boxImage)
        val personName: TextView = view.findViewById(R.id.boxName)

        if (country != null) {
            personImage.setImageResource(country.image)
            personName.text = country.name
        }


        return view
    }
}
