package Clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class Usuarios {
    String nombre,apellido,email,username,clave,rol;

    public Usuarios(String nombre, String apellido, String email, String username, String clave, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.username = username;
        this.clave = clave;
        this.rol = rol;
        
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getClave() {
        return clave;
    }

    public String getRol() {
        return rol;
    }
    public static boolean Validar_Usuario(String username,String clave){
        Conector con = new Conector();
        Connection conn = con.conectar();
        
        String sql = "SELECT * FROM Usuarios WHERE username = ? and clave = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,clave);
            
            ResultSet rs = ps.executeQuery();
            boolean existe = rs.next();
            
            if (existe){
                System.out.println("Usuario valido");
            }else{
                System.out.println("Usuario o clave incorrecto");   
            }
            return existe;
        }catch (SQLException e ){
            System.out.println("Error al validar usuario:"+e.getMessage());
            return false;
        }finally{
            con.desconectar();
        }
    }
    public static boolean registrarUsuarios(String nombre,String apellido,String email,String username,String clave,String rol) throws SQLException{
        Conector con = new Conector();
        Connection conn = con.conectar();
        
        if(conn==null){
            System.out.println("No se pudo establecer la conexion para registar ");
            return false;
        }
        String sql = "INSERT INTO Usuarios (nombre,apellido,email,username,clave,rol) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, email);
            ps.setString(4, username);
            ps.setString(5, clave);
            ps.setString(6, rol);
            
            System.out.println("Usuario Registrado :)");
        }catch(SQLException e){
            System.out.println("Error registrando usuario"+e.getMessage());
            return false;
        }
        finally{
            con.desconectar();
        }
return false;
    }
    public static Usuarios loginUsuario(String username, String clave) {
    Conector con = new Conector();
    Connection conn = con.conectar();
        if (conn == null) {
        System.out.println("No se pudo establecer la conexión para login.");
        return null;
    }
    
    String sql = "SELECT * FROM Usuarios WHERE username = ? AND clave = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, username);
        ps.setString(2, clave);
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) { 
                Usuarios usuario = new Usuarios(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("clave"), 
                    rs.getString("rol")
                );
                System.out.println("Login exitoso para: " + usuario.getNombre());
                return usuario;
            } else {
                System.out.println("Usuario o clave incorrecto");
                return null;
            }
        }
    } catch (SQLException e) {
        System.out.println("Error en login: " + e.getMessage());
        return null;
    } finally {
        con.desconectar();
    }
}

}
