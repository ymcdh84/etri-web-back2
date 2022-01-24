package com.iljin.apiServer.template.bigdata.site;

import com.iljin.apiServer.core.util.Util;
import com.iljin.apiServer.template.bigdata.connect.BigdataConnect;
import com.iljin.apiServer.template.bigdata.connect.BigdataConnectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BigdataSiteServiceImpl implements BigdataSiteService {

	private final BigdataSiteRepository bigdataSiteRepository;
	private final BigdataConnectRepository bigdataConnectRepository;

	private final Util util;

//////////////////////////////////////////////
	@Override
	public List<BigdataSiteDto> getSiteList(BigdataSiteDto bigdatSiteDto) {
		String siteId = bigdatSiteDto.getSiteId();

		List<BigdataSiteDto> siteList = new ArrayList<>();

		//1. get Group Code List by Search conditions (
		siteList = bigdataSiteRepository.getSiteList(siteId)
				.stream()
				.map(s -> new BigdataSiteDto(
						String.valueOf(Optional.ofNullable(s[0]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
				))
				.collect(Collectors.toList());

		return siteList;
	}

	@Override
	public ResponseEntity<String> saveSite(List<BigdataSiteDto> siteList) {
		if(siteList.size() > 0) {
			for(BigdataSiteDto bigdataSiteDto : siteList) {
				String siteId = bigdataSiteDto.getSiteId();

				Optional<BigdataSite> site = bigdataSiteRepository.findById(siteId);

				if(site.isPresent()) {
					//update
					site.ifPresent(c -> {
						c.updateBigdataSite(
								bigdataSiteDto.getSiteNm(),
								bigdataSiteDto.getSiteDesc());

						bigdataSiteRepository.save(c);
					});
				} else {
					//insert
					BigdataSite c = new BigdataSite(bigdataSiteDto.getSiteId(),
							bigdataSiteDto.getSiteNm(),
							bigdataSiteDto.getSiteDesc()
					);

					bigdataSiteRepository.save(c);
				}
			}
		}

		return new ResponseEntity<>("저장 되었습니다.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteSite(String siteId) {

		//[STEP-1] 하위 테이블(TB_BIGDATA_CONNECT) 데이터 조회
		List<BigdataConnect> conncetList = bigdataConnectRepository.findBySiteId(siteId);

		if(conncetList.size() > 0){
			throw new RuntimeException("사이트 하위에 연결정보가 존재하여 삭제할 수 없습니다.");
		}

		//[STEP-2] 사이트(TB_BIGDATA_SITE) 데이터 삭제
		bigdataSiteRepository.deleteById(siteId);

		return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
	}
}
