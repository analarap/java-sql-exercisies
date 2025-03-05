-- 2. Consulta contendo a quantidade de tarefas de ressuprimentos solicitadas por dia.

SELECT 
    DATE(h.DATA_HORA) AS DATA,
    h.ID_USUARIO,
    COUNT(*) AS QTD_TAREFAS_RESSUPRIMENTO
FROM HISTORICO h
JOIN TAREFA t ON h.ID_TAREFA = t.ID_TAREFA
WHERE t.TIPO = 1
GROUP BY DATE(h.DATA_HORA), h.ID_USUARIO
ORDER BY DATA, h.ID_USUARIO;