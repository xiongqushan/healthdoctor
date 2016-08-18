package haozuo.com.healthdoctor.view.consult;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.ExpressionConst;
import haozuo.com.healthdoctor.bean.UsefulExpressionBean;
import haozuo.com.healthdoctor.contract.IBasePresenter;
import haozuo.com.healthdoctor.contract.UsefulMessageContract;
import haozuo.com.healthdoctor.framework.SysConfig;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.view.threePart.common.DrawableClickableEditText;

/**
 * Created by hzguest3 on 2016/8/9.
 */
public class UsefulMessageFragment extends AbstractView implements UsefulMessageContract.IUsefulMessageView,DrawableClickableEditText.DrawableRightListener {
    Context mContext;
    View rootView;
    private UsefulMessageContract.IUsefulMessagePresenter mIUsefulMessagePresenter;
    private UsefulMessageAdapter mUsefulMessageAdapter;
    private static ConsultReplyBean mConsultReplyBean;
//    private Map<String,String> mSelectedExpressionMap;
    private List<UsefulExpressionBean> mSelectedExpressionMap;
    private List<ExpressionConst> mExpressionConstList;

    @Bind(R.id.txt_reportdetail_content)
    TextView txt_reportdetail_content;
    @Bind(R.id.usefulmessage_list)
    ListView usefulmessage_list;
//    @Bind(R.id.et_Expression)
//    EditText et_Expression;

    @OnClick(R.id.btn_nextstep)
    public void nextPage(View v) {
        showDialogPage();
//        refreshExpressionContent();
    }

    public UsefulMessageFragment() {}

    @Override
    protected IBasePresenter getPresenter() {
        return mIUsefulMessagePresenter;
    }

    public static UsefulMessageFragment newInstance(@NonNull ConsultReplyBean consultReplyItem) {
        UsefulMessageFragment fragment = new UsefulMessageFragment();
        mConsultReplyBean = consultReplyItem;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_usefulmessage_list, container, false);
            ButterKnife.bind(this, rootView);
        }
        mSelectedExpressionMap = new ArrayList<>();
        setConsultContent();
        mUsefulMessageAdapter = new UsefulMessageAdapter(mContext);
        usefulmessage_list.setAdapter(mUsefulMessageAdapter);
//        usefulmessage_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CheckBox checkBox = (CheckBox) view.findViewById(R.id.txt_message_content);
//                UsefulExpressionBean selectedExpressionItem = mUsefulMessageAdapter.getDataSource().get(position);
//                if(checkBox.isChecked()){
//                    mSelectedExpressionList.add(selectedExpressionItem);
//                }
//                else {
//                    mSelectedExpressionList.remove(selectedExpressionItem);
//                }
//            }
//        });
        mIUsefulMessagePresenter.getDefaultUsefulExpression();
        mExpressionConstList = SysConfig.getExpressionConstList();

        final DrawableClickableEditText et_TitleBar_search = (DrawableClickableEditText) getActivity().findViewById(R.id.et_TitleBar_search);
        et_TitleBar_search.setDrawableRightListener(new DrawableClickableEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                String keyword = et_TitleBar_search.getText().toString();
                mIUsefulMessagePresenter.searchUsefulExpression(keyword);
            }
        });

        et_TitleBar_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm.isActive()){
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0 );
                        String keyword = et_TitleBar_search.getText().toString();
                        mIUsefulMessagePresenter.searchUsefulExpression(keyword);
                    }
                    return true;
                }
                return false;
            }
        });
        return rootView;
    }

    @Override
    public void setPresenter(UsefulMessageContract.IUsefulMessagePresenter presenter) {
        mIUsefulMessagePresenter = presenter;
    }

    @Override
    public void refreshUsefulMessageAdapter(List<UsefulExpressionBean> dataList) {
        mUsefulMessageAdapter.refresh(dataList);
        mUsefulMessageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDrawableRightClick(View view) {}

    public void setConsultContent() {
        switch (mConsultReplyBean.ConsultType) {
            case 1:
            case 3:
                txt_reportdetail_content.setText(mConsultReplyBean.Content);
                break;
            case 2:
                break;
            case 4:
                break;
            default:
                break;
        }
    }

    public void showDialogPage(){
        Dialog dialog = new Dialog(mContext, R.style.Dialog_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(refreshExpressionContent());
        dialog.show();
        Window win = dialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        win.setGravity(Gravity.BOTTOM);
        win.setWindowAnimations(R.style.Dialog_Fullscreen);
        WindowManager.LayoutParams lp = win.getAttributes();
        Display d = getActivity().getWindowManager().getDefaultDisplay();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (int) (d.getHeight() * 0.8);
        win.setAttributes(lp);
    }

    public View refreshExpressionContent(){
        String Content = "";
        for (ExpressionConst e : mExpressionConstList){
            if (e.Postion<0){
                Content += e.Content+"\n";
            }
        }
        for (int i =0;i<mSelectedExpressionMap.size();i++){
            Content += (i+1)+"."+mSelectedExpressionMap.get(i).Content+"\n";
        }
        for (ExpressionConst e : mExpressionConstList){
            if (e.Postion>0){
                Content += e.Content+"\n";
            }
        }

        //动态加载布局生成View对象
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View longinDialogView = layoutInflater.inflate(R.layout.fragment_usefulmessage_dialog, null);

        //获取布局中的控件
        EditText et_Expression = (EditText)longinDialogView.findViewById(R.id.et_Expression);
        et_Expression.setText(Content);

        return longinDialogView;
    }

    class UsefulMessageAdapter extends BaseAdapter {
        LayoutInflater myInflater;
        List<UsefulExpressionBean> dataSource;
        View.OnClickListener clickListener = null;

        public UsefulMessageAdapter(Context context) {
            this.myInflater = LayoutInflater.from(context);
            dataSource = new ArrayList<>();
        }

        public void refresh(List<UsefulExpressionBean> dataList) {
            dataSource.clear();
            dataSource.addAll(dataList);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return dataSource.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = myInflater.inflate(R.layout.fragment_usefulmessage_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
                convertView.setOnClickListener(clickListener);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final UsefulExpressionBean usefulExpressionEntity = dataSource.get(position);
            holder.messageContent.setText((position+1)+"."+usefulExpressionEntity.Content);
//            holder.messageContent.setChecked(usefulExpressionEntity.IsChecked);
//            for (String k : mSelectedExpressionMap.keySet()){
//                if (k.equals(usefulExpressionEntity.Id)){
//                    holder.messageContent.setChecked(true);
//                    break;
//                }
//                else {
//                    holder.messageContent.setChecked(false);
//                }
//            }
            for (int i=0;i<mSelectedExpressionMap.size();i++){
                if (mSelectedExpressionMap.get(i).Id.equals(usefulExpressionEntity.Id)){
                    holder.messageContent.setChecked(true);
                    break;
                } else {
                    holder.messageContent.setChecked(false);
                }
            }
            holder.messageContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox)v;
                    UsefulExpressionBean selectedExpression = new UsefulExpressionBean();
//                    usefulExpressionEntity.IsChecked = checkBox.isChecked();
                    if(checkBox.isChecked()){
//                        mSelectedExpressionMap.put(usefulExpressionEntity.Id,usefulExpressionEntity.Content);
                        selectedExpression.Id = usefulExpressionEntity.Id;
                        selectedExpression.Content = usefulExpressionEntity.Content;
                        mSelectedExpressionMap.add(selectedExpression);
                    }
                    else {
//                        for(String id : mSelectedExpressionMap.keySet()){
//                            if (usefulExpressionEntity.Id.equals(id)){
//                                mSelectedExpressionMap.remove(id);
//                            }
//                        }

                        for (int i =0;i<mSelectedExpressionMap.size();i++){
                            if (mSelectedExpressionMap.get(i).Id.equals(usefulExpressionEntity.Id)){
                                mSelectedExpressionMap.remove(i);
                            }
                        }
                    }
                }
            });

            return convertView;
        }

        public class ViewHolder {

            @Bind(R.id.txt_message_content)
            public CheckBox messageContent;

            public ViewHolder(View convertView) {
                ButterKnife.bind(this, convertView);
            }
        }
    }


}
