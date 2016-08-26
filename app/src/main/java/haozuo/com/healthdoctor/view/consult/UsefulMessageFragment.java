package haozuo.com.healthdoctor.view.consult;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.contract.UsefulMessageContract;
import haozuo.com.healthdoctor.framework.SysConfig;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.view.threePart.common.DrawableClickableEditText;

/**
 * Created by hzguest3 on 2016/8/9.
 */
public class UsefulMessageFragment extends AbstractView implements UsefulMessageContract.IUsefulMessageView, DrawableClickableEditText.DrawableRightListener {
    Context mContext;
    View rootView;
    private UsefulMessageContract.IUsefulMessagePresenter mIUsefulMessagePresenter;
    private UsefulMessageAdapter mUsefulMessageAdapter;
    private static ConsultReplyBean mConsultReplyBean;
    private List<UsefulExpressionBean> mSelectedExpressionMap;
    private List<ExpressionConst> mExpressionConstList;
    private String mReplyContent;

    @Bind(R.id.txt_reportdetail_content)
    TextView txt_reportdetail_content;
    @Bind(R.id.usefulmessage_list)
    ListView usefulmessage_list;

    @OnClick(R.id.btn_nextstep)
    public void nextPage(View v) {
        showDialogPage();
    }

    public UsefulMessageFragment() {
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mIUsefulMessagePresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    public static UsefulMessageFragment newInstance(@NonNull ConsultReplyBean consultReplyItem) {
        UsefulMessageFragment fragment = new UsefulMessageFragment();
        mConsultReplyBean = consultReplyItem;
        return fragment;
    }

    @Override
    public void onResume(){
        super.onResume();
        mIUsefulMessagePresenter.start();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.lv_usefulmessage, container, false);
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

        mExpressionConstList = new ArrayList<ExpressionConst>();
        mExpressionConstList.addAll(SysConfig.getExpressionConstList());

        final DrawableClickableEditText et_TitleBar_search = (DrawableClickableEditText) getActivity().findViewById(R.id.et_TitleBar_search);
        //搜索框按钮监听
        et_TitleBar_search.setDrawableRightListener(new DrawableClickableEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                String keyword = et_TitleBar_search.getText().toString();
                mIUsefulMessagePresenter.searchUsefulExpression(keyword);
            }
        });
        //搜索框回车监听
        et_TitleBar_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER&& event.getAction() == KeyEvent.ACTION_UP) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
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
    public void onStop() {
        super.onStop();
        mIUsefulMessagePresenter.cancelRequest();
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
    public void onDrawableRightClick(View view) {
    }

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

    public void showDialogPage() {
        final Dialog dialog = new Dialog(mContext, R.style.Dialog_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_usefulmessage);
        refreshExpressionContent(dialog);
        dialog.show();
        dialog.hide();

        Window win = dialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        win.setGravity(Gravity.BOTTOM);
        win.setWindowAnimations(R.style.Dialog_Fullscreen);
        WindowManager.LayoutParams lp = win.getAttributes();
        Display d = getActivity().getWindowManager().getDefaultDisplay();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (int) (d.getHeight() * 0.8);
        win.setAttributes(lp);

        LinearLayout cbgroup_expression = (LinearLayout)dialog.findViewById(R.id.cbgroup_expression);
        for(int i=0;i<mExpressionConstList.size();i++){
            final int finalI = i;
            final CheckBox checkBox = new CheckBox(mContext);
            checkBox.setText(mExpressionConstList.get(i).Content);
            checkBox.setBackgroundResource(0);
            checkBox.setButtonDrawable(0);
            Drawable drawableLeft= getResources().getDrawable(R.drawable.bgcheckbox_usefulmessage);
            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
            checkBox.setCompoundDrawables(drawableLeft,null,null,null);
            checkBox.setCompoundDrawablePadding(5);
            checkBox.setChecked(mExpressionConstList.get(i).IsChecked);
            checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mExpressionConstList.get(finalI).IsChecked =  !mExpressionConstList.get(finalI).IsChecked;
                        refreshExpressionContent(dialog);
                    }
            }
            );
            cbgroup_expression.addView(checkBox);
        }
    }

    public String refreshExpressionContent(final Dialog dialog) {
        String Content = "";
        for (ExpressionConst e : mExpressionConstList) {
            if (e.Postion < 0 && e.IsChecked) {
                if (!Content.equals("")){
                    Content +="\n";
                }
                Content += e.Content;
            }
        }
        for (int i = 0; i < mSelectedExpressionMap.size(); i++) {
            if (!Content.equals("")){
                Content +="\n";
            }
            Content += (i + 1) + "." + mSelectedExpressionMap.get(i).Content ;
        }
        for (ExpressionConst e : mExpressionConstList) {
            if (e.Postion > 0 && e.IsChecked) {
                if (!Content.equals("")){
                    Content +="\n";
                }
                Content += e.Content ;
            }
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_hideDialog:
                        dialog.dismiss();
//                        dialog.hide();
                        break;
                    case R.id.btn_submit:
                        dialog.dismiss();
                        EditText et_Expression = (EditText) dialog.findViewById(R.id.et_Expression);
                        mReplyContent = (String) et_Expression.getText().toString();
                        Intent intent = new Intent();
                        intent.putExtra(String.valueOf(ConsultDetailFragment.RESULT_EXPRESSION), mReplyContent);
                        getActivity().setResult(ConsultDetailFragment.RESULT_EXPRESSION, intent);
                        getActivity().finish();
                        break;
                }
            }
        };

        Button btn_hideDialog = (Button) dialog.findViewById(R.id.btn_hideDialog);
        Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
        btn_hideDialog.setOnClickListener(clickListener);
        btn_submit.setOnClickListener(clickListener);
        EditText et_Expression = (EditText) dialog.findViewById(R.id.et_Expression);
        et_Expression.setText(Content);
        return Content;
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
                convertView = myInflater.inflate(R.layout.lvitem_usefulmessage, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
                convertView.setOnClickListener(clickListener);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final UsefulExpressionBean usefulExpressionEntity = dataSource.get(position);
            holder.messageContent.setText((position + 1) + "." + usefulExpressionEntity.Content);
            for (int i = 0; i < mSelectedExpressionMap.size(); i++) {
                if (mSelectedExpressionMap.get(i).Id.equals(usefulExpressionEntity.Id)) {
                    holder.messageContent.setChecked(true);
                    break;
                } else {
                    holder.messageContent.setChecked(false);
                }
            }
            holder.messageContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) v;
                    UsefulExpressionBean selectedExpression = new UsefulExpressionBean();
                    if (checkBox.isChecked()) {
                        selectedExpression.Id = usefulExpressionEntity.Id;
                        selectedExpression.Content = usefulExpressionEntity.Content;
                        mSelectedExpressionMap.add(selectedExpression);
                    } else {
                        for (int i = 0; i < mSelectedExpressionMap.size(); i++) {
                            if (mSelectedExpressionMap.get(i).Id.equals(usefulExpressionEntity.Id)) {
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