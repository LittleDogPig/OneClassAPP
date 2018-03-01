package eclass.dogking.com.oneclass.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import eclass.dogking.com.oneclass.R;
import eclass.dogking.com.oneclass.entiry.PptShow;

/**
 * Created by dog on 2018/2/25 0025.
 */
public class PPtAdapter  extends RecyclerView.Adapter<PPtAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<PptShow> list;
    protected RvListener listener;
    private int checkedPosition;

    public PPtAdapter(Context context, List<PptShow> list, RvListener listener) {
        this.list = list;
        this.mContext = context;
        this.listener = listener;
        inflater = LayoutInflater.from(mContext);
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.resourceitem, viewGroup, false);
        ViewHolder vh = new ViewHolder(view,listener);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.t1.setText(list.get(position).getName());

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
            t1 = (TextView) view.findViewById(R.id.name);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(), getAdapterPosition());
                }
            });

        }


        }

    }