package site.recomi.studentmanagement.gui.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import site.recomi.studentmanagement.R;
import site.recomi.studentmanagement.gui.activities.base.MySwipeBackActivity;
import site.recomi.studentmanagement.gui.adapter.LoginPagerAdapter;
import site.recomi.studentmanagement.gui.fragments.login.FindPasswordFragment;
import site.recomi.studentmanagement.gui.fragments.login.SignInFragment;
import site.recomi.studentmanagement.gui.fragments.login.SignUpFragment;

public class LoginActivity extends MySwipeBackActivity {

    @BindView(R.id.viewpage_account)
    ViewPager pager;
    @BindView(R.id.tab_login)
    TabLayout tabLayout;
    private ImageView img_cursor;
    private Button button_page_login;
    private Button button_page_signup;
    private Button button_page_password;
    private CardView cardView;

    private String[] titles ;
    private ArrayList<Fragment> fragments;
    private int offset = 0;//移动条图片的偏移量
    private int currIndex = 0;//当前页面的编号
    private int bmpWidth;// 移动条图片的长度
    private int one = 0; //移动条滑动一页的距离
    private int two = 0; //滑动条移动两页的距离

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_base);
        initToolbarDefaultStyle(toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        titles= new String[]{
                getString(R.string.sign_in),
                getString(R.string.sign_up),
                getString(R.string.find_password)
        };
        fragments = new ArrayList<Fragment>();
        fragments.add(new SignInFragment());
        fragments.add(new SignUpFragment());
        fragments.add(new FindPasswordFragment());
        pager.setAdapter(new LoginPagerAdapter(mContext,getSupportFragmentManager(),fragments,titles));
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(pager,false);
    }


}