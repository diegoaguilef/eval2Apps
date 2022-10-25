package com.diego.eval2apps;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.diego.eval2apps.models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerCourses, spinnerDialogCourses;
    private EditText edtRut, edtName, edtEmail, edtDialogName, edtDialogEmail;
    private Button btnAddStudent, btnUpdateStudent, btnDeleteStudent;
    private List<Student> students;
    private ListView listViewStudents;
    private DatabaseReference studentsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Ingreso de Alumnos");
        spinnerCourses = findViewById(R.id.spinner);
        edtRut = findViewById(R.id.edtRut);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        btnAddStudent = findViewById(R.id.btnAddStudent);
        studentsDB = FirebaseDatabase.getInstance().getReference("students");
        listViewStudents = findViewById(R.id.listViewStudents);
        students = new ArrayList<>();

        btnAddStudent.setOnClickListener(view -> {
            String rut = edtRut.getText().toString();
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();
            String course = (String) spinnerCourses.getSelectedItem();

            if(!(rut.isEmpty() || name.isEmpty() || email.isEmpty())){
                Student student = new Student(rut, name, email, course);
                try {
                    if (!addStudent(student)) {
                        Toast.makeText(this, "Rut ya existe", Toast.LENGTH_LONG).show();
                    }
                }catch (DatabaseException e){
                    Toast.makeText(this, "Error de servidor, intente mas tarde", Toast.LENGTH_LONG).show();
                    //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                    System.out.println(e);
                }
            }else {
                Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_LONG).show();
            }
        });
        listViewStudents.setOnItemClickListener((adapterView, view, i, l) -> {
            Student student = students.get(i);
            Intent intent = new Intent(this, ScoresRegisterActivity.class);
            intent.putExtra("rut", student.getRut());
            intent.putExtra("name", student.getName());
            startActivity(intent);
        });

        listViewStudents.setOnItemLongClickListener((adapterView, view, i, l) -> {
            // adapterView = la vista que vamos a mostrar en el menu
            // view = es la vista donde lo vamos a mostrar
            // i = es la posicion del item
            // l = es la duracion del click
            Student student = students.get(i);
            showStudentDialog(student);
            return true;
        });
    }

    private boolean addStudent(Student student) {
        for(Student s : students){
            if(s.getRut().equals(student.getRut())) return false;
        }
        studentsDB.child(student.getRut()).setValue(student);
        return true;
    }

    private void updateStudent(Student student) {

        studentsDB.child(student.getRut()).child("name").setValue(student.getName());
        studentsDB.child(student.getRut()).child("email").setValue(student.getEmail());
        studentsDB.child(student.getRut()).child("course").setValue(student.getCourse());
        Toast.makeText(this, "Estudiante Actualizado", Toast.LENGTH_LONG).show();
    }

    private void deleteStudent(String rut) {
        studentsDB.child(rut).removeValue();
        Toast.makeText(this, "Estudiante Eliminado", Toast.LENGTH_LONG).show();
    }

    private void showStudentDialog(final Student student) {
        //Instancio una Alerta vacia
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        //lleno o inflo la vista con un layout
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.student_menu_dialog, null);
        // lleno la alerta con la vista dialogo
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(student.getRut());

        edtDialogName = dialogView.findViewById(R.id.edtDialogName);
        edtDialogEmail = dialogView.findViewById(R.id.edtDialogEmail);
        spinnerDialogCourses = dialogView.findViewById(R.id.spinnerDialogCourses);
        ArrayAdapter<String> spinnerAdapter = (ArrayAdapter) spinnerDialogCourses.getAdapter();
        btnUpdateStudent = dialogView.findViewById(R.id.btnUpdateStudent);
        btnDeleteStudent = dialogView.findViewById(R.id.btnDeleteStudent);

        edtDialogName.setText(student.getName());
        edtDialogEmail.setText(student.getEmail());

        spinnerDialogCourses.setSelection(spinnerAdapter.getPosition(student.getCourse()));
        //Create el dialogo
        final AlertDialog aDialog = dialogBuilder.create();
        //muestra el dialogo
        aDialog.show();
        //Agrega los listeners a los botones
        btnUpdateStudent.setOnClickListener(view -> {
            String newName   = edtDialogName.getText().toString().trim();
            String newEmail  = edtDialogEmail.getText().toString();
            String newCourse = (String) spinnerDialogCourses.getSelectedItem();
            if(!(newName.isEmpty() || newEmail.isEmpty())){
                student.setName(newName);
                student.setEmail(newEmail);
                student.setCourse(newCourse);
                try {
                    updateStudent(student);
                    aDialog.dismiss();
                }catch (DatabaseException e){
                    Toast.makeText(this, "Error de servidor, intente mas tarde", Toast.LENGTH_LONG).show();
                    //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                    System.out.println(e);
                }
            }else {
                Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_LONG).show();
            }
        });

        btnDeleteStudent.setOnClickListener(view -> {
            deleteStudent(student.getRut());
            aDialog.dismiss();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        studentsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                students.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Student student = ds.getValue(Student.class);
                    students.add(student);
                }
                StudentsListAdapter studentsListAdapter = new StudentsListAdapter(MainActivity.this, students);
                listViewStudents.setAdapter(studentsListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }

}