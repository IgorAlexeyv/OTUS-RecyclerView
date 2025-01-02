package otus.gpb.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), Listener {

    private lateinit var list :MutableList<Chat>
    private lateinit var adapter :ChatAdapter
    private lateinit var recyclerView :RecyclerView
    private var pagination :Int = 1
    private val list_generate_size = 40

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ChatAdapter(this)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val decorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        AppCompatResources.getDrawable(this, R.drawable.divider)?.let { decorator.setDrawable(it) }
        recyclerView.addItemDecoration(decorator)
        ItemTouchHelper(ItemTouchCallbacks(this)).attachToRecyclerView(recyclerView)
        recyclerView.adapter = adapter
        list = generateList()
        adapter.setItems(this.list)
    }

    // Клик на сообщении чата.
    override fun onItemClick(item: Chat) {
        Toast.makeText(this, item.name + "\n" + item.message, Toast.LENGTH_SHORT).show()
    }

    // Клик на иконке sound_on_off
    // Меняет иконку "sound_on <-> sound_off" у участника чата
    override fun onSoundClick(item: Chat) {

        val sound = if ( item.sound == Chat.Sound.ON )
            Chat.Sound.OFF
        else
            Chat.Sound.ON

        for( i in list )
        {
            if( i.name == item.name)
            {
                i.sound = sound
            }
        }

        recyclerView.post {
                adapter.notifyDataSetChanged() // Прямой вызов notifyDataSetChanged() приводит к исключению "Cannot call this method while RecyclerView is computing a layout or scrolling"
        }
    }

    // Удаляет сообщение из чата
    override fun onItemSwiped(item: Chat) {
        val index = list.indexOf(item)
        list.removeAt(index)

        recyclerView.post {
            adapter.notifyItemRemoved(index)  // Прямой вызов notifyItemRemoved() приводит к исключению "Cannot call this method while RecyclerView is computing a layout or scrolling"
        }
    }

    // Подгружает 40 новых сообщений в чат
    override fun onLoadMoreItem() {
        val position = list.size - 1
        val newList = generateList()
        list+= newList

        recyclerView.post {
            adapter.notifyItemRangeInserted(position, newList.size) // Прямой вызов notifyItemRangeInserted() приводит к исключению "Cannot call this method while RecyclerView is computing a layout or scrolling"
        }
    }

    private fun generateList(): MutableList<Chat> {
        data class Avatar(val resId: Int, val name: String, val sound :Chat.Sound)
        val avatars = listOf(
            Avatar(R.drawable.avatar_man_1, "Александр", Chat.Sound.ON),
            Avatar(R.drawable.avatar_girl_1, "Виктория", Chat.Sound.ON),
            Avatar(R.drawable.avatar_girl_2, "Светлана", Chat.Sound.OFF)
            )

        val list = mutableListOf<Chat>()
        repeat(list_generate_size) {
            val i = (0..2).random()
            val item = Chat(
                avatars[i].name,
                "Привет, как дела?",
                String.format("%02d:%02d", pagination, it), //Время. Часы - это номер порции добавленных итемов, а минуты - это порядковый номер итема чата в текущей "порции"
                ResourcesCompat.getDrawable(getResources(), avatars[i].resId,null),
                avatars[i].sound )
            list.add(item)

        }

        pagination++
        return list
    }

}