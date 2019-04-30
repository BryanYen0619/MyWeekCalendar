package bryanyen.myweekcalendar.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import bryanyen.myweekcalendar.eventbus.BusProvider;
import bryanyen.myweekcalendar.eventbus.Event;
import bryanyen.myweekcalendar.fragment.WeekFragment;

import static bryanyen.myweekcalendar.fragment.WeekFragment.DATE_KEY;
import static bryanyen.myweekcalendar.fragment.WeekFragment.DATE_MONTH_MODE;
import static bryanyen.myweekcalendar.fragment.WeekFragment.DATE_NOTE_DOT;
import static bryanyen.myweekcalendar.view.WeekPager.NUM_OF_PAGES;

/**
 * Created by nor on 12/4/2015.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "PagerAdapter";
    private int currentPage = NUM_OF_PAGES / 2;
    private DateTime date;
    private boolean isNoteDot = false;
    private boolean isMonthMode = false;
    private int limit;

    public PagerAdapter(FragmentManager fm, DateTime date, boolean isNoteDot, boolean isMonthMode) {
        super(fm);
        this.date = date;
        this.isNoteDot = isNoteDot;
        this.isMonthMode = isMonthMode;
    }

    @Override
    public Fragment getItem(int position) {
        if(isMonthMode) {
            limit = 30;
        } else {
            limit = 7;
        }

        WeekFragment fragment = new WeekFragment();
        Bundle bundle = new Bundle();

        if (position < currentPage)
            bundle.putSerializable(DATE_KEY, getPerviousDate());
        else if (position > currentPage)
            bundle.putSerializable(DATE_KEY, getNextDate());
        else
            bundle.putSerializable(DATE_KEY, getTodaysDate());

        bundle.putBoolean(DATE_NOTE_DOT, isNoteDot);
        bundle.putBoolean(DATE_MONTH_MODE, isMonthMode);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_OF_PAGES;
    }

    private DateTime getTodaysDate() {
        return date;
    }

    private DateTime getPerviousDate() {
        return date.plusDays(-limit);
    }

    private DateTime getNextDate() {
        return date.plusDays(limit);
    }

    @Override
    public int getItemPosition(Object object) {
        //Force rerendering so the week is drawn again when you return to the view after
        // back button press.
        return POSITION_NONE;
    }

    public void swipeBack() {
        date = date.plusDays(-limit);
        currentPage--;
        currentPage = currentPage <= 1 ? NUM_OF_PAGES / 2 : currentPage;
        BusProvider.getInstance().post(
                new Event.OnWeekChange(date.withDayOfWeek(DateTimeConstants.MONDAY),
                        date.withDayOfWeek(DateTimeConstants.WEDNESDAY), false));
    }


    public void swipeForward() {
        date = date.plusDays(limit);
        currentPage++;
        currentPage = currentPage >= NUM_OF_PAGES - 1 ? NUM_OF_PAGES / 2 : currentPage;
        BusProvider.getInstance().post(
                new Event.OnWeekChange(date.withDayOfWeek(DateTimeConstants.MONDAY),
                        date.withDayOfWeek(DateTimeConstants.WEDNESDAY), true));
    }

   /* public DateTime getDate() {
        return date;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
*/

}
