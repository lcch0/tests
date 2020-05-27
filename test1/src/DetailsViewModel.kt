class DetailsViewModel(private val model:Model)
{
    var refreshView: (()->Unit)? = null

    init {
        model.onListIdChanged.add{onListIdChanged()}
    }

    val listId: Int
        get() = model.listId


    private fun onListIdChanged()
    {
        //load new stuff for new id from listId, then
        //notify UI to update
        refreshView?.let {
            it()
        }
    }
}