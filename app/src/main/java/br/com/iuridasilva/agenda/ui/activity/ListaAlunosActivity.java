package br.com.iuridasilva.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.iuridasilva.R;
import br.com.iuridasilva.agenda.dao.AlunoDAO;
import br.com.iuridasilva.agenda.model.Aluno;
import br.com.iuridasilva.agenda.ui.adapter.ListaAlunosAdapter;


public class ListaAlunosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Alunos";
    private final AlunoDAO alunoDAO = new AlunoDAO();
    private ListaAlunosAdapter adapter;

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
        atualizarListaAlunos();
    }

    private void atualizarListaAlunos() {
        adapter.atualiza(alunoDAO.listarTodos());
    }

    //FAB = floatActionButton
    private void configurarFabNovoAluno() {
        FloatingActionButton botaoNovoAluno = findViewById(R.id.lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirFormularioModoInsert();
            }
        });
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
        configurarAdapter(listaDeAlunos);
        //
        //
        configurarListenerClickItem(listaDeAlunos);
        //configurarListenerLongClick(listaDeAlunos);
        registerForContextMenu(listaDeAlunos);
    }

    private void configurarListenerLongClick(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno alunoSelecionado = (Aluno) parent.getItemAtPosition(position);
                removerAluno(alunoSelecionado);
                //return true; //retornar true, impede a execução do click normal
                return false;
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()== R.id.activity_lista_alunos_menu_remover){
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Aluno alunoEscolhido = (Aluno) adapter.getItem(menuInfo.position);
            removerAluno(alunoEscolhido);
        }

        return super.onContextItemSelected(item);
    }

    private void removerAluno(Aluno aluno) {
        alunoDAO.remove(aluno);
        adapter.remove(aluno);
    }

    private void configurarListenerClickItem(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno alunoSelecionado = (Aluno) parent.getItemAtPosition(position);
                abrirFormularioModoEdit(alunoSelecionado);
            }
        });
    }

    private void configurarAdapter(ListView listaDeAlunos) {
        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        //adapter = new ArrayAdapter<>(this, R.layout.item_aluno);
        adapter = new ListaAlunosAdapter(this);
        listaDeAlunos.setAdapter(adapter);
    }
}
