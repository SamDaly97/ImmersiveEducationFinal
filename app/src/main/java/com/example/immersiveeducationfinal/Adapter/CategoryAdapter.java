package com.example.immersiveeducationfinal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.immersiveeducationfinal.Common.Common;
import com.example.immersiveeducationfinal.Model.Category;
import com.example.immersiveeducationfinal.QuestionActivity;
import com.example.immersiveeducationfinal.R;

import java.util.List;

//bridge class for catgeories
//adds access to data
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_category_item,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.txt_category_name.setText(categories.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView card_category;
        TextView txt_category_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_category = (CardView)itemView.findViewById(R.id.card_category);
            txt_category_name = (TextView)itemView.findViewById(R.id.txt_category_name);
            card_category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Common.selectedCategory = categories.get(getAdapterPosition()); // Assign current Category
                    Intent intent = new Intent(context,QuestionActivity.class);
                    context.startActivity(intent);

                }
            });
        }
    }
}
