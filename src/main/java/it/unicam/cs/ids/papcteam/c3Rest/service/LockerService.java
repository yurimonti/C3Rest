package it.unicam.cs.ids.papcteam.c3Rest.service;

import it.unicam.cs.ids.papcteam.c3Rest.entity.LockerEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import it.unicam.cs.ids.papcteam.c3Rest.repository.LockerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LockerService {
    @Autowired
    private LockerRepository lockerRepository;

    public LockerService() {
    }

    public List<LockerEntity> getLockers(){
        return this.lockerRepository.findAll();
    }
    public List<OrdineEntity> getOrdiniLocker(long id){
        LockerEntity locker= this.lockerRepository.findById(id).orElseThrow(NullPointerException::new);
        return locker.getOrdini();
    }
}
