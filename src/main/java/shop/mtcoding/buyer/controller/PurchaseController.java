package shop.mtcoding.buyer.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.buyer.dto.PurchaseAllDto;
import shop.mtcoding.buyer.model.Product;
import shop.mtcoding.buyer.model.ProductRepository;
import shop.mtcoding.buyer.model.Purchase;
import shop.mtcoding.buyer.model.PurchaseRepository;
import shop.mtcoding.buyer.model.User;
import shop.mtcoding.buyer.service.PurchaseService;

@Controller
public class PurchaseController {

    @Autowired
    HttpSession session;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    ProductRepository productRepository;
    /*
     * 1. 검증된 유저인지 확인
     * 2. 입력된 제품 아이디가 존재하는지 확인
     * 3. 카운트가 제품의 물량보다 적은지 확인
     * 4. 제품 수량 업데이트
     * 5. 구매 목록에 행 인서트
     */
    @PostMapping("/product/buy")
    public String buy(int productId, int count) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/missing";
        }
        int result = purchaseService.buyTransaction(principal.getId(), productId, count);
        if (result == -1) {
            return "redirect:/missing";
        }
        return "redirect:/";
    }

    @GetMapping("/purchase")
    public String purchase(Model model){
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/missing";
        }
        List<PurchaseAllDto> purchaseList = purchaseRepository.findByUserId(principal.getId());
        model.addAttribute("purchaseList", purchaseList);
        return "purchase/list";
    }
    
    /*
    *  로그인 검증 (participal)
    *  구매 목록이 있는지 검증
    *  제품이 있는지 검증
    *  구매 목록 지우기
    *  재고 업데이트하기
    */
    

    @PostMapping("/purchase/{purchaseId}/delete")
    public String deletePurcahse(@PathVariable("purchaseId") int purchaseId){
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/missing";
        }
        Purchase purchase = purchaseRepository.findById(purchaseId);
        if (purchase == null) {
            return "redirect:/missing";
        }
        int result = purchaseService.removePurchase(purchase);
        if (result == -1) {
            return "redirect:/missing";
        }
        return "redirect:/purchase";
    }
}
