package shop.mtcoding.buyer.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.buyer.model.ProductRepository;
import shop.mtcoding.buyer.model.User;

@Controller
public class PurchaseController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    HttpSession session;

    @GetMapping("/purchase")
    public String purchaseList() {
        return "user/purchaseList";
    }

    @PostMapping("product/{id}/buy")
    public String buy(@PathVariable("id") int id, int count) {
        User user = (User) session.getAttribute("principal");
        if (user == null) {
            return "redirect:/loginForm";
        } else {
            int result = productRepository.updateQtyByID(id, count);
            if (result == 1) {
                return "redirect:/product/{id}";
            } else {
                return "redirect:/product/{id}";
            }
        }
    }
}
