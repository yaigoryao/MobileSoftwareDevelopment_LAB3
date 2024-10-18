package ru.msfd.lab3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editText = null;
    Button button = null;
    RadioGroup colorsRadioGroup = null;
    List<Integer> toggleIds = null;
    Map<Integer, Integer> idsColors = null;
    LinearLayout layoutsColumn = null;
    int selectedColor = -1;
    int selectedToggleId = -1;
    int layoutNumber = 1;

    final static int TOTAL_LAYOUTS = 3;
    int[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, -1 };
    Map<Integer, String> colorsLabels = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Setup();
    }

    private void Setup()
    {
        editText = (EditText) findViewById(R.id.edit_text_id);
        button = (Button) findViewById(R.id.button_id);
        colorsRadioGroup = (RadioGroup) findViewById(R.id.colors_group_id);
        layoutsColumn = (LinearLayout) findViewById(R.id.layouts_column_id);

        toggleIds = new ArrayList<>();
        idsColors = new HashMap<>();
        colorsLabels = new HashMap<>();

        //for(int i = 0; i < TOTAL_LAYOUTS; i++) checkedToggle.put(View.generateViewId(), false);
        for(int i = 0; i < TOTAL_LAYOUTS; i++) toggleIds.add(View.generateViewId());

        SetupLabels();
        SetupToggleButtons();
        SetupRadioButtons();
        SetupButton();
    }

    private void SetupButton()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String log = "Send: \ntext: " + editText.getText().toString() + "\ncolor: " + selectedColor + "\nlayoutNumber: " + layoutNumber;
                Toast.makeText(MainActivity.this, log, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("text", editText.getText().toString());
                intent.putExtra("color", selectedColor);
                intent.putExtra("layoutNumber", layoutNumber);
                startActivity(intent);
            }
        });
    }

    private void SetupLabels()
    {
        if(colorsLabels != null)
        {
            colorsLabels.put(Color.RED, getString(R.string.red_label));
            colorsLabels.put(Color.GREEN, getString(R.string.green_label));
            colorsLabels.put(Color.BLUE, getString(R.string.blue_label));
            colorsLabels.put(Color.YELLOW, getString(R.string.yellow_label));
            colorsLabels.put(-1, getString(R.string.random_color_label));

        }
    }

    private void SetupToggleButtons()
    {
        ToggleButton toggleButton;
        View.OnClickListener toggleButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleButton toggle = (ToggleButton) view;
                boolean state = toggle.isChecked();
                for(int id : toggleIds)
                {
                   ((ToggleButton) findViewById(id)).setChecked(false);
                }
                if(!state)
                {
                    ((ToggleButton) findViewById(toggleIds.get(0))).setChecked(true);
                    selectedToggleId = toggleIds.get(0);
                    layoutNumber = 0;
                }
                else
                {
                    toggle.setChecked(true);
                    selectedToggleId = toggle.getId();
                    layoutNumber = toggleIds.indexOf(selectedToggleId);
                }
            }
        };

        String baseText = "Layout ";
        int layoutNumber = 1;
        if(toggleIds != null)
        {
            for (int id : toggleIds)
            {
                toggleButton = new ToggleButton(this);
                toggleButton.setId(id);
                toggleButton.setText(baseText + layoutNumber);
                toggleButton.setTextOn(baseText + layoutNumber + " ON");
                toggleButton.setTextOff(baseText + layoutNumber + " OFF");
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                int marginInPixels = (int) (16 * getResources().getDisplayMetrics().density);
                layoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
                layoutParams.gravity = Gravity.CENTER;

                toggleButton.setLayoutParams(layoutParams);
                toggleButton.setOnClickListener(toggleButtonOnClickListener);
                layoutsColumn.addView(toggleButton);
                layoutNumber++;
            }
        }
    }

    private void SetupRadioButtons()
    {
        RadioButton radioButton;

        int id;
        for(int color : colors)
        {
            radioButton = new RadioButton(this);
            id = View.generateViewId();
            idsColors.put(id, color);
            radioButton.setId(id);

            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            int marginInPixels = (int) (16 * getResources().getDisplayMetrics().density);
            layoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
            layoutParams.gravity = Gravity.CENTER;

            radioButton.setLayoutParams(layoutParams);
            if(colorsLabels != null) radioButton.setText(colorsLabels.get(color));
            colorsRadioGroup.addView(radioButton);
        }
        colorsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(idsColors != null) selectedColor = idsColors.getOrDefault(i, -1);
                else selectedColor = -1;
            }
        });
    }
}