package za.mobile.remoodver2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JurnalAdapter
        extends RecyclerView.Adapter {
    private final Context ctx;
    private final List<Jurnal> data;

    public JurnalAdapter(Context ctx, List<Jurnal> data) {
        this.ctx = ctx;
        this.data = data;
    }

    public class JurnalVH extends RecyclerView.ViewHolder {

        private final TextView tvJudul;
        private final TextView tvIsi;
        private final TextView tvTanggal;
        private final ImageView ivMood;
        private final ImageButton btDelete;

        public JurnalVH(@NonNull View itemView) {
            super(itemView);
            this.tvJudul = itemView.findViewById(R.id.tvJudulJurnal);
            this.tvIsi = itemView.findViewById(R.id.tvIsiDetail);
            this.tvTanggal = itemView.findViewById(R.id.tvTanggalDetail);
            this.ivMood = itemView.findViewById(R.id.ivMoodDetail);
            this.btDelete = itemView.findViewById(R.id.btDelete);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(this.ctx)
                .inflate(R.layout.rowview, parent, false);
        RecyclerView.ViewHolder vh = new JurnalVH(rowView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Jurnal j = this.data.get(position);
        JurnalVH vh = (JurnalVH) holder;

        vh.tvJudul.setText(j.getJudul());

        String shortText = j.getIsiText().length() > 50 ? j.getIsiText().substring(0, 50) + "..." : j.getIsiText();
        vh.tvIsi.setText(shortText);

        vh.tvTanggal.setText(j.getWaktu());
        vh.ivMood.setImageResource(j.getIvMood());


        if (j != null) {
            vh.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ctx, za.mobile.remoodver2.DetailJurnal.class);
                    intent.putExtra("judul", j.getJudul());
                    intent.putExtra("isiText", j.getIsiText()); // Mengirim isi jurnal lengkap
                    intent.putExtra("waktu", j.getWaktu());
                    intent.putExtra("ivMood", j.getIvMood());
                    intent.putExtra("moodLabel", j.getTvMoodLabel());
                    ctx.startActivity(intent);
                }
            });
        }
        if ("fun".equalsIgnoreCase(j.getTvMoodLabel())) {
            vh.ivMood.setImageResource(R.drawable.fill); // Gambar untuk "fun"
        } else if ("sad".equalsIgnoreCase(j.getTvMoodLabel())) {
            vh.ivMood.setImageResource(R.drawable.sad); // Gambar untuk "sad"
        }

        // Tambahkan listener untuk tombol delete
        vh.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dapatkan posisi item secara dinamis menggunakan holder.getAdapterPosition()
                int adapterPosition = vh.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    data.remove(adapterPosition); // Hapus item dari daftar data
                    notifyItemRemoved(adapterPosition); // Notifikasi penghapusan item
                    notifyItemRangeChanged(adapterPosition, data.size()); // Update posisi item lainnya
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.data.size();
    }
}
