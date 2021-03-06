package haozuo.com.healthdoctor.view.consult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.ReportParamsBean;
import haozuo.com.healthdoctor.contract.ConsultDetailContract;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.util.DateUtil;
import haozuo.com.healthdoctor.util.JsonParser;
import haozuo.com.healthdoctor.util.UIHelper;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.view.custom.CustomDetailActivity;
import haozuo.com.healthdoctor.view.custom.CustomerReportActivity;
import haozuo.com.healthdoctor.view.custom.PhotoPreviewActivity;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToLoadMoreLayout;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullableListView;
import haozuo.com.healthdoctor.view.threePart.common.FlowLayout;

public class ConsultDetailFragment extends AbstractView implements ConsultDetailContract.IConsultDetailView {
    Context mContext;
    View rootView;
    ConsultListAdapter mConsultListAdapter;
    public ConsultDetailContract.IConsultDetailPresenter mConsultDetailPresenter;
    public static int RESULT_EXPRESSION = 0;
    private String mURI;
    private ConsultReplyBean mConsultReplmyItem;
    private CustomDetailBean mCustomDetailBean;
    private static DoctorBean mDoctorEntity;
    private static int mCustomerId;
    private List<ConsultReplyBean> mConsultReplyList;

    public static final String PREFER_NAME = "com.iflytek.setting";
    public static final String SELECT_POSITION_SMOOTH = "SELECT_POSITION_SMOOTH";
    public static final String SELECT_POSITION_DIRECT = "SELECT_POSITION_DIRECT";
    public static final String SELECTED_CUSTOMID = "SELECTED_CUSTOMID";
    private static String TAG = ConsultDetailFragment.class.getSimpleName();
    private SpeechRecognizer mIat;                                                              // 语音听写对象
    private RecognizerDialog mIatDialog;                                                       // 语音听写UI
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();        // 用HashMap存储听写结果
    private String mEngineType = SpeechConstant.TYPE_CLOUD;                                  // 引擎类型
    int ret = 0;                                                                                // 函数调用返回值
    private Toast mToast;
    private SharedPreferences mSharedPreferences;

//    @Inject
//    int customID;
    @Bind(R.id.consult_detail_ListView)
    PullableListView consult_detail_List;
    @Bind(R.id.consult_detail_pull_to_refresh_layout)
    PullToLoadMoreLayout consult_detail_pull_to_refresh_layout;
    @Bind(R.id.edittxt_message)
    EditText edittxt_message;
    @Bind(R.id.edit_area)
    RelativeLayout layoutEditArea;

    @OnClick(R.id.btn_sound_message)
    public void getVoiceContent() {
        // 移动数据分析，收集开始听写事件
        FlowerCollector.onEvent(mContext, "iat_recognize");
        edittxt_message.setText(null);// 清空显示内容
        mIatResults.clear();
        // 设置参数
        setParam();
        boolean isShowDialog = mSharedPreferences.getBoolean(
                getString(R.string.pref_key_iat_show), true);
        if (isShowDialog) {
            // 显示听写对话框
            mIatDialog.setListener(mRecognizerDialogListener);
            mIatDialog.show();
            showTip(getString(R.string.text_begin));
        } else {
            // 不显示听写对话框
            ret = mIat.startListening(mRecognizerListener);
            if (ret != ErrorCode.SUCCESS) {
                showTip("听写失败,错误码：" + ret);
            } else {
                showTip(getString(R.string.text_begin));
            }
        }
    }

    @OnClick(R.id.btn_usually_message)
    public void getUsefulMessage(View v) {
        startActivityForResult(new Intent(mContext, UsefulMesasgeActivity.class)
                .putExtra(UsefulMesasgeActivity.CUSTOM_DETAIL_INFO,mCustomDetailBean)
                .putExtra(UsefulMesasgeActivity.LAST_CONSULT_CONTENT, mConsultReplmyItem), RESULT_EXPRESSION);
    }

    public ConsultDetailFragment() {}

    @Override
    protected IBasePresenter getPresenter() {
        return mConsultDetailPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    public static ConsultDetailFragment newInstance(int CustomerId, String AccountId) {
        ConsultDetailFragment fragment = new ConsultDetailFragment();
        mDoctorEntity = UserManager.getInstance().getDoctorInfo();
        mCustomerId = CustomerId;
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_consult_detail, container, false);
            ButterKnife.bind(this, rootView);
            if (!getActivity().getIntent().getBooleanExtra(ConsultDetailActivity.EXTRA_SHOW_EDITLAYOUT, true)) {
                //如果是从反馈页面进入，则隐藏回复模块
                layoutEditArea.setVisibility(View.GONE);
            }
        }

        //监听软键盘弹出，在弹出状态下重新调整窗口
        //WindowResize.assistActivity(getActivity());
        mConsultListAdapter = new ConsultListAdapter(mContext);
        consult_detail_List.setAdapter(mConsultListAdapter);
        consult_detail_pull_to_refresh_layout.setOnRefreshListener(new PullListener());
        mConsultDetailPresenter.start();

        mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);
        mIatDialog = new RecognizerDialog(mContext, mInitListener);
        mSharedPreferences = mContext.getSharedPreferences(this.PREFER_NAME,
                Activity.MODE_PRIVATE);
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);

        //回复框回车监听
        edittxt_message.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                        String replyContent = edittxt_message.getText().toString();
                        addDoctorReply(replyContent);
                    }
                    return true;
            }
                return false;
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mConsultDetailPresenter.cancelRequest();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出时释放连接
        mIat.cancel();
        mIat.destroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        ConsultReplyBean replyContent = (ConsultReplyBean) data.getExtras().getSerializable(String.valueOf(RESULT_EXPRESSION));
        mConsultListAdapter.addDoctorReply(replyContent);
        setListViewPosition(mConsultReplyList.size(),SELECT_POSITION_SMOOTH);
//        addDoctorReply(replyContent);
    }

    @Override
    public void setPresenter(ConsultDetailContract.IConsultDetailPresenter presenter) {
        mConsultDetailPresenter = presenter;
    }

    @Override
    public void refreshCustomAdapter(List<ConsultReplyBean> dataList) {
        mConsultListAdapter.refresh(dataList);
    }

    @Override
    public void setListViewPosition(int position, String SELECT_POSITION_TYPE) {
        switch(SELECT_POSITION_TYPE){
            case SELECT_POSITION_SMOOTH:
                consult_detail_List.smoothScrollToPosition(position);
                break;
            case SELECT_POSITION_DIRECT:
                consult_detail_List.setSelection(position);
                break;
            default:
                break;
        }
    }

    @Override
    public void refreshFinish(int status) {
        consult_detail_pull_to_refresh_layout.refreshFinish(status);
        if (status == PullToRefreshLayout.SUCCEED){
            playSuccessSound();
        }
    }

    @Override
    public void setCustmoerInfo(CustomDetailBean customDetailItem) {
        mCustomDetailBean = customDetailItem;
        String customInfo = mCustomDetailBean.Cname + " " + mCustomDetailBean.GetSex() + " " + mCustomDetailBean.GetAge();
        ((ConsultDetailActivity)getActivity()).setCustomerTitle(customInfo);
        getActivity().findViewById(R.id.txt_TitleBar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, CustomDetailActivity.class)
                        .putExtra(CustomDetailActivity.EXTRA_ACCOUNT_ID, mCustomDetailBean.Account_Id)
                        .putExtra(CustomDetailActivity.EXTRA_CUSTOMER_ID, mCustomDetailBean.Id));
            }
        });
    }

    @Override
    public void RefreshConsultPage(List<ConsultReplyBean> mConsultReplyBeanList){
        mConsultListAdapter.refresh(mConsultReplyBeanList);
        Intent intent = new Intent(BROADFILTER_CONSULT_REPLAY);
        intent.putExtra(SELECTED_CUSTOMID, mCustomerId);
        getContext().sendBroadcast(intent);
    }

//    @Override
//    public void LoadmoreConsultPage(List<ConsultReplyBean> mConsultReplyBeanList){
//        mConsultListAdapter.loadmore(mConsultReplyBeanList);
//    }

    @Override
    public void changeRetryLayer(boolean isShow) {
        if (isShow) {
//            showRetryLayer(R.id.rLayout,true,getString(R.string.connect_fail));
            showRetryLayer(R.id.rLayout);
        } else {
            hideRetryLayer(R.id.rLayout);
        }
    }

    class PullListener implements PullToLoadMoreLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            mConsultDetailPresenter.loadmoreConsultList();
        }

    }

    class ConsultListAdapter extends BaseAdapter {
        LayoutInflater myInflater;
        private String mCommitOn = "";
        private static final int TYPE_COSTUMER = 0;
        private static final int TYPE_DOCTOR = 1;
        private static final int TYPE_COUNT = 2;
        private int currentType;

        public ConsultListAdapter(Context context) {
            this.myInflater = LayoutInflater.from(context);
            mConsultReplyList = new ArrayList<>();
        }

        public void refresh(List<ConsultReplyBean> dataList) {
            mConsultReplyList.clear();
            mConsultReplyList.addAll(dataList);
            mConsultReplmyItem = new ConsultReplyBean();
            //获取最后一条客户回复异常项，若不存在异常项则取客户咨询
            for (int i= mConsultReplyList.size()-1;i>=0;i--){
                if (mConsultReplyList.get(i).IsDoctorReply == 0 &&mConsultReplyList.get(i).ConsultType == 1){ //客户回复内容
                    mConsultReplmyItem = mConsultReplyList.get(i);
                    break;
                }
            }
            for (int i= mConsultReplyList.size()-1;i>=0;i--){
                if (mConsultReplyList.get(i).IsDoctorReply == 0 &&mConsultReplyList.get(i).ConsultType == 3){ //客户回复内容
                    mConsultReplmyItem = mConsultReplyList.get(i);
                    break;
                }
            }
            notifyDataSetChanged();
        }

        public void addDoctorReply(ConsultReplyBean consultReplyBean){
            mConsultReplyList.add(consultReplyBean);
            mConsultReplmyItem = new ConsultReplyBean();
            //获取最后一条客户回复异常项，若不存在异常项则取客户咨询
            for (int i= mConsultReplyList.size()-1;i>=0;i--){
                if (mConsultReplyList.get(i).IsDoctorReply == 0 &&mConsultReplyList.get(i).ConsultType == 1){ //客户回复内容
                    mConsultReplmyItem = mConsultReplyList.get(i);
                    break;
                }
            }
            for (int i= mConsultReplyList.size()-1;i>=0;i--){
                if (mConsultReplyList.get(i).IsDoctorReply == 0 &&mConsultReplyList.get(i).ConsultType == 3){ //客户回复内容
                    mConsultReplmyItem = mConsultReplyList.get(i);
                    break;
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mConsultReplyList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            if (mConsultReplyList.get(position).IsDoctorReply == 0) {
                return TYPE_COSTUMER;
            } else if (mConsultReplyList.get(position).IsDoctorReply == 1) {
                return TYPE_DOCTOR;
            } else {
                return 100;
            }
        }

        @Override
        public int getViewTypeCount() {
            return TYPE_COUNT;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            currentType = getItemViewType(position);
            if (currentType == TYPE_COSTUMER) {
                ViewHolderLeft holder = null;
                if (convertView == null) {
                    convertView = myInflater.inflate(R.layout.lvitemleft_consult_detail, parent, false);
                    holder = new ViewHolderLeft(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolderLeft) convertView.getTag();
                }
                final ConsultReplyBean consultReplyEntity = mConsultReplyList.get(position);
                UIHelper.setFrescoURL(holder.drawee_consult_item_photo,consultReplyEntity.PhotoUrl
                        ,"res://haozuo.com.healthdoctor.view.custom/"+R.drawable.user_default_url);

                switch (consultReplyEntity.ConsultType) {
                    case 1://纯文本
                    case 4://问卷相关信息
                        holder.txt_consult_item.setVisibility(View.VISIBLE);
                        holder.flowLayout_consult_photo.setVisibility(View.GONE);
                        holder.txt_consult_item.setText(consultReplyEntity.Content);
                        holder.txt_consult_item.setOnClickListener(null);
                        break;
                    case 2://照片病例
                        holder.txt_consult_item.setVisibility(View.GONE);
                        holder.flowLayout_consult_photo.setVisibility(View.VISIBLE);
                        String[] photoList = consultReplyEntity.AppendInfo.split(",");
                        holder.flowLayout_consult_photo.removeAllViews();
                        for (int i = 0; i < photoList.length; i++) {
                            SimpleDraweeView consult_photo = (SimpleDraweeView) LayoutInflater.from(mContext).inflate(R.layout.lvitemleft_consult_detail_photo, holder.flowLayout_consult_photo, false);
                            Uri photoUri = Uri.parse(photoList[i] + "!small200");
                            consult_photo.setImageURI(photoUri);
                            final int finalI = i;
                            consult_photo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(mContext, PhotoPreviewActivity.class)
                                            .putExtra(PhotoPreviewActivity.EXTRA_URL_LIST, (String) consultReplyEntity.AppendInfo)
                                            .putExtra(PhotoPreviewActivity.EXTRA_PAGER_INDEX, (int) finalI));
                                    getActivity().overridePendingTransition(R.anim.photopreview_enter_anim, R.anim.photopreview_exit_anim);
                                }
                            });
                            holder.flowLayout_consult_photo.addView(consult_photo);

                        }
                        break;
                    case 3://体检异常项
                        holder.txt_consult_item.setVisibility(View.VISIBLE);
                        holder.flowLayout_consult_photo.setVisibility(View.GONE);
                        holder.txt_consult_item.setText(consultReplyEntity.Content);
                        holder.txt_consult_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ReportParamsBean bean = new ReportParamsBean();
                                bean.customerId = mCustomerId;
                                bean.WorkNo = consultReplyEntity.AppendInfo.split(";")[0];
                                bean.CheckUnitCode = consultReplyEntity.AppendInfo.split(";")[1];
                                startActivity(new Intent(mContext, CustomerReportActivity.class)
                                        .putExtra(CustomerReportActivity.REPORTPARAMSBEAN, bean));
                            }
                        });
                        break;
                    default:
                        break;
                }
                mCommitOn = DateUtil.TimeFormatByWeek(consultReplyEntity.CommitOn,"yyyy-MM-dd HH:mm:ss");
                if (position == 0) {//如果是列表中的第一条数据则直接展示时间
                    holder.txt_consult_commiton.setVisibility(View.VISIBLE);
                    holder.txt_consult_commiton.setText(mCommitOn);
                } else {//与列表中上一条数据的时间相比较，若间隔时间小于30s则展示时间
                    if (DateUtil.getSecondDiff(mConsultReplyList.get(position - 1).CommitOn, consultReplyEntity.CommitOn) >= 30) {
                        holder.txt_consult_commiton.setVisibility(View.VISIBLE);
                        holder.txt_consult_commiton.setText(mCommitOn);
                    } else {
                        holder.txt_consult_commiton.setVisibility(View.GONE);
                    }
                }
            } else if (currentType == TYPE_DOCTOR) {
                ViewHolderRight holder = null;
                if (convertView == null) {
                    convertView = myInflater.inflate(R.layout.lvitemright_consult_detail, parent, false);
                    holder = new ViewHolderRight(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolderRight) convertView.getTag();
                }
                ConsultReplyBean consultReplyEntity = mConsultReplyList.get(position);
                UIHelper.setFrescoURL(holder.drawee_consult_item_photo,mDoctorEntity.PhotoUrl
                        ,"res://haozuo.com.healthdoctor.view.custom/"+R.drawable.user_default_url);
                holder.txt_consult_item.setText(consultReplyEntity.Content);
                mCommitOn = DateUtil.TimeFormatByWeek(consultReplyEntity.CommitOn,"yyyy-MM-dd HH:mm:ss");
                if (position == 0) {//如果是列表中的第一条数据则直接展示时间
                    holder.txt_consult_commiton.setVisibility(View.VISIBLE);
                    holder.txt_consult_commiton.setText(mCommitOn);
                } else {//与列表中上一条数据的时间相比较，若间隔时间小于30s则展示时间
                    if (DateUtil.getSecondDiff(mConsultReplyList.get(position - 1).CommitOn, consultReplyEntity.CommitOn) >= 30) {
                        holder.txt_consult_commiton.setVisibility(View.VISIBLE);
                        holder.txt_consult_commiton.setText(mCommitOn);
                    } else {
                        holder.txt_consult_commiton.setVisibility(View.GONE);
                    }
                }
            }
            return convertView;
        }

        public class ViewHolderLeft {

            @Bind(R.id.txt_consult_commiton)
            TextView txt_consult_commiton;

            @Bind(R.id.drawee_consult_item_photo)
            SimpleDraweeView drawee_consult_item_photo;

            @Bind(R.id.txt_consult_item)
            TextView txt_consult_item;

            @Bind(R.id.flowLayout_consult_photo)
            FlowLayout flowLayout_consult_photo;

            public ViewHolderLeft(View convertView) {
                ButterKnife.bind(this, convertView);
            }
        }
        public class ViewHolderRight {

            @Bind(R.id.txt_consult_commiton)
            TextView txt_consult_commiton;

            @Bind(R.id.drawee_consult_item_photo)
            SimpleDraweeView drawee_consult_item_photo;

            @Bind(R.id.txt_consult_item)
            TextView txt_consult_item;

            public ViewHolderRight(View convertView) {
                ButterKnife.bind(this, convertView);
            }
        }
    }

    public void addDoctorReply(String replyContent) {
        if (replyContent.equals("")) {
            showTip("请输入需要回复的内容");
            return;
        }
        String CommitOn = DateUtil.date2Str(new Date(), "yyyy-MM-dd'T'HH:mm:ss");
        mConsultDetailPresenter.addDoctorReply(mCustomDetailBean.DoctorID, mDoctorEntity.Doctor_ID, mDoctorEntity.Name, mCustomerId, replyContent, CommitOn);
        edittxt_message.setText("");
//        sendCustomBroadcast(BROADFILTER_CONSULT_REPLAY);
    }




    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code);
            }
        }
    };

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            printResult(results);

            if (isLast) {

            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());
        String sn = null;       // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        edittxt_message.setText(resultBuffer.toString());
        edittxt_message.setSelection(edittxt_message.length());
    }

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }

    };

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 参数设置
     *
     * @param
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "5000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }

}