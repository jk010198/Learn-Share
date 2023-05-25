package ithub.com.blogposting.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ithub.com.blogposting.FullCodeView;
import ithub.com.blogposting.R;
import ithub.com.blogposting.Models.BlogModel;


public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.MyViewHolder> {

    private Context mContext;

    ArrayList<BlogModel> al;
    BlogModel bm;
    //CartModel cartModel;
    BlogModel blogModel;
    MyViewHolder holder;

    public RecyclerViewAdaptor(Context mContext, ArrayList<BlogModel> blogModels) {
        this.mContext = mContext;
        al =  blogModels;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView code_title;
        TextView author;
        TextView programe_code;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            code_title = itemView.findViewById(R.id.tv_title_name);
            author = itemView.findViewById(R.id.author_name);
            programe_code = itemView.findViewById(R.id.et_code);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_preview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.code_title.setText(al.get(position).getCode_head());
        holder.author.setText(al.get(position).getAuthor_name());
        holder.programe_code.setText(al.get(position).getCode());

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
        return al.size();
    }
}