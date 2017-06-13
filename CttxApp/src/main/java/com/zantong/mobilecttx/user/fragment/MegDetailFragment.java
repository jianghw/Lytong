package com.zantong.mobilecttx.user.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.PullableBaseFragment;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.eventbus.GetMsgAgainEvent;
import com.zantong.mobilecttx.interf.IMegDetailAtyContract;
import com.zantong.mobilecttx.user.activity.MegDetailActivity;
import com.zantong.mobilecttx.user.bean.Meg;
import com.zantong.mobilecttx.user.bean.MessageDetailResult;
import com.zantong.mobilecttx.utils.ImageOptions;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 消息详情
 * 最新修改日期：2017-05-10
 */

public class MegDetailFragment extends PullableBaseFragment
        implements IMegDetailAtyContract.IMegDetailAtyView {

    private static String STR_TYPE = "messageDetailId";

    private ImageView headImage;
    private TextView titleTv;
    private TextView contentTv;
    private TextView timeTv;
    private IMegDetailAtyContract.IMegDetailAtyPresenter mPresenter;

    public static MegDetailFragment newInstance(String id) {
        MegDetailFragment fragment = new MegDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(STR_TYPE, Integer.valueOf(id));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.activity_meg_detail;
    }

    @Override
    protected void initFragmentView(View view) {
        headImage = (ImageView) view.findViewById(R.id.img_head);
        titleTv = (TextView) view.findViewById(R.id.tv_title);
        contentTv = (TextView) view.findViewById(R.id.tv_content);
        timeTv = (TextView) view.findViewById(R.id.tv_time);
    }

    @Override
    protected void onLoadMoreData() {
        loadingData();
    }

    @Override
    protected void onRefreshData() {
        loadingData();
    }

    @Override
    public void setPresenter(IMegDetailAtyContract.IMegDetailAtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onSubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unSubscribe();
    }

    /**
     * 可下拉刷新
     *
     * @return true
     */
    @Override
    protected boolean isRefresh() {
        return true;
    }

    /**
     * 不可加载更多
     *
     * @return false
     */
    @Override
    protected boolean isLoadMore() {
        return false;
    }

    @Override
    protected void loadingData() {
        if (mPresenter != null) mPresenter.findMessageDetail();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void findMessageDetailSucceed(MessageDetailResult messageResult) {
        Meg message = messageResult.getData();
        if (message == null) {
            onShowFailed();
        } else {
            onShowContent();
            setResultForRefresh();

            EventBus.getDefault().postSticky(new GetMsgAgainEvent(messageResult.getResponseCode() == 2000));

            if (!TextUtils.isEmpty(message.getImage()) && message.getImage() != null)
                ImageLoader.getInstance().displayImage(
                        message.getImage(),
                        headImage,
                        ImageOptions.getDefaultOptions());

            titleTv.setText(message.getTitle());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.SIMPLIFIED_CHINESE);
            Long time = Long.valueOf(message.getTime());
            Date date = new Date(time);
            timeTv.setText("时间：" + simpleDateFormat.format(date));

            String content = message.getContent();
            if (!TextUtils.isEmpty(content) && content.contains("target=\"_self\"")) {
                customDisplaysHyperlinks(content);
            } else {
                contentTv.setText(Html.fromHtml(content));
                contentTv.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接为可点击状态
            }
        }
    }

    private void setResultForRefresh() {
        MegDetailActivity activity = (MegDetailActivity) getActivity();
        activity.setResultForRefresh();
    }

    private void customDisplaysHyperlinks(String content) {
        Pattern pattern = Pattern.compile("<a href=\\\"(.*?)\\\" target=\\\"(.*?)\\\".*?>(.*?)<\\/a>");
        Matcher matcher = pattern.matcher(content);
        int start = 0, end = 0;
        while (matcher.find()) {
            start = matcher.start();
            end = matcher.end();
        }
        //超链接文本
        String lineString = content.substring(start, end);
        String contentString = matcherContent(lineString);
        final String contentHrefLine = matcherHrefLine(lineString);

        //显示的新文本
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(content.substring(0, start));
        stringBuilder.append(contentString);
        stringBuilder.append(content.substring(end, content.length()));
        String newContent = stringBuilder.toString();

        SpannableString spannableString = new SpannableString(newContent);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(Color.parseColor("#ff33b5e5"));//设置超链接的颜色
                textPaint.setUnderlineText(true);
            }

            @Override
            public void onClick(View widget) {
                // 单击事件处理
                internalBrowser(contentHrefLine);
            }
        };
        spannableString.setSpan(span, start, start + contentString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        contentTv.setText(spannableString);
        contentTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 跳转应用内部浏览器
     */
    private void internalBrowser(String contentHrefLine) {
        PublicData.getInstance().webviewUrl = contentHrefLine;
        PublicData.getInstance().webviewTitle = titleTv.getText().toString();
        PublicData.getInstance().isCheckLogin = true;
        Act.getInstance().gotoIntent(getActivity(), BrowserActivity.class);
    }

    /**
     * 超链接文本中的内容
     */
    public String matcherContent(String content) {
        Pattern patternStart = Pattern.compile("<a href=\\\"(.*?)\\\" target=\\\"(.*?)\\\".*?>");
        Matcher matcherStart = patternStart.matcher(content);
        int start = 0;
        while (matcherStart.find()) {
            start = matcherStart.end();
        }
        Pattern patternEnd = Pattern.compile("<\\/a>");
        Matcher matcherEnd = patternEnd.matcher(content);
        int end = 0;
        while (matcherEnd.find()) {
            end = matcherEnd.start();
        }
        return content.substring(start, end);
    }

    /**
     * 网址
     */
    public static String matcherHrefLine(String content) {
        Pattern patternStart = Pattern.compile("<a href=\\\"");
        Matcher matcherStart = patternStart.matcher(content);
        int start = 0;
        while (matcherStart.find()) {
            start = matcherStart.end();
        }
        Pattern patternEnd = Pattern.compile("\\\" target=\\\"(.*?)\\\".*?>");
        Matcher matcherEnd = patternEnd.matcher(content);
        int end = 0;
        while (matcherEnd.find()) {
            end = matcherEnd.start();
        }
        return content.substring(start, end);
    }

    @Override
    public void findMessageDetailError(String message) {
        onShowFailed();
        ToastUtils.showShort(getContext().getApplicationContext(), message);
    }

    @Override
    public int getIdByArguments() {
        return getArguments().getInt(STR_TYPE);
    }


}
