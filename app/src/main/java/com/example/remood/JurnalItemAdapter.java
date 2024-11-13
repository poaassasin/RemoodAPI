package com.example.remood;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.remood.databinding.ItemJurnalBinding;
import com.example.remood.model.JurnalModel;

import java.util.List;

public class JurnalItemAdapter extends RecyclerView.Adapter<JurnalItemAdapter.ViewHolder> {

    private List<JurnalModel> mData;
    private ItemCallbackListener listener;

    public interface ItemCallbackListener {
        void onClick(int position);
    }


    public JurnalItemAdapter(List<JurnalModel> data, ItemCallbackListener callbackListener) {
        this.mData = data;
        this.listener = callbackListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemJurnalBinding binding;

        public ViewHolder(@NonNull ItemJurnalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(JurnalModel model, int position, ItemCallbackListener listener) {
            int emotionResource = 0;
            switch (model.getLinkGambarEmosi()) {
                case "MARAH":
                    emotionResource = R.drawable.perasaanmarah;
                    break;
                case "SENANG":
                    emotionResource = R.drawable.perasaansenang;
                    break;
                case "DATAR":
                    emotionResource = R.drawable.perasaandatar;
                    break;
                case "OKE":
                    emotionResource = R.drawable.perasaanoke;
                    break;
                case "SEDIH":
                    emotionResource = R.drawable.perasaansedih;
                    break;
                default:
                    emotionResource = R.drawable.amico;
            }
            binding.date.setText(model.getTanggal() + model.getWaktu());
            binding.description.setText(model.getDetailCurhatan());
            binding.title.setText(model.getCurhatan());
            binding.emotion.setImageResource(
                    emotionResource
            );

            binding.deleteItem.setOnClickListener(v -> listener.onClick(position));
        }
    }

    @NonNull
    @Override
    public JurnalItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemJurnalBinding binding = ItemJurnalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull JurnalItemAdapter.ViewHolder holder, int position) {
        holder.bind(mData.get(position), position, listener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
