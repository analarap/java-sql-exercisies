-- 3. Consulta com a quantidade de tarefas por dia por usuário e a quantidade distinta de produtos ressuprimentos.

SELECT 
    t.TIPO AS TIPO_TAREFA,
    COUNT(DISTINCT t.ID_TAREFA) AS QTD_TAREFAS,
    COUNT(r.ID_PRODUTO) AS QTD_PRODUTOS_RESSUPRIMENTOS
FROM TAREFA t
LEFT JOIN RESSUPRIMENTO r ON t.ID_TAREFA = r.ID_TAREFA
GROUP BY t.TIPO
ORDER BY t.TIPO;