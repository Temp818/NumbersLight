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
    private int selectedPos = RecyclerView.NO_POSITION;
    private boolean shouldHighlight;

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
        holder.bind(listener, numberLights.get(position));
    }

    @Override
    public int getItemCount() {
        return numberLights.size();
    }

    public void setNumberLights(List<NumberLight> numberLights, boolean shouldHighlight) {
        this.numberLights = numberLights;
        this.shouldHighlight = shouldHighlight;
        notifyDataSetChanged();
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Listener listener = null;
        private ImageView imageView;
        private TextView textView;
        private NumberLight number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
            textView = itemView.findViewById(R.id.tv_number);
            ConstraintLayout item = itemView.findViewById(R.id.item);
            item.setOnClickListener(this);
        }

        public void bind(Listener listener, NumberLight number) {
            this.listener = listener;
            this.number = number;
            textView.setText(number.getName());
            Picasso.get().load(number.getImage())
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_error)
                    .into(imageView);
            itemView.setSelected(selectedPos == getAdapterPosition() && shouldHighlight);
        }

        @Override
        public void onClick(View view) {
            if(listener != null) {
                listener.onNumberClick(number);
                setSelectedPos(getAdapterPosition());
                notifyDataSetChanged();
            }
        }
    }

    interface Listener {
        void onNumberClick(NumberLight numberLight);
    }
}

