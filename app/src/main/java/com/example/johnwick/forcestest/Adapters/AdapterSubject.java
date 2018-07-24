package com.example.johnwick.forcestest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.johnwick.forcestest.Models.Subject;
import com.example.johnwick.forcestest.QuesionShowActivity;
import com.example.johnwick.forcestest.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterSubject extends RecyclerView.Adapter<AdapterSubject.Holder> {
    ArrayList<Subject> subjectList = new ArrayList<>();
    Context ctx ;
    String forceName ;

    public AdapterSubject(ArrayList<Subject> subjectList, Context ctx, String forceName) {
        this.subjectList = subjectList;
        this.ctx = ctx;
        this.forceName = forceName ;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_subjects, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Subject subject = subjectList.get(position);
        holder.subjectName.setText(subject.getName());
        String url = subject.getImgUrl();

        Glide.with(ctx).load(url).into(holder.img);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, QuesionShowActivity.class);
                intent.putExtra("forceName", forceName);
                intent.putExtra("subjectName", subject.getName());
                ctx.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView subjectName ;
        ConstraintLayout parentLayout ;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.subject_viewholder_img);
            subjectName = itemView.findViewById(R.id.subject_viewholder_text);
            parentLayout = itemView.findViewById(R.id.subject_parentLayout);
        }
    }
}
