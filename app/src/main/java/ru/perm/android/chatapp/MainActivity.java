package ru.perm.android.chatapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("messages");

    EditText message_edit_text;
    Button send_message_button;
    RecyclerView messages_recyclerview;

    ArrayList<String> messages;
    DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messages = new ArrayList<>();
        adapter = new DataAdapter(this, messages);

        message_edit_text = findViewById(R.id.message_edit_text);
        send_message_button = findViewById(R.id.send_message_button);
        messages_recyclerview = findViewById(R.id.messages_recyclerview);

        messages_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        messages_recyclerview.setAdapter(adapter);

        send_message_button.setOnClickListener(onClickListener);
        databaseReference.addChildEventListener(childEventListener);
    }

    /// ------------------------ Listeners -----------------------------
    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            String msg = snapshot.getValue(String.class);
            messages.add(msg);
            adapter.notifyDataSetChanged();
            messages_recyclerview.smoothScrollToPosition(messages.size());
        }
        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }
        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }
        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    View.OnClickListener onClickListener = (view) -> {
            String msg = message_edit_text.getText()
                    .toString().trim();

            if (msg.equals("")) {
                Toast.makeText(getApplicationContext(),
                        "Введите сообщение!",
                        Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            databaseReference.push().setValue(msg);
            message_edit_text.setText("");
    };
}