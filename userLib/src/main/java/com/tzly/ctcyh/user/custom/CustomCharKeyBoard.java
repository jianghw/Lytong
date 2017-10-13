package com.tzly.ctcyh.user.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tzly.ctcyh.user.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * 字符输入键盘
 */
public class CustomCharKeyBoard extends LinearLayout {

    TextView myTextA, myTextB, myTextC, myTextD, myTextE, myTextF, myTextG, myTextH, myTextI, myTextJ,
            myTextK, myTextL, myTextM, myTextN, myTextO, myTextP, myTextQ, myTextR, myTextS, myTextT,
            myTextU, myTextV, myTextW, myTextX, myTextY, myTextZ,
            myFinish, myNum;
    View myDel, myTab, mHide;
    ImageView myTabImg;

    public CustomCharKeyBoard(Context context) {
        super(context);
        init(context);
    }

    public CustomCharKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCharKeyBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_key_board_char, this, true);
        myTextA = (TextView) view.findViewById(R.id.input_a);
        myTextB = (TextView) view.findViewById(R.id.input_b);
        myTextC = (TextView) view.findViewById(R.id.input_c);
        myTextD = (TextView) view.findViewById(R.id.input_d);
        myTextE = (TextView) view.findViewById(R.id.input_e);
        myTextF = (TextView) view.findViewById(R.id.input_f);
        myTextG = (TextView) view.findViewById(R.id.input_g);
        myTextH = (TextView) view.findViewById(R.id.input_h);
        myTextI = (TextView) view.findViewById(R.id.input_i);
        myTextJ = (TextView) view.findViewById(R.id.input_j);
        myTextK = (TextView) view.findViewById(R.id.input_k);
        myTextL = (TextView) view.findViewById(R.id.input_l);
        myTextM = (TextView) view.findViewById(R.id.input_m);
        myTextN = (TextView) view.findViewById(R.id.input_n);
        myTextO = (TextView) view.findViewById(R.id.input_o);
        myTextP = (TextView) view.findViewById(R.id.input_p);
        myTextQ = (TextView) view.findViewById(R.id.input_q);
        myTextR = (TextView) view.findViewById(R.id.input_r);
        myTextS = (TextView) view.findViewById(R.id.input_s);
        myTextT = (TextView) view.findViewById(R.id.input_t);
        myTextU = (TextView) view.findViewById(R.id.input_u);
        myTextV = (TextView) view.findViewById(R.id.input_v);
        myTextW = (TextView) view.findViewById(R.id.input_w);
        myTextX = (TextView) view.findViewById(R.id.input_x);
        myTextY = (TextView) view.findViewById(R.id.input_y);
        myTextZ = (TextView) view.findViewById(R.id.input_z);


        myTabImg = (ImageView) view.findViewById(R.id.input_tab_img);
        myTab = view.findViewById(R.id.input_tab);
        myDel = view.findViewById(R.id.input_word_del);
        myFinish = (TextView) view.findViewById(R.id.input_word_finish);
        myNum = (TextView) view.findViewById(R.id.input_num);
        mHide = view.findViewById(R.id.input_word_hide);
    }

    public ArrayList<TextView> getCharList() {
        ArrayList<TextView> list = new ArrayList<>();
        list.add(myTextA);
        list.add(myTextB);
        list.add(myTextC);
        list.add(myTextD);
        list.add(myTextE);
        list.add(myTextF);
        list.add(myTextG);
        list.add(myTextH);
        list.add(myTextI);
        list.add(myTextK);
        list.add(myTextL);
        list.add(myTextM);
        list.add(myTextN);
        list.add(myTextO);
        list.add(myTextP);
        list.add(myTextQ);
        list.add(myTextR);
        list.add(myTextS);
        list.add(myTextU);
        list.add(myTextV);
        list.add(myTextW);
        list.add(myTextX);
        list.add(myTextY);
        list.add(myTextZ);
        list.add(myTextJ);
        list.add(myTextT);
        return list;
    }

    //小写转大写
    public void changeUpper() {
        for (int i = 0; i < getCharList().size(); i++) {
            getCharList().get(i).setText(getCharList().get(i).getText().toString().toUpperCase());
        }
    }

    //小写转大写
    public void changeLower() {
        for (int i = 0; i < getCharList().size(); i++) {
            getCharList().get(i).setText(getCharList().get(i).getText().toString().toLowerCase());
        }
    }

    ArrayList<TextView> list;

    public ArrayList<TextView> getRandomList() {
        list = getCharList();
        String[] arr = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
                "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        List<String> strs = Arrays.asList(arr);
        Collections.shuffle(strs);
        for (int i = 0; i < strs.size(); i++) {
            list.get(i).setText(strs.get(i));
        }
        return list;
    }

    public View getCharDelView() {
        return myDel;
    }

    public View getCharHideView() {
        return mHide;
    }

    public TextView getCharFinishView() {
        return myFinish;
    }

    public TextView getChangeNumView() {
        return myNum;
    }

    //大小写
    public View getCharTabView() {
        return myTab;
    }

    //大小写背景图片
    public View getCharTabViewImg() {
        return myTabImg;
    }

}