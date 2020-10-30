package com.solutionplanets.navkar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SummaryList extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference summaryRef = db.collection("JaapSummary");
    private SummaryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        Query query = summaryRef.orderBy("date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Summary> options = new FirestoreRecyclerOptions.Builder<Summary>()
                .setQuery(query, Summary.class)
                .build();
        adapter = new SummaryAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.jaapListRView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.summary:
                startActivity(new Intent(this, SummaryList.class));
                return true;
            case R.id.audio:
                startActivity(new Intent(this, SoundTrack.class));
                return true;
            case R.id.support:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.solutionplanets.com/")));
                return true;
            case R.id.logout:
                startActivity(new Intent(this, Login.class));
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }
}