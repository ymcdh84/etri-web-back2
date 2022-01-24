package com.iljin.apiServer.template.tax;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxInvoiceItemRepository extends JpaRepository<TaxInvoiceItem, TaxInvoiceItemKey> {

}
