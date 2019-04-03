# Clone From WeekCalendar
	https://github.com/nomanr/WeekCalendar

從WeekCalendar修改

<h3>修改內容</h3>
1. 日期更改成中文
2. 當天日期改用填滿園角矩形
3. 選擇日期改用空心圓角矩形

<h3>新增功能</h3>
1. 週數切換interface

<h3>截圖</h3>

上傳中

<h3>Setup</h3>


----------


<h5>Gradle</h5>

    dependencies {
       compile 'bryanyen.myweekcalendar:weekcalendar:1.0.1'
    }

 <h5>Maven</h5>

    <dependency>
	  <groupId>bryanyen.myweekcalendar</groupId>
	  <artifactId>weekcalendar</artifactId>
	  <version>1.0.1</version>
	  <type>pom</type>
	</dependency>

<h3>Sample Usage</h3>


----------

     <bryanyen.myweekcalendar.WeekCalendar
        android:id="@+id/weekCalendar"
        android:layout_width="match_parent"
        android:layout_height="65dp"
        android:background="@color/colorPrimary"/>
<h4>Theme the calendar</h4>
There are a few xml attributes to customise the calendar. If you feel that any customization option is missing, let me know.


----------

 1. Origin
 
 - `numOfPages` 
 - `daysTextSize`
 - `daysTextColor`
 - `daysBackgroundColor`
 - `weekTextSize`
 - `weekTextColor`
 - `weekBackgroundColor`
 - `selectedBgColor`
 - `todaysDateBgColor`
 - `todaysDateTextColor`
 - `hideNames`
 - `dayNameLength`

2. New

- `holidayTextColor` 
- `showChtWeekDayName` 

----------

<h5>Example</h5>

     <bryanyen.myweekcalendar.WeekCalendar
       android:id="@+id/weekCalendar"
       android:layout_width="match_parent"
       android:layout_height="65dp"
       android:background="@color/colorPrimary"
       app:numOfPages="150"
       app:dayNameLength="threeLetters"
       app:todaysDateBgColor="#ffffff"
       app:todaysDateTextColor="#000000"/>

<h5>Explained</h5>

 - `numOfPages`  by default, calendar has 100 pages. You can scroll 49 to left and 49 to right. Using this attribute you can set number of pages. You can send it to 1000, it depends on requirements. 
 - `daysTextSize` day means day of the month. By default text size is `17sp`.
 - `daysTextColor` by default the day text color is set to be white.
 - `daysBackgroundColor` if you have `colorPrimary` attribute in `color.xml`, then the backgroud color will be that one. Otherwise the purple color shown in the demo.
 - `weekTextSize` week means day of the week,i.e (S,M,T ..). By default text size is `17sp`.
 - `weekTextColor` by default the week day text color is set to be white.
 - `weekBackgroundColor`  same as `daysBackgroundColor`
 - `selectedBgColor` By default, its color is set to be `colorAccent`, if you've that attribute in attribute in `color.xml`, then the backgroud color will be that one. Otherwise the pink color shown in the demo.
 - `todaysDateBgColor` todays date background color, same as `selectedBgColor`.
 - `todaysDateTextColor` todays date text color,  by default the text color is set to be white.
 - `hideNames` , set this attribute to hide name of week days.
 - `dayNameLength` week day name length, singleLetter means (S,M,T..) and threeLetters means (Sun, Mun, Tue..).
- `holidayTextColor ` , set holiday title and day text color.
- `showChtWeekDayName` , on / off calendar week text show CHT text.

----------
<h3>Impelement Listener </h3>

`OnDateClickListener` returns `DateTime` object. `DateTime` is class available in <a href="http://www.joda.org/joda-time/" target="_blank">Joda Time</a>. I will recommend using this library if you are playing with date and time.

    weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                Toast.makeText(MainActivity.this, 
                "You Selected " + dateTime.toString(), Toast.LENGTH_SHORT).show();
            }

        });
  See the sample project for usage of methods like 
  - `reset()` 
  - `moveToNext()` 
  - `moveToPrevious()`
  - `setSelectedDate(DateTime)`
  - `setStartDate(DateTime)`


<h3>Libraries Used</h3>


----------
 - <a href="https://github.com/nomanr/WeekCalendar" target="_blank"> WeekCalendar </a>

<h3>License</h3>


----------

    Copyright (c) 2019 Bryan Yen Rafique

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
    http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    

