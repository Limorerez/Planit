package edu.sfsu.cs.orange.ocr.network;

import android.content.Context;
import android.os.Environment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.sfsu.cs.orange.ocr.R;

public class ConnectionManager {


    public interface ServerRequestListener {
        public void onSuccess(Object data);
        public void onError(Object data);
    }

    public static String BASE_URL;


    private static ConnectionManager _instance;
    private Context mContext;

//    public enum Services {
//        REGISTER, LAST_SUBMITTED, TEST_QUESTIONS, TEST_SPEECH, TEST_BREATH, TEST_DRAWING, SMS, CALL_LOG, WALKING_TEST,FORGOT_PASS_SEND_MAIL,
//        FORGOT_PASS_UPDATE_NEW_PASS,TAPPING_TEST;
//    }

    private ConnectionManager() {

    }


    public static ConnectionManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new ConnectionManager();
        }
        BASE_URL = context.getString(R.string.url_server);
        _instance.mContext = context;
        ALSRequest.setContext(context);
       // MultipartRequest.setContext(context);
        return _instance;
    }


    public void getCookie(String [] SummaryData, final ServerRequestListener listener) throws JSONException{

        String url = "https://sapjira.wdf.sap.corp/";
        JSONObject params = null;
        ALSRequest req = new ALSRequest(Request.Method.GET, url,
                params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                listener.onError(error.toString());
            }
        }, false);
    }
    public void createBLI(String cookie , String [] SummaryData, final ServerRequestListener listener) throws JSONException {
      //  InputStream keyStore = getResources().openRawResource(R.raw.pk);
        for ( int i = 0 ; i < SummaryData.length ; i++){
            //String payload = "{ 'fields':{ 'project':{  'id':'33636' },'priority':{  'id':'2' }, 'assignee':{  'name':null  },'summary':'BLI new test', 'description':'Yossi is an a developer', 'issuetype':{ 'id':'6' }}}" ;

            String payload = "{ 'fields':{ 'project':{  'id':'33636' },'priority':{  'id':'2' }, 'assignee':{  'name':null  },'summary':'" + SummaryData[i] + "', 'description':'" + SummaryData[i] +"' , 'issuetype':{ 'id':'6' }}}" ;

            JSONObject params = new JSONObject(payload);


            String url = "https://sapjira.wdf.sap.corp/rest/api/2/issue/";
            ALSRequest req = new ALSRequest(cookie , Request.Method.POST, url,
                    params, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    listener.onSuccess(response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    listener.onError(error.toString());
                }
            }, false);
        }

    }

    public void getBLIID(String cookie, String bliParentKey , final ServerRequestListener listener) throws JSONException {

           // String payload = "{ 'fields':{ 'project':{  'id':'33636' },'priority':{  'id':'2' }, 'assignee':{  'name':null  },'parent':{'key':'" +bliParent +"' },'summary':'" + SummaryData[i] + "', 'description':" + SummaryData[i] + "' , 'issuetype':{ 'id':'5' }}}";
            JSONObject params = null;


            String url = "https://sapjira.wdf.sap.corp/rest/api/2/issue/RAKJIRA-" + bliParentKey ;
            ALSRequest req = new ALSRequest(cookie, Request.Method.GET, url,
                    params, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    listener.onSuccess(response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    listener.onError(error.toString());
                }
            }, false);
    }
    public void createTask(String cookie, String [] SummaryData, String bliParent, final ServerRequestListener listener) throws JSONException {
        //  InputStream keyStore = getResources().openRawResource(R.raw.pk);
       // String payload = "{ 'fields':{ 'project':{  'id':'33636' },'priority':{  'id':'2' }, 'assignee':{  'name':null  },'summary':'BLI new test', 'description':'Yossi is an a developer', 'issuetype':{ 'id':'6' }}}" ;
        for ( int i = 0 ; i < SummaryData.length ; i++) {
            String payload = "{ 'fields':{ 'project':{  'id':'33636' },'priority':{  'id':'2' }, 'assignee':{  'name':null  },'parent':{'key':'" +bliParent +"' },'summary':'" + SummaryData[i] + "', 'description':'" + SummaryData[i] + "' , 'issuetype':{ 'id':'5' }}}";
            JSONObject params = new JSONObject(payload);


            String url = "https://sapjira.wdf.sap.corp/rest/api/2/issue/";
            ALSRequest req = new ALSRequest(cookie, Request.Method.POST, url,
                    params, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    listener.onSuccess(response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    listener.onError(error.toString());
                }
            }, false);
        }
    }
    public void getBLIfromProject( final ServerRequestListener listener) throws JSONException {

        String url = "https://sapjira.wdf.sap.corp/rest/api/2/search?jql=project='33636'";
        JSONObject params = null;
        ALSRequest req = new ALSRequest(Request.Method.GET, url,
                params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                listener.onError(error.toString());
            }
        }, false);

    }




}
