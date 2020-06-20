package br.com.iuridasilva.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.iuridasilva.R;
import br.com.iuridasilva.agenda.model.Aluno;
import br.com.iuridasilva.agenda.ui.ListaAlunosView;


public class ListaAlunosActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR = "Lista de Alunos";
    private final ListaAlunosView listaAlunosView = new ListaAlunosView(this);

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.add("Remover");
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu_remover, menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITULO_APPBAR);
        setContentView(R.layout.activity_lista_alunos);
        configurarFabNovoAluno();
        configurarLista();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaAlunosView.atualizarListaAlunos();
    }


    //FAB = floatActionButton
    private void configurarFabNovoAluno() {
        FloatingActionButton botaoNovoAluno = findViewById(R.id.lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener(v -> abrirFormularioModoInsert());
    }

    private void abrirFormularioModoInsert() {
        startActivity(new Intent(this, FormularioAlunoActivity.class));
    }

    private void abrirFormularioModoEdit(Aluno alunoSelecionado) {
        Intent formularioAluno = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
        formularioAluno.putExtra("aluno", alunoSelecionado);
        startActivity(formularioAluno);
    }

    private void configurarLista() {
        final ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        //alterado para 2
        listaAlunosView.configurarAdapter(listaDeAlunos);
        //
        //
        configurarListenerClickItem(listaDeAlunos);
        //configurarListenerLongClick(listaDeAlunos);
        registerForContextMenu(listaDeAlunos);
    }

    private void configurarListenerLongClick(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemLongClickListener((parent, view, position, id) -> {
            Aluno alunoSelecionado = (Aluno) parent.getItemAtPosition(position);
            //listaAlunosView.removerAluno(alunoSelecionado);
            //return true; //retornar true, impede a execução do click normal
            return false;
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.activity_lista_alunos_menu_remover){
            listaAlunosView.confirmarRemocao(item);
        }

        return super.onContextItemSelected(item);
    }

    private void configurarListenerClickItem(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemClickListener((parent, view, position, id) -> {
            Aluno alunoSelecionado = (Aluno) parent.getItemAtPosition(position);
            abrirFormularioModoEdit(alunoSelecionado);
        });
    }
}
