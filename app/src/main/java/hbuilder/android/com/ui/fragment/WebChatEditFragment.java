package hbuilder.android.com.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.qrcode.Constant;
import com.example.qrcode.ScannerActivity;
import com.example.qrcode.utils.QRCodeUtil;
import com.growalong.util.util.GALogger;
import com.growalong.util.util.GsonUtil;
import com.growalong.util.util.Md5Utils;
import com.lxj.xpopup.XPopup;

import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.presenter.WebChatEditPresenter;
import hbuilder.android.com.presenter.contract.WebChatEditContract;
import hbuilder.android.com.ui.activity.BalancePassWordActivity;
import hbuilder.android.com.ui.activity.PaySettingActivity;
import hbuilder.android.com.ui.widget.CenterErWeiMaPopupView;
import hbuilder.android.com.ui.widget.WenChatBindingPopupView;
import hbuilder.android.com.util.ToastUtil;

public class WebChatEditFragment extends BaseFragment implements WebChatEditContract.View {
    private static final String TAG = WebChatEditFragment.class.getSimpleName();
    @BindView(R.id.fl_title_comtent)
    FrameLayout flTitleComtent;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_wenchat_name)
    EditText etWenchatName;
    @BindView(R.id.et_webchat_code)
    EditText etWebchatCode;
    @BindView(R.id.iv_webchat_image)
    ImageView ivWebchatImage;
    @BindView(R.id.et_forget_password)
    EditText etForgetPassword;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private PaySettingActivity paySettingActivity;
    private WebChatEditPresenter presenter;
    private String sIdcardFront;
    private Bitmap qrImage;
    private Bitmap bitmap;

    public static WebChatEditFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        WebChatEditFragment fragment = new WebChatEditFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paySettingActivity = (PaySettingActivity) getActivity();
    }

    @Override
    protected int getRootView() {
        return R.layout.web_chat_edit_fragment;
    }

    @Override
    protected void initView(View root) {
        setRootViewPaddingTop(flTitleComtent);
        tvTitle.setText("微信设置");
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
    }

    @Override
    public void wechatSuccess(String name, String account, String base64Img) {
        new XPopup.Builder(getContext())
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .hasStatusBarShadow(true) //启用状态栏阴影
                .asCustom(new WenChatBindingPopupView(getContext()))
                .show();

//        paySettingActivity.setResult(Activity.RESULT_OK);
//        paySettingActivity.finish();
    }

    @Override
    public void setPresenter(WebChatEditContract.Presenter presenter) {
        this.presenter = (WebChatEditPresenter) presenter;
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @OnClick({R.id.iv_back, R.id.iv_webchat_image, R.id.tv_forget_password, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                paySettingActivity.finish();
                break;
            case R.id.iv_webchat_image:
                if (ContextCompat.checkSelfPermission(paySettingActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    goScanner();
                } else {
                    ActivityCompat.requestPermissions(paySettingActivity, new String[]{Manifest.permission.CAMERA}, 99);
                }
                break;
            case R.id.tv_forget_password:
                BalancePassWordActivity.startThis(paySettingActivity);
                break;
            case R.id.tv_submit:
                String wenchatName = etWenchatName.getText().toString().trim();
                if (TextUtils.isEmpty(wenchatName)) {
                    ToastUtil.shortShow("请输入微信名");
                    return;
                }

                String webchatCode = etWebchatCode.getText().toString().trim();
                if (TextUtils.isEmpty(webchatCode)) {
                    ToastUtil.shortShow("请输入微信号");
                    return;
                }

                if (TextUtils.isEmpty(sIdcardFront)) {
                    ToastUtil.shortShow("请上传微信收款码");
                    return;
                }

                String forgetPassword = etForgetPassword.getText().toString().trim();
                if (TextUtils.isEmpty(forgetPassword)) {
                    ToastUtil.shortShow("请输入资金密码");
                    return;
                }
                long currentTime = System.currentTimeMillis();
                presenter.wechat(0, wenchatName, webchatCode, sIdcardFront, "", Md5Utils.getMD5(forgetPassword + currentTime), currentTime);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        if (qrImage != null && !qrImage.isRecycled()) {
            qrImage.recycle();
            qrImage = null;
        }
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 99: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goScanner();
                }
                return;
            }
        }
    }

    private void goScanner() {
        Intent intent = new Intent(paySettingActivity, ScannerActivity.class);
        //这里可以用intent传递一些参数，比如扫码聚焦框尺寸大小，支持的扫码类型。
//        //设置扫码框的宽
        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_WIDTH, 400);
//        //设置扫码框的高
        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_HEIGHT, 400);
//        //设置扫码框距顶部的位置
        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_TOP_PADDING, 400);
//        //设置是否启用从相册获取二维码。
        intent.putExtra(Constant.EXTRA_IS_ENABLE_SCAN_FROM_PIC, true);
//        Bundle bundle = new Bundle();
//        //设置支持的扫码类型
//        bundle.putSerializable(Constant.EXTRA_SCAN_CODE_TYPE, mHashMap);
//        intent.putExtras(bundle);
        paySettingActivity.startActivityForResult(intent, 100);
    }

    public void onActivityResultF(int requestCode, int resultCode, Intent data) {
        String type = data.getStringExtra(Constant.EXTRA_RESULT_CODE_TYPE);
        String content = data.getStringExtra(Constant.EXTRA_RESULT_CONTENT);
        GALogger.d(TAG, "codeType:" + type + "-----content:" + content);
        if (!TextUtils.isEmpty(content)) {
            bitmap = BitmapFactory.decodeResource(MyApplication.appContext.getResources(), R.drawable.ic_launcher_round);
            qrImage = QRCodeUtil.createQRCodeBitmap(content, 650, 650, "UTF-8",
                    "H", "1", Color.BLACK, Color.WHITE, bitmap, 0.2F, null);
            sIdcardFront = content;
            ivWebchatImage.setImageBitmap(qrImage);
            ivWebchatImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }
}
