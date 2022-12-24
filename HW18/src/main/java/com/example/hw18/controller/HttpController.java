package com.example.hw18.controller;

import com.example.hw18.exeption.OwnerExistException;
import com.example.hw18.exeption.OwnerNotFoundException;
import com.example.hw18.model.Car;
import com.example.hw18.model.Owner;
import com.example.hw18.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class HttpController {
    @Autowired
    private OwnerService ownerService;

    @GetMapping("/")
    public String defaultHtml(Model model) {
        model.addAttribute("owners",ownerService.getAll());
        return "index";
    }

    @GetMapping("/showNewOwnerForm")
    public String showNewOwnerForm(Model model) {
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
    public String updateOwner(Owner owner) throws  OwnerNotFoundException {
        ownerService.updateById(owner);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable("id") int id,Model model) throws OwnerNotFoundException {
        Owner owner = ownerService.getById(id);
        model.addAttribute("owner",owner);
        return "owner_update";
    }

    @GetMapping("/deleteOwner/{id}")
    public String deleteOwner(@PathVariable("id") int id) {
        this.ownerService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/showCars/{id}")
    public String showCars(@PathVariable("id") int id, Model model) throws OwnerNotFoundException {
        Owner owner = ownerService.getById(id);
        model.addAttribute("cars",ownerService.getAllCars(id));
        model.addAttribute("owner", owner);
        return "cars";
    }

    @GetMapping("/showNewCarForm/{id}")
    public String showNewCarForm(@PathVariable("id") int id, Model model) throws OwnerNotFoundException {
        Car car = new Car();
        Owner owner = ownerService.getById(id);
        model.addAttribute("car",car);
        model.addAttribute("owner",owner);
        return "new_car";
    }

    @PostMapping("/saveCar/{id}")
    public String saveCar(@PathVariable("id") int id, Car car) throws OwnerNotFoundException {
        ownerService.addCar(id,car);
        return "redirect:/";
    }

    @GetMapping("/deleteCar/{cid}/owner/{id}")
    public String deleteCar(@PathVariable("cid") int cid, @PathVariable("id") int id) throws OwnerNotFoundException {
        ownerService.deleteCar(id,cid);
        return "redirect:/";
    }

}