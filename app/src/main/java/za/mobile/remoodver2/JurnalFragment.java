package za.mobile.remoodver2;


import android.os.Bundle;
import java.util.ArrayList;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JurnalFragment extends Fragment {
    private RecyclerView rvRiwayat;
    private JurnalAdapter jurnalAdapter;
    private List<Jurnal> data;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jurnal_fragment, container, false);

        gson = new Gson();
        data = new ArrayList<>();

        rvRiwayat = view.findViewById(R.id.rvRiwayat);
        jurnalAdapter = new JurnalAdapter(getContext(), data);
        rvRiwayat.setAdapter(jurnalAdapter);
        rvRiwayat.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchDataFromApi();
        return view;
    }

    private void fetchDataFromApi() {
        String url = "https://api-pam.portoku.my.id/RemoodVer2.php";
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    Type listType = new TypeToken<List<Jurnal>>() {}.getType();
                    data.clear();
                    data.addAll(gson.fromJson(response.toString(), listType));
                    requireActivity().runOnUiThread(() -> jurnalAdapter.notifyDataSetChanged());
                },
                error -> error.printStackTrace());

        queue.add(jsonArrayRequest);
    }
}
