package clink.youparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;

public class HoldSpotProblemActivity extends AppCompatActivity implements AsyncResponse {

    RadioGroup radioGroup1, radioGroup2;
    RadioButton rb1, rb2;
    String transactionID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hold_spot_problem);

        Bundle extras = getIntent().getExtras();
        transactionID = extras.getString("transID");

        radioGroup1 = (RadioGroup)findViewById(R.id.question1Holdgroup);
        radioGroup1.clearCheck();
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb1 = (RadioButton) group.findViewById(checkedId);
                if(rb1 != null && checkedId > -1){
                    Toast.makeText(getApplicationContext(), "You clicked " + rb1.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        radioGroup2 = (RadioGroup)findViewById(R.id.question2Holdgroup);
        radioGroup2.clearCheck();
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb2 = (RadioButton) group.findViewById(checkedId);
                if(rb2 != null && checkedId > -1){
                    Toast.makeText(getApplicationContext(), "You clicked " + rb2.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void goToMainFromHold(View view)
    {
        EditText commentsField = (EditText)findViewById(R.id.holdProblemComments);
        String comments = commentsField.getText().toString();

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.delegate = this;
        backgroundWorker.execute("saveProblems", transactionID, comments);

        Toast.makeText(this, "Thank you for your input!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void processFinish(String output) throws JSONException {
        if (output.contains("Error")) {
            Toast.makeText(this, output, Toast.LENGTH_LONG).show();
        }
    }
}
