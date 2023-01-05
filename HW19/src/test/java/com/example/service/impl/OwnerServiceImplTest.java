package com.example.service.impl;

import com.example.exeption.OwnerExistException;
import com.example.exeption.OwnerNotFoundException;
import com.example.model.Owner;
import com.example.repository.OwnerDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceImplTest {
    @Mock
    public OwnerDao ownerDao;

    @Test
    void delete() throws OwnerNotFoundException {
        OwnerServiceImpl ownerService = new OwnerServiceImpl(ownerDao);
        Owner owner = mock(Owner.class);
        lenient().when(ownerDao.getById(owner.getId())).thenReturn(owner);
        ownerService.deleteById(owner.getId());
        verify(ownerDao).deleteById(owner.getId());
    }

    @Test
    void addAndUpdateTest() {
        OwnerServiceImpl ownerService = new OwnerServiceImpl(ownerDao);
        Owner owner = mock(Owner.class);
        doNothing().when(ownerDao).updateById(owner);
        ownerService.updateById(owner);
        verify(ownerDao, times(1)).updateById(owner);
    }


    @Test
    void getById() throws OwnerNotFoundException {
        OwnerServiceImpl ownerService = new OwnerServiceImpl(ownerDao);
        Owner owner = mock(Owner.class);
        when(ownerDao.getById(anyInt())).thenReturn(owner);
        Owner ownerOne = ownerService.getById(anyInt());
        Assertions.assertEquals(owner, ownerOne);
    }


    @Test
    void carNotFoundById() throws OwnerNotFoundException {
        OwnerServiceImpl ownerService = new OwnerServiceImpl(ownerDao);
        Owner owner = mock(Owner.class);
        when(ownerDao.getById(anyInt())).thenReturn(null);
        Owner ownerOne = ownerService.getById(anyInt());
        Assertions.assertNotEquals(ownerOne, owner);
    }

    @Test
    void getAllByOwnerId() {
        OwnerServiceImpl ownerService = new OwnerServiceImpl(ownerDao);
        List<Owner> owners = new ArrayList<>();
        owners.add(Owner.builder().id(1).firstName("Alex").lastName("Pshechko").age(13).build());
        owners.add(Owner.builder().id(2).firstName("Oleg").lastName("Dvoretskiy").age(32).build());
        owners.add(Owner.builder().id(2).firstName("Olga").lastName("Dvoretska").age(30).build());
        when(ownerService.getAll()).thenReturn(owners);
        List<Owner> ownersOne = ownerService.getAll();
        Assertions.assertEquals(ownersOne, owners);
    }

    @Test
    @DisplayName("Test assert exception")
    void getByIdThrowException() throws OwnerNotFoundException {
        OwnerServiceImpl ownerService = new OwnerServiceImpl(ownerDao);
        when(ownerDao.getById(1)).thenThrow(new OwnerNotFoundException(1));
        Throwable throwable = assertThrows(OwnerNotFoundException.class, () -> {
            ownerService.getById(1);
        });

        Assertions.assertEquals(OwnerNotFoundException.class, throwable.getClass());
    }

    @Test
    @DisplayName("Test assert exception")
    void addNewOwnerThrowException() throws OwnerExistException {
        OwnerServiceImpl ownerService = new OwnerServiceImpl(ownerDao);
        Owner owner = mock(Owner.class);
        doThrow(new OwnerExistException(owner.getId())).when(ownerDao).add(owner);
        Throwable throwable = assertThrows(OwnerExistException.class, () -> {
            ownerService.add(owner);
        });

        Assertions.assertEquals(OwnerExistException.class, throwable.getClass());
    }

}