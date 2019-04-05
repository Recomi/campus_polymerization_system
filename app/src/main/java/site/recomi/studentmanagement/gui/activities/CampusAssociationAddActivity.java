package site.recomi.studentmanagement.gui.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import site.recomi.studentmanagement.R;
import site.recomi.studentmanagement.gui.adapter.BaseRecycleViewAdapter;
import site.recomi.studentmanagement.gui.adapter.ViewHolder;
import site.recomi.studentmanagement.other.CampusAssociationItem;

public class CampusAssociationAddActivity extends AppCompatActivity {
    @BindView(R.id.rv)
    public RecyclerView rv;
    BaseRecycleViewAdapter<CampusAssociationItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_association_add);
        ButterKnife.bind(this);

        List<CampusAssociationItem> lists = new ArrayList<>();
        lists.add(new CampusAssociationItem(1,"","",false));
        lists.add(new CampusAssociationItem(1,"","",false));
        lists.add(new CampusAssociationItem(1,"","",false));
        lists.add(new CampusAssociationItem(1,"","",false));
        lists.add(new CampusAssociationItem(1,"","",false));
        adapter = new BaseRecycleViewAdapter<CampusAssociationItem>(this ,lists , R.layout.recycler_view_item_1) {
            @Override
            public void convert(ViewHolder holder, CampusAssociationItem campusAssociationItem, int position) {
                    holder.setText(R.id.textView , campusAssociationItem.getName());
                    holder.setText(R.id.textView2 , campusAssociationItem.getSubTitle());
//                    holder.setImageResource(R.id.imageView2 , campusAssociationItem.getImgSrc());



            }
        };

        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
    }

}
