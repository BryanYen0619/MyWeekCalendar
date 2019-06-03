package bryanyen.sample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bryanyen.myweekcalendar.WeekCalendar;
import bryanyen.myweekcalendar.listener.OnDateClickListener;
import bryanyen.myweekcalendar.listener.OnWeekChangeListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private WeekCalendar weekCalendar;
    private Button nextButton;
    private Button previousButton;

    String currentString;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        weekCalendar = view.findViewById(R.id.weekCalendar);
        nextButton = view.findViewById(R.id.nextButton);
        previousButton = view.findViewById(R.id.previousButton);

        setListener();
        initCalendar();

        return view;
    }

    private void setListener() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekCalendar.moveToNextWeek();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekCalendar.moveToPreviousWeek();
            }
        });

    }

    private void initCalendar() {
        DateTime currentDate = DateTime.now();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(currentDate.toString("yyyyMMdd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

//        val dateCount = calendar.get(Calendar.DAY_OF_WEEK)
//        if (dateCount > 3) {
//            middleDate = currentDate!!.plusDays(-(dateCount - 3 - 1))
//        }
//        if (dateCount < 3) {
//            middleDate = currentDate!!.plusDays(1 + 3 - dateCount)
//        }
//
//        YfyShopLog.d("DATE", "middle Date:" + middleDate!!.toString("yyyyMMdd")
//        )
//
//        currentMonText!!.text = currentDate!!.toString("yyyy 年 MM 月")


        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {

            }
        });

        weekCalendar.setOnWeekChangeListener(new OnWeekChangeListener() {
            @Override
            public void onWeekChange(DateTime middleDayOfTheWeek, boolean forward) {
                String text = "week change middle date:" + middleDayOfTheWeek.toString("yyyy 年 MM 月 dd 日");
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
