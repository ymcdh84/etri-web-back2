package com.iljin.apiServer.template.system.code;

import com.iljin.apiServer.core.util.Pair;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
public interface CodeService {

	List<CodeDetail> getCodeDetailAll();
	List<Pair> getComboBox(CodeDto codeDto);
	List<Map> getByCodeDto(CodeDto codeDto);
	List<Map> getCodeAll();

	List<CodeDto> getGroupCodeList(CodeDto codeDto);

	List<CodeDto> getGroupCodeDetailList(CodeDto codeDto);

	@Modifying
	ResponseEntity<String> saveCodeLists(CodeHeaderDetails codeHeaderDetails);

	@Modifying
	ResponseEntity<String> deleteCode(String groupCd);

	Optional<CodeDetail> getGroupCodeDetail(CodeDto codeDto);
}
