package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.RefundDTO;
import lk.ijse.shoeshop.repo.RefundRepo;
import lk.ijse.shoeshop.service.RefundService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {
    private final RefundRepo refundRepo;
    private final Mapping mapping;

    @Override
    public RefundDTO saveRefund(RefundDTO refundDTO) {
        refundDTO.setRefundId(UtilMatters.generateId());
        return mapping.toRefundDTO(refundRepo.save(mapping.toRefundEntity(refundDTO)));
    }
}
