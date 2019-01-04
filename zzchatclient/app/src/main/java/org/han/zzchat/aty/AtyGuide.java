package org.han.zzchat.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import org.han.zzchat.adapter.AdapterGuideViewPager;

import org.han.zzchat.R;

import java.util.ArrayList;
import java.util.List;

public class AtyGuide extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private int[] indicatorDotIds={R.id.iv_indicator_dot1,R.id.iv_indicator_dot2,R.id.iv_indicator_dot3};
    private List<View> viewList;
    private ImageView imageViews[]=new ImageView[3];
    private Button btnToMain;
    private ViewPager viewPager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.aty_guide);

        initViews();
    }

    private void initViews() {
        //记载view页面
        //项目使用adapter是，加载item布局时会使用Layoutinflater
        final LayoutInflater inflater =LayoutInflater.from(this);
        viewList=new ArrayList<>();
        viewList.add(inflater.inflate(R.layout.guide_page1,null));
        viewList.add(inflater.inflate(R.layout.guide_page2,null));
        viewList.add(inflater.inflate(R.layout.guide_page3,null));
        //给每个-点imageView绑定id值
        for(int i=0;i<indicatorDotIds.length;i++){
            imageViews[i]=(ImageView)findViewById(indicatorDotIds[i]);
        }
        AdapterGuideViewPager adapterGuideViewPager = new AdapterGuideViewPager(this,viewList);
        viewPager= (ViewPager) findViewById(R.id.vp_guide);
        viewPager.setAdapter(adapterGuideViewPager);
        viewPager.addOnAdapterChangeListener((ViewPager.OnAdapterChangeListener) this);

        btnToMain= (Button) viewList.get(2).findViewById(R.id.btn_to_main);
        btnToMain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AtyGuide.this,AtyLoginOrRegister.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    /**
     * 页面更改时更改指示符
     * 当前页面是第几页，要给用户一个明显的提示，利用 两个不同颜色的小圆点，但是想知道移动的改变，就要实现监听事件
     * @param位置当前页面ID
     */
    @Override
    public void onPageSelected(int position) {
        for(int i=0;i<indicatorDotIds.length;i++){
            if (i!=position){
                imageViews[i].setImageResource(R.drawable.unselected);
            }else {
                imageViews[i].setImageResource(R.drawable.selected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
