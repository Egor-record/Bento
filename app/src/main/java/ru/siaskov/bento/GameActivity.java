package ru.siaskov.bento;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;


public class GameActivity extends FragmentActivity {


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_game);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
        if ( bundle.get("id") != null) {

                String firstName = bundle.getString("id");

                System.out.println(firstName);

            }
        }

    }




}
