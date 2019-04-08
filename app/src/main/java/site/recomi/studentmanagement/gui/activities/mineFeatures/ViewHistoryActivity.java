package site.recomi.studentmanagement.gui.activities.mineFeatures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import site.recomi.studentmanagement.R;
import site.recomi.studentmanagement.gui.activities.base.MySwipeBackActivity;

public class ViewHistoryActivity extends MySwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        ButterKnife.bind(ViewHistoryActivity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_base);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        setSupportActionBar(toolbar);
        setTitle("浏览历史");
    }
}
