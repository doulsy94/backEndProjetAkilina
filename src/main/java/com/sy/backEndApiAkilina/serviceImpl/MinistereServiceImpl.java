package com.sy.backEndApiAkilina.serviceImpl;

import com.sy.backEndApiAkilina.models.Ministere;
import com.sy.backEndApiAkilina.repository.MinistereRepository;
import com.sy.backEndApiAkilina.security.services.MinistereService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//Annotation permettant de gérer les problèmes de constructeur pour tous les champs
@AllArgsConstructor
@Service
public class MinistereServiceImpl implements MinistereService {

    private final MinistereRepository ministereRepository;

   @Override
    public Ministere add(Ministere ministere) {
        return ministereRepository.save(ministere);
    }

    @Override
    public List<Ministere> read() {
        return ministereRepository.findAll();
    }

    @Override
    public Ministere update(Long id_ministere, Ministere ministere) {
        return ministereRepository.findById(id_ministere)
                .map(ministere1-> {
                    if(ministere.getLibelle() != null)
                    ministere1.setLibelle(ministere.getLibelle());
                    if(ministere.getImage() != null)
                    ministere1.setImage(ministere.getImage());
                    if(ministere.getDescription() != null)
                    ministere1.setDescription(ministere.getDescription());
                    return ministereRepository.save(ministere1);
                }).orElseThrow(() -> new RuntimeException("Ministère non trouvé !"));
    }
    @Override
    public String delete(Long id_ministere) {
        ministereRepository.deleteById(id_ministere);
        return "Ministère supprimé avec succès";
    }

    @Override
    public Ministere trouverMinistereParLibelle(String libelle) {
        return ministereRepository.findByLibelle(libelle);
    }

    @Override
    public Optional<Ministere> trouverMinistereParID(long id_ministere) {
        return ministereRepository.findById(id_ministere);
    }


}

