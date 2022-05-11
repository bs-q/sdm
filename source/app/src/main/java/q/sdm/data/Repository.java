package q.sdm.data;

import q.sdm.data.local.prefs.PreferencesService;
import q.sdm.data.local.sqlite.DbService;
import q.sdm.data.remote.ApiService;


public interface Repository {

    /**
     * ################################## Preference section ##################################
     */
    String getToken();
    void setToken(String token);
    PreferencesService getSharedPreferences();


    /**
     * ################################## Sqlite section ##################################
     */
    DbService getSqliteService();



    /**
     *  ################################## Remote api ##################################
     */
    ApiService getApiService();


}
