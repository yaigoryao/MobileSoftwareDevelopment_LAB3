package ru.msfd.lab3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.strictmode.SetUserVisibleHintViolation;

import java.util.Random;

public class ResultActivity extends AppCompatActivity {

    String text = null;
    int color = -1;
    int layoutNumber = 1;
    TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Setup();
        EdgeToEdge.enable(this);
//        int layoutId = 1;
//        switch(layoutNumber)
//        {
//            case 0: layoutId = R.layout.layout1; break;
//            case 1: layoutId = R.layout.layout2; break;
//            case 2: layoutId = R.layout.layout3; break;
//            default: break;
//        }
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SetupTextView();
    }

    private void Setup()
    {
        Intent intent = getIntent();
        text = intent.getStringExtra("text");
        color = intent.getIntExtra("color", -1);
        layoutNumber = intent.getIntExtra("layoutNumber", 1);
        Toast.makeText(this, "Recieved: \ntext: " + text + "\ncolor: " + color + "\nlayoutNumber: " + layoutNumber, Toast.LENGTH_LONG).show();
    }

    private void SetupTextView()
    {
        Integer[] textViewsIds = { R.id.first_layout_text_view, R.id.second_layout_text_view, R.id.third_layout_text_view };
        Integer[] layoutsIds = { R.id.first_layout, R.id.second_layout, R.id.third_layout };
        ((LinearLayout) findViewById(layoutsIds[layoutNumber])).setVisibility(View.VISIBLE);
        textView = (TextView) findViewById(textViewsIds[layoutNumber]);
        textView.setText(text);
        if(color == -1)
        {
            Random random = new Random();
            textView.setTextColor(Color.rgb(random.nextInt(256),
                                            random.nextInt(256),
                                            random.nextInt(256) ));
        }
        else textView.setTextColor(color);
    }
}