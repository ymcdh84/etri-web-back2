package com.iljin.apiServer.template.system.code;

import com.iljin.apiServer.core.util.Pair;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

//@SqlResultSetMapping(name="PairResult", classes = {
//	@ConstructorResult(targetClass = Pair.class, 
//	columns = {@ColumnResult(name="key"), @ColumnResult(name="value")})
//})
@Repository
public class CodeRepositoryCustomImpl implements CodeRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<CodeDto> getGroupCodeDetailList(CodeDto codeDto) {
		String compCd = codeDto.getCompCd();
		String groupCd = codeDto.getGroupCd();

		StringBuilder sb = new StringBuilder();
		sb.append("" +
				"SELECT DT.COMP_CD" +
				"       ,DT.GROUP_CD" +
				"       ,DT.DETAIL_CD" +
				"       ,DT.DETAIL_NM" +
				"       ,DT.USE_YN" +
				"       ,DT.ORDER_SEQ" +
				"       ,DT.REMARK1" +
				"       ,DT.REMARK2" +
				"       ,DT.REMARK3" +
				"       ,DT.REMARK4" +
				"       ,DT.REMARK5" +
				"       ,DT.DETAIL_DESC" +
				"  FROM TB_CODE_DT DT" +
				" WHERE 1=1" +
				"   AND DT.COMP_CD = :compCd" +
				"   AND DT.GROUP_CD = :groupCd" +
				" ORDER BY DT.ORDER_SEQ ASC");

		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("compCd", compCd);
		query.setParameter("groupCd", groupCd);

		return new JpaResultMapper().list(query, CodeDto.class);
	}

}
