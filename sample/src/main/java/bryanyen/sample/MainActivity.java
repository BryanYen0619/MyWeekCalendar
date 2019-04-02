package bryanyen.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bryanyen.myweekcalendar.WeekCalendar;
import bryanyen.myweekcalendar.listener.OnDateClickListener;
import bryanyen.myweekcalendar.listener.OnWeekChangeListener;

public class MainActivity extends AppCompatActivity {
    private WeekCalendar weekCalendar;
private TextView currentMonText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
//        Button todaysDate = (Button) findViewById(R.id.today);
//        Button selectedDate = (Button) findViewById(R.id.selectedDateButton);
//        Button startDate = (Button) findViewById(R.id.startDate);
//        todaysDate.setText(new DateTime().toLocalDate().toString() + " (Reset Button)");
//        selectedDate.setText(new DateTime().plusDays(50).toLocalDate().toString()
//                + " (Set Selected Date Button)");
//        startDate.setText(new DateTime().plusDays(7).toLocalDate().toString()
//                + " (Set Start Date Button)");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        currentMonText = (TextView) findViewById(R.id.currentMonText);
        currentMonText.setText(String.valueOf(year)+"年"+ String.valueOf(month)+"月");

        weekCalendar = (WeekCalendar) findViewById(R.id.weekCalendar);
        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                Toast.makeText(MainActivity.this, "You Selected " + dateTime.toString(), Toast
                        .LENGTH_SHORT).show();
            }

        });
        weekCalendar.setOnWeekChangeListener(new OnWeekChangeListener() {
            @Override
            public void onWeekChange(DateTime middleDayOfTheWeek, boolean forward) {
                currentMonText.setText(middleDayOfTheWeek.getYear() + "年" +middleDayOfTheWeek.getMonthOfYear()+"月");
            }
        });

        List<DateTime> dayNoteDot = new ArrayList<>();

        DateTime test = new DateTime();
        dayNoteDot.add(test);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_github){
            openGithubRepo();
        }
        return true;
    }

    private void openGithubRepo() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/nomanr/WeekCalendar"));
        startActivity(intent);
    }

    public void onNextClick(View veiw) {
        weekCalendar.moveToNextWeek();
    }


    public void onPreviousClick(View view) {
        weekCalendar.moveToPreviousWeek();
    }

    public void onResetClick(View view) {
        weekCalendar.reset();

    }
    public void onSelectedDateClick(View view){
        weekCalendar.setSelectedDate(new DateTime().plusDays(50));
    }
    public void onStartDateClick(View view){
        weekCalendar.setStartDate(new DateTime().plusDays(7));
    }
}
