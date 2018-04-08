package com.example.hug.star;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import static android.R.attr.id;
import static android.R.attr.name;

public class LoginActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    Button share,details, back;
    ShareDialog shareDialog;
    LoginButton login;
    ProfilePictureView profile;
    Dialog details_dialog;
    TextView details_txt, name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        login = (LoginButton)findViewById(R.id.login_facebook);
        profile = (ProfilePictureView)findViewById(R.id.picture);
        details = (Button)findViewById(R.id.details);
        shareDialog = new ShareDialog(this);
        share = (Button)findViewById(R.id.share);
        back = (Button)findViewById(R.id.back);
        name = (TextView)findViewById(R.id.name);
        email = (TextView)findViewById(R.id.email);

        //Permissions
        login.setReadPermissions("public_profile email");

        share.setVisibility(View.INVISIBLE);
        details.setVisibility(View.INVISIBLE);

        details_dialog = new Dialog(this);
        details_dialog.setContentView(R.layout.dialog_details_fb);
        details_dialog.setTitle("Detail");
        details_txt = (TextView)details_dialog.findViewById(R.id.details);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                details_dialog.show();
            }
        });

        if(AccessToken.getCurrentAccessToken() != null){
            RequestData();
            share.setVisibility(View.VISIBLE);
            details.setVisibility(View.VISIBLE);
            profile.setVisibility(View.VISIBLE);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AccessToken.getCurrentAccessToken() != null) {
                    share.setVisibility(View.INVISIBLE);
                    details.setVisibility(View.INVISIBLE);
                    //profile.setProfileId(null);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle b = new Bundle();
                    b.putString("name", name.getText().toString());
                    b.putString("email", email.getText().toString());
                    b.putString("picture", profile.toString());
                    // add bundle intent
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareLinkContent content = new ShareLinkContent.Builder().build();
                shareDialog.show(content);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("Main", response.toString());
                                serProfileView(object);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
            }
        });

    }
    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object,GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if(json != null){
                        String text = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Địa Chỉ :</b> "+json.getString("link");
                        details_txt.setText(Html.fromHtml(text));
                        //profile.setProfileId(json.getString("id"));
                        name.setText(object.getString("name"));
                        email.setText(object.getString("email"));
                        profile.setPresetSize(ProfilePictureView.NORMAL);
                        profile.setProfileId(object.getString("id"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }
    // BITMAP
    //public static Bitmap getFBProfilePicture(String id) throws IOException{
    //    URL imageURL = new URL("https:graph.facebook.com/" + id + "/picture?type=large");
    //    Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
    //    return bitmap;
    //}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Come back MainActivity when login success
        //Toast.makeText(getApplicationContext(), "Welcome!" , Toast.LENGTH_SHORT).show();
            //Intent i = new Intent(this, MainActivity.class);
            //startActivity(i);
        }
    private  void serProfileView(JSONObject jsonObject){
        try {
            profile.setPresetSize(ProfilePictureView.NORMAL);
            profile.setProfileId(jsonObject.getString("id"));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
