package br.com.iuridasilva.agenda.dao;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.iuridasilva.agenda.model.Aluno;

public class AlunoDAO {

    private static int contadorIds = 1;
    private final static List<Aluno> alunos = new ArrayList<>();

    public void salvar(Aluno aluno) {
        aluno.setId(contadorIds);
        alunos.add(aluno);
        incrementaId();
    }

    private void incrementaId() {
        contadorIds++;
    }

    public void editar(Aluno aluno){
        Aluno alunoEncontrado = buscarAlunoPorId(aluno);
        if(alunoEncontrado!=null){
            int posicaoAluno = alunos.indexOf(alunoEncontrado);
            alunos.set(posicaoAluno, aluno);
        }
    }

    @Nullable
    private Aluno buscarAlunoPorId(Aluno aluno) {
        for (Aluno a:alunos) {
            if(a.getId() == aluno.getId()){
                return a;
            }
        }
        return null;
    }

    public List<Aluno> listarTodos() {
        return new ArrayList<>(alunos);
    }

    public void remove(Aluno aluno) {
        Aluno alunoEncontrado = buscarAlunoPorId(aluno);
        if(aluno != null){
            alunos.remove(alunoEncontrado);
        }
    }
}
