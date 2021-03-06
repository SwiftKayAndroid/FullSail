package com.swiftkaydevelopment.fullsailcommunicate.data;

import java.io.Serializable;

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
 * Package Name: com.swiftkaydevelopment.fullsailcommunicate.data
 * Project: Full Sail Communicate
 * Class Name: Grade
 * Class Description:
 */
public class Grade implements Serializable {
    public static final String TAG = "Grade";

    public String className;
    public String section;
    public String startDate;
    public String grade;
    public String status;

}
