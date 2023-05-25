package ithub.com.blogposting.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ithub.com.blogposting.FullCodeView;
import ithub.com.blogposting.Models.FirebaseBlogModel;
import ithub.com.blogposting.R;


public class RecyclerViewAdaptorSecond extends RecyclerView.Adapter<RecyclerViewAdaptorSecond.MyViewHolder> {

    private Context mContext;

    HashMap<Integer,View> allRows = new HashMap<Integer,View>();
    ArrayList<FirebaseBlogModel> ap;
    MyViewHolder holder;
    TextView textView_cart_badge;

    public RecyclerViewAdaptorSecond(Context mContext, ArrayList<FirebaseBlogModel> blogs) {
        this.mContext = mContext;
        ap =  blogs;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView code_title, author, programe_code, set_email ;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardview);
            code_title = itemView.findViewById(R.id.tv_title_name);
            author = itemView.findViewById(R.id.author_name);
            programe_code = itemView.findViewById(R.id.et_code);
            set_email = itemView.findViewById(R.id.tv_email);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_preview, parent, false);
        allRows.put(viewType,itemView);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        ////////////////////////////// data set on recycler view //////////////////////////////////
        holder.code_title.setText(ap.get(position).getCode_title());
        holder.programe_code.setText(ap.get(position).getCode());
        holder.author.setText("By " +ap.get(position).getAuthor());
        holder.set_email.setText("Email Id :- " +ap.get(position).getEmail());

        ////////////////////////////////////////////////////////////////////////////////////////////


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, FullCodeView.class);
                i.putExtra("code_title", holder.code_title.getText().toString());
                i.putExtra("code", holder.programe_code.getText().toString());
                i.putExtra("author", holder.author.getText().toString());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ap.size();
    }
}