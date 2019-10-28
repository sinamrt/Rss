package au.edu.myscribble.rssreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import au.edu.myscribble.rssreader.Interface.ItemClickListener;
import au.edu.myscribble.rssreader.Model.RSSObject;
import au.edu.myscribble.rssreader.R;
import au.edu.myscribble.rssreader.web_explorer;

/**
 * Created by reale on 5/5/2017.
 */

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
{

    public TextView txtTitle,txtPubDate,txtContent;
    private ItemClickListener itemClickListener;

    public FeedViewHolder(View itemView)
    {
        super(itemView);

        Context context;

        txtTitle = (TextView)itemView.findViewById(R.id.txtTitle);
        txtPubDate = (TextView)itemView.findViewById(R.id.txtPubDate);
        txtContent = (TextView)itemView.findViewById(R.id.txtContent);

        //Set Event
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v)
    {

        itemClickListener.onClick(v,getAdapterPosition(),false);

    }

    @Override
    public boolean onLongClick(View v)
    {
        itemClickListener.onClick(v,getAdapterPosition(),true);



        return true;
    }
}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder>
{

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;

    public FeedAdapter(RSSObject rssObject, Context mContext)
    {
        this.rssObject = rssObject;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
       View itemView = inflater.inflate(R.layout.row,parent,false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position)
    {

        holder.txtTitle.setText(rssObject.getItems().get(position).getTitle());
        holder.txtPubDate.setText(rssObject.getItems().get(position).getPubDate());
        holder.txtContent.setText(rssObject.getItems().get(position).getContent());

        holder.setItemClickListener(new ItemClickListener()
        {
            @Override
            public void onClick(View view, int position, boolean isLongClick)
            {
                if(!isLongClick)
                {




                    String url = rssObject.getItems().get(position).getLink();


                   // Toast.makeText(mContext,"hello" + url,Toast.LENGTH_LONG).show();

                     Intent browserIntent = new Intent(mContext,web_explorer.class);
                      browserIntent.putExtra("rss_object",url);
                     mContext.startActivity(browserIntent);










                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return rssObject.items.size();
    }
}
