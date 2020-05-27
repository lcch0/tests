class Model
{
    var onListIdChanged: MutableList<()->Unit> = mutableListOf()

    private var _listId = -1

    var listId: Int
        get() = _listId
        set(value)
        {
            if(_listId != value)
            {
                _listId = value
                onListIdChanged.forEach{ it -> it()}
            }
        }
}