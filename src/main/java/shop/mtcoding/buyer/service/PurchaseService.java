package shop.mtcoding.buyer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.buyer.model.Product;
import shop.mtcoding.buyer.model.ProductRepository;
import shop.mtcoding.buyer.model.Purchase;
import shop.mtcoding.buyer.model.PurchaseRepository;

@Service
public class PurchaseService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PurchaseRepository purchaseRepository;


    /*
     * 1. 검증된 유저인지 확인
     * 2. 입력된 제품 아이디가 존재하는지 확인
     * 3. 카운트가 제품의 물량보다 적은지 확인
     * 4. 제품 수량 업데이트
     * 5. 구매 목록에 행 인서트
     */

    @Transactional
    public int buyTransaction(int principalId, int productId, int count){
        Product product = productRepository.findById(productId);
        if (product == null) {
            return -1;
        }
        if (product.getQty() < count) {
            return -1;
        }
        int result = productRepository.updateById(product.getId(), product.getName(), product.getPrice(), product.getQty() - count);
        if (result != 1) {
            return -1;
        }
        result = purchaseRepository.insert(principalId, productId, count);
        if (result != 1) {
            return -1;
        }
        return 1;
    }

    @Transactional
    public int removePurchase(Purchase purchase){
        Product product = productRepository.findById(purchase.getProductId());
        if (product == null) {
            return -1;
        }
        int result = purchaseRepository.delete(purchase.getId());
        if (result != 1) {
            return -1;
        }
        result = productRepository.updateById(product.getId(), product.getName(), product.getPrice(),
                product.getQty() + purchase.getCount());
        if (result != 1) {
            return -1;
        }
        return 1;
    }
}
