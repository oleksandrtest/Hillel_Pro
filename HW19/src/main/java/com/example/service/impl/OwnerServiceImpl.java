package com.example.service.impl;

import com.example.exeption.OwnerExistException;
import com.example.exeption.OwnerNotFoundException;
import com.example.model.Owner;
import com.example.repository.OwnerDao;
import com.example.service.OwnerService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {


    private final OwnerDao ownerDao;

    public OwnerServiceImpl(OwnerDao ownerDao) {
        this.ownerDao = ownerDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Owner owner) throws OwnerExistException {
        ownerDao.add(owner);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "allOwners", key = "#owner.id")
    public void updateById(Owner owner) {
        ownerDao.updateById(owner);
    }

    @Override
    @CacheEvict(value = "allOwners", key = "#id")
    public void deleteById(int id) {
        ownerDao.deleteById(id);
    }

    @Override
    @Cacheable(value = "allOwners", key = "#id")
    public Owner getById(int id) throws OwnerNotFoundException {
        return ownerDao.getById(id);
    }

    @Override
    @Cacheable("allOwners")
    public List<Owner> getAll() {
        return ownerDao.getAll();
    }
}
