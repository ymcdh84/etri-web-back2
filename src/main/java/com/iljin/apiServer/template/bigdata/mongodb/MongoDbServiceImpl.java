package com.iljin.apiServer.template.bigdata.mongodb;

import com.iljin.apiServer.template.bigdata.asset.BigdataAssetDto;
import com.iljin.apiServer.template.bigdata.tag.BigdataAssetTagDto;
import com.iljin.apiServer.template.bigdata.tag.BigdataAssetTagService;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MongoDbServiceImpl implements MongoDbService {

	@Autowired
	private MongoTemplate mongoTemplate;

	private final BigdataAssetTagService bigdataAssetTagService;

	public List<DocumentDto> getMongoDbCollections() {

		List<DocumentDto> list =  new ArrayList<>();

		// 몽고DB Collection 리스트 조회
		MongoDatabase db = mongoTemplate.getDb();

		for(String name : db.listCollectionNames()){
			DocumentDto doc = new DocumentDto();
			doc.setKey(name);

			list.add(doc);
		}

		list = list.stream().sorted(Comparator.comparing(DocumentDto::getKey)).collect(Collectors.toList());

		return list;
	}

	public List<DocumentDto> getMongoDbCollectionInfo(BigdataAssetDto bigdataAssetDto) {

		List<DocumentDto> list =  new ArrayList<>();

		String assetId = bigdataAssetDto.getAssetId();

		//[STEP-1] 성장로 등록된 태그 조회
		BigdataAssetTagDto assetTagDto = new BigdataAssetTagDto();

		assetTagDto.setSiteId(bigdataAssetDto.getSiteId());
		assetTagDto.setAssetId(assetId);

		List<BigdataAssetTagDto> listTag = bigdataAssetTagService.getAssetTag(assetTagDto);

		//[STEP-2] MongoDB 성장로 컬렉션 필드 -> 리스트 형태로 변환
		MongoCollection<Document> documents =  mongoTemplate.getCollection(assetId);

		Document doc = documents.find().first();

		if(doc != null){
			Set<String> keySet = doc.keySet();

			for (String key : keySet) {

				DocumentDto asset = new DocumentDto();

				asset.setKey(key);

				Object val = doc.get(key);

				// 이미 태그로 등록된 필드 제외
				Optional exist = listTag.stream().filter(x -> x.getMappingKey().equals(key)).findAny();

				if(!exist.isPresent()){
					if(val instanceof String){
						asset.setDataType("String");
					}else if(val instanceof Integer){
						asset.setDataType("Int");
					}else if(val instanceof Double){
						asset.setDataType("Double");
					}else if(val instanceof Date){
						asset.setDataType("Date");
					}else if(val instanceof java.math.BigInteger){
						asset.setDataType("Double");
					}

					asset.setValue(val.toString());

					list.add(asset);
				}

			}
		}

		return list;
	}

	/**
	 * 성장로(컬렉션) Lot번호별 데이터 건수 조회
	 * @return
	 */
	public List<AssetLotNumDto> getColLotDataCnt() {

		List<AssetLotNumDto> listLotInfos = new ArrayList<>();

		// 몽고DB Collection 리스트 조회
		MongoDatabase db = mongoTemplate.getDb();

		MongoIterable<String> colNames =  db.listCollectionNames();

		for(String name : colNames){

			MongoCollection<Document> documents =  mongoTemplate.getCollection(name);

			//Lot번호 Group By
			AggregateIterable<Document> aggregateResult = documents.aggregate(Arrays.asList(
					new Document("$group", new Document("_id", "$5").append("count", new Document("$sum", 1)))
				  , new Document("$sort", new Document("_id", 1))
			));

			for (Document dbObject : aggregateResult) {
				AssetLotNumDto lotInfo = new AssetLotNumDto();

				lotInfo.setAssetId(name);
				lotInfo.setLotNo(dbObject.get("_id").toString());
				lotInfo.setDataCnt(Integer.parseInt(dbObject.get("count").toString()));

				listLotInfos.add(lotInfo);
			}
		}

		return listLotInfos;
	}
}
