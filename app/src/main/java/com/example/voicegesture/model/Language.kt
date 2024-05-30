package com.example.voicegesture.model
import com.example.voicegesture.R

data class Language(val image: Int, val name: String)

object Languages {

    private val images = intArrayOf(
        R.drawable.espana,
        R.drawable.uk,
        R.drawable.francia,
        R.drawable.italia,
        R.drawable.portugal
    )

    public val languages = arrayOf(
        "Español",
        "Ingles",
        "Frances",
        "Italiano",
        "Portugues"
    )

    var list: ArrayList<Language>? = null
        get() {

            if (field != null)
                return field

            field = ArrayList()
            for (i in images.indices) {

                val imageId = images[i]
                val languagesName = languages[i]

                val language = Language(imageId, languagesName)
                field!!.add(language)
            }

            return field
        }
}