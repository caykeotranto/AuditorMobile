const express = require("express");
const sql = require("mssql");
const cors = require("cors");

const app = express();

app.use(cors());

const config = {
    user: 'admSaurus',
    password: '201o$@UruS202502',

    server: 'prod-202502.database.saurus.net.br',
    port: 14333,

    database: 'dbsaurus_kvaratskhelia',

    options: {
        encrypt: false,
        trustServerCertificate: true,
        enableArithAbort: true
    }
};

app.get("/auditorias", async (req, res) => {

    try {

        await sql.connect(config);

const result = await sql.query(`
    SELECT TOP 20

        A.aud_id,
        A.aud_idProduto,
        A.aud_produto,
        A.aud_precoAntigo,
        A.aud_precoNovo,
        A.aud_usuario,
        A.aud_dataHora,

        ISNULL(L.cad_login, '') AS aud_nomeUsuario,

        P.pro_idLoja AS cad_idLoja

    FROM tbAuditoriaPreco A

    LEFT JOIN tbCadastroLogins L
        ON L.cad_idLogin = TRY_CONVERT(INT, A.aud_usuario)

    LEFT JOIN tbProdutoPrecos P
    ON P.pro_idProduto = A.aud_idProduto
    AND P.pro_vPreco = A.aud_precoNovo

    ORDER BY A.aud_id DESC
`);

        res.json(result.recordset);

    } catch (err) {

        console.log(err);

        res.status(500).send(err.message);
    }
});

app.listen(3000, () => {

    console.log("API rodando na porta 3000");
});