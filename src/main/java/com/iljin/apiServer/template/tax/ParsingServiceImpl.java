package com.iljin.apiServer.template.tax;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ParsingServiceImpl implements ParsingService{

    private final TaxInvoiceRepository taxInvoiceRepository;

    private final TaxInvoiceItemRepository taxInvoiceItemRepository;

    /**
     * 세금계산서 파일 데이터 베이스 저장
     * @param taxIssueIdList
     * @throws Exception
     */
    public void saveTaxBillToDataBase(List<String> taxIssueIdList) throws Exception {

        String xmlFileDir = "C:/tax/";

        for(String taxIssueId : taxIssueIdList){
            try {
                File fXmlFile = new File(xmlFileDir + taxIssueId + ".xml");

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                dbFactory.setIgnoringElementContentWhitespace(true);
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);

                doc.getDocumentElement().normalize();

                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                //1. 관리정보 (Exchanged Document)
                NodeList exchDocList = doc.getElementsByTagName("ExchangedDocument");
                Map<String, Object> exchMap = this.parseExchangedDocument(exchDocList);

                //2. 기본 정보(TaxInvoice Document)
                NodeList taxInvDocList = doc.getElementsByTagName("TaxInvoiceDocument");
                Map<String, Object> taxInvMap = this.parseTaxInvoiceDocument(taxInvDocList);

                //3. 계산서 정보(TaxInvoice Trade Settlement)
                NodeList invoicerPartyList = doc.getElementsByTagName("InvoicerParty");
                NodeList invoiceePartyList = doc.getElementsByTagName("InvoiceeParty");
                NodeList brokerPartyList = doc.getElementsByTagName("BrokerParty");
                NodeList paymentMeansList = doc.getElementsByTagName("SpecifiedPaymentMeans");
                NodeList monetarySumList = doc.getElementsByTagName("SpecifiedMonetarySummation");

                Map<String, Object> taxInvSettMap = this.parseTaxInvoiceTradeSettlement(invoicerPartyList
                                                                                        , invoiceePartyList
                                                                                        , brokerPartyList
                                                                                        , paymentMeansList
                                                                                        , monetarySumList);

                //4. 상품 정보(TaxInvoice TradeLine Item)
                NodeList taxInvTrdLineItemList = doc.getElementsByTagName("TaxInvoiceTradeLineItem");
                List<Map<String, Object>> taxInvItemList = this.parseTaxInvoiceTradeLineItem(taxInvTrdLineItemList);

                System.out.println("===================================================");
                System.out.println("====================PARSING END====================");
                System.out.println("===================================================");

                this.saveTaxBill(exchMap, taxInvMap, taxInvSettMap, taxInvItemList);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 세금계산서 DB 저장
     * @param exchMap
     * @param taxInvMap
     * @param taxInvSettMap
     * @param taxInvItemList
     * @return
     */
    public ResponseEntity<String> saveTaxBill(  Map<String, Object> exchMap
                                              , Map<String, Object> taxInvMap
                                              , Map<String, Object> taxInvSettMap
                                              , List<Map<String, Object>> taxInvItemList) {

        //[STEP-1] tax_invoice 데이터 저장
        TaxInvoice invoice = new TaxInvoice(
                stringValueOf(taxInvMap.get("taxInvDocIssueId")),
                stringValueOf(exchMap.get("exchDocId")),
                stringValueOf(exchMap.get("exchDocReferId")),
                stringValueOf(exchMap.get("exchDocIssueDateTime")),
                stringValueOf(taxInvMap.get("taxInvDocIssueId")),
                stringValueOf(taxInvMap.get("taxInvDocIssueDateTime")),
                stringValueOf(taxInvMap.get("taxInvDocTypeCode")),
                stringValueOf(taxInvMap.get("taxInvDocPurposeCode")),
                stringValueOf(taxInvMap.get("taxInvDocAmendCode")),
                stringValueOf(taxInvMap.get("taxInvDocOriId")),
                stringValueOf(taxInvMap.get("taxInvDocDesc")),
                stringValueOf(taxInvMap.get("taxInvDocRefId")),
                stringValueOf(taxInvMap.get("taxInvDocAcceptStDateTime")),
                stringValueOf(taxInvMap.get("taxInvDocAcceptEndDateTime")),
                BigDecimal.valueOf(NumberUtils.toDouble(stringValueOf(taxInvMap.get("taxInvDocRefItemQuan")))),
                stringValueOf(taxInvSettMap.get("invoicerId")),
                stringValueOf(taxInvSettMap.get("invoicerTaxRegId")),
                stringValueOf(taxInvSettMap.get("invoicerName")),
                stringValueOf(taxInvSettMap.get("invoicerSpecifiedPerson")),
                stringValueOf(taxInvSettMap.get("invoicerSpecifiedAddr")),
                stringValueOf(taxInvSettMap.get("invoicerTypeCode")),
                stringValueOf(taxInvSettMap.get("invoicerClassificationCode")),
                stringValueOf(taxInvSettMap.get("invoicerDefineDept")),
                stringValueOf(taxInvSettMap.get("invoicerDefinePerson")),
                stringValueOf(taxInvSettMap.get("invoicerDefineTel")),
                stringValueOf(taxInvSettMap.get("invoicerDefineUri")),
                stringValueOf(taxInvSettMap.get("invoiceeId")),
                stringValueOf(taxInvSettMap.get("invoiceeBusinessTypeCode")),
                stringValueOf(taxInvSettMap.get("invoiceeTaxRegId")),
                stringValueOf(taxInvSettMap.get("invoiceeName")),
                stringValueOf(taxInvSettMap.get("invoiceeSpecifiedPerson")),
                stringValueOf(taxInvSettMap.get("invoiceeSpecifiedAddr")),
                stringValueOf(taxInvSettMap.get("invoiceeTypeCode")),
                stringValueOf(taxInvSettMap.get("invoiceeClassificationCode")),
                stringValueOf(taxInvSettMap.get("invoiceePriDept")),
                stringValueOf(taxInvSettMap.get("invoiceePriPerson")),
                stringValueOf(taxInvSettMap.get("invoiceePriTel")),
                stringValueOf(taxInvSettMap.get("invoiceePriUri")),
                stringValueOf(taxInvSettMap.get("invoiceeSecondDept")),
                stringValueOf(taxInvSettMap.get("invoiceeSecondPerson")),
                stringValueOf(taxInvSettMap.get("invoiceeSecondTel")),
                stringValueOf(taxInvSettMap.get("invoiceeSecondUri")),
                stringValueOf(taxInvSettMap.get("brokerId")),
                stringValueOf(taxInvSettMap.get("brokerTaxRegId")),
                stringValueOf(taxInvSettMap.get("brokerName")),
                stringValueOf(taxInvSettMap.get("brokerSpecifiedPerson")),
                stringValueOf(taxInvSettMap.get("brokerSpecifiedAddr")),
                stringValueOf(taxInvSettMap.get("brokerTypeCode")),
                stringValueOf(taxInvSettMap.get("brokerClassificationCode")),
                stringValueOf(taxInvSettMap.get("brokerDefineDept")),
                stringValueOf(taxInvSettMap.get("brokerDefinePerson")),
                stringValueOf(taxInvSettMap.get("brokerDefineTel")),
                stringValueOf(taxInvSettMap.get("brokerDefineUri")),
                stringValueOf(taxInvSettMap.get("payTypeCode")),
                BigDecimal.valueOf(NumberUtils.toDouble(stringValueOf(taxInvSettMap.get("payAmt")))),
                BigDecimal.valueOf(NumberUtils.toDouble(stringValueOf(taxInvSettMap.get("chargeTotalAmt")))),
                BigDecimal.valueOf(NumberUtils.toDouble(stringValueOf(taxInvSettMap.get("taxTotalAmt")))),
                BigDecimal.valueOf(NumberUtils.toDouble(stringValueOf(taxInvSettMap.get("grandTotalAmt")))),
                "scheduler",
                LocalDateTime.now(),
                "scheduler",
                LocalDateTime.now()
        );

        taxInvoiceRepository.save(invoice);

        //[STEP-2] tax_invoice_item 데이터 저장
        for(Map<String, Object> taxInvItem : taxInvItemList) {

            TaxInvoiceItem item = new TaxInvoiceItem(
                    stringValueOf(taxInvMap.get("taxInvDocIssueId")),
                    stringValueOf(taxInvItem.get("seq")),
                    stringValueOf(taxInvItem.get("itemPurchExpDateTime")),
                    stringValueOf(taxInvItem.get("itemName")),
                    stringValueOf(taxInvItem.get("itemInform")),
                    stringValueOf(taxInvItem.get("itemDesc")),
                    BigDecimal.valueOf(NumberUtils.toDouble(stringValueOf(taxInvItem.get("itemChargeUnitQuan")))),
                    BigDecimal.valueOf(NumberUtils.toDouble(stringValueOf(taxInvItem.get("itemUnitAmt")))),
                    BigDecimal.valueOf(NumberUtils.toDouble(stringValueOf(taxInvItem.get("itemAmt")))),
                    BigDecimal.valueOf(NumberUtils.toDouble(stringValueOf(taxInvItem.get("itemCalcAmt")))),
                    "scheduler",
                    LocalDateTime.now(),
                    "scheduler",
                    LocalDateTime.now()
            );

            taxInvoiceItemRepository.save(item);
        }

        return new ResponseEntity<>("저장 되었습니다.", HttpStatus.OK);
    }

    /**
     * 1. 관리 정보(Exchanged Document) 데이터 파싱
     * @param exchDocList
     * @return
     * @throws Exception
     */
    public Map<String, Object> parseExchangedDocument(NodeList exchDocList) throws Exception {
        Map<String, Object> rst = new HashMap<>();

        Node exchDocNode = exchDocList.item(0);
        Element eElement = (Element) exchDocNode;

        rst.put("exchDocId", getTagValue("ID", eElement));
        rst.put("exchDocReferId", getTagValue("ReferencedDocument", "ID", eElement));
        rst.put("exchDocIssueDateTime", getTagValue("IssueDateTime", eElement));

        return rst;
    }

    /**
     * 2. 기본 정보(TaxInvoice Document) 데이터 파싱
     * @param taxInvDocList
     * @return
     * @throws Exception
     */
    public Map<String, Object> parseTaxInvoiceDocument(NodeList taxInvDocList) throws Exception {
        Map<String, Object> rst = new HashMap<>();

        Node taxInvDocNode = taxInvDocList.item(0);
        Element eElement = (Element) taxInvDocNode;

        rst.put("taxInvDocIssueId", getTagValue("IssueID", eElement));
        rst.put("taxInvDocIssueDateTime", getTagValue("IssueDateTime", eElement));
        rst.put("taxInvDocTypeCode", getTagValue("TypeCode", eElement));
        rst.put("taxInvDocPurposeCode", getTagValue("PurposeCode", eElement));
        rst.put("taxInvDocAmendCode", getTagValue("AmendmentStatusCode", eElement));
        rst.put("taxInvDocOriId", getTagValue("OriginalIssueID", eElement));
        rst.put("taxInvDocDesc", getTagValue("DescriptionText", eElement));
        rst.put("taxInvDocRefId", getTagValue("ReferencedImportDocument", "ID", eElement));
        rst.put("taxInvDocRefItemQuan", getTagValue("ReferencedImportDocument", "ItemQuantity", eElement));
        rst.put("taxInvDocAcceptStDateTime", getTagValue("ReferencedImportDocument", "AcceptablePeriod", "StartDateTime", eElement));
        rst.put("taxInvDocAcceptEndDateTime", getTagValue("ReferencedImportDocument", "AcceptablePeriod", "EndDateTime", eElement));

        return rst;
    }


    /**
     * 3. 계산서 정보(TaxInvoice Trade Settlement) 데이터 파싱
     * @param invoicerPartyList
     * @param invoiceePartyList
     * @param brokerPartyList
     * @param paymentMeansList
     * @param monetarySumList
     * @return
     * @throws Exception
     */
    public Map<String, Object> parseTaxInvoiceTradeSettlement( NodeList invoicerPartyList
                                                             , NodeList invoiceePartyList
                                                             , NodeList brokerPartyList
                                                             , NodeList paymentMeansList
                                                             , NodeList monetarySumList) throws Exception {
        Map<String, Object> rst = new HashMap<>();

        //[STEP-1] 거래처 정보 - 공급자 Parsing
        Node invoicerNode = invoicerPartyList.item(0);
        Element eInvoicerElement = (Element) invoicerNode;

        rst.put("invoicerId", getTagValue("ID", eInvoicerElement));
        rst.put("invoicerTaxRegId", getTagValue("SpecifiedOrganization", "TaxRegistrationID", eInvoicerElement));
        rst.put("invoicerName", getTagValue("NameText", eInvoicerElement));
        rst.put("invoicerSpecifiedPerson", getTagValue("SpecifiedPerson", "NameText", eInvoicerElement));
        rst.put("invoicerSpecifiedAddr", getTagValue("SpecifiedAddress", "LineOneText", eInvoicerElement));
        rst.put("invoicerTypeCode", getTagValue("TypeCode", eInvoicerElement));
        rst.put("invoicerClassificationCode", getTagValue("ClassificationCode", eInvoicerElement));
        rst.put("invoicerDefineDept", getTagValue("DefinedContact", "DepartmentNameText", eInvoicerElement));
        rst.put("invoicerDefinePerson", getTagValue("DefinedContact", "PersonNameText", eInvoicerElement));
        rst.put("invoicerDefineTel", getTagValue("DefinedContact", "TelephoneCommunication", eInvoicerElement));
        rst.put("invoicerDefineUri", getTagValue("DefinedContact", "URICommunication", eInvoicerElement));

        //[STEP-2] 거래처 정보 - 공급받는자 Parsing
        Node invoiceeNode = invoiceePartyList.item(0);
        Element eInvoiceeElement = (Element) invoiceeNode;

        rst.put("invoiceeId", getTagValue("ID", eInvoiceeElement));
        rst.put("invoiceeBusinessTypeCode", getTagValue("SpecifiedOrganization", "BusinessTypeCode", eInvoiceeElement));
        rst.put("invoiceeTaxRegId", getTagValue("SpecifiedOrganization", "TaxRegistrationID", eInvoiceeElement));
        rst.put("invoiceeName", getTagValue("NameText", eInvoiceeElement));
        rst.put("invoiceeSpecifiedPerson", getTagValue("SpecifiedPerson", "NameText", eInvoiceeElement));
        rst.put("invoiceeSpecifiedAddr", getTagValue("SpecifiedAddress", "LineOneText", eInvoiceeElement));
        rst.put("invoiceeTypeCode", getTagValue("TypeCode", eInvoiceeElement));
        rst.put("invoiceeClassificationCode", getTagValue("ClassificationCode", eInvoiceeElement));
        rst.put("invoiceePriDept", getTagValue("PrimaryDefinedContact", "DepartmentNameText", eInvoiceeElement));
        rst.put("invoiceePriPerson", getTagValue("PrimaryDefinedContact", "PersonNameText", eInvoiceeElement));
        rst.put("invoiceePriTel", getTagValue("PrimaryDefinedContact", "TelephoneCommunication", eInvoiceeElement));
        rst.put("invoiceePriUri", getTagValue("PrimaryDefinedContact", "URICommunication", eInvoiceeElement));
        rst.put("invoiceeSecondDept", getTagValue("SecondaryDefinedContact", "DepartmentNameText", eInvoiceeElement));
        rst.put("invoiceeSecondPerson", getTagValue("SecondaryDefinedContact", "PersonNameText", eInvoiceeElement));
        rst.put("invoiceeSecondTel", getTagValue("SecondaryDefinedContact", "TelephoneCommunication", eInvoiceeElement));
        rst.put("invoiceeSecondUri", getTagValue("SecondaryDefinedContact", "URICommunication", eInvoiceeElement));


        //[STEP-3] 거래처 정보 - 수탁자 Parsing
        Node brokerNode = brokerPartyList.item(0);
        Element eBrokerElement = (Element) brokerNode;

        if(eBrokerElement != null){

            rst.put("brokerId", getTagValue("ID", eBrokerElement));
            rst.put("brokerTaxRegId", getTagValue("SpecifiedOrganization", "TaxRegistrationID", eBrokerElement));
            rst.put("brokerName", getTagValue("NameText", eBrokerElement));
            rst.put("brokerSpecifiedPerson", getTagValue("SpecifiedPerson", "NameText", eBrokerElement));
            rst.put("brokerSpecifiedAddr", getTagValue("SpecifiedAddress", "LineOneText", eBrokerElement));
            rst.put("brokerTypeCode", getTagValue("TypeCode", eBrokerElement));
            rst.put("brokerClassificationCode", getTagValue("ClassificationCode", eBrokerElement));
            rst.put("brokerDefineDept", getTagValue("DefinedContact", "DepartmentNameText", eBrokerElement));
            rst.put("brokerDefinePerson", getTagValue("DefinedContact", "PersonNameText", eBrokerElement));
            rst.put("brokerDefineTel", getTagValue("DefinedContact", "TelephoneCommunication", eBrokerElement));
            rst.put("brokerDefineUri", getTagValue("DefinedContact", "URICommunication", eBrokerElement));
        }


        //[STEP-4] 결제정보 - 결제방법별금액
        Node paymentNode = paymentMeansList.item(0);
        Element ePaymentElement = (Element) paymentNode;

        if(ePaymentElement != null){
            rst.put("payTypeCode", getTagValue("TypeCode", ePaymentElement));
            rst.put("payAmt", getTagValue("PaidAmount", ePaymentElement));
        }

        //[STEP-5] 결제정보 - Summary
        Node monetaryNode = monetarySumList.item(0);
        Element eMonetaryElement = (Element) monetaryNode;

        if(eMonetaryElement != null){
            rst.put("chargeTotalAmt", getTagValue("ChargeTotalAmount", eMonetaryElement));
            rst.put("taxTotalAmt", getTagValue("TaxTotalAmount", eMonetaryElement));
            rst.put("grandTotalAmt", getTagValue("GrandTotalAmount", eMonetaryElement));
        }

        return rst;
    }

    /**
     * 4. 상품 정보(TaxInvoice TradeLine Item) 데이터 파싱
     * @param taxInvTrdLineItemList
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> parseTaxInvoiceTradeLineItem(NodeList taxInvTrdLineItemList) throws Exception {

        List<Map<String, Object>> rst = new ArrayList<>();

        for (int i = 0; i < taxInvTrdLineItemList.getLength(); i++) {

            Map<String, Object> map = new HashMap<>();

            Node nNode = taxInvTrdLineItemList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                map.put("seq", getTagValue("SequenceNumeric", eElement));
                map.put("itemPurchExpDateTime", getTagValue("PurchaseExpiryDateTime", eElement));
                map.put("itemName", getTagValue("NameText", eElement));
                map.put("itemInform", getTagValue("InformationText", eElement));
                map.put("itemDesc", getTagValue("DescriptionText", eElement));
                map.put("itemChargeUnitQuan", getTagValue("ChargeableUnitQuantity", eElement));
                map.put("itemUnitAmt", getTagValue("UnitPrice", "UnitAmount", eElement));
                map.put("itemAmt", getTagValue("InvoiceAmount", eElement));
                map.put("itemCalcAmt", getTagValue("TotalTax", "CalculatedAmount", eElement));
            }

            rst.add(map);
        }

        return rst;
    }

    /**
     * 자식-자식 [3 Depth] 태그값 리턴 함수
     * @param sPTag
     * @param sCTag
     * @param eElement
     * @return
     */
    private static String getTagValue(String sPTag, String sCTag, String sCCTag, Element eElement) {

        String tagValue = "";

        Node nNode = eElement.getElementsByTagName(sPTag).item(0);

        if(nNode != null){
            NodeList pList = nNode.getChildNodes();

            for(int i = 0; i < pList.getLength(); i++){

                Node cNode = pList.item(i);

                if(cNode.getNodeName().equalsIgnoreCase(sCTag)){
                    NodeList ccList = cNode.getChildNodes();

                    for(int j = 0; j < ccList.getLength(); j++){
                        Node ccNode = ccList.item(j);
                        if(ccNode.getNodeName().equalsIgnoreCase(sCCTag)){
                            tagValue = ccNode.getChildNodes().item(0).getNodeValue().trim();
                        }
                    }
                }
            }
        }

        return tagValue;
    }

    /**
     * 자식 [2 Depth] 태그값 리턴 함수
     * @param sPTag
     * @param sCTag
     * @param eElement
     * @return
     */
    private static String getTagValue(String sPTag, String sCTag, Element eElement) {

        String tagValue = "";

        Node nNode = eElement.getElementsByTagName(sPTag).item(0);

        if(nNode != null){
            NodeList pList = nNode.getChildNodes();

            for(int i = 0; i < pList.getLength(); i++){

                Node cNode = pList.item(i);

                if(cNode.getNodeName().equalsIgnoreCase(sCTag)){
                    tagValue = cNode.getChildNodes().item(0).getNodeValue().trim();
                }
            }
        }

        return tagValue;
    }

    /**
     * 태그값 리턴 함수
     * @param sTag
     * @param eElement
     * @return
     */
    private static String getTagValue(String sTag, Element eElement) {

        String tagValue = "";

        Node nNode = eElement.getElementsByTagName(sTag).item(0);

        if(nNode != null){
            NodeList nlList = nNode.getChildNodes();

            tagValue = nlList.item(0).getNodeValue().trim();
        }

        return tagValue;
    }

    private String stringValueOf(Object object) {
        return object == null ? "" : String.valueOf(object);
    }
}
