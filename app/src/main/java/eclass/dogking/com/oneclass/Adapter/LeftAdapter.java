package eclass.dogking.com.oneclass.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import eclass.dogking.com.oneclass.R;

/**
 * Created by dog on 2018/2/12 0012.
 */



public class LeftAdapter  extends RecyclerView.Adapter<LeftAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<String> list;
    protected RvListener listener;
    private int checkedPosition;

    public LeftAdapter(Context context, List<String> list, RvListener listener) {
        this.list = list;
        this.mContext = context;
        this.listener = listener;
        inflater = LayoutInflater.from(mContext);
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.testrightrv, viewGroup, false);
        ViewHolder vh = new ViewHolder(view,listener);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.bindHolder(list.get(position),position);

    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView t1;
        private View mview;
        protected RvListener mListener;

        public ViewHolder(View view,RvListener listener) {
            super(view);
            this.mListener=listener;
            this.mview=view;
            t1 = (TextView) view.findViewById(R.id.t1);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(), getAdapterPosition());
                }
            });

        }

        public void bindHolder(String string, int position) {
            t1.setText(string);
            if (position == checkedPosition) {
                mview.setBackgroundColor(Color.parseColor("#ffffff"));
                t1.setTextColor(Color.parseColor("#3f51b5"));
            } else {
                mview.setBackgroundColor(Color.parseColor("#ebebeb"));
                t1.setTextColor(Color.parseColor("#1e1d1d"));
            }
        }

    }
}