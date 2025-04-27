package com.example.shoetrack;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.shoetrack.Dialog.CategoriasDialogo;
import com.example.shoetrack.Dialog.ClientesDialogos;
import com.example.shoetrack.Fragments.CategoriasFragment;
import com.example.shoetrack.Fragments.ClientesFragment;
import com.example.shoetrack.Fragments.EmpleadosFragment;
import com.example.shoetrack.Fragments.ProductosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainDashboardActivity extends AppCompatActivity implements ClientesDialogos.ClientesListener, CategoriasDialogo.CategoriaListener {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Cargar fragmento inicial (Clientes por defecto)
        loadFragment(new ClientesFragment());

        // Escuchar cambios en el BottomNavigation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_clientes:
                    selectedFragment = new ClientesFragment();
                    break;
                case R.id.nav_empleados:
                    selectedFragment = new EmpleadosFragment();
                    break;
                case R.id.nav_productos:
                    selectedFragment = new ProductosFragment();
                    break;
                case R.id.nav_categorias:
                    selectedFragment = new CategoriasFragment();
                    break;
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
    // Implementación de la interfaz ClientesListener
    @Override
    public void onClientesListener() {
        // Aquí puedes actualizar la lista de clientes o hacer cualquier acción después de agregar un cliente
        // Por ejemplo, podrías recargar el fragmento de clientes
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new ClientesFragment())
                .commit();
    }


    @Override
    public void onCategoriaListener() {
        // Acción cuando se agrega una categoría
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new CategoriasFragment())
                .commit();
    }
}