/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.ModeloCliente;

/**
 *
 * @author Benson & Haurio
 */
public class ControleCliente {

    ConectaBanco conectaC = new ConectaBanco();
    ModeloCliente modeloCliente = new ModeloCliente();

    int codBairro, codCidade;
    String Bairro, Cidade, Telefone;

    public void InserirCliente(ModeloCliente mod) {

        conectaC.conexao();
        buscaCod(mod.getIdCidade());

        try {

            PreparedStatement pst = conectaC.conn.prepareStatement("INSERT INTO clientes(nome_cliente, endereco_cliente, rg_cliente,cpf_cliente,bairro,id_cidade, telefone, limite_compra, email, apelido,sexo,uf,inscricao_estadual,inscricao_municipal,numero,complemento,ibge,cep"
                    + ")VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, mod.getNome());
            pst.setString(2, mod.getEndereco());
            pst.setString(3, mod.getRg());
            pst.setString(4, mod.getCpf());
            pst.setString(5, mod.getBairro());
            buscaCod(mod.getIdCidade());
            pst.setInt(6, codCidade);
            pst.setString(7, mod.getTelefone());
            pst.setFloat(8, mod.getLimite());
            pst.setString(9, mod.getEmail());
            pst.setString(10, mod.getApelido());
            pst.setString(11, mod.getSexo());
            pst.setString(12, mod.getUf());
            pst.setString(13, mod.getInscricao_estadual());
            pst.setInt(14, mod.getInscricao_municipal());
            pst.setInt(15, mod.getNumero());
            pst.setString(16, mod.getComplemento());
            pst.setInt(17, mod.getIbge());
            pst.setInt(18, mod.getCep());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Cliente Cadastrado com Sucesso");

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro ao Cadastrada Cliente!" + e);
        }

    }

    public void AlterarCliente(ModeloCliente mod) {

        conectaC.conexao();
        buscaCod(mod.getIdCidade());

        try {

            PreparedStatement pst = conectaC.conn.prepareStatement("UPDATE clientes SET nome_cliente =? , endereco_cliente= ?, rg_cliente=?,cpf_cliente= ?,bairro = ?,id_cidade=?, telefone=?, limite_compra=?, email = ?, apelido = ?, sexo = ?, inscricao_estadual = ?, inscricao_municipal = ?, numero = ?, complemento = ?, ibge = ?, cep = ?, uf = ? WHERE id_cliente = ?");
            pst.setString(1, mod.getNome());
            pst.setString(2, mod.getEndereco());
            pst.setString(3, mod.getRg());
            pst.setString(4, mod.getCpf());
            pst.setString(5, mod.getBairro());
            buscaCod(mod.getIdCidade());
            pst.setInt(6, codCidade);
            pst.setString(7, mod.getTelefone());
            pst.setFloat(8, mod.getLimite());
            pst.setString(9, mod.getEmail());
            pst.setString(10, mod.getApelido());
            pst.setString(11, mod.getSexo());
            pst.setString(12, mod.getUf());
            pst.setString(13, mod.getInscricao_estadual());
            pst.setInt(14, mod.getInscricao_municipal());
            pst.setInt(15, mod.getNumero());
            pst.setString(16, mod.getComplemento());
            pst.setInt(17, mod.getIbge());
            pst.setInt(18, mod.getCep());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Cliente Atualizado com Sucesso!");

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro ao Atualizar Cliente" + e);
        }

    }

    public void ExcluirCliente(ModeloCliente mod) {

        conectaC.conexao();

        try {
            PreparedStatement pst = conectaC.conn.prepareStatement("DELETE FROM clientes WHERE id_cliente = ?");
            pst.setInt(1, mod.getId());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Cliente Excluido com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Excluir o Cliente \n" + ex);
        }

    }

   public void buscaCod(String Cidade) {

        try {
            conectaC.executaSQL("SELECT * FROM cidade WHERE nome_cidade ='" + Cidade + "'");
            conectaC.rs.first();
            codCidade = conectaC.rs.getInt("id_cidade");

        } catch (SQLException ex) {
            Logger.getLogger(ControleCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void buscaNomes(int codBairro, int codCid) {

        try {

            conectaC.executaSQL("SELECT * FROM bairro WHERE id_bairro = " + codBairro);
            conectaC.rs.first();
            Bairro = conectaC.rs.getString("nome_bairro");

            conectaC.executaSQL("SELECT * FROM cidade WHERE id_cidade = " + codCid);
            conectaC.rs.first();
            Cidade = conectaC.rs.getString("nome_cidade");

        } catch (Exception ex) {
            Logger.getLogger(ControleCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ModeloCliente pesquisaCliente(ModeloCliente mod) {

        conectaC.conexao();

        conectaC.executaSQL("SELECT * FROM clientes WHERE nome_cliente LIKE '%" + mod.getPesquisa() + "%'");

        try {
            conectaC.rs.first();
            //buscaNomeFornecedor(conectaC.rs.getInt("id_fornecedor"));
            mod.setId(conectaC.rs.getInt("id_cliente"));
            mod.setNome(conectaC.rs.getString("nome_cliente"));
            mod.setEndereco(conectaC.rs.getString("endereco_cliente"));
            mod.setRg(conectaC.rs.getString("rg_cliente"));
            mod.setCpf(conectaC.rs.getString("cpf_cliente"));
            mod.setTelefone(conectaC.rs.getString("telefone"));
            mod.setSexo(conectaC.rs.getString("sexo"));
            mod.setUf(conectaC.rs.getString("uf"));
            mod.setInscricao_estadual(conectaC.rs.getString("inscricao_estadual)"));
            mod.setInscricao_municipal(conectaC.rs.getInt("inscricao_municipal"));
            mod.setNumero(conectaC.rs.getInt("numero"));
            mod.setComplemento(conectaC.rs.getString("complemento"));
            mod.setIbge(conectaC.rs.getInt("ibge"));
            mod.setCep(conectaC.rs.getInt("cep"));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Pesquisar o Produto " + ex);
        }
        conectaC.desconecta();
        return mod;

    }

    public ModeloCliente chamaCliente(ModeloCliente mod) {

        conectaC.conexao();

        conectaC.executaSQL("SELECT * FROM clientes WHERE nome_cliente LIKE '%" + mod.getPesquisa() + "%'");

        try {
            conectaC.rs.first();
            //buscaNomeFornecedor(conectaC.rs.getInt("id_fornecedor"));
            mod.setId(conectaC.rs.getInt("id_cliente"));
            mod.setNome(conectaC.rs.getString("nome_cliente"));
            mod.setEndereco(conectaC.rs.getString("endereco_cliente"));
            mod.setRg(conectaC.rs.getString("rg_cliente"));
            mod.setCpf(conectaC.rs.getString("cpf_cliente"));
            mod.setTelefone(conectaC.rs.getString("telefone"));
            mod.setSexo(conectaC.rs.getString("sexo"));
            mod.setUf(conectaC.rs.getString("uf"));
            mod.setInscricao_estadual(conectaC.rs.getString("inscricao_estadual)"));
            mod.setInscricao_municipal(conectaC.rs.getInt("inscricao_municipal"));
            mod.setNumero(conectaC.rs.getInt("numero"));
            mod.setComplemento(conectaC.rs.getString("complemento"));
            mod.setIbge(conectaC.rs.getInt("ibge"));
            mod.setCep(conectaC.rs.getInt("cep"));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Pesquisar o Produto " + ex);
        }
        conectaC.desconecta();
        return mod;

    }

}
