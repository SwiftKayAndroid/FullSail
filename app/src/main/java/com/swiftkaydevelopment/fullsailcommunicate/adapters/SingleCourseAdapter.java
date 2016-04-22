package com.swiftkaydevelopment.fullsailcommunicate.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swiftkaydevelopment.fullsailcommunicate.R;
import com.swiftkaydevelopment.fullsailcommunicate.data.CourseItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Kevin Haines on 1/5/16.
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
 * Package Name: com.swiftkaydevelopment.fullsailcommunicate.adapters
 * Project: Full Sail Communicate
 * Class Name: SingleCourseAdapter
 * Class Description:
 */
public class SingleCourseAdapter extends RecyclerView.Adapter<SingleCourseAdapter.CourseItemViewHolder> {
    public static final String TAG = "SingleCourseAdapter";

    private static final String WEIGHT_APPEND = "%\nWeight";

    private ArrayList<CourseItem> mCourseItems;

    public SingleCourseAdapter(ArrayList<CourseItem> mCourseItems) {
        this.mCourseItems = mCourseItems;
        sortAssignments();
    }

    @Override
    public CourseItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CourseItemViewHolder(CourseItem.inflateItem(parent, viewType), viewType);
    }

    @Override
    public void onBindViewHolder(CourseItemViewHolder holder, int position) {
        CourseItem item = mCourseItems.get(position);

        switch (item.itemType) {
            case CourseItem.COURSE_ITEM_CONTENT:
                holder.tvAssignmentTitle.setText(item.assignmentTitle);
                holder.tvAssignentWeight.setText(item.weight + WEIGHT_APPEND);
                holder.tvAssignmentStartDate.setText(item.startDate);
                holder.tvAssignmentEndDate.setText(item.endDate);
                holder.tvAssignmentType.setText(item.assignmentType);
                break;
            case CourseItem.COURSE_ITEM_DIVIDER:
                holder.tvDividerTitle.setText(item.dividerTitle);
                break;
            case CourseItem.COURSE_ITEM_HEADER:
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mCourseItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mCourseItems.get(position).itemType;
    }

    /**
     * Sets the CourseItems after the fragment fetches them from server
     *
     * @param assignments
     */
    public void setAssignments(ArrayList<CourseItem> assignments) {
        this.mCourseItems = assignments;
        sortAssignments();
    }

    private void sortAssignments() {
        Comparator<CourseItem> comparator = new Comparator<CourseItem>() {
            @Override
            public int compare(CourseItem lhs, CourseItem rhs) {
                if (lhs.group < rhs.group) {
                    return -1;
                } else if (lhs.group > rhs.group) {
                    return 1;
                }
                return 0;
            }
        };

        Collections.sort(mCourseItems, comparator);

        int currentGroup = -1;
        for (int i = 0; i < mCourseItems.size(); i++) {
            CourseItem item = mCourseItems.get(i);
            if (item.group != currentGroup) {
                currentGroup = item.group;
                mCourseItems.add(i, CourseItem.createDivider(currentGroup));
            }

        }

        notifyDataSetChanged();
    }

    protected class CourseItemViewHolder extends RecyclerView.ViewHolder {
        //divider
        TextView tvDividerTitle;

        //Content
        TextView tvAssignmentTitle, tvAssignmentStartDate, tvAssignmentEndDate;
        TextView tvAssignentWeight, tvAssignmentGrade, tvAssignmentType;
        ImageView ivAssignmentLock;

        public CourseItemViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == CourseItem.COURSE_ITEM_DIVIDER) {
                tvDividerTitle = (TextView) itemView.findViewById(R.id.tvDividerTitle);
            } else if (viewType == CourseItem.COURSE_ITEM_HEADER) {

            } else {
                tvAssignmentTitle = (TextView) itemView.findViewById(R.id.tvAssignmentTitle);
                tvAssignmentStartDate = (TextView) itemView.findViewById(R.id.tvAssignmentStartDate);
                tvAssignmentEndDate = (TextView) itemView.findViewById(R.id.tvAssignmentDueDate);
                tvAssignentWeight = (TextView) itemView.findViewById(R.id.tvAssignmentWeight);
                tvAssignmentGrade = (TextView) itemView.findViewById(R.id.tvAssignmentWeight);
                tvAssignmentType = (TextView) itemView.findViewById(R.id.tvAssignmentType);
            }
        }
    }
}
