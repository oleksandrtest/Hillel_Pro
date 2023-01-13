package com.example.controller;

import com.example.model.Car;
import com.example.model.Owner;
import com.example.service.CarService;
import com.example.service.OwnerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class HttpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @MockBean
    private CarService carService;

    @Test
    void defaultHtml() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/")
                        .accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("owners"));
    }


    @Test
    void showNewOwnerForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/showNewOwnerForm")
                        .accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("new_owner"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    void saveOwner() throws Exception {
        Owner owner = mock(Owner.class);
        willDoNothing().given(ownerService).add(owner);
        ResultActions response = mockMvc.perform(post("/saveOwner", owner));
        response.andExpect(status().is3xxRedirection());

    }

    @Test
    void showFormForUpdate() throws Exception {
        Owner owner = mock(Owner.class);
        willDoNothing().given(ownerService).updateById(owner);
        ResultActions response = mockMvc.perform(post("/updateOwner", owner));
        response.andExpect(status().is3xxRedirection());
    }

    @Test
    void deleteOwner() throws Exception {
        int ownerId = 1;
        willDoNothing().given(ownerService).deleteById(ownerId);
        ResultActions response = mockMvc.perform(get("/deleteOwner/{id}", ownerId));
        response.andExpect(status().is3xxRedirection());
    }

    @Test
    void saveCar() throws Exception {
        Car car = mock(Car.class);
        int ownerId = 1;
        willDoNothing().given(carService).add(ownerId, car);
        ResultActions response = mockMvc.perform(post("/saveCar/{id}", ownerId));
        response.andExpect(status().is3xxRedirection());
    }

    @Test
    void deleteCar() throws Exception {
        int carId = 1;
        int ownerId = 1;
        willDoNothing().given(carService).delete(1,1);
        ResultActions response = mockMvc.perform(get("/deleteCar/{cid}/owner/{id}",carId,ownerId));
        response.andExpect(status().is3xxRedirection());
    }
}