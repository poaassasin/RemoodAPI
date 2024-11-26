package za.mobile.remoodver2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DetailJurnalFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailjurnal, container, false);

        Bundle args = getArguments();
        String judul = args != null ? args.getString("judul") : "Judul Default";
        String isi = args != null ? args.getString("isi") : "Isi Default";
        int ivMoodRes = args != null ? args.getInt("ivMood", R.drawable.fill) : R.drawable.sad;
        String moodLabel = args != null ? args.getString("moodLabel") : "Mood Default";

        TextView tvTanggal = view.findViewById(R.id.tvTanggalDetail);
        TextView tvJudul = view.findViewById(R.id.tvJudulJurnal);
        TextView tvIsi = view.findViewById(R.id.tvIsiDetail);
        TextView tvMoodLable = view.findViewById(R.id.tvMoodLable);
        ImageView ivMood = view.findViewById(R.id.ivMoodDetail);

        tvJudul.setText(judul);
        tvIsi.setText(isi);
        tvMoodLable.setText(moodLabel);
        ivMood.setImageResource(ivMoodRes);

        return view;
    }
    public static DetailJurnalFragment newInstance(String judul, String isi, int ivMood, String moodLabel) {
        DetailJurnalFragment fragment = new DetailJurnalFragment();
        Bundle args = new Bundle();
        args.putString("judul", judul);
        args.putString("isi", isi);
        args.putInt("ivMood", ivMood);
        args.putString("moodLabel", moodLabel);
        fragment.setArguments(args);
        return fragment;
    }
}
