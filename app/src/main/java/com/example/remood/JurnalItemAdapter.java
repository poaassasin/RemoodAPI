package com.example.remood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.remood.model.JurnalModel;
import java.util.List;

public class JurnalItemAdapter extends RecyclerView.Adapter<JurnalItemAdapter.ViewHolder> {

    private List<JurnalModel> mData;
    private ItemCallbackListener listener;

    private Context context;

    public interface ItemCallbackListener {
        void onClick(int position, String pilihan);
    }

    public JurnalItemAdapter(List<JurnalModel> data, ItemCallbackListener callbackListener, Context context) {
        this.mData = data;
        this.listener = callbackListener;
        this.context = context;
    }

    public void setSearchList(List<JurnalModel> jurnal) {
        this.mData = jurnal;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivEmoji;
        private final TextView tvDate;
        private final TextView tvTitle;
        private final TextView tvDescription;
        private final View btDeleteItem;
        private final View btEditItem;
<<<<<<< HEAD
=======
        private final View btCardItem;

>>>>>>> d1831e0 (Menambahkan RecyclerView, Tambah Edit Hapus Pilihan Mood, dan Detail Jurnal punya Alya dan Zahrina)
        public ViewHolder(@NonNull View view) {
            super(view);
            this.ivEmoji = view.findViewById(R.id.emotion);
            this.tvDate = view.findViewById(R.id.date);
            this.tvTitle = view.findViewById(R.id.title);
            this.tvDescription = view.findViewById(R.id.description);
            this.btDeleteItem = view.findViewById(R.id.delete_item);
            this.btEditItem = view.findViewById(R.id.update_item);
<<<<<<< HEAD

=======
            this.btCardItem = view.findViewById(R.id.cardItem);
>>>>>>> d1831e0 (Menambahkan RecyclerView, Tambah Edit Hapus Pilihan Mood, dan Detail Jurnal punya Alya dan Zahrina)
        }
        public void bind(JurnalModel model, int position, ItemCallbackListener listener) {
            int emotionResource = 0;
            switch (model.getLinkGambarEmosi()) {
                case "Marah!":
                    emotionResource = R.drawable.perasaanmarah;
                    break;
                case "Happy!":
                    emotionResource = R.drawable.perasaansenang;
                    break;
                case "Meh":
                    emotionResource = R.drawable.perasaandatar;
                    break;
                case "Oke!":
                    emotionResource = R.drawable.perasaanoke;
                    break;
                case "Sedih:(":
                    emotionResource = R.drawable.perasaansedih;
                    break;
                default:
                    emotionResource = R.drawable.amico;
            }
            this.tvDate.setText(model.getTanggal() + " | " + model.getWaktu());
            this.tvDescription.setText(model.getDetailCurhatan());
            this.tvTitle.setText(model.getCurhatan());
            this.ivEmoji.setImageResource(
                    emotionResource
            );
<<<<<<< HEAD

=======
            this.btCardItem.setOnClickListener(v -> listener.onClick(position, "lihatDetail"));
>>>>>>> d1831e0 (Menambahkan RecyclerView, Tambah Edit Hapus Pilihan Mood, dan Detail Jurnal punya Alya dan Zahrina)
            this.btDeleteItem.setOnClickListener(v -> listener.onClick(position, "delete"));
            this.btEditItem.setOnClickListener(v -> listener.onClick(position, "edit"));
        }
    }

    @NonNull
    @Override
    public JurnalItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context)
                .inflate(R.layout.item_jurnal, parent, false);
        return new ViewHolder(v);
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
