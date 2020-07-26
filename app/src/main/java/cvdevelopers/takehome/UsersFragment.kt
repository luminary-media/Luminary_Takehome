package cvdevelopers.takehome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import cvdevelopers.githubstalker.R
import cvdevelopers.takehome.models.Client
import cvdevelopers.takehome.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.fragment_users.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : Fragment() {

    private val clickListener: ClickListener = this::onClientClicked
    private val viewModel: UsersViewModel by viewModel()
    private var mAdapter = Adapter(clickListener)
    private var dataSet = emptyList<Client>()

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSwipeToRefresh()
        setupRecyclerView()

        getUsers()

        viewModel.usersList.observe(viewLifecycleOwner, Observer {
            Log.d("observing::", "observing")
            renderClients(it)
        })
    }

    private fun setUpSwipeToRefresh() {
        simpleSwipeRefreshLayout.setOnRefreshListener {
            Toast.makeText(requireContext(), "Swipe detected Fetching users", Toast.LENGTH_SHORT).show()
            clearDatabase()
            renderClients(emptyList())
            getUsers()
            simpleSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getUsers() {
        viewModel.getUsers()
    }

    private fun clearDatabase() {
        viewModel.clearCache()
    }

    private fun renderClients(clients: List<Client>) {
        Log.d("rendering clients::", "display client users")
        loadingIndicator.visibility = View.GONE
        dataSet = clients
        mAdapter.updateClient(clients)
        recyclerview.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.adapter = mAdapter
        recyclerview.setHasFixedSize(true)
    }

    private fun onClientClicked(client: Client) {
        view?.let { Snackbar.make(it, client.name.first, Snackbar.LENGTH_LONG).show() }
    }
}