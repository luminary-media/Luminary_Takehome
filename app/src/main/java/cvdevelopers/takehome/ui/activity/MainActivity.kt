package cvdevelopers.takehome.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cvdevelopers.githubstalker.databinding.ActivityMainBinding
import cvdevelopers.takehome.common.Constants
import cvdevelopers.takehome.ui.adapter.ClientsAdapter
import cvdevelopers.takehome.ui.viewmodel.UsersViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import cvdevelopers.takehome.model.result.Result
import cvdevelopers.takehome.model.users.Client
import cvdevelopers.takehome.ui.decoration.ClientsItemDecoration
import cvdevelopers.takehome.ui.lifecycle.AutoDisposable
import cvdevelopers.takehome.ui.lifecycle.autoDispose

class MainActivity : AppCompatActivity(),
        SwipeRefreshLayout.OnRefreshListener {
    private val className = javaClass.simpleName

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersViewModel: UsersViewModel
    private val autoDisposable = AutoDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        autoDisposable.bindTo(lifecycle)

        initUI()
        initObservers()

        usersViewModel.loadClients()
    }

    private fun initUI() {
        binding.swipeToRefresh.setOnRefreshListener(this)
        binding.clients.adapter = ClientsAdapter()
        binding.clients.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.clients.addItemDecoration(ClientsItemDecoration())
    }

    private fun initObservers() {
        usersViewModel.observeClients()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onClientsResult(it) }
                .autoDispose(autoDisposable)
    }

    private fun onClientsResult(result: Result<List<Client>>) {
        Log.d(Constants.LOG_TAG, "[$className] on clients result")
        binding.swipeToRefresh.isRefreshing = false

        if (result.error != null) {
            // handle error
            return
        }

        val clientsAdapter = binding.clients.adapter as? ClientsAdapter ?: return
        clientsAdapter.updateClients(result.data)
    }

    override fun onRefresh() {
        Log.d(Constants.LOG_TAG, "[$className] pull to refresh")
        usersViewModel.getClientsNetwork()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.swipeToRefresh.setOnRefreshListener(null)
    }
}
