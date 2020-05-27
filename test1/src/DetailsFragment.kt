class DetailsFragment(private var viewModel: DetailsViewModel): Fragment() {

    init {
        viewModel.refreshView = {
            //refresh ui using viewModel's data,
            //or, if list id is -1, hide fragment
        }
    }

}
