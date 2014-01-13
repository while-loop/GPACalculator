package com.cvballa3g0.gpacalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class AFragment extends SherlockFragment {
    public static ArrayAdapter<CharSequence> gradeAdapter;
    public static ArrayAdapter<CharSequence> hourAdapter;
    public static String GPA = "0.00";
    public static Spinner[] collegeGradeSpinner = new Spinner[8];
    public static Spinner[] collegeHourSpinner = new Spinner[8];
    public static TextView gpaTextView;

    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater
                .inflate(R.layout.afragment, container, false);

        gpaTextView = (TextView) view.findViewById(R.id.collegeGPA);
        gpaTextView.setText(GPA);

        gradeAdapter = ArrayAdapter.createFromResource(getSherlockActivity(),
                R.array.grades, android.R.layout.simple_spinner_item);
        hourAdapter = ArrayAdapter.createFromResource(getSherlockActivity(),
                R.array.credithours, android.R.layout.simple_spinner_item);

        gradeAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int startID = 2130968632 ;

        for (int i = 0; i < 8; i++) {
            collegeGradeSpinner[i] = (Spinner) view.findViewById(startID);
            collegeGradeSpinner[i].setAdapter(gradeAdapter);
            startID++;
            collegeHourSpinner[i] = (Spinner) view.findViewById(startID);
            collegeHourSpinner[i].setAdapter(hourAdapter);
            startID++;
        }

        Button collegeCalculatorButton = (Button) view
                .findViewById(R.id.collegeCalculateButton);
        collegeCalculatorButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculateGPA();
            }
        });
        return view;
    }

    public static double totalValue = 0;
    public static double totalHours = 0;

    public static void calculateGPA() {

        totalHours = 0;
        totalValue = 0;
        for (int i = 0; i < 8; i++) {
            final int grade = collegeGradeSpinner[i].getSelectedItemPosition();
            final int hours = collegeHourSpinner[i].getSelectedItemPosition();

            if (grade == 0 || hours == 0) {

            } else {
                switch (grade) {
                case (1):
                    totalValue += 4 * hours;
                    totalHours += hours;
                    break;
                case (2):
                    totalValue += 3 * hours;
                    totalHours += hours;
                    break;
                case (3):
                    totalValue += 2 * hours;
                    totalHours += hours;
                    break;
                case (4):
                    totalValue += 1 * hours;
                    totalHours += hours;
                    break;
                case (5):
                    totalHours += hours;
                    break;
                default:
                    break;
                }
            }
        }

        GPA = String.format("%.2f", (totalValue / totalHours));
        if (GPA.equals("NaN")) {
            GPA = "0.00";
        } else {
            gpaTextView.setText(GPA);
        }

    }

    public static void reset() {
        for (int i = 0; i < 8; i++) {
            collegeGradeSpinner[i].setAdapter(gradeAdapter);
            collegeHourSpinner[i].setAdapter(hourAdapter);
            gpaTextView.setText("0.00");
        }
    }
}
