package com.example.alex.audiogid.BackClass.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.text.method.Touch;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.alex.audiogid.BackClass.DrawerMenuItem;
import com.example.alex.audiogid.MapsActivity;
import com.example.alex.audiogid.R;
import com.skyfishjy.library.RippleBackground;

import java.util.List;

public class DrawerRecyclerView extends RecyclerView.Adapter<DrawerRecyclerView.DrawerRecyclerViewViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(DrawerMenuItem item);
    }
private List<DrawerMenuItem> data;
private final OnItemClickListener listener;
private  final RippleView.OnRippleCompleteListener listener2;
    public DrawerRecyclerView(List<DrawerMenuItem> data,OnItemClickListener listener,RippleView.OnRippleCompleteListener listener2) {
        this.data = data;
        this.listener = listener;
        this.listener2 = listener2;
    }

    @NonNull
    @Override
    public DrawerRecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_menu_element, parent, false);

        return new DrawerRecyclerViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerRecyclerViewViewHolder holder, int position) {

        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    class DrawerRecyclerViewViewHolder extends RecyclerView.ViewHolder {
private ImageView DrawerimageIfTuchItemMenu1;
        private ImageView DrawerimageIfTuchItemMenu2;
        private  ImageView DrawericonItemMenu;
        private ConstraintLayout constraintLayout;
        private RippleBackground rippleBackground;
private  TextView DrawerTextDrawerMenuItem;

        public DrawerRecyclerViewViewHolder(View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.itemsRecyclerViewBacrgrount);
           rippleBackground=(RippleBackground)itemView.findViewById(R.id.RippleAnimation);
            DrawericonItemMenu = itemView.findViewById(R.id.DrawericonItemMenu);
            DrawerTextDrawerMenuItem = itemView.findViewById(R.id.DrawerTextDrawerMenuItem);
        }

        public void bind(final DrawerMenuItem data, final OnItemClickListener listener) {



    DrawerTextDrawerMenuItem.setText(data.getNameOfItem());
            DrawericonItemMenu.setBackgroundResource(data.getIconOfItem());
           // DrawericonItemMenu.setImageDrawable(data.getIconOfItem());
            final RippleView rippleView = (RippleView) itemView.findViewById(R.id.rippleView);
            rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView rippleView) {
                    listener2.onComplete(rippleView);
                    listener.onItemClick(data);
                }
            });

            rippleView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                }
            });
        }
    }
}