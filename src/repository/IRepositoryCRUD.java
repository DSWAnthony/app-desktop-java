
package repository;

public interface IRepositoryCRUD <T> extends IRepositoryGET<T>{
    
    boolean registrar(T entidad);
    boolean actualizar(T entidad);
    boolean eliminar(int id);
    
}
