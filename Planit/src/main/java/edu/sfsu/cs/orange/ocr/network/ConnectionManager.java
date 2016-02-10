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


    public void loginUser(String user, String password, final ServerRequestListener listener) throws JSONException {
      //  InputStream keyStore = getResources().openRawResource(R.raw.pk);
        String payload = "{ 'fields':{ 'project':{  'id':'33636' },'priority':{  'id':'2' }, 'assignee':{  'name':null  },'summary':'BLI new test', 'description':'Yossi is an a developer', 'issuetype':{ 'id':'6' }}}" ;
        JSONObject params = new JSONObject(payload);


        String url = BASE_URL;//+ "j_spring_security_check?j_username=" + "i040922" + "&j_password=" + "Leeebsay2609";
        ALSRequest req = new ALSRequest(Request.Method.POST, url,
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

//    public void loginUserAfterRegister(String user, String password, final ServerRequestListener listener) {
//        String url = BASE_URL + "j_spring_security_check?j_username=" + user + "&j_password=" + password;
//        ALSRequest req = new ALSRequest(Request.Method.POST, url,
//                null, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                listener.onError(error.toString());
//            }
//        });
//    }
//
//    public void registerUser(JSONObject params, final ServerRequestListener listener) {
//        String url = getURL(Services.REGISTER);
//
//        ALSRequest req = new ALSRequest(Request.Method.POST, url,
//                params, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                listener.onError(error);
//            }
//        });
//    }
//
//    public void sendCallLog(JSONObject params, final ServerRequestListener listener) {
//        String url = getURL(Services.CALL_LOG);
//
//        ALSRequest req = new ALSRequest(Request.Method.POST, url,
//                params, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                listener.onError(error);
//            }
//        },false);
//    }
//
//    public void sendWalking(JSONObject params, final ServerRequestListener listener) {
//        String url = getURL(Services.WALKING_TEST);
//
//        ALSRequest req = new ALSRequest(Request.Method.POST, url,
//                params, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                listener.onError(error);
//            }
//        },false);
//    }
//
//
//    public void sendSMS(JSONObject params, final ServerRequestListener listener) {
//        String url = getURL(Services.SMS);
//
//        ALSRequest req = new ALSRequest(Request.Method.POST, url,
//                params, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                listener.onError(error);
//            }
//        },false);
//
//
//    }
//
//    public void sendTapping(JSONObject params, final ServerRequestListener listener) {
//        String url = getURL(Services.TAPPING_TEST);
//
//        ALSRequest req = new ALSRequest(Request.Method.POST, url,
//                params, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                listener.onError(error);
//            }
//        },false);
//
//
//    }
//
//    public void lastSubmitted(final ServerRequestListener listener) {
//        String url = getURL(Services.LAST_SUBMITTED);
//        ALSRequest req = new ALSRequest(Request.Method.GET, url,
//                null, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                listener.onError(error);
//            }
//        });
//    }
//
//
//    public void submitBreathing(final ArrayList<FileObject> files, final ServerRequestListener listener) {
//        String url = getURL(Services.TEST_BREATH);
//        MultipartRequest req = new MultipartRequest(url,
//                files, null, MultipartRequest.AUDIO_TYPE, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                listener.onError(error);
//            }
//        });
//
//    }
//
//    public void submitSpeech(final ArrayList<FileObject> files, final ServerRequestListener listener) {
//        String url = getURL(Services.TEST_SPEECH);
//
//
//        if (files != null) {
//            MultipartRequest req = new MultipartRequest(url,
//                    files, null, MultipartRequest.AUDIO_TYPE, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    listener.onSuccess(response);
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                    listener.onError(error);
//                }
//            });
//        }
//    }
//
//    public void submitDrawings(final ArrayList<FileObject> files, final ServerRequestListener listener) {
//        String url = getURL(Services.TEST_DRAWING);
//        if (files != null) {
//            MultipartRequest req = new MultipartRequest(url,
//                    files, null, MultipartRequest.IMAGE_TYPE, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    listener.onSuccess(response);
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                    listener.onError(error);
//                }
//            });
//        }
//    }
//
//
//    public void Questanire(JSONObject jsonObject, final ServerRequestListener listener) {
//        String url = getURL(Services.TEST_QUESTIONS);
//
//        ALSRequest req = new ALSRequest(Request.Method.POST, url,
//                jsonObject, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                listener.onError(error);
//            }
//        });
//    }
//
//
//    public void forgetPasswordSendMail(JSONObject jsonObject, final ServerRequestListener listener) {
//        String url = getURL(Services.FORGOT_PASS_SEND_MAIL);
//
//        ALSRequest req = new ALSRequest(Request.Method.POST, url,
//                jsonObject, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//
//            }
//        });
//    }
//
//    public void forgetPasswordUpdateNewPassword(JSONObject jsonObject, final ServerRequestListener listener) {
//
//
//        String url = getURL(Services.FORGOT_PASS_UPDATE_NEW_PASS);
//
//        ALSRequest req = new ALSRequest(Request.Method.POST, url,
//                jsonObject, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//
//
//    }
//
//    private String getURL(Services type) {
//        String url = "";
//        switch (type) {
//            case REGISTER:
//                return BASE_URL + "api/patients";
//            case LAST_SUBMITTED:
//                return BASE_URL + "api/tasks/lastSubmitted";
//            case TEST_QUESTIONS:
//                return BASE_URL + "api/tasks/questionnaires";
//            case TEST_SPEECH:
//                return BASE_URL + "api/tasks/speechTests";
//            case TEST_BREATH:
//                return BASE_URL + "api/tasks/breathTests";
//            case TEST_DRAWING:
//                return BASE_URL + "api/tasks/writingTests";
//            case SMS:
//                return BASE_URL + "api/tasks/smsTests";
//            case CALL_LOG:
//                return BASE_URL + "api/tasks/callTests";
//            case WALKING_TEST:
//                return BASE_URL + "api/tasks/stepTests";
//            case FORGOT_PASS_SEND_MAIL:
//                return BASE_URL + "api/forgotPassword/sendMail";
//            case FORGOT_PASS_UPDATE_NEW_PASS:
//                return  BASE_URL+ "api/forgotPassword/updatePassword";
//            case TAPPING_TEST:
//                return BASE_URL + "api/tasks/tappingTests";
//
//            default:
//                break;
//        }
//        return url;
//    }


}
