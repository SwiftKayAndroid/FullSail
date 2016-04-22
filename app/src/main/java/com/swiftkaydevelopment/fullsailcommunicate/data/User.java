package com.swiftkaydevelopment.fullsailcommunicate.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Kevin Haines on 1/2/16.
 * Copyright (C) 2015 Kevin Haines
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * Package Name: com.swiftkaydevelopment.fullsailcommunicate.data
 * Project: Full Sail Communicate
 * Class Name: User
 * Class Description:
 */
public class User implements Serializable{
    public String firstname;
    public String middleName;
    public String lastname;
    public String uid;
    public String profilePictureLocation;
    public String phoneNumber;
    public String address;
    public String emailAddress;


    /**
     * Creates a new Instance of a User
     * @return new User()
     */
    public static User instance() {
        return new User();
    }

    /**
     * Uses json returned from server to create a new user
     * Convenience method
     *
     * @param object JSONObject containing only user information
     * @return this instance
     */
    public User createUserFromJson(JSONObject object) {
        try {
            this.firstname = object.getString("firstname");
            this.lastname = object.getString("lastname");
            this.uid = object.getString("uid");
            return this;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
