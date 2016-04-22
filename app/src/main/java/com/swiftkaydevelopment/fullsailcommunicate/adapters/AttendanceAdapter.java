package com.swiftkaydevelopment.fullsailcommunicate.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swiftkaydevelopment.fullsailcommunicate.R;
import com.swiftkaydevelopment.fullsailcommunicate.data.AttendanceItem;

import java.util.ArrayList;

/**
 * Created by Kevin Haines on 1/3/16.
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
 * Class Name: AttendanceAdapter
 * Class Description:
 */
public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {
    public static final String TAG = "AttendanceAdapter";

    private ArrayList<AttendanceItem> mItems = new ArrayList<>();

    public AttendanceAdapter(ArrayList<AttendanceItem> mItems) {
        this.mItems = mItems;
    }

    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AttendanceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AttendanceViewHolder holder, int position) {
        AttendanceItem item = mItems.get(position);
        holder.tvClass.setText(item.className);
        holder.tvSection.setText(item.section);
        holder.tvAttendance.setText(item.attendance);
        holder.tvStartDate.setText(item.startDate);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    protected class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView tvClass, tvStartDate, tvAttendance, tvSection;
        public AttendanceViewHolder(View itemView) {
            super(itemView);
            tvClass = (TextView) itemView.findViewById(R.id.attendanceItemClassName);
            tvStartDate = (TextView) itemView.findViewById(R.id.attendanceItemStartDate);
            tvAttendance = (TextView) itemView.findViewById(R.id.attendanceItemAttendance);
            tvSection = (TextView) itemView.findViewById(R.id.attendanceItemSection);
        }
    }
}
