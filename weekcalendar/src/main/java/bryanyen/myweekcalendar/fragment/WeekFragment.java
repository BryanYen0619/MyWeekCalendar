package bryanyen.myweekcalendar.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import bryanyen.myweekcalendar.R;
import bryanyen.myweekcalendar.eventbus.BusProvider;
import bryanyen.myweekcalendar.eventbus.Event;

/**
 * Created by nor on 12/4/2015.
 */
public class WeekFragment extends Fragment {
    public static String DATE_KEY = "date_key";
    public static String DATE_NOTE_DOT = "date_note_dot";
    public static String DATE_MONTH_MODE = "date_month_mode";
    private GridView gridView;
    private WeekAdapter weekAdapter;
    public static DateTime selectedDateTime = new DateTime();
    public static DateTime CalendarStartDate = new DateTime();
    public static ArrayList<DateTime> setDateDotList = new ArrayList<>();
    private DateTime startDate;
    private DateTime endDate;
    private boolean isVisible;
    private boolean isShowDateDot;
    private boolean isMonthMode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridView);
        init();
        return rootView;
    }

    private void init() {
        isShowDateDot = getArguments().getBoolean(DATE_NOTE_DOT);
        isMonthMode = getArguments().getBoolean(DATE_MONTH_MODE);

        ArrayList<DateTime> days = new ArrayList<>();
        DateTime midDate = (DateTime) getArguments().getSerializable(DATE_KEY);
        if (midDate != null) {
            midDate = midDate.withDayOfWeek(DateTimeConstants.WEDNESDAY);
        }

        int limit;
        if(isMonthMode) {
            //Getting 30 days mode
            limit = 31;
        } else {
            //Getting 7 days mode
            limit = 3;
        }

        //Getting all days
        for (int i = -3; i <= limit; i++)
            days.add(midDate != null ? midDate.plusDays(i) : null);

        startDate = days.get(0);
        endDate = days.get(days.size() - 1);

        weekAdapter = new WeekAdapter(getActivity(), days);

        if (isShowDateDot) {
            weekAdapter.setShowDateDot(true);
            weekAdapter.setDateDotList(setDateDotList);
        }

        gridView.setAdapter(weekAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusProvider.getInstance().post(new Event.OnDateClickEvent(weekAdapter.getItem
                        (position)));
                selectedDateTime = weekAdapter.getItem(position);
                BusProvider.getInstance().post(new Event.InvalidateEvent());
            }
        });
    }

    @Subscribe
    public void updateSelectedDate(Event.UpdateSelectedDateEvent event) {
        if (isVisible) {
            selectedDateTime = selectedDateTime.plusDays(event.getDirection());
            if (selectedDateTime.toLocalDate().equals(endDate.plusDays(1).toLocalDate())
                    || selectedDateTime.toLocalDate().equals(startDate.plusDays(-1).toLocalDate())) {
                if (!(selectedDateTime.toLocalDate().equals(startDate.plusDays(-1).toLocalDate()) &&
                        event.getDirection() == 1)
                        && !(selectedDateTime.toLocalDate().equals(endDate.plusDays(1)
                        .toLocalDate()) && event.getDirection() == -1))
                    BusProvider.getInstance().post(new Event.SetCurrentPageEvent(event.getDirection()));
            }
            if (event.getDirection() == 7) {
                BusProvider.getInstance().post(new Event.SetCurrentPageEvent(1));
            }
            if (event.getDirection() == -7) {
                BusProvider.getInstance().post(new Event.SetCurrentPageEvent(-1));
            }
            BusProvider.getInstance().post(new Event.InvalidateEvent());
        }
    }


    @Subscribe
    public void invalidate(Event.InvalidateEvent event) {
        gridView.invalidateViews();
    }

    @Subscribe
    public void updateUi(Event.OnUpdateUi event) {
        weekAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        BusProvider.getInstance().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        BusProvider.getInstance().unregister(this);
        super.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isVisible = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
    }

    public class WeekAdapter extends BaseAdapter {
        private ArrayList<DateTime> days;
        private ArrayList<DateTime> dateDotList;
        private Context context;
        private DateTime firstDay;
        private boolean isShowDateDot = false;

        WeekAdapter(Context context, ArrayList<DateTime> days) {
            this.days = days;
            this.context = context;
        }

        @Override
        public int getCount() {
            return days.size();
        }

        @Override
        public DateTime getItem(int position) {
            return days.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void setDateDotList(ArrayList<DateTime> dateDotList) {
            this.dateDotList = dateDotList;
            notifyDataSetChanged();
        }

        public void setShowDateDot(boolean showDateDot) {
            isShowDateDot = showDateDot;
        }

        @SuppressLint("InflateParams")
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.grid_item, null);
                firstDay = getItem(0);
            }

            DateTime dateTime = getItem(position).withMillisOfDay(0);

            TextView dayTextView = (TextView) convertView.findViewById(R.id.daytext);
            dayTextView.setText(String.valueOf(dateTime.getDayOfMonth()));

            ImageView dayDotImageView = (ImageView) convertView.findViewById(R.id.dayDot);

            if (isShowDateDot) {
                DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd");
                dayDotImageView.setVisibility(View.INVISIBLE);

                if (dateDotList != null) {
                    for (int i = 0; i < dateDotList.size(); i++) {
                        if (formatter.print(getItem(position)).equals(formatter.print(dateDotList.get(i)))) {
                            dayDotImageView.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            } else {
                dayDotImageView.setVisibility(View.GONE);
            }

            BusProvider.getInstance().post(new Event.OnDayDecorateEvent(convertView, dayTextView,
                    dateTime, firstDay, WeekFragment.selectedDateTime));
            return convertView;
        }
    }


}
