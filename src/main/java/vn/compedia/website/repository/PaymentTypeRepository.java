package vn.compedia.website.repository;

import org.springframework.data.repository.CrudRepository;
import vn.compedia.website.model.PaymentType;

public interface PaymentTypeRepository extends CrudRepository<PaymentType, Long> {
}
