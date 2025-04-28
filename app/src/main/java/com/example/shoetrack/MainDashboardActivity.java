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
import com.example.shoetrack.Fragments.VentasFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainDashboardActivity extends AppCompatActivity implements ClientesDialogos.ClientesListener, CategoriasDialogo.CategoriaListener {

    private BottomNavigationView bottomNavigationView;
    private boolean enProductos = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_dashboard);

        // Corregimos: no buscar un ID que no existe
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragmentContainer), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar el BottomNavigationView correctamente
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Cargar fragmento inicial
        loadFragment(new ClientesFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (!enProductos) {
                switch (item.getItemId()) {
                    case R.id.nav_clientes:
                        selectedFragment = new ClientesFragment();
                        break;
                    case R.id.nav_empleados:
                        selectedFragment = new EmpleadosFragment();
                        break;
                    case R.id.nav_productos:
                        selectedFragment = new ProductosFragment();
                        bottomNavigationView.getMenu().clear();
                        bottomNavigationView.inflateMenu(R.menu.menu_productos); // Este es el nuevo menú que tú debes crear
                        enProductos = true;
                        break;
                    case R.id.nav_categorias:
                        selectedFragment = new CategoriasFragment();
                        break;
                    case R.id.nav_ventas:
                        selectedFragment = new VentasFragment();
                        break;
                }
            } else {
                switch (item.getItemId()) {
                    case R.id.nav_productos:
                        selectedFragment = new ProductosFragment();
                        break;
                    case R.id.nav_categorias:
                        selectedFragment = new CategoriasFragment();
                        break;
                    case R.id.nav_regresar:
                        bottomNavigationView.getMenu().clear();
                        bottomNavigationView.inflateMenu(R.menu.menu_navegacion);
                        selectedFragment = new ClientesFragment();
                        enProductos = false;
                        break;
                }
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

    @Override
    public void onClientesListener() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new ClientesFragment())
                .commit();
    }

    @Override
    public void onCategoriaListener() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new CategoriasFragment())
                .commit();
    }
}
