package com.example.shoetrack.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoetrack.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovimientosInventarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovimientosInventarioFragment extends Fragment {


    public MovimientosInventarioFragment() {
        // Required empty public constructor
    }


    public static MovimientosInventarioFragment newInstance(String param1, String param2) {
        MovimientosInventarioFragment fragment = new MovimientosInventarioFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movimientos_inventario, container, false);
        return view;
    }
}