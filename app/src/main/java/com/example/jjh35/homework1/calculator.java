package com.example.jjh35.homework1;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import java.util.Map;

/** Jesse Hulse. jjh35 cs262. Homework 1. Sept. 29.
 * This is a basic calculator app.
 */
public class calculator extends AppCompatActivity {

    private Spinner spinner;
    private SharedPreferences savedValues;
    int operand1 = 1;
    int operand2 = 1;
    String operator = "";

    /**
     * OnCreate is the i
     */
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_calculator );

        String[] operators = { "+", "-", "*", "÷" };
        ArrayAdapter <String> stringArrayAdapter = new ArrayAdapter <String> (
                this,
                android.R.layout.simple_spinner_dropdown_item,
                operators
        );
        spinner = ( Spinner ) findViewById( R.id.operator );
        spinner.setAdapter( stringArrayAdapter );
        savedValues = getSharedPreferences( "SavedValues", MODE_PRIVATE );
    }

    /**
     * This function is called when the user hits the calculate button.
     * It will caculate the result and display it to the user.
     * If a field is left empty, this function will notify the user.
     *
     * @param v
     * The view of the activity
     */
    protected void calculateResult( View v )
    {
        double result = 0.0;
        //find the text fields
        EditText operandField1 = ( EditText ) findViewById( R.id.operand1 );
        EditText operandField2 = ( EditText ) findViewById( R.id.operand2 );
        TextView resultField = ( TextView ) findViewById( R.id.result );
        String test = spinner.getSelectedItem().toString();

       //if a field is left empty this will catch it and notify the user via a toast
        if ( operandField1.getText().toString().equals( "" ) || operandField2.getText().toString().equals( "" ) )
        {
            Toast.makeText( this, "Please enter in values", Toast.LENGTH_LONG ).show();
            return;
        }
        //get the values
        operand1 =  Integer.parseInt( operandField1.getText().toString() );
        operand2 =  Integer.parseInt( operandField2.getText().toString() );

        //calculate the result
        switch ( spinner.getSelectedItem().toString() )
        {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "÷":
                //stop a divide by zero error
                if ( operand2 == 0 )
                {
                    Toast.makeText( this, "Divide by zero error", Toast.LENGTH_LONG ).show();
                    return;
                }
                result = operand1 / operand2;
                break;
        }
        //print the results
        resultField.setText( Double.toString(result) );
    }

    /**
     * This function will save the values in the app losses focus
     */
    @Override
    public void onPause()
    {
        Editor editor = savedValues.edit();
        editor.putInt( "operand1", operand1 );
        editor.putInt( "operand2", operand2 );
        editor.putString( "operator", operator );
        editor.commit();
        super.onPause();
    }

    /**
     * This function will restore the values once the app activity is resumed
     */
    @Override
    public void onResume()
    {
        super.onResume();
        operand1 = savedValues.getInt( "operand1", 1 );
        operand2 = savedValues.getInt( "operand2", 1 );
        operator = savedValues.getString( "operator", "+" );
    }
}
