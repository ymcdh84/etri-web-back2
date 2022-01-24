package com.iljin.apiServer.template.system.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CodeDetailRepository extends JpaRepository<CodeDetail, CodeDetailKey> {
	
	@Query(value=" select " +
			" 	detail_cd as 'key', " +
			" 	detail_nm as 'value' " +
			" from " +
			" 	tb_code_dt " +
			" where " +
			" 	comp_cd = :compCd " +
			" 	and group_cd = :groupCd " +
			" 	and remark1 = if(ifnull(:remark1,'')='', remark1, :remark1) ", nativeQuery = true)
	List<Map> getByGroupCdAndRemark1(@Param("compCd") String compCd, @Param("groupCd") String groupCd, @Param("remark1") String remark1);
	
	
	@Query(value=" select " +
			" 	detail_cd as detailCd , " +
			" 	detail_nm as detailNm, " +
			" 	remark1, " +
			" 	remark2, " +
			" 	remark3, " +
			" 	remark4, " +
			" 	remark5 " +
			" from " +
			" 	tb_code_dt " +
			" where " +
			" 	comp_cd = :compCd " +
			" 	and group_cd = :groupCd " +
			" 	and use_yn = 'Y' ", nativeQuery=true)
	List<Map> findByGroupCd(@Param("compCd") String compCd, @Param("groupCd") String groupCd);

	@Query(value=" select " +
			" 	detail_cd as detailCd , " +
			" 	detail_nm as detailNm, " +
			" 	remark1, " +
			" 	remark2, " +
			" 	remark3, " +
			" 	remark4, " +
			" 	remark5 " +
			" from " +
			" 	tb_code_dt " +
			" where " +
			" 	comp_cd = :compCd " +
			" 	and group_cd = :groupCd " +
			" 	and remark3 like concat('%',:remark3,'%') " +
			" 	and use_yn = 'Y' ", nativeQuery=true)
	List<Map> findByGroupCdAndRemark3(@Param("compCd") String compCd, @Param("groupCd") String groupCd, @Param("remark3") String remark3);

	@Query(value=" select " +
			" 	detail_cd as detailCd , " +
			" 	detail_nm as detailNm, " +
			" 	remark1, " +
			" 	remark2, " +
			" 	remark3, " +
			" 	remark4, " +
			" 	remark5 " +
			" from " +
			" 	tb_code_dt " +
			" where " +
			" 	comp_cd = :compCd " +
			" 	and group_cd = :groupCd " +
			" 	and remark5 like concat('%',:remark5,'%') " +
			" 	and use_yn = 'Y' ", nativeQuery=true)
	Optional<Map> findTopByGroupCdAndRemark5OrderByOrderSeqAsc(@Param("compCd") String compCd, @Param("groupCd") String groupCd, @Param("remark5") String remark5);
	
	@Query(value="select h.group_cd as groupCd , h.group_nm as groupNm , h.comp_cd as compCd , d.detail_cd as detailCd , d.detail_nm as detailNm, d.use_yn as useYn from tb_code_hd h join tb_code_dt d on d.GROUP_CD = h.GROUP_CD and d.COMP_CD = h.COMP_CD", nativeQuery=true)
	List<Map> getCodeAll();

	void deleteByCompCdAndGroupCd(String compCd, String detailGroupCd);

	Optional<CodeDetail> findByCompCdAndGroupCdAndUseYnAndDetailCd(String compCd, String groupCd, String useYn, String detailCd);

	@Query(value = "" +
			"SELECT cd.compCd" +
			"       ,cd.groupCd" +
			"       ,cd.detailCd" +
			"       ,cd.detailNm" +
			"       ,cd.useYn" +
			"       ,cd.orderSeq" +
			"       ,cd.remark1" +
			"       ,cd.remark2" +
			"       ,cd.remark3" +
			"       ,cd.remark4" +
			"       ,cd.remark5" +
			"       ,cd.detailDesc" +
			"  FROM CodeDetail cd" +
			" WHERE 1=1" +
			"   AND cd.compCd = :compCd" +
			"   AND cd.groupCd = :groupCd" +
			" ORDER BY cd.orderSeq ASC")
	List<Object[]> getGroupCodeDetailList(@Param("compCd") String compCd, @Param("groupCd") String groupCd);

	@Query(value = "" +
			"SELECT cd.detailCd" +
			"       ,cd.detailNm" +
			"  FROM CodeDetail cd" +
			" WHERE 1=1" +
			"   AND useYn = 'Y'" +
			"   AND (:compCd IS NULL OR cd.compCd = :compCd)" +
			"   AND (:groupCd IS NULL OR cd.groupCd = :groupCd)" +
			"   AND (:remark1 IS NULL OR cd.remark1 = :remark1)" +
			"   AND (:remark2 IS NULL OR cd.remark2 = :remark2)" +
			"   AND (:remark3 IS NULL OR cd.remark3 = :remark3)" +
			" ORDER BY cd.orderSeq")
	List<Object[]> getComboByCodeDto(@Param("compCd") String compCd, @Param("groupCd") String groupCd, @Param("remark1") String remark1, @Param("remark2") String remark2, @Param("remark3") String remark3);
}
