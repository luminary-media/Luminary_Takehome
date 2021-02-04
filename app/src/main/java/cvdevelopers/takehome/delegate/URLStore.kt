package cvdevelopers.takehome.delegate

import cvdevelopers.githubstalker.R
import cvdevelopers.takehome.app.App

class URLStore : IURLStore {

    override fun getClientApiBaseURL(): String {
        return App.INSTANCE.resources.getString(R.string.client_api_base_url)
    }
}
