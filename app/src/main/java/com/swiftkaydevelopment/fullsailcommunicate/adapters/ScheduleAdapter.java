package com.swiftkaydevelopment.fullsailcommunicate.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.swiftkaydevelopment.fullsailcommunicate.R;
import com.swiftkaydevelopment.fullsailcommunicate.data.ScheduleItem;

import java.util.ArrayList;

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
 * Package Name: com.swiftkaydevelopment.fullsailcommunicate.adapters
 * Project: Full Sail Communicate
 * Class Name: ScheduleAdapter
 * Class Description: Adapter to contain the items for the Schedule RecyclerView
 *
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> implements Filterable {

    public interface ScheduleAdapterListener {
        void onItemClicked(ScheduleItem item);
    }
    public static final String TAG = "ScheduleAdapter";

    private ArrayList<ScheduleItem> mItems = new ArrayList<>();
    private ArrayList<ScheduleItem> mUseItems = new ArrayList<>();

    private ScheduleAdapterListener mListener;

    public ScheduleAdapter(ArrayList<ScheduleItem> mItems) {
        this.mItems = mItems;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    /**
     * Sets the ScheduleAdapterListener for this instance of the adapter
     *
     * @param listener ScheduleAdapterListener
     */
    public void setScheduleAdapterListener(ScheduleAdapterListener listener) {
        mListener = listener;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScheduleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        ScheduleItem item = mUseItems.get(position);
        holder.tvDate.setText(item.date);
        holder.tvClass.setText(item.className);
        holder.tvSection.setText("Section: " + item.section);
        holder.tvRoom.setText("Room: " + item.room);
        holder.tvInstructor.setText("Instructor: " + item.instructor);
        holder.tvStartEnd.setText("Start: " + item.startTime + " End: " + item.endTime);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUseItems.size();
    }

    protected class ScheduleViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvDate, tvClass, tvInstructor, tvStartEnd, tvRoom, tvSection;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.scheduleItemDate);
            tvClass = (TextView) itemView.findViewById(R.id.scheduleItemClass);
            tvInstructor = (TextView) itemView.findViewById(R.id.scheduleItemInstructor);
            tvStartEnd = (TextView) itemView.findViewById(R.id.scheduleItemStartEnd);
            tvRoom = (TextView) itemView.findViewById(R.id.scheduleItemRoom);
            tvSection = (TextView) itemView.findViewById(R.id.scheduleItemSection);
        }
    }
}
