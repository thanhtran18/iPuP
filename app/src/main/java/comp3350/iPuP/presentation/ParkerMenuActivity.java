package comp3350.iPuP.presentation;

import comp3350.iPuP.R;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ParkerMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parker_menu);
    }
    public void buttonCreateOnClick(View v)
    {
        Intent parkerMenuIntent = new Intent(ParkerMenuActivity.this, ParkerSearchActivity.class);
        ParkerMenuActivity.this.startActivity(parkerMenuIntent);
    }
}

