package com.diego.eval2apps;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptIntrinsic;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.diego.eval2apps.models.Score;
import com.diego.eval2apps.models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.zip.Inflater;

public class ScoresRegisterActivity extends AppCompatActivity {
    String studentName, studentRut;
    DatabaseReference scoresDB;
    EditText edtScore;
    TextView txtAverage;
    Button btnAddScore;
    ListView listViewScores;
    List<Score> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores_register);
        Intent intent = getIntent();
        studentName = intent.getStringExtra("name");
        studentRut = intent.getStringExtra("rut");

        setTitle("Ingreso de notas " + studentName);
        btnAddScore = findViewById(R.id.btnAddScore);
        edtScore = findViewById(R.id.edtScore);
        txtAverage = findViewById(R.id.txtAverage);
        listViewScores = findViewById(R.id.listViewScores);
        scores = new ArrayList<>();
        scoresDB = FirebaseDatabase.getInstance().getReference("students").child(studentRut).child("scores");

        btnAddScore.setOnClickListener(view -> {
            if (!edtScore.getText().toString().isEmpty()) {
                float scorePoint = Float.parseFloat(edtScore.getText().toString());
                SimpleTimeZone.setDefault(TimeZone.getTimeZone("America/Santiago"));
                Date evalDate = Calendar.getInstance().getTime();
                Score score = new Score(evalDate, scorePoint, studentRut);
                try {
                    addScore(score);
                } catch (DatabaseException e) {
                    Toast.makeText(this, "Error de servidor, intente mas tarde", Toast.LENGTH_LONG).show();
                    System.out.println(e);
                }
            } else {
                Toast.makeText(this, "Debe ingresar una hota", Toast.LENGTH_LONG).show();
            }

        });

    }

    private boolean addScore(Score score) {
        for (Score s : scores) {
            if (s.getId() == score.getId()) return false;
        }
        score.setId(scores.size() + 1);
        scoresDB.child(String.valueOf(score.getId())).setValue(score);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        scoresDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scores.clear();
                float average = 0, partial = 0;
                int count = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Score score = ds.getValue(Score.class);
                    scores.add(score);
                    partial += score.getScore();
                    count++;
                }
                average = (partial / count);
                if(count == 0){
                    average = 0;
                }
                ScoresListAdapter scoresListAdapter = new ScoresListAdapter(ScoresRegisterActivity.this, scores);
                listViewScores.setAdapter(scoresListAdapter);
                txtAverage.setText("Promedio de notas: " + average);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }
}