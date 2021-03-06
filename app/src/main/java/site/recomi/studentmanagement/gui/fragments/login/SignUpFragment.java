package site.recomi.studentmanagement.gui.fragments.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import site.recomi.studentmanagement.Constant;
import site.recomi.studentmanagement.R;
import site.recomi.studentmanagement.common.Constants;
import site.recomi.studentmanagement.gui.activities.GuideActivity;
import site.recomi.studentmanagement.gui.activities.RegisterFaceActivity;
import site.recomi.studentmanagement.gui.fragments.Base.BaseFragment;
import site.recomi.studentmanagement.model.UserAllInfo;

public class SignUpFragment extends BaseFragment {

    private Toast toast = null;
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };


//    @BindView(R.id.btn_register_facePic)
//    Button btn_register_facePic;
    @BindView(R.id.btn_signup)
    Button btn_signup;
    @BindView(R.id.input_signup_id)
    TextView tv_id;
    @BindView(R.id.input_signup_name)
    TextView tv_name;
    @BindView(R.id.input_signup_phone)
    TextView tv_phone;
    @BindView(R.id.input_signup_password)
    TextView tv_password;
    @BindView(R.id.input_signup_password_again)
    TextView tv_password_again;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frag_account_signup,container,false);
        mContext = getContext();
        ButterKnife.bind(this, mView);   //绑定ButterKnife
        initView(); //初始化视图
        return mView;
    }

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    //初始化视图布局监听
    private void initView() {
//        btn_register_facePic.setOnClickListener(v ->
//                startActivityOnly(mContext, RegisterFaceActivity.class));
        btn_signup.setOnClickListener(v -> {
            boolean is_empty = tv_id.getText().toString().equals("")
                    || tv_name.getText().toString().equals("")
                    || tv_phone.getText().toString().equals("")
                    || tv_password.getText().toString().equals("")
                    || tv_password_again.getText().toString().equals("");
            boolean is_same_pass = tv_password.getText().toString()
                    .equals(tv_password_again.getText().toString());
            if (is_empty) {
                toastShortMessage(mContext,"全部必填，请勿留空");
                return;
            }
            if (!is_same_pass) {
                toastShortMessage(mContext,"两次密码不一致");
                return;
            }
            activeEngine(v);
            saveLocalUserBaseInfo();
            getOnlineData();
        });
    }

    private void saveLocalUserBaseInfo() {
        SharedPreferences.Editor editor = mContext.getSharedPreferences("UserBaseInfo", Context.MODE_PRIVATE).edit();
        editor.putString("id",tv_id.getText().toString());
        editor.putString("name",tv_name.getText().toString());
        editor.putString("phone",tv_phone.getText().toString());
//        editor.putString("password",tv_password);
        editor.apply();
    }

    private void getOnlineData(){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("type" , "register")
                .add("id" , tv_id.getText().toString())
                .add("name" , tv_name.getText().toString())
                .add("phone" , tv_phone.getText().toString())
                .add("password" , tv_password.getText().toString())
                .build();
        Request request = new Request.Builder()
                .url(Constant.MAIN_PHP)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //针对异常情况处理
                Log.e("null","网络异常");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d("data", "服务器返回的数据: " + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if (status != null){
                        switch (status) {
                            case "succeed":
                                toastShortMessage("注册成功，请录入人脸信息");
                                if (getActivity() != null) {
                                    getActivity().startActivity(new Intent(getActivity(), RegisterFaceActivity.class));
                                }
                                break;
                            case "error":
                                toastShortMessage("注册失败，请重试");
                                break;
                            case "repeat":
                                toastShortMessage("帐号重复，请更换学号");
                                break;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void toastShortMessage(String message) {
        Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        });
    }



    /**
     * 激活引擎
     *
     * @param view
     */
    public void activeEngine(final View view) {
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }
        if (view != null) {
            view.setClickable(false);
        }
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                FaceEngine faceEngine = new FaceEngine();
                int activeCode = faceEngine.active(mContext, Constants.APP_ID, Constants.SDK_KEY);
                emitter.onNext(activeCode);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer activeCode) {
                        if (activeCode == ErrorInfo.MOK) {
                            showToast(getString(R.string.active_success));
                        } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
//                            showToast(getString(R.string.already_activated));
                        } else {
                            showToast(getString(R.string.active_failed, activeCode));
                        }

                        if (view != null) {
                            view.setClickable(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 检查权限
     * */
    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(mContext, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    private void showToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(mContext, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(s);
            toast.show();
        }
    }
}
