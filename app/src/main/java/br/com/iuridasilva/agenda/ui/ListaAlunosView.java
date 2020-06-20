package br.com.iuridasilva.agenda.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.iuridasilva.agenda.dao.AlunoDAO;
import br.com.iuridasilva.agenda.model.Aluno;
import br.com.iuridasilva.agenda.ui.adapter.ListaAlunosAdapter;

public class ListaAlunosView {

    private final ListaAlunosAdapter adapter;
    private final AlunoDAO alunoDAO;
    private final Context context;

    public ListaAlunosView(Context context) {
        this.context = context;
        this.adapter = new ListaAlunosAdapter(this.context);
        this.alunoDAO = new AlunoDAO();
    }

    public void confirmarRemocao(final MenuItem item){
        new AlertDialog
                .Builder(context)
                .setTitle("Removendo aluno")
                .setMessage("Tem certeza que quer remover este aluno?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    Aluno alunoEscolhido = (Aluno) adapter.getItem(menuInfo.position);
                    removerAluno(alunoEscolhido);
                })
                .setNegativeButton("Não", null)
                .show();
    }


    public void atualizarListaAlunos() {
        adapter.atualiza(alunoDAO.listarTodos());
    }

    private void removerAluno(Aluno aluno) {
        alunoDAO.remove(aluno);
        adapter.remove(aluno);
    }

    public void configurarAdapter(ListView listaDeAlunos) {
        listaDeAlunos.setAdapter(adapter);
    }
}
