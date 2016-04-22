package com.swiftkaydevelopment.fullsailcommunicate.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swiftkaydevelopment.fullsailcommunicate.R;
import com.swiftkaydevelopment.fullsailcommunicate.data.Grade;

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
 * Class Name: GradesAdapter
 * Class Description: Adapter to contain Grades list items
 */
public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.GradesViewHolder>{
    public static final String TAG = "GradesAdapter";

    private ArrayList<Grade> mGrades = new ArrayList<>();

    public GradesAdapter(ArrayList<Grade> mGrades) {
        this.mGrades = mGrades;
    }

    @Override
    public GradesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GradesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grades_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(GradesViewHolder holder, int position) {
        Grade grade = mGrades.get(position);
        holder.tvGrade.setText(grade.grade);
        holder.tvSection.setText(grade.section);
        holder.tvStartDate.setText(grade.startDate);
        holder.tvStatus.setText(grade.status);
        holder.tvClass.setText(grade.className);
    }

    @Override
    public int getItemCount() {
        return mGrades.size();
    }

    protected class GradesViewHolder extends RecyclerView.ViewHolder {
        TextView tvClass, tvGrade, tvSection, tvStartDate, tvStatus;
        public GradesViewHolder(View itemView) {
            super(itemView);
            tvClass = (TextView) itemView.findViewById(R.id.attendanceItemClassName);
            tvGrade = (TextView) itemView.findViewById(R.id.attendanceItemAttendance);
            tvSection = (TextView) itemView.findViewById(R.id.attendanceItemSection);
            tvStartDate = (TextView) itemView.findViewById(R.id.attendanceItemStartDate);
            tvStatus = (TextView) itemView.findViewById(R.id.gradesItemStatus);
        }
    }
}
