package cm.deone.mesextensions.models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceAgents;
    private List<Agent> agents = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoader(List<Agent> agents, List<String> keys);
        void DataIsInsered();
        void DataIsUpdated();
        void DataIsDeleted();
        void DataIsSearched(List<Agent> agents, List<String> keys, String search);
    }
    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceAgents = mDatabase.getReference("agents");
    }

    public void readAgents(final DataStatus dataStatus){
        mReferenceAgents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                agents.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Agent agent = keyNode.getValue(Agent.class);
                    agents.add(agent);
                }
                dataStatus.DataIsLoader(agents, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addAgent(Agent agent, final DataStatus dataStatus){
        String key = mReferenceAgents.push().getKey();
        mReferenceAgents.child(key).setValue(agent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInsered();
            }
        });
    }

    public void updateAgent(String key, Agent agent, final DataStatus dataStatus){
        mReferenceAgents.child(key).setValue(agent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteAgent(String key, final DataStatus dataStatus){
        mReferenceAgents.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();
            }
        });
    }

    public void searchAgent(final String search, final DataStatus dataStatus){
        Query query = mReferenceAgents.orderByChild("login").startAt(search).endAt(search + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    agents.clear();
                    List<String> keys = new ArrayList<>();
                    for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                        keys.add(keyNode.getKey());
                        Agent agent = keyNode.getValue(Agent.class);
                        agents.add(agent);
                    }
                    dataStatus.DataIsSearched(agents, keys, search);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
