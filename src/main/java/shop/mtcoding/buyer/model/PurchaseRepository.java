package shop.mtcoding.buyer.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.buyer.dto.PurchaseAllDto;


@Mapper
public interface PurchaseRepository {

    public int insert(@Param("userId") int userId, @Param("productId") int productId, @Param("count") int count);

    public List<PurchaseAllDto> findByUserId(int userId);
}
