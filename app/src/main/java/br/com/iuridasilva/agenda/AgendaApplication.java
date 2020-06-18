package br.com.iuridasilva.agenda;

import android.app.Application;

import br.com.iuridasilva.agenda.dao.AlunoDAO;
import br.com.iuridasilva.agenda.model.Aluno;

public class AgendaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        criarAlunosTeste();
    }

    private void criarAlunosTeste() {
        AlunoDAO alunoDAO = new AlunoDAO();
        alunoDAO.salvar(new Aluno("iuri", "993012250", "iuridasilva@gmail.com"));
        alunoDAO.salvar(new Aluno("sabrina", "993880189", "sabrina.mello1985@gmail.com"));
        alunoDAO.salvar(new Aluno("noah", "15092015", "noah.mello.silva@gmail.com"));
        alunoDAO.salvar(new Aluno("enzo", "26062018", "enzo.mello.silva@gmail.com"));
        // }

    }
}
