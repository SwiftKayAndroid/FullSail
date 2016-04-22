package com.swiftkaydevelopment.fullsailcommunicate.managers;

import android.os.AsyncTask;

import com.swiftkaydevelopment.fullsailcommunicate.network.ConnectionManager;
import com.swiftkaydevelopment.fullsailcommunicate.network.NetworkHelper;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Kevin Haines on 12/31/15.
 * Copyright (C) 2015 Kevin Haines
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Package Name: com.swiftkaydevelopment.fullsailcommunicate.managers
 * Project: Full Sail
 * Class Name: AccountManager
 * Class Description: This class manager key aspects of the user's account such
 *                  as handling authentication, password changes, and other features
 *                  that pertain specifically to the account.
 */
public class AccountManager {

    public interface AuthenticationListener {
        int FAILED_NO_CONNECTION = 0;
        int FAILED_INVALID_CREDENTIALS = 1;
        int FAILED_OTHER = 2;
        void onAuthenticationSuccessful();
        void onAuthenticationFailed(int failType);
    }

    /**
     * Adds an authentication listener to this instance
     *
     * @param listener Authentication Listener
     */
    public void addAuthenticationListener(AuthenticationListener listener) {
        mAuthenticationListeners.addIfAbsent(listener);
    }

    /**
     * Removes the authentication listener in order to keep memory low
     *
     * @param listener
     */
    public void removeAuthenticationListener(AuthenticationListener listener) {
        mAuthenticationListeners.remove(listener);
    }

    private static AccountManager sInstance = null;

    private CopyOnWriteArrayList<AuthenticationListener> mAuthenticationListeners = new CopyOnWriteArrayList<>();

    /**
     * Gets the Singleton instance of AccountManager
     *
     * @return sInstance Singleton of AccountManager
     */
    public static AccountManager instance() {
        if (sInstance == null) {
            synchronized (AccountManager.class) {
                sInstance = new AccountManager();
            }
        }
        return sInstance;
    }

    /**
     * Makes the Async call to authenticate the user's credentials
     *
     * @param username String username to login with
     * @param password String password to login with
     */
    public void authenticateUser (String username, String password) {
        new AuthenticateUserTask(username, password).execute();
    }

    /**
     * Private class to perform the operation of logging a user into the app
     * or performing a verification that the user's credentials are still valid.
     */
    private class AuthenticateUserTask extends AsyncTask<Void, Void, Void> {
        String username;
        String password;

        public AuthenticateUserTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ConnectionManager connectionManager = new ConnectionManager();
            connectionManager.setMethod(ConnectionManager.POST);
            connectionManager.setUri(NetworkHelper.AUTHENTICATE_USER.AUTHENTICATE_URL);
            connectionManager.addParam(NetworkHelper.AUTHENTICATE_USER.USERNAME, username);
            connectionManager.addParam(NetworkHelper.AUTHENTICATE_USER.PASSWORD, password);
            //TODO: Production will actually send http requests.
//            String result = connectionManager.sendHttpRequest();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //TODO: Here we will perform a valid check of the transaction
            for (AuthenticationListener l : mAuthenticationListeners) {
                l.onAuthenticationSuccessful();
            }

        }
    }
}
