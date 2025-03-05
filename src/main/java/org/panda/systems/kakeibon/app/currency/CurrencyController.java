package org.panda.systems.kakeibon.app.currency;

import org.panda.systems.kakeibon.domain.model.currency.CurrencyList;
import org.panda.systems.kakeibon.domain.repository.currency.CurrencyListRepository;
import org.panda.systems.kakeibon.domain.service.currency.CurrencyListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/currency", method = {RequestMethod.GET, RequestMethod.POST})
public class CurrencyController {
    @Autowired
    CurrencyListService currencyService;
    @Autowired
    private CurrencyListRepository currencyListRepository;

    @PostMapping("")
    String list(Model model) {
        List<CurrencyList> currencies = currencyService.findAll();

        model.addAttribute("currencies", currencies);

        return "currency/showList";
    }

    @GetMapping("/add")
    String add(CurrencyListForm form, Model model) {
        List<CurrencyList> currencies = currencyService.findAll();
        CurrencyList currencyList = new CurrencyList();

        model.addAttribute("currencyList", currencyList);
        model.addAttribute("currencies", currencies);

        return "currency/add";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    String confirm(
            @ModelAttribute @Validated CurrencyList currencyList, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "入力内容に誤りがあります");
            return "currency/add";
        }

        CurrencyList currency = new CurrencyList();
        currency.setCurrencyName(currencyList.getCurrencyName());

        currency = currencyService.saveAndFlush(currency);
        CurrencyListForm currencyListForm = new CurrencyListForm();
        currencyListForm.setCurrencyId(currency.getCurrencyId());
        currencyListForm.setCurrencyName(currency.getCurrencyName());
        List<CurrencyList> currencies = currencyService.findAll();

        model.addAttribute("currencyListForm", currencyListForm);
        model.addAttribute("currencies", currencies);

        return "currency/showList";
    }

//  @RequestMapping(value = "{currencyId}/edit", method = RequestMethod.GET)
//  String edit(
//      @PathVariable("currencyId") Long currencyId,
//      @ModelAttribute CurrencyListForm form, Model model) {
//
//    CurrencyList currencyList = currencyService.findById(currencyId);
//
//    model.addAttribute("currencyList", currencyList);
//
//    return "/currency/edit";
//  }

    @RequestMapping(value = "/{currencyId}/edit", method = RequestMethod.POST)
    String editForm(@PathVariable("currencyId") Long currencyId,
                    Model model) {
        CurrencyList currencyList = currencyService.findById(currencyId);

        model.addAttribute("currencyList", currencyList);

        return "currency/edit";
    }

    @RequestMapping(value = "/{currencyId}/confirm", method = RequestMethod.POST)
    String confirm(
            @PathVariable("currencyId") Long currencyId,
            @ModelAttribute @Validated CurrencyList currencyList, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "入力内容に誤りがあります");
            return "currency/edit";
        }

        CurrencyList currency = new CurrencyList();
        currency.setCurrencyId(currencyList.getCurrencyId());
        currency.setCurrencyName(currencyList.getCurrencyName());
        currencyService.saveAndFlush(currency);

        List<CurrencyList> currencies = currencyService.findAll();

        model.addAttribute("currencies", currencies);

        return "currency/showList";
    }
}
