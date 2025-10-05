package Clases;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class Conector {
    
    private static final String URL="jdbc:mysql://localhost:3306/prog3_java";
    private static final String USER= "root";
    private static final String  PASS="052716";
    
    private Connection conexion;
    
    public Connection conectar(){
        try{
            conexion = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Conexion exitosa");
        }
        catch(SQLException e){
            System.out.println("Error de conexion" +e.getMessage());
        }
        return conexion;
    }
    public PreparedStatement prepararStatement(String sql) throws SQLException{
        Connection conn = conectar ();
        return conn.prepareStatement(sql);
    }
    public ResultSet ejecutarConsulta(PreparedStatement ps) throws SQLException{
        return ps.executeQuery();
    }
    public int ejecutarUpdate(PreparedStatement ps) throws SQLException{
        return ps.executeUpdate();
    }
    public void desconectar (){
        try{
            if(conexion !=null && !conexion.isClosed()){
                conexion.close();
                System.out.println("COnexion cerrada");
            }
        }catch(SQLException e){
            System.out.println("Error al desconectarse"+e.getMessage());
        }
    }
}
