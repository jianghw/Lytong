package com.zantong.mobilecttx.widght;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * RecyclerView的FooterView，简单的展示一个TextView
 */
public class CustomNumKeyBoard extends LinearLayout {

    TextView myText1,myText2,myText3,myText4,myText5,myText6,myText7,myText8,myText9,myText0,myFinish,myAbc;
    View mNumHide,myDel;

    public CustomNumKeyBoard(Context context) {
        super(context);
        init(context);
    }

    public CustomNumKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomNumKeyBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_input, this, true);
        myText1 = (TextView) view.findViewById(R.id.input_one);
        myText2 = (TextView) view.findViewById(R.id.input_two);
        myText3 = (TextView) view.findViewById(R.id.input_three);
        myText4 = (TextView) view.findViewById(R.id.input_four);
        myText5 = (TextView) view.findViewById(R.id.input_five);
        myText6 = (TextView) view.findViewById(R.id.input_six);
        myText7 = (TextView) view.findViewById(R.id.input_seven);
        myText8 = (TextView) view.findViewById(R.id.input_eight);
        myText9 = (TextView) view.findViewById(R.id.input_nine);
        myText0 = (TextView) view.findViewById(R.id.input_zero);
        myDel =  view.findViewById(R.id.input_del);
        myFinish = (TextView) view.findViewById(R.id.input_finish);
        myAbc = (TextView) view.findViewById(R.id.input_abc);
        mNumHide = view.findViewById(R.id.input_num_hide);
    }

    public ArrayList<TextView> getNumList(){
        ArrayList<TextView> list = new ArrayList<>();
        list.add(myText1);
        list.add(myText2);
        list.add(myText3);
        list.add(myText4);
        list.add(myText5);
        list.add(myText6);
        list.add(myText7);
        list.add(myText8);
        list.add(myText9);
        list.add(myText0);
        return list;
    }
    ArrayList<TextView> list;


    public ArrayList<TextView> getRandomList(){
        list = getNumList();
        String[] arr = new String[] {"1", "2","3", "4","5", "6","7", "8","9", "0"};
        List<String> strs = Arrays.asList(arr);
        Collections.shuffle(strs);
        for (int i=0; i < strs.size(); i++){
            list.get(i).setText(strs.get(i));
        }
        return list;
    }

    private ArrayList<TextView> getList(){
        return list;
    }
    public View getNumDelView(){
        return myDel;
    }
    public View getNumHideView(){
        return mNumHide;
    }
    public TextView getNumFinishView(){
        return myFinish;
    }
    public TextView getChangeAbcView(){
        return myAbc;
    }

}