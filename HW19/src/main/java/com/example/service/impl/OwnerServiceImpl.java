package com.example.hw19.service.impl;

import com.example.hw19.exeption.OwnerExistException;
import com.example.hw19.exeption.OwnerNotFoundException;
import com.example.hw19.model.Owner;
import com.example.hw19.repository.OwnerDao;
import com.example.hw19.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    OwnerDao ownerDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Owner owner) throws OwnerExistException {
        ownerDao.add(owner);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(Owner owner) {
        ownerDao.updateById(owner);
    }

    @Override
    public void deleteById(int id) throws OwnerNotFoundException {
        ownerDao.deleteById(id);
    }

    @Override
    public Owner getById(int id) throws OwnerNotFoundException {
        return ownerDao.getById(id);
    }

    @Override
    public List<Owner> getAll() {
        return ownerDao.getAll();
    }
}
