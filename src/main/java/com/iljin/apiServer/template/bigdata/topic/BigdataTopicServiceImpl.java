package com.iljin.apiServer.template.bigdata.topic;

import com.iljin.apiServer.template.bigdata.spark.SparkService;
import com.iljin.apiServer.template.system.code.CodeDetail;
import com.iljin.apiServer.template.system.code.CodeDto;
import com.iljin.apiServer.template.system.code.CodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BigdataTopicServiceImpl implements BigdataTopicService {

	private final BigdataTopicRepository bigdataTopicRepository;
	private final CodeService codeService;

	@Autowired
	private SparkService sparkService;

	@Override
	public List<BigdataTopicDto> getTopicList(BigdataTopicDto bigdataTopicDto) {
		String siteId = bigdataTopicDto.getSiteId();
		String connectId = bigdataTopicDto.getConnectId();

		List<BigdataTopicDto> topicList = new ArrayList<>();

		//1. get Group Code List by Search conditions (
		topicList = bigdataTopicRepository.getTopicList(siteId, connectId)
				.stream()
				.map(s -> new BigdataTopicDto(
						String.valueOf(Optional.ofNullable(s[0]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[4]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[5]).orElse(""))
				))
				.collect(Collectors.toList());

		return topicList;
	}

	@Override
	public ResponseEntity<String> saveTopic(List<BigdataTopicDto> topicList) {
		if(topicList.size() > 0) {
			for(BigdataTopicDto bigdataTopicDto : topicList) {
				String siteId = bigdataTopicDto.getSiteId();
				String connectId = bigdataTopicDto.getConnectId();
				String topicId = bigdataTopicDto.getTopicId();

				BigdataTopicKey bigdataTopictKey = new BigdataTopicKey(siteId, connectId, topicId);

				Optional<BigdataTopic> topic = bigdataTopicRepository.findById(bigdataTopictKey);
				if(topic.isPresent()) {
					//update
					topic.ifPresent(c -> {
						c.updateBigdataTopic(
								bigdataTopicDto.getTopicId(),
								bigdataTopicDto.getTopicDesc(),
								bigdataTopicDto.getSubmissionId(),
								bigdataTopicDto.getSubmissionStatCd());

						bigdataTopicRepository.save(c);
					});
				} else {
					//insert
					BigdataTopic c = new BigdataTopic(bigdataTopicDto.getSiteId(),
							bigdataTopicDto.getConnectId(),
							bigdataTopicDto.getTopicId(),
							bigdataTopicDto.getTopicDesc(),
							bigdataTopicDto.getSubmissionId(),
							"10"
					);

					bigdataTopicRepository.save(c);
				}
			}
		}

		return new ResponseEntity<>("저장 되었습니다.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteTopic(String siteId, String connectId, String topicId) {
		BigdataTopicKey bigdataTopicKey = new BigdataTopicKey(siteId, connectId, topicId);

		bigdataTopicRepository.deleteById(bigdataTopicKey);

		return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> doOperateTopic(BigdataTopicDto topicDto) {

		String siteId = topicDto.getSiteId();
		String connectId = topicDto.getConnectId();
		String topicId = topicDto.getTopicId();
		String submissionStatCd = topicDto.getSubmissionStatCd();

		BigdataTopicKey bigdataTopictKey = new BigdataTopicKey(siteId, connectId, topicId);

		Optional<BigdataTopic> topic = bigdataTopicRepository.findById(bigdataTopictKey);

		if(topic.isPresent()) {
			//update
			topic.ifPresent(c -> {
				String dbSbmsStatCd = c.getSubmissionStatCd();

				//[STEP-1]화면단에서 넘긴 상태 값과 DB 상에 저장되어 있는 상태 값 일치 확인
				if(!dbSbmsStatCd.equals(submissionStatCd)){
					throw new RuntimeException("상태값이 일치 하지 않아 실행할 수 없습니다.");
				}

				//[STEP-2]토픽 실행 Spark Application RestApi 호출(2021.08.13) -> 추후 개발 예정
//				if(!sparkService.insertTestMongoDb()){
//					throw new RuntimeException("카프카 토픽 실행 중 에러가 발생하였습니다.");
//				}

				//[STEP-3]RestApi 실행 결과 오류 없음 확인 후 상태 값 변경
				CodeDto codeDto = new CodeDto();
				codeDto.setCompCd("101600");
				codeDto.setGroupCd("TOPIC_STAT_CD");
				codeDto.setDetailCd(dbSbmsStatCd);

				//카프카 토픽 상태 공통 코드(TOPIC_STAT_CD)의 REMARK1로 상태 값 업데이트
				Optional<CodeDetail> getTopicStat = codeService.getGroupCodeDetail(codeDto);

				if(!getTopicStat.isPresent()) {
					throw new RuntimeException("기준코드에 토픽상태 데이터가 없습니다. (GROUP_CD : 'TOPIC_STAT_CD', DETAIL_CD : " + codeDto.getDetailCd() + ")");
				}

				c.updateBigdataTopicStatCd(getTopicStat.get().getRemark1());

				bigdataTopicRepository.save(c);
			});
		}

		return null;
	}
}
