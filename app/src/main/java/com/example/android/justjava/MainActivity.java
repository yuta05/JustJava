package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;

import static android.R.attr.name;
import static android.R.id.message;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int numberOfCoffees=0;
    //boolean hasWhippedCream = false;
    //boolean hasChocolate = false;
    int price=0;

    public void submitOrder(View view) {
        EditText nameOfCustomerEditText = (EditText) findViewById(R.id.name_edit);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();
        String nameOfCustomer = nameOfCustomerEditText.getText().toString();
        price =calculatePrice(hasWhippedCream,hasChocolate);

        String summary = createOrderSummary(nameOfCustomer, price, hasWhippedCream, hasChocolate);
        String subject = "Just Java order for "+nameOfCustomer;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



    public void increment(View view) {
        if(numberOfCoffees==100){
            return;
        }
        numberOfCoffees++;
        display(numberOfCoffees);
    }

    public void decrement(View view) {
        if(numberOfCoffees==0){
            return;
        }
        numberOfCoffees--;
        display(numberOfCoffees);

    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int priceOfPerCup = 5;
        //int priceOfWhippedCream =1;
        //int priceOfChocolate =2;
        if(addWhippedCream==true){
            priceOfPerCup+=1;
        }
        if(addChocolate==true){
            priceOfPerCup+=2;
        }
        price = numberOfCoffees*priceOfPerCup;

        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        return "Name: " + name +
                "\nAdd Whipped Cream?: "+addWhippedCream+
                "\nAdd Chocolate?: "+addChocolate+
                "\nQuantity: "+numberOfCoffees+"" +
                "\nTotal: $"+price+"" +
                "\nThank you";

    }
}