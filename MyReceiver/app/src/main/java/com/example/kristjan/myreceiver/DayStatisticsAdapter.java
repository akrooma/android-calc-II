package com.example.kristjan.myreceiver;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Kristjan on 12/04/2016.
 */
public class DayStatisticsAdapter extends CursorAdapter {
    private final LayoutInflater layoutInflater;
    private UOW uow;
    private ViewGroup parentViewGroup;

    public DayStatisticsAdapter(Context context, Cursor cursor, UOW uow) {
        super(context, cursor, 0);
        layoutInflater = LayoutInflater.from(context);
        this.uow = uow;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view=layoutInflater.inflate(R.layout.day_statistics, parent, false);
        parentViewGroup = parent;
        return view;
    }

    // this can be called several times by the system!!!
    // first pass - initial draw, get measurements
    // second pass - final draw
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView =(TextView) view.findViewById(R.id.dayStatistic);

        DayStatistic dayStatistic = uow.dayStatisticRepo.cursorToEntity(cursor);
        //OperationType operationType = uow.operationTypeRepo.getById(dayStatistic.getOperandId());
        //textView.setText(dayStatistic.toString(operationType.getOperand()));

        //DayStatistic dayStatistic = uow.dayStatisticRepo.dateCursorToEntity(cursor);
        textView.setText("Date: " + DateUtil.dateStampToString(dayStatistic.getDateStamp()));
        displayOperationTypes(view, dayStatistic);
    }

    // datestamps aren't unique by date so the following method is useless.
    // apparently selecting table rows based on a column that's supposed to be unique(datestamp / dateinmillis) is a bit too crazy.


    public void displayOperationTypes(View view, DayStatistic dayStatistic) {
        // get the contactsListView
        LinearLayout dayStatisticInfoListView = (LinearLayout) view.findViewById(R.id.dayStatisticListView);

        // if this gets called multiple times, first clean all up
        // otherwise you will add same childs several times
        dayStatisticInfoListView.removeAllViews();

        for (DayStatistic dayStat :
                uow.dayStatisticRepo.getForSpecificDate(dayStatistic.getDateStamp())) {

            // load the xml structure of your row
            View child = layoutInflater.inflate(R.layout.day_statistics_info, parentViewGroup, false);

            TextView textViewContactValue =(TextView) child.findViewById(R.id.dayStatisticInfo);

            textViewContactValue.setText("Operation: ' " + uow.operationTypeRepo.getById(dayStat.getOperandId()).getOperand()
                    + " ' occurrences: " + dayStat.getDayCounter());

            dayStatisticInfoListView.addView(child);
        }
    }

}
