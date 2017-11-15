package com.zantong.mobilecttx.msg_v;

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
import com.tzly.ctcyh.router.base.JxBaseRefreshFragment;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.msg_p.IMegDetailAtyContract;
import com.zantong.mobilecttx.msg_p.MegDetailAtyPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.bean.Meg;
import com.zantong.mobilecttx.user.bean.MessageDetailResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tzly.ctcyh.router.custom.image.ImageOptions;

/**
 * 消息详情
 * 最新修改日期：2017-05-10
 */

public class MegDetailFragment extends JxBaseRefreshFragment
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
    protected int initFragmentView() {
        return R.layout.activity_meg_detail;
    }

    @Override
    protected void bindFragmentView(View view) {

        headImage = (ImageView) view.findViewById(R.id.img_head);
        titleTv = (TextView) view.findViewById(R.id.tv_title);
        contentTv = (TextView) view.findViewById(R.id.tv_content);
        timeTv = (TextView) view.findViewById(R.id.tv_time);

        MegDetailAtyPresenter mPresenter =
                new MegDetailAtyPresenter(Injection.provideRepository(getActivity()), this);
    }

    @Override
    protected void onFirstDataVisible() {
        if (mPresenter != null) mPresenter.findMessageDetail();
    }

    @Override
    protected void onLoadMoreData() {
        onFirstDataVisible();
    }

    @Override
    protected void onRefreshData() {
        onFirstDataVisible();
    }

    @Override
    public void setPresenter(IMegDetailAtyContract.IMegDetailAtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void findMessageDetailSucceed(MessageDetailResponse messageResult) {
        Meg message = messageResult.getData();
        if (message == null) return;

        setResultForRefresh();

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

    /**
     * 前页面刷新
     */
    private void setResultForRefresh() {
        getActivity().setResult(MainGlobal.resultCode.meg_detail_del);
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
        String title = "信息";
        if (titleTv != null) title = titleTv.getText().toString().trim();

        MainRouter.gotoHtmlActivity(getActivity(), title, contentHrefLine);
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
        toastShort(message);
    }

    @Override
    public int getIdByArguments() {
        return getArguments().getInt(STR_TYPE);
    }


}
