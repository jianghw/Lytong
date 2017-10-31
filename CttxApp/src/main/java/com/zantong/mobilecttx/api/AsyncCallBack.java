package com.zantong.mobilecttx.api;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.tzly.ctcyh.router.util.LogUtils;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.eventbus.ErrorEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;

import cn.qqtheme.framework.bean.BankResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class AsyncCallBack<T> implements Callback {
    private Gson gson;
    private Context context;
    private Object tag;
    private Class<T> clazz;
    private CallBack<T> callback;

    private HashMap<String, String> errorList;

    public AsyncCallBack() {
    }

    public HashMap<String, String> getErrMsg() {
        HashMap<String, String> errors = new HashMap<String, String>();
        errors.put("000000", "成功");
        errors.put("CFB001", "推荐人不存在");
        errors.put("CFB002", "不能重复获取赠险");
        errors.put("CFB003", "档案号不存在");
        errors.put("CFB004", "该手机号已注册");
        errors.put("CFB005", "该用户名不存在");
        errors.put("CFB006", "密码不正确");
        errors.put("CFB007", "该手机号未注册");
        errors.put("CFB008", "重试次数超限");
        errors.put("CFB009", "该车牌号已存在");
        errors.put("P602", "你的驾驶证已经失效,请通过公安交通管理部门查询");
        errors.put("P603", "您的牡丹交通卡状态不正常，不能办理此项业务");
        errors.put("P604", "您所输入违章记录编号不存在，请通过公安交通管理部门查询");
        errors.put("P605", "您输入的车牌号码不存在违章记录，请通过公安交通管理部门查询");
        errors.put("P606", "该笔违章已处理");
        errors.put("P607", "该笔违章已在交巡警总队接受处理，请通过公安交通管理部门查询");
        errors.put("P608", "您所持的驾驶证不能缴付运营车辆交通罚款");
        errors.put("P609", "您所持的驾驶证的准驾类型与电子警察记录的违章车辆类型不符，不能缴付该笔交通违章罚款");
        errors.put("P610", "本次违章处理后您的驾驶证记分将达到或超过12分，根据交巡警总队规定，您必须到上海市公安局交巡警总队机动支队大柏树处理点接受违章处理");
        errors.put("P611", "您所持的驾驶证已失效，不能进行交通违章罚款缴付。您所持的驾照失效原因，可通过公安交通管理部门查询");
        errors.put("P612", "交易未能处理，请用违章记录编号查询缴费结果");
        errors.put("P613", "交易未能处理");
        errors.put("P614", "非法交易代码");
        errors.put("P615", "通讯错误");
        errors.put("P616", "违法记录入库失败");
        errors.put("P617", "该档案编号没有对应的处罚决定书记录");
        errors.put("P618", "游标关闭失败");
        errors.put("P619", "无此交易代码");
        errors.put("P620", "您所输入通知书编号不存在，请通过公安交通管理部门查询");
        errors.put("P621", "您所输入处罚决定书不存在，请通过公安交通管理部门查询");
        errors.put("P622", "查询批量控制表无记录");
        errors.put("P623", "查询批量控制表出错");
        errors.put("P624", "正在进行处理，不能缴费");
        errors.put("P625", "查询驾驶员几本信息出错");
        errors.put("P626", "该笔不是你的违法记录，如有疑问请致电56317000交警总队查询");
        errors.put("P627", "牡丹交通卡最多可缴纳2辆不同号牌的机动车交通违法罚款，如需对可缴费罚款车辆重新设定，请选择人工服务");
        errors.put("P628", "查询车辆表失败");
        errors.put("P629", "车牌修改好后12小时后生效");
        errors.put("P630", "指定档案号不存在");
        errors.put("P631", "违章号长度不正确");
        errors.put("P640", "该罚单为异地驾驶员接受交警处罚所开罚单，请通过跨省异地交通违法罚款项目交纳");
        errors.put("P666", "交易未能处理");
        errors.put("P699", "未知错误");
        return errors;
    }


    public AsyncCallBack(Context context, Object tag, CallBack<T> callback, Class<T> clazz) {
        this.callback = callback;
        this.context = context;
        this.tag = tag;
        this.clazz = clazz;
        this.gson = new Gson();
        this.errorList = new HashMap<String, String>();
    }

    public AsyncCallBack(Context context, CallBack<T> callback, Class<T> clazz) {
        this(context, context, callback, clazz);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

        if (response.isSuccessful()) {
            try {
                String reader = response.body().string();
                LoginData.getInstance().mHashMap.put("htmlResponse", reader);
                LogUtils.i("reader===" + reader);
                if (!TextUtils.isEmpty(reader)) {
                    T t = gson.fromJson(reader, clazz);
                    BankResponse bankResponse = (BankResponse) t;

                    if (!"CIE999".equals(bankResponse.getSYS_HEAD().getReturnCode())
                            && !"cip.cfc.v001.01".equals(bankResponse.getSYS_HEAD().getTransServiceCode())) {
                        sendErrorMsg(context, tag, bankResponse);
                    }
                    callback.sendSuccessMessage(t);
                } else {
                    EventBus.getDefault().post(
                            new ErrorEvent(Config.ERROR_PARSER,
                                    Config.ERROR_PARSER_MSG, tag, context));
                    callback.sendFailMessage(Config.ERROR_PARSER, Config.ERROR_PARSER_MSG);
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                EventBus.getDefault().post(
                        new ErrorEvent(Config.ERROR_PARSER,
                                Config.ERROR_PARSER_MSG, tag, context));
                callback.sendFailMessage(Config.ERROR_PARSER, Config.ERROR_PARSER_MSG);
            } catch (JsonIOException e) {
                e.printStackTrace();
                EventBus.getDefault().post(
                        new ErrorEvent(Config.ERROR_PARSER,
                                Config.ERROR_PARSER_MSG, tag, context));
                callback.sendFailMessage(Config.ERROR_PARSER, Config.ERROR_PARSER_MSG);
            }
        } else {
            LogUtils.i("response.code:" + response.code());
            EventBus.getDefault().post(
                    new ErrorEvent(Config.ERROR_IO, Config.ERROR_IO_MSG, tag,
                            context));
            callback.sendFailMessage(Config.ERROR_IO, Config.ERROR_IO_MSG);
        }
    }

    /**
     * 发送错误信息
     *
     * @param context 上下文对象
     * @param tag     标签
     * @param bankResponse  返回结果
     */
    private void sendErrorMsg(Context context, Object tag, BankResponse bankResponse) {
        if (bankResponse != null) {
            String status = "";
            String returnStatus = bankResponse.getSYS_HEAD().getReturnCode();
            try {
                status = returnStatus;
                LogUtils.i("ErrorMsgCode:" + status);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            String msg = "出错了,请稍后重试";
            if (!status.equals(Config.OK)) {
                String errMsg = bankResponse.getSYS_HEAD().getReturnMessage();
                msg = TextUtils.isEmpty(errMsg) ? msg : errMsg;
                LogUtils.i("status:" + status + ",Msg:" + msg);
                EventBus.getDefault().post(
                        new ErrorEvent(status,
                                msg, tag, context));
            }
            if (bankResponse.getSYS_HEAD().getTransServiceCode().equals("cip.cfc.v001.01")
                    && bankResponse.getSYS_HEAD().getReturnCode().equals("CIE999")) {

                EventBus.getDefault().post(new ErrorEvent(status, msg, tag, context));
            }
        } else {
            EventBus.getDefault().post(
                    new ErrorEvent(Config.ERROR_PARSER, Config.ERROR_PARSER_MSG, tag, context));

            callback.sendFailMessage(Config.ERROR_PARSER, Config.ERROR_PARSER_MSG);
        }
    }

    public HashMap<String, String> getErrorList() {
        if (errorList.size() == 0) {
            return getErrMsg();
        }
        return errorList;
    }

    @Override
    public void onFailure(Call call, IOException ex) {
        String msg = ex.getMessage();
        LogUtils.i("failed msg:" + msg);
        if ("Canceled".equals(msg)) {
            EventBus.getDefault()
                    .post(new ErrorEvent(Config.ERROR_IO, Config.ERROR_IO_MSG, tag,
                            context));
            callback.sendFailMessage(Config.ERROR_IO, Config.ERROR_IO_MSG);
        } else {
            EventBus.getDefault()
                    .post(new ErrorEvent(Config.ERROR_IO, Config.ERROR_IO_MSG, tag,
                            context));
            callback.sendFailMessage(Config.ERROR_IO, Config.ERROR_IO_MSG);
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public CallBack<T> getCallback() {
        return callback;
    }

    public void setCallback(CallBack<T> callback) {
        this.callback = callback;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }


}
