package site.recomi.studentmanagement.gui.fragments.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import site.recomi.studentmanagement.R;
import site.recomi.studentmanagement.entity.GirdButtonEntity;
import site.recomi.studentmanagement.entity.TitleAndIconEntity;
import site.recomi.studentmanagement.entity.UserSharingPost;
import site.recomi.studentmanagement.gui.activities.BookActivity;
import site.recomi.studentmanagement.gui.activities.CampusAssociationActivity;
import site.recomi.studentmanagement.gui.activities.ClassScheduleActivity;
import site.recomi.studentmanagement.gui.activities.GradeActivity;
import site.recomi.studentmanagement.gui.adapter.Base.BaseMultiItemTypeRecyclerViewAdapter;
import site.recomi.studentmanagement.gui.adapter.Base.BaseNestedSVOnScrollChangeListener;
import site.recomi.studentmanagement.gui.adapter.Base.BaseRecycleViewAdapter;
import site.recomi.studentmanagement.gui.adapter.Delegetes.SharingPostDelegete;
import site.recomi.studentmanagement.gui.adapter.MultiItemTypeSupport;
import site.recomi.studentmanagement.gui.adapter.PagerViewAdapter;
import site.recomi.studentmanagement.gui.adapter.ViewHolder;
import site.recomi.studentmanagement.gui.listenner.BaseRecyclerItemTouchListener;

public class MineFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.recy_mine_features)
    RecyclerView recy_features;

    View mView;
    Context mContext;
    ViewPager vp;
    MultiItemTypeSupport multiItemTypeSupport;
    BaseRecycleViewAdapter<TitleAndIconEntity> adapter;
    List<TitleAndIconEntity> moreFeaturesList;

    int testData = 0;
    int testDataTop = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine, container, false);
        mContext = mView.getContext();
        ButterKnife.bind(this, mView);   //绑定ButterKnife

        initView();                 //加载视图布局
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 加载视图布局
     */
    private void initView() {
        moreFeaturesList = new ArrayList<>();
        moreFeaturesList.add(new TitleAndIconEntity("我的班级", R.drawable.ic_action_bookmark));
        moreFeaturesList.add(new TitleAndIconEntity("小组", R.drawable.ic_action_bookmark));
        moreFeaturesList.add(new TitleAndIconEntity("笔记", R.drawable.ic_action_bookmark));
        moreFeaturesList.add(new TitleAndIconEntity("成绩", R.drawable.ic_action_bookmark));
        moreFeaturesList.add(new TitleAndIconEntity("浏览历史", R.drawable.ic_action_bookmark));
        moreFeaturesList.add(new TitleAndIconEntity("我的收藏", R.drawable.ic_action_bookmark));
        moreFeaturesList.add(new TitleAndIconEntity("更多", R.drawable.ic_action_bookmark));
        moreFeaturesList.add(new TitleAndIconEntity("设置", R.drawable.ic_action_bookmark));
        adapter = new BaseRecycleViewAdapter<TitleAndIconEntity>(getContext(), moreFeaturesList, R.layout.item_features_list) {
            @Override
            public void convert(ViewHolder holder, TitleAndIconEntity titleAndIconEntity, int position) {
                holder.setText(R.id.tv_feature_title,titleAndIconEntity.getTitle());
                holder.setImageResource(R.id.img_feature_icon,titleAndIconEntity.getResId());
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recy_features.setLayoutManager(manager);
        recy_features.setAdapter(adapter);
        recy_features.setNestedScrollingEnabled(false);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {

    }
}