package com.example.immersiveeducationfinal.DBHelper;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.immersiveeducationfinal.Interface.MyCallback;
import com.example.immersiveeducationfinal.Model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;

import java.util.ArrayList;
import java.util.List;

public class OnlineDBHelper {

    FirebaseDatabase firebaseDatabase;
    Context context;

    DatabaseReference reference;

    public OnlineDBHelper(Context context,FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
        this.context = context;
        reference = this.firebaseDatabase.getReference("Immersive Education");
    }

    private static OnlineDBHelper instance;

    public static synchronized  OnlineDBHelper getInstance(Context context, FirebaseDatabase firebaseDatabase)
    {
        if(instance == null)
            instance = new OnlineDBHelper(context,firebaseDatabase);
        return instance;
    }

    public void readData(final MyCallback myCallback, String category)
    {
        final AlertDialog dialog = new SpotsDialog.Builder()
                .setContext(context)
                .setCancelable(false)
                .build();

        if(!dialog.isShowing())
            dialog.show();

        reference.child(category)
                .child("question")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Question> questionList = new ArrayList<>();
                        for(DataSnapshot questionSnapShot:dataSnapshot.getChildren())
                            questionList.add(questionSnapShot.getValue(Question.class));
                        myCallback.setQuestionList(questionList);

                        if(dialog.isShowing())
                            dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(context, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}