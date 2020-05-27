class RootFragment : Fragment(R.layout.content) {

    private val fragmentFactory = FragmentFactoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)

        //all logic to show/hide/update etc should be inside fragments -
        //they have data to make decisions
        if (savedInstanceState == null) {
            childFragmentManager
                .beginTransaction()
                .add(R.id.content, fragmentFactory.listFragment())
                .add(R.id.content, fragmentFactory.detailsFragment())//with visibility gone
                .commit()
        }
    }
    
    private inner class FragmentFactoryImpl: FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
            when (className) {
                ListFragment::class.java.name -> listFragment()
                DetailsFragment::class.java.name -> detailsFragment()
                else -> super.instantiate(classLoader, className)
            }

        val model = Model()

        fun listFragment(): ListFragment = ListFragment(ListViewModel(model))
        
        fun detailsFragment(): DetailsFragment = DetailsFragment(DetailsViewModel(model))
    }
}
