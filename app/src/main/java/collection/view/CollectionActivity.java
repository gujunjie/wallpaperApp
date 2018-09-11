package collection.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.abc.kantu.CollectionAdapter;
import com.example.abc.kantu.PhotoAdapter;
import com.example.abc.kantu.R;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import collection.presenter.Presenter;
import io.realm.Realm;
import io.realm.RealmList;
import register.model.Collection;


public class CollectionActivity extends BaseActivity<IView, Presenter> implements IView {


    private Presenter presenter;

    private RealmList<Collection> list;

    private CollectionAdapter adapter;

    private RecyclerView rvCollection;

    private SwipeRefreshLayout refreshCollection;

    private  Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        setWindowStatusBarColor(this,R.color.colorAccent);

        rvCollection=(RecyclerView)findViewById(R.id.rv_collection);

        refreshCollection=(SwipeRefreshLayout)findViewById(R.id.refresh_collection);

        presenter.getCollectionList(this);//获取收藏列表

        refresh();




    }

    @Override
    public Presenter createPresenter() {
        presenter = new Presenter(this);
        return presenter;
    }

    @Override
    public void initAdapter(RealmList<Collection> list, Realm realm) {



        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        rvCollection.setLayoutManager(manager);

        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        adapter=new CollectionAdapter(this,list,this);

        rvCollection.setAdapter(adapter);

        this.realm=realm;

    }

    public void refresh()
    {
        refreshCollection.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refreshCollection.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                refreshCollection.setRefreshing(false);
            }
        });

    }

    @Override
    public void tipsNeedToCollect(Realm realm) {
       this.setContentView(R.layout.tips_collect);
       this.realm=realm;
    }

    public  void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          if(requestCode==1&&resultCode==1)
          {
              adapter.notifyDataSetChanged();
              if(adapter.getItemCount()==0)
              {
                  this.setContentView(R.layout.tips_collect);
              }
          }
    }
}
