package edu.upc.dsa;

import edu.upc.dsa.models.Usuario;

import java.util.List;

public interface UsuarioManager {

    public Usuario addUsuario(String idUsuario);
    public Usuario addUsuario(Usuario u);
    public Usuario getUsuario(String idUsuario);
    public List<Usuario> findAll();
    public void deleteUsuario(String idUsuario);

    public int size();
}
