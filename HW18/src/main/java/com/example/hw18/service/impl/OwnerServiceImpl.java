package com.example.hw18.service.impl;

import com.example.hw18.exeption.OwnerExistException;
import com.example.hw18.exeption.OwnerNotFoundException;
import com.example.hw18.model.Car;
import com.example.hw18.model.Owner;
import com.example.hw18.service.OwnerService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final List<Owner> owners = new ArrayList<>();
    public static int ownerId = 0;
    public static int carId = 0;


    @Override
    public void add(Owner owner) throws OwnerExistException {
        for (Owner o : owners) {
            if(owner.getId() == o.getId()){
                throw new OwnerExistException(owner.getId());
            }
        }
        owner.setId(++ ownerId);
        owners.add(owner);
    }

    @Override
    public void deleteById(int id) {
        owners.removeIf(owner -> owner.getId() == id);
    }

    @Override
    public Owner getById(int id) throws OwnerNotFoundException {
        for (Owner owner : owners) {
            if(owner.getId() == id){
                return owner;
            }
        }
        throw new OwnerNotFoundException(id);
    }

    @Override
    public List<Owner> getAll() {
        return owners;
    }

    @Override
    public void updateById(Owner owner) throws OwnerNotFoundException {
        Owner currentOwner = getById(owner.getId());
        if(currentOwner == null){
            throw new OwnerNotFoundException(owner.getId());
        }
        currentOwner.setFirstName(owner.getFirstName());
        currentOwner.setLastName(owner.getLastName());
        currentOwner.setGender(owner.getGender());
        currentOwner.setAge(owner.getAge());
        currentOwner.setCars(owner.getCars());
    }

    @Override
    public void addCar(int ownerId,Car car) throws OwnerNotFoundException {
        car.setId(++ carId);
        if(getById(ownerId).getCars() == null){
            getById(ownerId).setCars(new ArrayList<>(List.of(car)));
        } else {
            getById(ownerId).getCars().add(car);
        }
    }

    @Override
    public void deleteCar(int ownerId,int carId) throws OwnerNotFoundException {

        getById(ownerId).setCars(
                getById(ownerId).getCars()
                        .stream()
                        .filter(c->c.getId() != carId)
                        .toList());
    }

    @Override
    public List<Car> getAllCars(int id) throws OwnerNotFoundException {
        if(getById(id).getCars() == null){
            return new ArrayList<>();
        }
        return getById(id).getCars();
    }
}
