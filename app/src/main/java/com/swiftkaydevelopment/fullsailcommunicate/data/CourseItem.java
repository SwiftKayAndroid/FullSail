package com.swiftkaydevelopment.fullsailcommunicate.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swiftkaydevelopment.fullsailcommunicate.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

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
 * Package Name: com.swiftkaydevelopment.fullsailcommunicate.data
 * Project: Full Sail Communicate
 * Class Name: CourseItem
 * Class Description:
 */
public class CourseItem implements Serializable{
    public static final String TAG = "CourseItem";
    public static final int COURSE_ITEM_HEADER = 0X00;
    public static final int COURSE_ITEM_DIVIDER = 0x01;
    public static final int COURSE_ITEM_CONTENT = 0x02;

    public static final int GROUP_INTRO = 0;
    public static final int GROUP_WEEK_ONE = 1;
    public static final int GROUP_WEEK_TWO = 2;
    public static final int GROUP_WEEK_THREE = 3;
    public static final int GROUP_WEEK_FOUR = 4;
    public static final int GROUP_EXTRA = 5;

    public int itemType;
    public int group;

    //divider
    public String dividerTitle;

    //content
    public String assignmentTitle;
    public String grade;
    public boolean isLocked;
    public String weight;
    public String startDate;
    public String endDate;
    public String assignmentType;

    public static CourseItem createHeader() {
        CourseItem item = new CourseItem();
        item.itemType = COURSE_ITEM_HEADER;
        return item;
    }

    /**
     * Creates a new CourseItem for divider positions
     * @param group int group id
     * @return CourseItem for divider
     */
    public static CourseItem createDivider(int group) {
        CourseItem item = new CourseItem();
        item.itemType = COURSE_ITEM_DIVIDER;
        switch (group) {
            case GROUP_INTRO:
                item.dividerTitle = "Introduction";
                break;
            case GROUP_WEEK_ONE:
                item.dividerTitle = "Week One";
                break;
            case GROUP_WEEK_TWO:
                item.dividerTitle = "Week Two";
                break;
            case GROUP_WEEK_THREE:
                item.dividerTitle = "Week Three";
                break;
            case GROUP_WEEK_FOUR:
                item.dividerTitle = "Week Four";
                break;
            case GROUP_EXTRA:
                item.dividerTitle = "Other";
                break;
            default:
                item.dividerTitle = "";
                break;
        }
        return item;
    }

    public static CourseItem createItem(JSONObject object) throws JSONException{
        CourseItem item = new CourseItem();
        item.itemType = COURSE_ITEM_CONTENT;
        item.assignmentTitle = object.getString("title");
        item.assignmentType = object.getString("type");
        item.grade = object.getString("grade");
        item.isLocked = item.grade.equals("locked");
        item.endDate = object.getString("enddate");
        item.startDate = object.getString("startdate");
        item.weight = object.getString("weight");
        item.group = object.getInt("group");

        return item;
    }

    /**
     * Used by adapter to return the proper view for
     * each item.
     * @param viewType int passed from onCreateViewHolder
     * @return Proper view for itemType
     */
    public static View inflateItem(ViewGroup parent, int viewType) {
        switch (viewType) {
            case COURSE_ITEM_HEADER:
                return LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item_divider, parent, false);
            case COURSE_ITEM_DIVIDER:
                return LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item_divider, parent, false);
            case COURSE_ITEM_CONTENT:
                return LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item_content, parent, false);
            default:
                return null;
        }
    }
}
