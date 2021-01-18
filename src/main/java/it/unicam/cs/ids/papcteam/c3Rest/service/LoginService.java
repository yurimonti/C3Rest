package it.unicam.cs.ids.papcteam.c3Rest.service;

import it.unicam.cs.ids.papcteam.c3Rest.repository.ClienteRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.CommercianteRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.CorriereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CommercianteRepository commercianteRepository;
    @Autowired
    private CorriereRepository corriereRepository;

    public LoginService() {
    }

    private long getClienteId(String email,String password){
        if (verifyPresenceOfCliente(email,password))
            return this.clienteRepository.findAll().stream()
                    .filter(clienteEntity ->(clienteEntity.getEmail().equals(email)
                            && clienteEntity.getPassword().equals(password))).findFirst()
                    .orElseThrow(NullPointerException::new).getId();
        else return 0;
    }
    private long getCorriereId(String email,String password){
        if (verifyPresenceOfCorriere(email, password))
            return this.corriereRepository.findAll().stream()
                    .filter(corriereEntity ->(corriereEntity.getEmail().equals(email)
                            && corriereEntity.getPassword().equals(password))).findFirst()
                    .orElseThrow(NullPointerException::new).getId();
        else return 0;
    }

    private long getCommercianteId(String email,String password){
        if (verifyPresenceOfCommerciante(email,password))
            return this.commercianteRepository.findAll().stream()
                    .filter(commercianteEntity ->(commercianteEntity.getEmail().equals(email)
                            && commercianteEntity.getPassword().equals(password))).findFirst()
                    .orElseThrow(NullPointerException::new).getId();
        else return 0;
    }


    public long getUserId(String userType,String email,String password){
        long id=0;
        UserType u = UserType.valueOf(userType.toUpperCase());
        switch (u){
            case CLIENTE:id = getClienteId(email,password);
            break;
            case CORRIERE:id=getCorriereId(email,password);
            break;
            case COMMERCIANTE:id = getCommercianteId(email,password);
            break;
        }
        return id;
    }

    private boolean verifyPresenceOfCliente(String email,String password){
        return this.clienteRepository.findAll().stream().anyMatch(clienteEntity ->(clienteEntity.getEmail().equals(email)
                && clienteEntity.getPassword().equals(password)));
    }
    private boolean verifyPresenceOfCommerciante(String email,String password){
        return this.commercianteRepository.findAll().stream().anyMatch(clienteEntity ->(clienteEntity.getEmail().equals(email)
                && clienteEntity.getPassword().equals(password)));
    }
    private boolean verifyPresenceOfCorriere(String email,String password){
        return this.corriereRepository.findAll().stream().anyMatch(clienteEntity ->(clienteEntity.getEmail().equals(email)
                && clienteEntity.getPassword().equals(password)));
    }
}
