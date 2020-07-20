## Dado o seguinte modelo DER:

![alt text](https://https://github.com/gfjgabriel/esparta-internship/blob/master/SQL-exercise/DER.png?raw=true "Title")

# Escrever a consulta SQL para recuperar as seguintes informações:
- Buscar os nomes de todos os alunos que frequentam alguma turma do professor 'JOAO PEDRO'.
     - SELECT a.Nome FROM ALUNO a LEFT JOIN TURMA t ON a.id = t.ALUNO_id LEFT JOIN PROFESSOR p ON p.id = t.PROFESSOR_id WHERE p.nome = 'JOAO PEDRO'
- Buscar os dias da semana que tenham aulas da disciplina 'MATEMATICA'.
     - SELECT dia_da_semana FROM TURMA t LEFT JOIN DISCIPLINA d ON t.DISCIPLINA_id = d.id WHERE d.nome = 'MATEMATICA'
- Buscar todos os alunos que frequentem aulas de 'MATEMATICA' e também 'FISICA'.
     - SELECT DISTINCT T1.* FROM (SELECT a.* FROM ALUNO a LEFT JOIN TURMA t ON a.id = t.ALUNO_id LEFT JOIN DISCIPLINA d ON t.DISCIPLINA_id = d.id WHERE d.nome = 'MATEMATICA' ) T1, (SELECT a.* FROM ALUNO a LEFT JOIN TURMA t ON a.id = t.ALUNO_id LEFT JOIN DISCIPLINA d ON t.DISCIPLINA_id = d.id WHERE d.nome = 'FISICA') T2 WHERE T1.id = T2.id
- Buscar as disciplinas que não tenham nenhuma turma.
     - SELECT d.* FROM DISCIPLINA d LEFT JOIN TURMA t ON d.id=t.DISCIPLINA_id WHERE t.id IS NULL
- Buscar os alunos que frequentem aulas de 'MATEMATICA' exceto os que frequentem 'QUIMICA'.
     - SELECT DISTINCT a.* FROM ALUNO a LEFT JOIN TURMA t ON a.id = t.ALUNO_id LEFT JOIN DISCIPLINA d ON t.DISCIPLINA_id = d.id WHERE d.nome = 'MATEMATICA' and a.id not in (SELECT a.id FROM ALUNO a LEFT JOIN TURMA t ON a.id = t.ALUNO_id LEFT JOIN DISCIPLINA d ON t.DISCIPLINA_id = d.id WHERE d.nome = 'QUIMICA')
