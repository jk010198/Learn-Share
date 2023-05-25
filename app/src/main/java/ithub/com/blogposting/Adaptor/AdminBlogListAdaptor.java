package ithub.com.blogposting.Adaptor;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ithub.com.blogposting.Models.FirebaseBlogModel;
import ithub.com.blogposting.R;

public class AdminBlogListAdaptor extends ArrayAdapter<FirebaseBlogModel> {
    private Activity context;
    public static List<FirebaseBlogModel> bloglist;
    public static String name, add, model_name, model_issue, date;

    public AdminBlogListAdaptor(Activity context, List<FirebaseBlogModel> bloglist) {
        super(context, R.layout.mybloglist_preview, bloglist);
        this.context = context;
        this.bloglist = bloglist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listview = inflater.inflate(R.layout.adminbloglist_preview, null, true);

        TextView tv_code_title = listview.findViewById(R.id.tv_code_title);
        TextView tv_code_set_title = listview.findViewById(R.id.tv_code_title_set);
        TextView tv__set_author_name = listview.findViewById(R.id.tv_set_author_name);

        FirebaseBlogModel fbm = bloglist.get(position);

        tv_code_set_title.setText(fbm.getCode_title());
        tv__set_author_name.setText(fbm.getAuthor());

        return listview;
    }
}

