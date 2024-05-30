package com.example.voicegesture.model
import com.example.voicegesture.R

data class Person(val image: Int, val name: String)

object People {

    private val images = intArrayOf(
        R.drawable.mujer,
        R.drawable.hombre
    )

    public val people = arrayOf(
        "Mujer",
        "Hombre"
    )

    var list: ArrayList<Person>? = null
        get() {

            if (field != null)
                return field

            field = ArrayList()
            for (i in images.indices) {

                val imageId = images[i]
                val personName = people[i]

                val person = Person(imageId, personName)
                field!!.add(person)
            }

            return field
        }
}
