package com.example.hw19.controller;

import com.example.hw19.exeption.CarNotFoundException;
import com.example.hw19.exeption.OwnerExistException;
import com.example.hw19.exeption.OwnerNotFoundException;
import com.example.hw19.model.Car;
import com.example.hw19.model.Owner;
import com.example.hw19.repository.CarDao;
import com.example.hw19.repository.OwnerDao;
import com.example.hw19.service.CarService;
import com.example.hw19.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HttpController {
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private CarService carService;

    @GetMapping("/")
    public String defaultHtml(ModelMap model) {
        model.addAttribute("owners",ownerService.getAll());
        return "index";
    }

    @GetMapping("/showNewOwnerForm")
    public String showNewOwnerForm(ModelMap model) {
        Owner owner = new Owner();
        model.addAttribute("owner",owner);
        return "new_owner";
    }

    @PostMapping("/saveOwner")
    public String saveOwner(Owner owner) throws OwnerExistException {
        ownerService.add(owner);
        return "redirect:/";
    }

    @PostMapping("/updateOwner")
    public String updateOwner(Owner owner)  {
        ownerService.updateById(owner);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable("id") int id,ModelMap model) throws OwnerNotFoundException {
        Owner owner = ownerService.getById(id);
        model.addAttribute("owner",owner);
        return "owner_update";
    }

    @GetMapping("/deleteOwner/{id}")
    public String deleteOwner(@PathVariable("id") int id) throws OwnerNotFoundException {
        this.ownerService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/showCars/{id}")
    public String showCars(@PathVariable("id") int id, ModelMap model) throws OwnerNotFoundException {
        Owner owner = ownerService.getById(id);
        model.addAttribute("cars",carService.getAllByOwnerId(id));
        model.addAttribute("owner", owner);
        return "cars";
    }

    @GetMapping("/showNewCarForm/{id}")
    public String showNewCarForm(@PathVariable("id") int id, ModelMap model) throws OwnerNotFoundException {
        Car car = new Car();
        Owner owner = ownerService.getById(id);
        model.addAttribute("car",car);
        model.addAttribute("owner",owner);
        return "new_car";
    }

    @PostMapping("/saveCar/{id}")
    public String saveCar(@PathVariable("id") int id, Car car) {
        carService.add(id,car);
        return "redirect:/";
    }

    @GetMapping("/deleteCar/{cid}/owner/{id}")
    public String deleteCar(@PathVariable("cid") int cid, @PathVariable("id") int id) throws CarNotFoundException {
        carService.delete(id,cid);
        return "redirect:/";
    }

}