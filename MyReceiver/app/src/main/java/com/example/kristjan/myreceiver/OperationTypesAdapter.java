package com.example.kristjan.myreceiver;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Kristjan on 12/04/2016.
 */
public class OperationTypesAdapter extends CursorAdapter {
    private final LayoutInflater layoutInflater;
    private UOW uow;
    private ViewGroup parentViewGroup;

    public OperationTypesAdapter(Context context, Cursor cursor, UOW uow) {
        super(context, cursor, 0);
        layoutInflater = LayoutInflater.from(context);
        this.uow = uow;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view=layoutInflater.inflate(R.layout.operation_types, parent, false);
        parentViewGroup = parent;
        return view;
    }

    // this can be called several times by the system!!!
    // first pass - initial draw, get measurements
    // second pass - final draw
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView =(TextView) view.findViewById(R.id.operation_type);

        OperationType operationType = uow.operationTypeRepo.cursorToEntity(cursor);
        
        textView.setText("Operation " + operationType.getOperand() + " done " + operationType.getLifetimeCounter() + " times.");
    }
}
