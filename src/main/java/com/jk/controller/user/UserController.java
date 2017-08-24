package com.jk.controller.user;

import com.alibaba.druid.util.StringUtils;
import com.jk.entity.user.User;
import com.jk.service.user.UserService;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/18.
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 查询方法
     * @return
     */
    @RequestMapping("selectUserList")
    @ResponseBody
    ModelAndView selectUserList(){
        List<User> list =userService.selectUserList();
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", list);
        mv.setViewName("../../index");
        return mv;

    }

    @RequestMapping("selectUserListTemp")
    String selectUserListTemp(Model model){
        List<User> list =userService.selectUserList();
        Map<String, Object> map =new HashMap<String,Object>();
        map.put("list",list);
        model.addAttribute("map",map);

        return "user/userList";
    }

    /**
     * 新增方法
     * @param user
     * @return
     */
    @RequestMapping("addUser")
    String  addUser(User user){

        userService.addBook(user);
        return "redirect:selectUserListTemp.jhtml";
    }

    /**
     * 去新增页面
     * @return
     */
    @RequestMapping("toAddPage")
    String toAddPage(){
        return "user/addUser";
    }

    /**
     * 删除方法
     * @param user
     * @return
     */
    @RequestMapping("deleteUser")
    String deleteUser(User user){
        userService.deleteUser(user);
        return "redirect:selectUserListTemp.jhtml";
    }
}
