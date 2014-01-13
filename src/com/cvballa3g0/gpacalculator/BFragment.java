package com.cvballa3g0.gpacalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class BFragment extends SherlockFragment {
    public static ArrayAdapter<CharSequence> gradeAdapter;
    public static ArrayAdapter<CharSequence> levelAdapter;
    public static String GPA = "0.00";
    public static Spinner[] HSGradeSpinner = new Spinner[8];
    public static Spinner[] HSLevelSpinner = new Spinner[8];
    public static TextView gpaTextView;
    public static CheckBox unweighted;

    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater
                .inflate(R.layout.bfragment, container, false);

        gpaTextView = (TextView) view.findViewById(R.id.highSchoolGPA);
        gpaTextView.setText(GPA);

        gradeAdapter = ArrayAdapter.createFromResource(getSherlockActivity(),
                R.array.grades, android.R.layout.simple_spinner_item);
        levelAdapter = ArrayAdapter.createFromResource(getSherlockActivity(),
                R.array.classlevel, android.R.layout.simple_spinner_item);

        gradeAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int startID = 2130968656;
        for (int i = 0; i < 8; i++) {
            HSGradeSpinner[i] = (Spinner) view.findViewById(startID);
            HSGradeSpinner[i].setAdapter(gradeAdapter);
            startID++;
            HSLevelSpinner[i] = (Spinner) view.findViewById(startID);
            HSLevelSpinner[i].setAdapter(levelAdapter);
            startID++;
        }

        unweighted = (CheckBox) view.findViewById(R.id.unweighted);

        Button HSCalculatorButton = (Button) view
                .findViewById(R.id.HSCalculateButton);
        HSCalculatorButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateGPA();
            }
        });
        return view;
    }

    public static double totalValue = 0;
    public static double totalClasses = 0;

    public static void calculateGPA() {
        totalClasses = 0;
        totalValue = 0;
        if (unweighted.isChecked()) {
            for (int i = 0; i < 8; i++) {
                final int grade = HSGradeSpinner[i].getSelectedItemPosition();
                if (grade == 0) {

                } else {
                    switch (grade) {
                    case (1):
                        totalValue += 4;
                        totalClasses++;
                        break;
                    case (2):
                        totalValue += 3;
                        totalClasses++;
                        break;
                    case (3):
                        totalValue += 2;
                        totalClasses++;
                        break;
                    case (4):
                        totalValue += 1;
                        totalClasses++;
                        break;
                    case (5):
                        totalClasses++;
                        break;
                    default:
                        break;
                    }
                }
            }
        } else if (!unweighted.isChecked()) {
            for (int i = 0; i < 8; i++) {
                final int grade = HSGradeSpinner[i].getSelectedItemPosition();
                final int level = HSLevelSpinner[i].getSelectedItemPosition();

                if (grade == 0 || level == 0) {

                } else {

                    switch (level) {
                    case (2):
                        totalValue += 0.5;
                        break;
                    case (3):
                        totalValue += 1;
                        break;
                    default:
                        break;
                    }
                    switch (grade) {
                    case (1):
                        totalValue += 4;
                        totalClasses++;
                        break;
                    case (2):
                        totalValue += 3;
                        totalClasses++;
                        break;
                    case (3):
                        totalValue += 2;
                        totalClasses++;
                        break;
                    case (4):
                        totalValue += 1;
                        totalClasses++;
                        break;
                    case (5):
                        totalClasses++;
                        break;
                    default:
                        break;
                    }
                }
            }
        }

        GPA = String.format("%.2f", (totalValue / totalClasses));
        if (GPA.equals("NaN")) {
            GPA = "0.00";
        } else {
            gpaTextView.setText(GPA);
        }
    }

    public static void reset() {
        for (int i = 0; i < 8; i++) {
            HSGradeSpinner[i].setAdapter(gradeAdapter);
            HSLevelSpinner[i].setAdapter(levelAdapter);
            gpaTextView.setText("0.00");
        }
    }
}
