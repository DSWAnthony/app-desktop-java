package repository;

import java.util.List;

public interface IRepositoryGET <T>{
    
    List<T> listar();
    List<T> filtrar(String params);
    T traerEntidad(int id);
    
}
