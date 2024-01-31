package org.iesvdm.pruebaud3.service;

import org.iesvdm.pruebaud3.dao.PeliculaDAO;
import org.iesvdm.pruebaud3.dao.PeliculaDAOImpl;
import org.iesvdm.pruebaud3.domain.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaDAO peliculaDAO;

    @Autowired
    private PeliculaDAOImpl peliculaDAOImpl;

    public List<Pelicula> listAll() {
        return peliculaDAO.getAll();
    }

    public Pelicula one(Integer id) {
        Optional<Pelicula> optFab = peliculaDAO.find(id);
        if (optFab.isPresent())
            return optFab.get();
        else
            return null;
    }

    public void newPelicula(Pelicula pelicula) {

        peliculaDAO.create(pelicula);
    }

    public int peliculasTotales(){

        return peliculaDAOImpl.numPeliculasTotales();
    }
}
