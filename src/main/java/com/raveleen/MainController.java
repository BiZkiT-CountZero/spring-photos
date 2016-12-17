package com.raveleen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Святослав on 16.12.2016.
 */
@Controller
public class MainController {
    @Autowired
    private DishDAO dishDAO;

    @RequestMapping("/")
    public ModelAndView listDishes() {
        return new ModelAndView("main", "list", dishDAO.getWholeList());
    }

    @RequestMapping("/discount")
    public ModelAndView listDiscountDishes() {
        return new ModelAndView("main", "list", dishDAO.getDiscountList());
    }

    @RequestMapping("/portion")
    public ModelAndView listPortionList() {
        return new ModelAndView("main", "list", dishDAO.getOnePortionList());
    }

    @RequestMapping(value = "/price", method = RequestMethod.GET)
    public ModelAndView listPriceList(@RequestParam(value = "min_price") int minPrice,
                                      @RequestParam(value = "max_price") int maxPrice) {
        return new ModelAndView("main", "list", dishDAO.getPriceList(minPrice, maxPrice));
    }

    @RequestMapping("/add-form")
    public String listDiscountDishes(Model model) {
        return "add-form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addDish(@RequestParam(value = "name") String name,
                                @RequestParam(value = "weight") int weight,
                                @RequestParam(value = "price") int price,
                                @RequestParam(value = "discount") String discount,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        Dish dish;
        if (discount.equals("0")) {
            dish = new Dish(name, weight, price);
        } else {
            dish = new Dish(name, weight, price, Integer.parseInt(discount));
        }
        dishDAO.addDish(dish);
        return new ModelAndView("main", "list", dishDAO.getWholeList());
    }

}
