package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FragmentAccount extends Fragment {
    LoginButton loginButton;
    TextView name, e_mail;
    CircularImageView imageView;
    CallbackManager callbackManager;

    CircularImageView circularImageView;
    TextView textView;

    SharedPreferences sharedPreferences;
    AccessTokenTracker tokenTracker;

    private Context context;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String ID = "id";
    public static final String CHECK = "no";

    public FragmentAccount() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        name=getView().findViewById(R.id.tv_pf);
        e_mail=getView().findViewById(R.id.tv_email);
        imageView=getView().findViewById(R.id.iv_profile);
        loginButton=getView().findViewById(R.id.login_button);

        tokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null){
                    name.setText("Your Name");
                    e_mail.setText("Your E-mail ID");
                    imageView.setImageResource(R.drawable.ic_baseline_account_circle_24);
                    textView.setText("SCANNER");
                    circularImageView.setImageResource(R.drawable.icon);
                    DeleteSharedPreferences();
                }
                else{
                    loaduserprofile(currentAccessToken);
                }
            }
        };

        callbackManager = CallbackManager.Factory.create();

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.navmenu);
        View headerView = navigationView.getHeaderView(0);
        circularImageView = headerView.findViewById(R.id.nav_img);
        textView = headerView.findViewById(R.id.nav_name);
        LoadSharedPreferences();
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {}

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException error) {}
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loaduserprofile(AccessToken newAcesstoken){
        GraphRequest request=GraphRequest.newMeRequest(newAcesstoken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name=object.getString("first_name");
                    String last_name=object.getString("last_name");
                    String email=object.getString("email");
                    String id=object.getString("id");
                    String image_url="https://graph.facebook.com/"+id+"/picture?type=large&width=720&height=720";

                    e_mail.setText(email);
                    name.setText(first_name+" "+last_name);
                    RequestOptions requestOptions=new RequestOptions();
                    requestOptions.dontAnimate();
                    Glide.with(context).load(image_url).into(imageView);
                    Glide.with(context).load(image_url).into(circularImageView);
                    textView.setText(first_name+" "+last_name);
                    SaveSharedPreferences(first_name, last_name, id, email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle parameters=new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void SaveSharedPreferences(String first_name, String last_name, String id, String email) {
        sharedPreferences = this.context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME, first_name+" "+last_name);
        editor.putString(ID, id);
        editor.putString(EMAIL, email);
        editor.putString(CHECK, "yes");
        editor.apply();
    }

    public void DeleteSharedPreferences() {
        sharedPreferences = this.context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME, "name");
        editor.putString(ID, "id");
        editor.putString(EMAIL, "email");
        editor.putString(CHECK, "no");
        editor.apply();
    }

    public void LoadSharedPreferences() {
        sharedPreferences = this.context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String names = sharedPreferences.getString(NAME, "");
        String id = sharedPreferences.getString(ID, "");
        String email = sharedPreferences.getString(EMAIL, "");
        String check = sharedPreferences.getString(CHECK, "");
        if(check.length()==3){
            String image_url="https://graph.facebook.com/"+id+"/picture?type=large&width=720&height=720";
            RequestOptions requestOptions=new RequestOptions();
            requestOptions.dontAnimate();
            Glide.with(this.context).load(image_url).into(imageView);
            Glide.with(this.context).load(image_url).into(circularImageView);
            name.setText(names);
            e_mail.setText(email);
            textView.setText(names);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

}