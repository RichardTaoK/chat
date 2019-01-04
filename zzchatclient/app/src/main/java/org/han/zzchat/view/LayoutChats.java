package org.han.zzchat.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.han.zzchat.R;
public class LayoutChats extends Fragment {

    private View rootView;

    /**
     * Step1:定义Fragment的布局，就是fragment显示内容的,表示碎片布局活动
     * Step 2:自定义一个Fragment类,需要继承Fragment或者他的子类,重写onCreateView()方法
     * 在该方法中调用:inflater.inflate()方法加载Fragment的布局文件,接着返回加载的view对象
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       rootView=inflater.inflate(R.layout.layout_chats,container,false);
       return rootView;
    }
}
