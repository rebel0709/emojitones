package com.emojitones.keyboard.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.emojitones.keyboard.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EmojitonesAdapter extends RecyclerView.Adapter<EmojitonesAdapter.ViewHolder> {
    private List<String> labels;

    String[] emoji_list = null;
    public Context mcontext;


    public EmojitonesAdapter(Context context, String[]  emoji_list) {
//        labels = new ArrayList<>(count);
//        for (int i = 0; i < count; ++i) {
//            labels.add(String.valueOf(i));
//        }
        this.emoji_list = emoji_list;
        this.mcontext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emojitones_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String emojitones_name = emoji_list[position];
        String emojitones_path = "people&smileys/img/" + emojitones_name+".png";
        holder.emojiImage.setImageBitmap(getBitmapFromAssets(emojitones_path));
        if (position > 5)
        {
            holder.lockImage.setVisibility(View.VISIBLE);
        }
//        holder.textView.setText(label);
//
//        //handling item click event
//        holder.textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(holder.textView.getContext(), label, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return emoji_list.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView emojiImage;
        public ImageView lockImage;


        public ViewHolder(View itemView) {
            super(itemView);
            lockImage = (ImageView) itemView.findViewById(R.id.emoji_locked);
            emojiImage = (ImageView) itemView.findViewById(R.id.emojitone);
        }
    }


    // Custom method to get assets folder image as bitmap
    private Bitmap getBitmapFromAssets(String fileName){

        AssetManager am = mcontext.getAssets();
        InputStream is = null;
        try{
            is = am.open(fileName);
        }catch(IOException e){
            e.printStackTrace();
        }
//        Drawable emojiDrawable = Drawable.createFromStream(is, null);
//        return emojiDrawable;
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }
}