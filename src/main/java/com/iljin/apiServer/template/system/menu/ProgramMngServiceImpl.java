package com.iljin.apiServer.template.system.menu;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProgramMngServiceImpl implements ProgramMngService {

    private final MenuRepository menuRepository;
    private final MenuRepositoryCustom menuRepositoryCustom;

    @Override
    public List<MenuDto> getMenuList(MenuDto menuDto) {
        String menuNm = menuDto.getMenuNm();

        List<MenuDto> menuDtoList = new ArrayList<>();

        menuDtoList = menuRepository.getMenuListByMenuNm(menuNm)
                .stream()
                .map(s -> new MenuDto(
                        String.valueOf(Optional.ofNullable(s[0]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[4]).orElse(""))
                        ,(Integer) s[5]
                        ,String.valueOf(Optional.ofNullable(s[6]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[7]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[8]).orElse(""))
                ))
                .collect(Collectors.toList());

        return menuDtoList;
    }

    @Override
    public ResponseEntity<String> saveMenuList(List<MenuDto> list) {
        if (list.size() > 0) {
            for (MenuDto menuDto : list) {
                String compCd = menuDto.getCompCd();
                String menuNo = menuDto.getMenuNo();

                MenuKey menuKey = new MenuKey(compCd, menuNo);
                Optional<Menu> menu = menuRepository.findById(menuKey);

                if (menu.isPresent()) {
                    // Update
                    Menu isMenu = menu.get();
                    isMenu.updateMenu(
                            menuNo,
                            menuDto.getUpperMenuNo(),
                            menuDto.getMenuNm(),
                            menuDto.getProgramFileNm(),
                            menuDto.getMenuOrder(),
                            menuDto.getRelateImageNm(),
                            menuDto.getRelateImagePath(),
                            menuDto.getMenuDc());

                    menuRepository.save(isMenu);
                } else {
                    // insert
                    Menu newMenu = new Menu()
                            .builder()
                            .compCd(menuDto.getCompCd())
                            .menuNo(menuDto.getMenuNo())
                            .upperMenuNo(menuDto.getUpperMenuNo())
                            .menuNm(menuDto.getMenuNm())
                            .programFileNm(menuDto.getProgramFileNm())
                            .menuOrder(menuDto.getMenuOrder())
                            .relateImageNm(menuDto.getRelateImageNm())
                            .relateImagePath(menuDto.getRelateImagePath())
                            .menuDc(menuDto.getMenuDc())
                            .build();
                    /*Menu newMenu = new Menu();
                    try {
                        BeanUtils.copyProperties(newMenu, menuDto);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

                    menuRepository.save(newMenu);
                }
            }
        }

        return new ResponseEntity<>("저장되었습니다.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteMenu(String compCd, String menuNo) {
        MenuKey menuKey = new MenuKey(compCd, menuNo);

        menuRepository.deleteById(menuKey);

        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }
}
