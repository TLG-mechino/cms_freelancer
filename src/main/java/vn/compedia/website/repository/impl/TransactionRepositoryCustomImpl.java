package vn.compedia.website.repository.impl;

import org.apache.commons.lang3.StringUtils;
import vn.compedia.website.dto.TransactionDto;
import vn.compedia.website.dto.TransactionSearchDto;
import vn.compedia.website.repository.TransactionRepositoryCustom;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryCustomImpl implements TransactionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TransactionDto> search(TransactionSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT t.TRANSACTION, " +
                "t.SENDER, " +
                "t.RECIPIENT, " +
                "t.TRANSACTION_TIME, " +
                "t.AMOUNT_OF_MONEY, " +
                "t.DISCOUNT_MONEY, " +
                "t.FINAL_MONEY, " +
                "t.CODE, " +
                "t.PAYMENT_TYPE_ID, " +
                "t.STATUS ");
        appendQuery(sb, searchDto);
        if (searchDto.getSortField() != null) {
            if (searchDto.getSortField().equals("transactionId")) {
                sb.append(" ORDER BY t.TRANSACTION ");
            }
            if (searchDto.getSortField().equals("sender")) {
                sb.append(" ORDER BY t.SENDER ");
            }
            if (searchDto.getSortField().equals("recipient")) {
                sb.append(" ORDER BY t.RECIPIENT ");
            }
            if (searchDto.getSortField().equals("transactionTime")) {
                sb.append(" ORDER BY t.TRANSACTION_TIME ");
            }
            if (searchDto.getSortField().equals("amountOfMoney")) {
                sb.append(" ORDER BY t.AMOUNT_OF_MONEY ");
            }
            if (searchDto.getSortField().equals("discountMoney")) {
                sb.append(" ORDER BY t.DISCOUNT_MONEY ");
            }
            if (searchDto.getSortField().equals("finalMoney")) {
                sb.append(" ORDER BY t.FINAL_MONEY ");
            }
            if (searchDto.getSortField().equals("code")) {
                sb.append(" ORDER BY t.CODE ");
            }
            if (searchDto.getSortField().equals("paymentTypeId")) {
                sb.append(" ORDER BY t.PAYMENT_TYPE_ID ");
            }
            if (searchDto.getSortField().equals("status")) {
                sb.append(" ORDER BY t.STATUS ");
            }
            sb.append(searchDto.getSortOrder());
        } else {
            sb.append(" ORDER BY t.TRANSACTION DESC ");
        }
        Query query = createQuery(sb, searchDto);

        List<Object[]> resultList = query.getResultList();
        List<TransactionDto> list = new ArrayList<>();
        for (Object[] result : resultList) {
            TransactionDto dto = new TransactionDto();
            dto.setTransactionId(ValueUtil.getLongByObject(result[0]));
            dto.setSender(ValueUtil.getStringByObject(result[1]));
            dto.setRecipient(ValueUtil.getStringByObject(result[2]));
            dto.setTransactionTime(ValueUtil.getDateByObject(result[3]));
            dto.setAmountOfMoney(ValueUtil.getDoubleByObject(result[4]));
            dto.setDiscountMoney(ValueUtil.getDoubleByObject(result[5]));
            dto.setFinalMoney(ValueUtil.getDoubleByObject(result[6]));
            dto.setCode(ValueUtil.getStringByObject(result[7]));
            dto.setPaymentTypeId(ValueUtil.getIntegerByObject(result[8]));
            dto.setStatus(ValueUtil.getIntegerByObject(result[9]));
            list.add(dto);
        }
        return list;
    }

    @Override
    public BigInteger countSearch(TransactionSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(t.TRANSACTION) ");
        appendQuery(sb, searchDto);
        Query query = createQuery(sb, searchDto);
        return (BigInteger) query.getSingleResult();
    }

    public Query createQuery(StringBuilder sb, TransactionSearchDto searchDto) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            query.setParameter("keyword", "%" + searchDto.getKeyword().trim() + "%");
        }
        if (searchDto.getStatus() != null) {
            query.setParameter("status", searchDto.getStatus());
        }
        return query;

    }

    public void appendQuery(StringBuilder sb, TransactionSearchDto SearchDto) {
        sb.append(" FROM transaction t WHERE 1 = 1 ");
        if (SearchDto.getKeyword() != null) {
            sb.append(" AND (t.CODE LIKE :keyword OR t.SENDER LIKE :keyword OR t.RECIPIENT LIKE :keyword) ");
        }
        if (SearchDto.getPaymentTypeSearch() != null) {
            sb.append(" AND t.PAYMENT_TYPE_ID = :paymentTypeSearch ");
        }
        if (SearchDto.getLessMoney() != 0) {
            sb.append(" AND t.AMOUNT_MONEY >= :lessMoney ");
        }
        if (SearchDto.getGreatMoney() != 0) {
            sb.append(" AND t.AMOUNT_MONEY <= :greatMoney ");
        }
    }

    public Query createQueryExport(StringBuilder sb, TransactionSearchDto SearchDto, Integer offset, Integer limit) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (SearchDto.getKeyword() != null) {
            query.setParameter("keyword", "%" + SearchDto.getKeyword().trim() + "%");
        }
        if (SearchDto.getPaymentTypeSearch() != null) {
            query.setParameter("paymentTypeSearch", SearchDto.getPaymentTypeSearch());
        }
        if (SearchDto.getLessMoney() != 0) {
            query.setParameter("lessMoney", SearchDto.getLessMoney());
        }
        if (SearchDto.getEndTime() != null) {
            query.setParameter("greatMoney", SearchDto.getGreatMoney());
        }
        if (offset != null) {
            query.setParameter("offset", offset);
        }
        if (limit != null) {
            query.setParameter("limit", limit);
        }
        return query;
    }

    @Override
    public List<TransactionDto> exportExcel(TransactionSearchDto SearchDto, Integer offset, Integer limit) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT t.TRANSACTION, " +
                "t.SENDER, " +
                "t.RECIPIENT, " +
                "t.TRANSACTION_TIME, " +
                "t.AMOUNT_OF_MONEY, " +
                "t.DISCOUNT_MONEY, " +
                "t.FINAL_MONEY, " +
                "t.CODE, " +
                "t.PAYMENT_TYPE_ID, " +
                "t.STATUS ");
        appendQuery(sb, SearchDto);
        if (SearchDto.getSortField() != null) {
            if (SearchDto.getSortField().equals("transactionId")) {
                sb.append(" ORDER BY t.TRANSACTION ");
            }
            if (SearchDto.getSortField().equals("sender")) {
                sb.append(" ORDER BY t.SENDER ");
            }
            if (SearchDto.getSortField().equals("recipient")) {
                sb.append(" ORDER BY t.RECIPIENT ");
            }
            if (SearchDto.getSortField().equals("transactionTime")) {
                sb.append(" ORDER BY t.TRANSACTION_TIME ");
            }
            if (SearchDto.getSortField().equals("amountOfMoney")) {
                sb.append(" ORDER BY t.AMOUNT_OF_MONEY ");
            }
            if (SearchDto.getSortField().equals("discountMoney")) {
                sb.append(" ORDER BY t.DISCOUNT_MONEY ");
            }
            if (SearchDto.getSortField().equals("finalMoney")) {
                sb.append(" ORDER BY t.FINAL_MONEY ");
            }
            if (SearchDto.getSortField().equals("code")) {
                sb.append(" ORDER BY t.CODE ");
            }
            if (SearchDto.getSortField().equals("paymentTypeId")) {
                sb.append(" ORDER BY t.PAYMENT_TYPE_ID ");
            }
            if (SearchDto.getSortField().equals("status")) {
                sb.append(" ORDER BY t.STATUS ");
            }
            sb.append(SearchDto.getSortOrder());
        } else {
            sb.append(" ORDER BY t.TRANSACTION DESC ");
        }
        sb.append(" LIMIT :offset, :limit");

        Query query = createQueryExport(sb, SearchDto, offset, limit);

        List<Object[]> resultList = query.getResultList();
        List<TransactionDto> list = new ArrayList<>();
        for (Object[] result : resultList) {
            TransactionDto dto = new TransactionDto();
            dto.setTransactionId(ValueUtil.getLongByObject(result[0]));
            dto.setSender(ValueUtil.getStringByObject(result[1]));
            dto.setRecipient(ValueUtil.getStringByObject(result[2]));
            dto.setTransactionTime(ValueUtil.getDateByObject(result[3]));
            dto.setAmountOfMoney(ValueUtil.getDoubleByObject(result[4]));
            dto.setDiscountMoney(ValueUtil.getDoubleByObject(result[5]));
            dto.setFinalMoney(ValueUtil.getDoubleByObject(result[6]));
            dto.setCode(ValueUtil.getStringByObject(result[7]));
            dto.setPaymentTypeId(ValueUtil.getIntegerByObject(result[8]));
            dto.setStatus(ValueUtil.getIntegerByObject(result[9]));
            list.add(dto);
        }
        return list;
    }
}
