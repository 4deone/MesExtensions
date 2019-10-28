package cm.deone.mesextensions;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

import java.util.List;

import cm.deone.mesextensions.models.Agent;
import cm.deone.mesextensions.models.FirebaseDatabaseHelper;
import cm.deone.mesextensions.models.RecyclerView_Config;

public class AgentListActivity extends AppCompatActivity {

    private RecyclerView mRecylerView;
    private FirebaseAuth mAuth;

    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);

        mAuth = FirebaseAuth.getInstance();

        mRecylerView = (RecyclerView)findViewById(R.id.recycler_agents);
        edtSearch = (EditText)findViewById(R.id.edtSearch);

        new FirebaseDatabaseHelper().readAgents(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoader(List<Agent> agents, List<String> keys) {
                findViewById(R.id.loading_agent_pb).setVisibility(View.GONE);
                new RecyclerView_Config().setConfig(mRecylerView, AgentListActivity.this, agents, keys);
            }

            @Override
            public void DataIsInsered() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }

            @Override
            public void DataIsSearched(List<Agent> agents, List<String> keys, String search) {

            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    new FirebaseDatabaseHelper().searchAgent(s.toString(), new FirebaseDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoader(List<Agent> agents, List<String> keys) {

                        }

                        @Override
                        public void DataIsInsered() {

                        }

                        @Override
                        public void DataIsUpdated() {

                        }

                        @Override
                        public void DataIsDeleted() {

                        }

                        @Override
                        public void DataIsSearched(List<Agent> agents, List<String> keys, String search) {
                            new RecyclerView_Config().setConfig(mRecylerView, AgentListActivity.this, agents, keys);
                        }
                    });
                }else {
                    new FirebaseDatabaseHelper().searchAgent("", new FirebaseDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoader(List<Agent> agents, List<String> keys) {

                        }

                        @Override
                        public void DataIsInsered() {

                        }

                        @Override
                        public void DataIsUpdated() {

                        }

                        @Override
                        public void DataIsDeleted() {

                        }

                        @Override
                        public void DataIsSearched(List<Agent> agents, List<String> keys, String search) {
                            new RecyclerView_Config().setConfig(mRecylerView, AgentListActivity.this, agents, keys);
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        FirebaseUser user = mAuth.getCurrentUser();
        getMenuInflater().inflate(R.menu.agentlist_activity_menu, menu);
        if (user!=null){
            menu.getItem(0).setVisible(true); //New Agent
            menu.getItem(1).setVisible(false); // Connexion / register
            menu.getItem(2).setVisible(true); // password
            menu.getItem(3).setVisible(true); // logout
        }else {
            menu.getItem(0).setVisible(false); //New Agent
            menu.getItem(1).setVisible(true); // Connexion / register
            menu.getItem(2).setVisible(false); // password
            menu.getItem(3).setVisible(false); // logout
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            menu.getItem(0).setVisible(true); //New Agent
            menu.getItem(1).setVisible(false); // Connexion / register
            menu.getItem(2).setVisible(true); // password
            menu.getItem(3).setVisible(true); // logout
        }else {
            menu.getItem(0).setVisible(false); //New Agent
            menu.getItem(1).setVisible(true); // Connexion / register
            menu.getItem(2).setVisible(false); // password
            menu.getItem(3).setVisible(false); // logout
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_agent:
                startActivity(new Intent(this, NewAgentActivity.class));
                return true;
            case R.id.sign_in:
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            case R.id.password:
                startActivity(new Intent(this, VipSecretActivity.class));
                return true;
            case R.id.sign_out:
                mAuth.signOut();
                invalidateOptionsMenu();
                RecyclerView_Config.logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
