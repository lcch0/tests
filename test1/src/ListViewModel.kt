class ListViewModel(private val model:Model)
{
    var listId: Int
        get() = model.listId
        set(value)
        {
            model.listId = value
        }
}