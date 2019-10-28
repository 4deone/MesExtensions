package cm.deone.mesextensions.models;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import cm.deone.mesextensions.DetailsAgentActivity;
import cm.deone.mesextensions.R;
import cm.deone.mesextensions.SignInActivity;

public class RecyclerView_Config {
    FirebaseAuth mAuth;
    private static FirebaseUser user;
    private Context mContext;
    private AgentAdapter mAgentAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Agent> agents, List<String> keys){

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mContext = context;
        mAgentAdapter = new AgentAdapter(agents, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAgentAdapter);
    }

    class AgentItemView extends RecyclerView.ViewHolder{
        private TextView mLogin;
        private TextView mExtension;
        private TextView mNom;
        private TextView mCampagne;
        private TextView mDomain;
        private TextView mPassword;
        private TextView mWeb;
        private String key;

        public AgentItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.agent_list_item, parent, false));

            mLogin = (TextView) itemView.findViewById(R.id.tvLogin);
            mExtension = (TextView) itemView.findViewById(R.id.tvExtension);
            mNom = (TextView) itemView.findViewById(R.id.tvNom);
            mCampagne = (TextView) itemView.findViewById(R.id.tvCampagne);
            mDomain = (TextView) itemView.findViewById(R.id.tvDomain);
            mPassword = (TextView) itemView.findViewById(R.id.tvPassword);
            mWeb = (TextView) itemView.findViewById(R.id.tvWeb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user!=null){
                        Intent intent = new Intent(mContext, DetailsAgentActivity.class);
                        intent.putExtra("key", key);
                        intent.putExtra("campagne", mCampagne.getText().toString());
                        intent.putExtra("nom", mNom.getText().toString());
                        intent.putExtra("extension", Long.parseLong(mExtension.getText().toString()));
                        intent.putExtra("login", mLogin.getText().toString());
                        intent.putExtra("password", mPassword.getText().toString());
                        intent.putExtra("domain", mDomain.getText().toString());
                        intent.putExtra("web", mWeb.getText().toString());

                        mContext.startActivity(intent);
                    }else {
                        mContext.startActivity(new Intent(mContext, SignInActivity.class));
                    }
                }
            });
        }

        public void bind(Agent agent, String key){
            mLogin.setText(agent.getLogin());
            mExtension.setText(""+agent.getExtension());
            mDomain.setText(agent.getDomain());
            mCampagne.setText(agent.getCampagne());

            mPassword.setText(agent.getPassword());
            mNom.setText(agent.getNom());
            mWeb.setText(agent.getWeb());

            this.key = key;
        }
    }
    class AgentAdapter extends RecyclerView.Adapter<AgentItemView>{
        private List<Agent> mAgentList;
        private List<String> mKeys;

        public AgentAdapter(List<Agent> mAgentList, List<String> mKeys) {
            this.mAgentList = mAgentList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public AgentItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new AgentItemView(viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull AgentItemView agentItemView, int i) {
            agentItemView.bind(mAgentList.get(i), mKeys.get(i));
        }

        @Override
        public int getItemCount() {
            return mAgentList.size();
        }
    }
    public static void logout(){
        user = null;
    }
}
