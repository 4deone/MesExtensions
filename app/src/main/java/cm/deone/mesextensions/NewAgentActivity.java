package cm.deone.mesextensions;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import cm.deone.mesextensions.models.Agent;
import cm.deone.mesextensions.models.FirebaseDatabaseHelper;

public class NewAgentActivity extends AppCompatActivity {

    private EditText mLogin;
    private Spinner mspDomain;
    private EditText mExtension;
    private EditText mNom;
    private EditText mPassword;
    private Spinner mspWeb;
    private Spinner mspCampagne;

    private Button mAdd_btn;
    private Button mBack_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_agent);
        mLogin = (EditText)findViewById(R.id.tvLogin);
        mspDomain = (Spinner) findViewById(R.id.spDomain);
        mExtension = (EditText)findViewById(R.id.tvExtension);
        mNom = (EditText)findViewById(R.id.tvNomAgent);
        mPassword = (EditText)findViewById(R.id.tvPassword);
        mspWeb = (Spinner) findViewById(R.id.spWeb);
        mspCampagne = (Spinner) findViewById(R.id.spCampagne);
        mAdd_btn = (Button) findViewById(R.id.add_btn);
        mBack_btn = (Button)findViewById(R.id.back_btn);

        mAdd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agent agent = new Agent();
                agent.setLogin(mLogin.getText().toString());
                agent.setDomain(mspDomain.getSelectedItem().toString());
                agent.setExtension(Long.parseLong(mExtension.getText().toString()));
                agent.setNom(mNom.getText().toString());
                agent.setPassword(mPassword.getText().toString());
                agent.setWeb(mspWeb.getSelectedItem().toString());
                agent.setCampagne(mspCampagne.getSelectedItem().toString());
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                agent.setAddedby(user.getEmail());
                new FirebaseDatabaseHelper().addAgent(agent, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoader(List<Agent> agents, List<String> keys) {

                    }

                    @Override
                    public void DataIsInsered() {
                        Toast.makeText(NewAgentActivity.this, "The agent record has " +
                                "been inserted successfully", Toast.LENGTH_LONG).show();
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
            }
        });

        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); return;
            }
        });
    }
}
