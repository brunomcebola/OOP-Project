CREATE VIEW vendas(ean, cat, ano, trimestre, dia_mes, dia_semana, distrito, concelho, unidades)
AS SELECT ean, cat, instante, EXTRACT(YEAR FROM instante) AS ano,
							  EXTRACT(QUARTER FROM instante) AS trimestre,
							  EXTRACT(DAY FROM instante) AS dia_mes,
							  EXTRACT(DOW FROM instante) AS dia_semana,
							  distrito, concelho, COUNT(*) AS unidades
FROM evento_reposicao NATURAL JOIN ponto_de_retalho NATURAL JOIN instala_em NATURAL JOIN produto
GROUP BY ano, trimestre, dia_mes, dia_semana;