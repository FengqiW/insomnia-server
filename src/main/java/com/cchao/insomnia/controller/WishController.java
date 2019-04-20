package com.cchao.insomnia.controller;

import com.cchao.insomnia.constant.enums.WishType;
import com.cchao.insomnia.resp.RespBean;
import com.cchao.insomnia.resp.RespListBean;
import com.cchao.insomnia.resp.fall.FallImageVo;
import com.cchao.insomnia.service.WishService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping(value = "/wish")
public class WishController {

    @Autowired
    WishService mWishService;

    /**
     * 图片的wish列表
     */
    @RequestMapping(value = "/getImageList")
    @RequiresAuthentication
    public RespListBean<FallImageVo> getFallIndex(@RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {

        Page<FallImageVo> pageObj = mWishService.getWishImageByPage(PageRequest.of(page, pageSize));
        return RespListBean.of(pageObj, pageObj.getContent(), page);
    }

    @RequestMapping(value = "/addwish")
    @RequiresAuthentication
    public RespBean postFallImage(@RequestParam("type") int type, @RequestParam("wish_id") long wishId) {
        // 1 ： image
        // 2 ： music
        WishType wishType = WishType.IMAGE;
        switch (type) {
            case 2:
                wishType = WishType.MUSIC;
                break;
        }
        mWishService.addWish(wishType, wishId);
        return RespBean.suc();
    }
}
