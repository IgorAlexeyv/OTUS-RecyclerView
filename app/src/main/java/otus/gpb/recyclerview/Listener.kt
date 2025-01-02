package otus.gpb.recyclerview

interface Listener {
    fun onItemClick(item: Chat)
    fun onSoundClick(item: Chat)
    fun onItemSwiped(item: Chat)
    fun onLoadMoreItem()
}