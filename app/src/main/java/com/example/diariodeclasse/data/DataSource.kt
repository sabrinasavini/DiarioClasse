package com.example.diariodeclasse.data

import com.example.diariodeclasse.R

class DataSource() {
    fun carregarAlunos(): List<Aluno>{
        return listOf(
            Aluno(
                nome = "Fulano de Tal",
                foto = R.drawable.account,
                curso = "Desenvolvimento de Sistemas"
            ),
            Aluno(
                nome = "Jojo Toddynho",
                foto = R.drawable.account,
                curso = "Mecatrônica"
            ),
            Aluno(
                nome = "Valéria Almeida",
                foto = R.drawable.account,
                curso = "Segurança do Trabalho"
            ),
        )
    }
}