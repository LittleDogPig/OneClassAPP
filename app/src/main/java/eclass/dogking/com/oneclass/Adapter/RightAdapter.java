package eclass.dogking.com.oneclass.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import eclass.dogking.com.oneclass.LectureActivity;
import eclass.dogking.com.oneclass.R;
import eclass.dogking.com.oneclass.entiry.LectureShow;
import eclass.dogking.com.oneclass.utils.OneclassUtils;

/**
 * Created by dog on 2018/2/12 0012.
 */


public class RightAdapter extends RecyclerView.Adapter<RightAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<String> list;

    public RightAdapter(Context context,List<String> list) {
        this.list=list;
        this.mContext=context;
        inflater=LayoutInflater. from(mContext);
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view =inflater.inflate(R.layout.testrightrv,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.t1.setText(list.get(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            public void  onClick(View v){
                Toast.makeText(v.getContext(),"你点击的课为："+list.get(position),Toast.LENGTH_SHORT).show();
            }

        });


    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView t1;
        public ViewHolder(View view){
            super(view);
            t1 = (TextView) view.findViewById(R.id.t1);

        }
    }
}