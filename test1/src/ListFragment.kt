class ListFragment(private var viewModel: ListViewModel) : Fragment() {

    //some user input
    fun onListIdChanged(listId: Int)
    {
        viewModel.listId = listId
    }
}
