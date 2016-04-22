package com.swiftkaydevelopment.fullsailcommunicate.managers;

import android.os.AsyncTask;

import com.swiftkaydevelopment.fullsailcommunicate.data.Course;
import com.swiftkaydevelopment.fullsailcommunicate.data.CourseItem;
import com.swiftkaydevelopment.fullsailcommunicate.network.ConnectionManager;
import com.swiftkaydevelopment.fullsailcommunicate.network.NetworkHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Kevin Haines on 1/9/16.
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
 * Project: Full Sail Communicate
 * Class Name: CourseManager
 * Class Description:
 */
public class CourseManager {

    public interface CourseManagerListener {
        void onGetCourses(ArrayList<Course> courses);
        void onGetAssignments(ArrayList<CourseItem> assignments);
    }

    private CopyOnWriteArrayList<CourseManagerListener> mListeners = new CopyOnWriteArrayList<>();
    private static CourseManager sInstance = null;

    /**
     * Gets a Singleton instance of the CourseManager
     *
     * @return Singleton instance of CourseManager
     */
    public static CourseManager instance() {
        if (sInstance == null) {
            synchronized (CourseManager.class) {
                sInstance = new CourseManager();
            }
        }
        return sInstance;
    }

    /**
     * Adds a listener to the list of listeners
     *
     * @param listener CourseManagerListener
     */
    public void addListener(CourseManagerListener listener) {
        mListeners.addIfAbsent(listener);
    }

    /**
     * Removes the listener from the list of listeners
     *
     * @param listener Instance of CourseManagerListener
     */
    public void removeListener(CourseManagerListener listener) {
        mListeners.remove(listener);
    }

    /**
     * Gets the list of current courses from the server
     *
     * @param uid Current User's id
     */
    public void getCurrentCourses(String uid) {
        new GetCurrentCoursesTask(uid).execute();
    }

    public void getAssignments(String uid, Course course) {
        new GetAssignmentsTask(uid, course).execute();
    }

    private class GetCurrentCoursesTask extends AsyncTask<Void, Void, ArrayList<Course>> {
        String uid;

        public GetCurrentCoursesTask(String uid) {
            this.uid = uid;
        }

        @Override
        protected ArrayList<Course> doInBackground(Void... params) {
            ConnectionManager connectionManager = new ConnectionManager();
            connectionManager.setMethod(ConnectionManager.POST);
            connectionManager.setUri(NetworkHelper.GET_COURSES_URL);
            connectionManager.addParam("uid", uid);
            String result = connectionManager.sendHttpRequest();
            ArrayList<Course> courses = new ArrayList<>();

            try {
                if (result != null) {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("courses");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);
                        Course course = Course.createCourseFromJson(child);
                        if (course != null) {
                            courses.add(course);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return courses;
        }

        @Override
        protected void onPostExecute(ArrayList<Course> courses) {
            super.onPostExecute(courses);
            for (CourseManagerListener l : mListeners) {
                l.onGetCourses(courses);
            }
        }
    }

    private class GetAssignmentsTask extends AsyncTask<Void, Void, ArrayList<CourseItem>> {
        String uid;
        Course course;

        public GetAssignmentsTask(String uid, Course course) {
            this.uid = uid;
            this.course = course;
        }

        @Override
        protected ArrayList<CourseItem> doInBackground(Void... params) {
            ConnectionManager connectionManager = new ConnectionManager();
            connectionManager.setMethod(ConnectionManager.POST);
            connectionManager.setUri(NetworkHelper.GET_ASSIGNMENTS_URL);
            connectionManager.addParam("uid", uid);
            connectionManager.addParam("courseid", course.courseName);
            String result = connectionManager.sendHttpRequest();
            ArrayList<CourseItem> assignments = new ArrayList<>();

            try {
                if (result != null) {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("assignments");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);
                        CourseItem item = CourseItem.createItem(child);
                        if (item != null) {
                            assignments.add(item);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return assignments;
        }

        @Override
        protected void onPostExecute(ArrayList<CourseItem> courseItems) {
            super.onPostExecute(courseItems);

            for (CourseManagerListener l : mListeners) {
                l.onGetAssignments(courseItems);
            }
        }
    }

}
