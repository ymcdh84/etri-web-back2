package com.iljin.apiServer.template.system.code;

import com.iljin.apiServer.core.security.user.User;
import com.iljin.apiServer.core.util.Pair;
import com.iljin.apiServer.core.util.Util;
import com.iljin.apiServer.template.system.menu.UserMenuDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CodeServiceImpl implements CodeService {

	private final CodeHeaderRepository codeHeaderRepository;
	private final CodeDetailRepository codeDetailRepository;
	private final Util util;

	@Override
	public List<CodeDetail> getCodeDetailAll() {
		return codeDetailRepository.findAll();
	}

	@Override
	public List<Pair> getComboBox(CodeDto codeDto) {
		List<Pair> combo = new ArrayList<>();

		combo = codeDetailRepository.getComboByCodeDto(
				util.getLoginCompCd()
				,codeDto.getGroupCd()
				,codeDto.getRemark1()
				,codeDto.getRemark2()
				,codeDto.getRemark3())
				.stream()
				.map(s -> new Pair<>(
						String.valueOf(Optional.ofNullable(s[0]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
				))
				.collect(Collectors.toList());

		return combo;
	}

	@Override
	public List<Map> getByCodeDto(CodeDto codeDto) {
		if (StringUtils.isEmpty(codeDto.getRemark3())) {
			return codeDetailRepository.findByGroupCd(util.getLoginCompCd(), codeDto.getGroupCd());
			//return codeDetailRepository.findByGroupCd("101600", codeDto.getGroupCd());
		} else {
			return codeDetailRepository.findByGroupCdAndRemark3(util.getLoginCompCd(), codeDto.getGroupCd(),
					codeDto.getRemark3());
		}
	}

	@Override
	public List<Map> getCodeAll() {
		return codeDetailRepository.getCodeAll();
	}

	@Override
	public List<CodeDto> getGroupCodeList(CodeDto codeDto) {
		String compCd = codeDto.getCompCd();
		String groupCd = codeDto.getGroupCd();
		String useYn = codeDto.getUseYn();

		List<CodeDto> groupCodeList = new ArrayList<>();

		//1. get Group Code List by Search conditions (
		groupCodeList = codeHeaderRepository.getGroupCodeList(compCd, groupCd, useYn)
				.stream()
				.map(s -> new CodeDto(
						String.valueOf(Optional.ofNullable(s[0]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[4]).orElse(""))
				))
				.collect(Collectors.toList());

		return groupCodeList;
	}

	@Override
	public List<CodeDto> getGroupCodeDetailList(CodeDto codeDto) {
		String compCd = codeDto.getCompCd();
		String groupCd = codeDto.getGroupCd();

		List<CodeDto> groupCodeDetailList = new ArrayList<>();

		//1. get Group Code List by Search conditions (
		groupCodeDetailList = codeDetailRepository.getGroupCodeDetailList(compCd, groupCd)
				.stream()
				.map(s -> new CodeDto(
						String.valueOf(Optional.ofNullable(s[0]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[4]).orElse(""))
						,(Integer) s[5]
						,String.valueOf(Optional.ofNullable(s[6]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[7]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[8]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[9]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[10]).orElse(""))
						,String.valueOf(Optional.ofNullable(s[11]).orElse(""))
				))
				.collect(Collectors.toList());

		return groupCodeDetailList;
	}

	@Override
	public ResponseEntity<String> saveCodeLists(CodeHeaderDetails codeHeaderDetails) {
		User loginUser = util.getLoginUser();
		String loginId = loginUser.getLoginId();
		String compCd = loginUser.getCompCd();
		//String loginId = "admin";
		//String compCd = "101600";

		List<CodeDto> codeHeaders = codeHeaderDetails.getCodeHeader();
		List<CodeDto> codeDetails = codeHeaderDetails.getCodeDetail();

		/* 그룹코드 영역(헤더) 처리 */
		for (CodeDto header : codeHeaders) {
			String groupCd = header.getGroupCd();
			String codeCompCd = header.getCompCd();

			CodeHeaderKey codeHeaderKey = new CodeHeaderKey();
			codeHeaderKey.setGroupCd(groupCd);
			codeHeaderKey.setCompCd(codeCompCd);

			Optional<CodeHeader> codeHeader = codeHeaderRepository.findById(codeHeaderKey);

			if (codeHeader.isPresent()) {
				/* update */
				codeHeader.ifPresent(c -> {
					c.updateCodeHeader(header.getGroupNm(),
							header.useYn,
							header.groupDesc,
							loginId,
							LocalDateTime.now()
					);

					codeHeaderRepository.save(c);
				});
			} else {
				/* new Insert */
				CodeHeader c = new CodeHeader().builder()
						.groupCd(header.getGroupCd())
						.compCd(header.getCompCd())
						.groupNm(header.getGroupNm())
						.groupDesc(header.getGroupDesc())
						.useYn(header.getUseYn())
						.build();
				/*try {
					PropertyUtils.copyProperties(c, header);
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				c.setCodeHeader(loginId,
						LocalDateTime.now(),
						loginId,
						LocalDateTime.now());

				codeHeaderRepository.save(c);
			}
		}

		/*
		 * 상세코드 영역(상세) 처리 Desc. 해당 회사코드/그룹코드로 전체 삭제 후 새로 추가
		 */
		String detailGroupCd = codeDetails.get(0).groupCd;
		codeDetailRepository.deleteByCompCdAndGroupCd(compCd, detailGroupCd);
		for (CodeDto detail : codeDetails) {
			CodeDetail c = new CodeDetail().builder()
					.groupCd(detail.getGroupCd())
					.detailCd(detail.getDetailCd())
					.compCd(detail.getCompCd())
					.detailNm(detail.getDetailNm())
					.useYn(detail.getUseYn())
					.orderSeq(detail.getOrderSeq())
					.detailDesc(detail.getDetailDesc())
					.remark1(detail.getRemark1())
					.remark2(detail.getRemark2())
					.remark3(detail.getRemark3())
					.remark4(detail.getRemark4())
					.remark5(detail.getRemark5())
					.build();
			/*try {
				PropertyUtils.copyProperties(c, detail);
			} catch (Exception e) {
				e.printStackTrace();
			}*/

			c.setCodeDetail(loginId,
					LocalDateTime.now(),
					loginId,
					LocalDateTime.now());

			codeDetailRepository.save(c);
		}

		return new ResponseEntity<>("저장되었습니다.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteCode(String groupCd) {
		String compCd = util.getLoginCompCd();
		//String compCd = "101600";

		/* delete Detail codes */
		codeDetailRepository.deleteByCompCdAndGroupCd(compCd, groupCd);

		/* delete Header Code */
		CodeHeaderKey codeHeaderKey = new CodeHeaderKey();
		codeHeaderKey.setCompCd(compCd);
		codeHeaderKey.setGroupCd(groupCd);
		codeHeaderRepository.deleteById(codeHeaderKey);

		return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
	}

	@Override
	public Optional<CodeDetail> getGroupCodeDetail(CodeDto codeDto) {
		return codeDetailRepository.findByCompCdAndGroupCdAndUseYnAndDetailCd(codeDto.getCompCd(), codeDto.getGroupCd(), "Y", codeDto.getDetailCd());
	}

}
