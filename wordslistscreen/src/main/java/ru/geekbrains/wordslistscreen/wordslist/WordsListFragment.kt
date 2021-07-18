package ru.geekbrains.wordslistscreen.wordslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.android.synthetic.main.fragment_words_list.*
import org.koin.android.ext.android.getKoin
import ru.geekbrains.core.BackButtonListener
import ru.geekbrains.core.base.BaseFragment
import ru.geekbrains.wordslistscreen.R
import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.DataModel
import ru.geekbrains.dictionary.utils.network.isOnline
import ru.geekbrains.historyscreen.SearchDialogFragment
import org.koin.core.qualifier.named


import ru.geekbrains.wordslistscreen.wordslist.adapter.WordsListRVAdapter

class WordsListFragment : BaseFragment<AppState>(), BackButtonListener {

    override val model: WordsListViewModel by lazy {
        ViewModelProvider(this, getKoin().get()).get(WordsListViewModel::class.java)
    }

    private val observer = Observer<AppState> { renderData(it)  }
    private var adapter: WordsListRVAdapter? = null
    private val onListItemClickListener: WordsListRVAdapter.OnListItemClickListener =
        object : WordsListRVAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                model.wordClicked(data)
            }
        }
    private lateinit var splitInstallManager: SplitInstallManager


    companion object {
        fun newInstance() = WordsListFragment()
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "12345"
        private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyscreen"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    fun initViewModel(){
        val factory = getKoin().get<ViewModelProvider.Factory>(qualifier = named("appViewModelProvider"))
        val viewModel = ViewModelProvider(this, factory).get(WordsListViewModel::class.java)
        model = viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View {
        val v: View = inflater.inflate(R.layout.fragment_words_list, parent, false)
        setHasOptionsMenu(true)
        return v
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("model: $model ")
        model.subscribe().observe(viewLifecycleOwner, observer)
        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    isNetworkAvailable = isOnline(activity!!.applicationContext)
                    if (isNetworkAvailable) {
                        model.getData(searchWord, isNetworkAvailable)
                    } else {
                        showNoInternetConnectionDialog()
                    }
                }
            })
            searchDialogFragment.show(childFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                val historyFragment = Class
                    .forName("com.lenatopoleva.dictionary.view.historyscreen.HistoryFragment")
                    .newInstance()

                splitInstallManager = SplitInstallManagerFactory.create(requireActivity())
                val request =
                    SplitInstallRequest
                        .newBuilder()
                        .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
                        .build()

                splitInstallManager
                    .startInstall(request)
                    .addOnSuccessListener {
                        if (historyFragment != null) {
                            model.historyMenuItemClicked(historyFragment)
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            requireContext(),
                            "Couldn't download feature: " + it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun backPressed(): Boolean = model.backPressed()

    override fun setDataToAdapter(data: List<DataModel>) {
        if (adapter == null) {
            words_list_recyclerview.layoutManager = LinearLayoutManager(context)
            words_list_recyclerview.adapter = WordsListRVAdapter(onListItemClickListener, data)
        } else {
            adapter!!.setData(data)
        }
    }
}