package com.dev.numberslight.numbers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.dev.numberslight.R;
import com.dev.numberslight.model.NumberLight;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NumbersAdapter extends RecyclerView.Adapter<NumbersAdapter.ViewHolder> {

    private Listener listener;
    private List<NumberLight> numberLights = new ArrayList<>();

    public NumbersAdapter(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.number_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listener, numberLights);
    }

    @Override
    public int getItemCount() {
        return numberLights.size();
    }

    public void setNumberLights(List<NumberLight> numberLights) {
        this.numberLights = numberLights;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Listener listener = null;
        private ImageView imageView;
        private TextView textView;
        private ConstraintLayout item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
            textView = itemView.findViewById(R.id.tv_number);
            item = itemView.findViewById(R.id.item);
            item.setOnClickListener(this);
        }

        public void bind(Listener listener, List<NumberLight> numberLights) {
            this.listener = listener;
            textView.setText(numberLights.get(getAdapterPosition()).getName());
            Picasso.get().load(numberLights.get(getAdapterPosition()).getImage())
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_error)
                    .into(imageView);
        }

        @Override
        public void onClick(View view) {
            if(listener != null) {
                listener.onNumberClick(getAdapterPosition());
                item.setSelected(true);
            }
        }
    }

    interface Listener {
        void onNumberClick(int position);
    }
}

