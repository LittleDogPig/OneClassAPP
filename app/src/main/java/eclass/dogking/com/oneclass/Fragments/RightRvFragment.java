package eclass.dogking.com.oneclass.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eclass.dogking.com.oneclass.Adapter.RightAdapter;
import eclass.dogking.com.oneclass.R;

/**
 * Created by dog on 2018/2/12 0012.
 */

public class RightRvFragment extends Fragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private List<List<String>> mylist;
    private int mIndex = 0;
    private RightAdapter rightAdapter;
    private List<String> list;
    private Context context;
    private LinearLayoutManager mLinearLayoutManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.rightfragment, container, false);

        //对View中控件的操作方法

        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    public void setData(int n) {
        mIndex = n;
    }

    protected void initview(){
        context = this.getContext();
        mLinearLayoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(mLinearLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));


    }


    protected void initData() {
        for(int i=0;i<5;i++){
            List<String> list=new ArrayList<>();
            list.add(i+"");
            list.add(i+"");
            list.add(i+"");
            list.add(i+"");
            list.add(i+"");
            mylist.add(list);
        }
        //rightAdapter=new RightAdapter(context,mylist);
        //rv.setAdapter(rightAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
