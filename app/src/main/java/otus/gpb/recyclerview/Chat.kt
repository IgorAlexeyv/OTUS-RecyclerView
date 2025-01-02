package otus.gpb.recyclerview

import android.graphics.drawable.Drawable

data class Chat (
    val name: String,
    val message: String,
    val time: String,
    val avatar: Drawable? = null,
    var sound: Sound = Sound.ON
    ){
    enum class Sound{ ON, OFF}
}