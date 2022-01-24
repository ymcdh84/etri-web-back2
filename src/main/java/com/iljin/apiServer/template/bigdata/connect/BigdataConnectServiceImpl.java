package com.iljin.apiServer.template.bigdata.connect;

import com.iljin.apiServer.core.util.Util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BigdataConnectServiceImpl implements BigdataConnectService {
	@PersistenceContext
	EntityManager entityManager;

	private final BigdataConnectRepository bigdataConnectRepository;

	private final Util util;

	@Override
	public List<BigdataConnectDto> getConnectList(BigdataConnectDto bigdataConnectDto) {
		String siteId = bigdataConnectDto.getSiteId();
		String connectId = bigdataConnectDto.getConnectId();
		String connectTypeCd = bigdataConnectDto.getConnectTypeCd();

		List<BigdataConnectDto> connectList = new ArrayList<>();

		//1. get Group Code List by Search conditions (
		connectList = bigdataConnectRepository.getConnectList(siteId, connectId, connectTypeCd)
				.stream()
				.map(s -> new BigdataConnectDto(
						String.valueOf(Optional.ofNullable(s[0]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[4]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[5]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[6]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[7]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[8]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[9]).orElse(""))
						,(LocalDateTime) s[10]
						,(LocalDateTime) s[11]
				))
				.collect(Collectors.toList());

		return connectList;
	}

	@Override
	public ResponseEntity<String> saveConnect(List<BigdataConnectDto> connectList) {
		if(connectList.size() > 0) {
			for(BigdataConnectDto bigdataConnectDto : connectList) {
				String siteId = bigdataConnectDto.getSiteId();
				String connectId = bigdataConnectDto.getConnectId();
				BigdataConnectKey bigdataConnectKey = new BigdataConnectKey(siteId, connectId);

				Optional<BigdataConnect> connect = bigdataConnectRepository.findById(bigdataConnectKey);
				if(connect.isPresent()) {
					//update
					connect.ifPresent(c -> {
						c.updateBigdataConnect(
								bigdataConnectDto.getConnectNm(),
								bigdataConnectDto.getConnectTypeCd(),
								bigdataConnectDto.getConnectStatCd(),
								bigdataConnectDto.getProgramNm(),
								bigdataConnectDto.getIp(),
								bigdataConnectDto.getPort(),
								bigdataConnectDto.getLoginId(),
								bigdataConnectDto.getPassword());

						bigdataConnectRepository.save(c);
					});
				} else {
					//insert
					BigdataConnect c = new BigdataConnect(bigdataConnectDto.getSiteId(),
							bigdataConnectDto.getConnectId(),
							bigdataConnectDto.getConnectNm(),
							bigdataConnectDto.getConnectTypeCd(),
							bigdataConnectDto.getConnectStatCd(),
							bigdataConnectDto.getProgramNm(),
							bigdataConnectDto.getIp(),
							bigdataConnectDto.getPort(),
							bigdataConnectDto.getLoginId(),
							bigdataConnectDto.getPassword()
					);

					bigdataConnectRepository.save(c);
				}
			}
		}

		return new ResponseEntity<>("저장 되었습니다.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteConnect(String siteId, String connectId) {
		BigdataConnectKey bigdataConnectKey = new BigdataConnectKey(siteId, connectId);

		bigdataConnectRepository.deleteById(bigdataConnectKey);

		return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
	}

	@Override
	public List<BigdataConnectDto> getSiteConnectList(BigdataConnectDto bigdataConnectDto) {
		String siteId = bigdataConnectDto.getSiteId();
		String connectId = bigdataConnectDto.getConnectId();
		String connectTypeCd = bigdataConnectDto.getConnectTypeCd();

		List<BigdataConnectDto> connectList = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT D1.SITE_ID \n" +
				  "      , D1.SITE_NM \n" +
				  "      , D2.CONNECT_ID \n" +
				  "      , D2.CONNECT_NM \n" +
				  "      , D2.PROGRAM_NM \n" +
				  "   FROM TB_BIGDATA_SITE D1\n" +
				  "        INNER JOIN TB_BIGDATA_CONNECT D2 on D1.SITE_ID  = D2.SITE_ID\n" +
				  "  WHERE 1 = 1");

		if (!StringUtils.isEmpty(siteId)) {// 사이트ID
			sb.append("   AND (D1.SITE_ID LIKE CONCAT('%', ifnull(:siteId,''), '%')" +
					  "        OR D1.SITE_NM LIKE CONCAT('%', ifnull(:siteId,''), '%'))");
		}
		if (!StringUtils.isEmpty(connectId)) {// 연결ID
			sb.append("   AND (D2.CONNECT_ID LIKE CONCAT('%', ifnull(:connectId,''), '%')" +
					  "        OR D2.CONNECT_NM LIKE CONCAT('%', ifnull(:connectId,''), '%'))");
		}
		if (!StringUtils.isEmpty(connectTypeCd)) {// 연결방법
			sb.append(" AND D2.CONNECT_TYPE_CD = :connectTypeCd");
		}
		sb.append(" ORDER BY D1.SITE_ID, D2.CONNECT_ID");

		Query query = entityManager.createNativeQuery(sb.toString());

		if (!StringUtils.isEmpty(siteId)) {// 사이트ID
			query.setParameter("siteId", siteId);
		}
		if (!StringUtils.isEmpty(connectId)) {// 연결ID
			query.setParameter("connectId", connectId);
		}
		if (!StringUtils.isEmpty(connectTypeCd)) {// 연결방법
			query.setParameter("connectTypeCd", connectTypeCd);
		}

		return new JpaResultMapper().list(query, BigdataConnectDto.class);
	}
}
